package com.cis.powermap.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cis.powermap.entities.Contact;
import com.cis.powermap.entities.Node;
import com.cis.powermap.entities.NodeFunction;
import com.cis.powermap.entities.NodeType;
import com.cis.powermap.entities.PowerMap;
import com.cis.powermap.entities.Relation;
import com.cis.powermap.entities.Resource;
import com.cis.powermap.repositories.PowerMapRepository;

@Service
public class PowerMapBusiness implements IPowerMapBusiness {

	@Autowired
	private PowerMapRepository repository;

	/**
	 * Get All contacts of an opportunity from OSC
	 * 
	 * @Param optyNumber optyNumber of the opportunity
	 * 
	 * @Return Opportunity Contacts List
	 * 
	 * @see com.cis.powermap.business.IPowerMapBusiness#getContactsOfOpportunity(java.lang.
	 *      Long)
	 */
	@Override
	public List<Contact> getContactsOfOpportunity(String optyNumber) {

		List<Contact> contacts = new ArrayList<Contact>();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors()
				.add(new BasicAuthorizationInterceptor("bougsid.ayoub@accenture.com", "Khab@1960"));
		String contactsString = restTemplate
				.exchange("https://cctn-dev1.crm.em3.oraclecloud.com:443/salesApi/resources/11.12.1.0/opportunities/"
						+ optyNumber + "/child/OpportunityContact", HttpMethod.GET, null, String.class)
				.getBody();

		JSONObject contactsJson = new JSONObject(contactsString);
		JSONArray contactsJSONArray = contactsJson.getJSONArray("items");

		for (int jsonArrayIndex = 0; jsonArrayIndex < contactsJSONArray.length(); jsonArrayIndex++) {
			String partyId = contactsJSONArray.getJSONObject(jsonArrayIndex).getString("ContactPartyNumber");
			String contactName = contactsJSONArray.getJSONObject(jsonArrayIndex).getString("PartyName");
			String jobTitle = "";
			String roleCd = "";
			String emailAddress = "";
			String formattedPhoneNumber = "";
			try {
				roleCd = contactsJSONArray.getJSONObject(jsonArrayIndex).getString("RoleCd");
				jobTitle = contactsJSONArray.getJSONObject(jsonArrayIndex).getString("PersonCentricJobTitle");
				emailAddress = contactsJSONArray.getJSONObject(jsonArrayIndex).getString("EmailAddress");
				formattedPhoneNumber = contactsJSONArray.getJSONObject(jsonArrayIndex)
						.getString("FormattedPhoneNumber");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Contact contact = new Contact();
			contact.setPartyId(partyId);
			contact.setContactName(contactName);
			contact.setFormattedPhoneNumber(formattedPhoneNumber);
			contact.setEmailAddress(emailAddress);
			contact.setJobTitle(jobTitle);
			contact.setRoleCd(roleCd);
			contacts.add(contact);
		}
		return contacts;

	}

	/**
	 * Get All resources of an opportunity from OSC
	 * 
	 * @Param optyNumber optyNumber of the opportunity
	 * 
	 * @Return Opportunity Resources List
	 * 
	 * @see com.cis.powermap.business.IPowerMapBusiness#getTeamOfOpportunity(java.lang.
	 *      Long)
	 */
	@Override
	public List<Resource> getTeamOfOpportunity(String optyNumber) {
		List<Resource> resources = new ArrayList<Resource>();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors()
				.add(new BasicAuthorizationInterceptor("bougsid.ayoub@accenture.com", "Khab@1960"));
		String resourcesString = restTemplate
				.exchange("https://cctn-dev1.crm.em3.oraclecloud.com:443/salesApi/resources/11.12.1.0/opportunities/"
						+ optyNumber + "/child/OpportunityResource", HttpMethod.GET, null, String.class)
				.getBody();

		JSONObject resourcesJson = new JSONObject(resourcesString);
		JSONArray resourcesJSONArray = resourcesJson.getJSONArray("items");

		for (int jsonArrayIndex = 0; jsonArrayIndex < resourcesJSONArray.length(); jsonArrayIndex++) {
			String resourceId = resourcesJSONArray.getJSONObject(jsonArrayIndex).getString("ResourcePartyNumber");
			String partyName = resourcesJSONArray.getJSONObject(jsonArrayIndex).getString("PartyName");
			String EmailAddress = resourcesJSONArray.getJSONObject(jsonArrayIndex).getString("EmailAddress");
			String formattedPhoneNumber = "";
			try {
				formattedPhoneNumber = resourcesJSONArray.getJSONObject(jsonArrayIndex)
						.getString("FormattedPhoneNumber");
			} catch (Exception ex) {
			}
			resources.add(new Resource(resourceId, partyName, EmailAddress, formattedPhoneNumber));
		}
		return resources;
	}

	/**
	 * Get the PowerMap of the given opportunity
	 * 
	 * @Param optyNumber optyNumber of the opportunity
	 * 
	 * @Return PowerMap
	 * 
	 * @see com.cis.powermap.business.IPowerMapBusiness#getOpportunityPowerMap(java.
	 *      lang.Long)
	 */
	@Override
	public PowerMap getOpportunityPowerMap(String optyNumber) {
		List<Contact> contacts = this.getContactsOfOpportunity(optyNumber);
		List<Resource> resources = this.getTeamOfOpportunity(optyNumber);

		Map<String, Node> contactsNodes = this.contactToNodes(contacts);
		Map<String, Node> resourcesNodes = this.resourcesToNodes(resources);

		List<Relation> relations = new ArrayList<Relation>();
		for (int contactPosition = 0; contactPosition < contacts.size(); contactPosition++) {

			Contact contact = contacts.get(contactPosition);
			List<Resource> contactTeam = this.getContactTeam(contact);
			
			this.buildRelationsAndAddContactTeamToNodes(contact, relations, contactTeam, resourcesNodes);
			
		}
		return this.buildPowerMap(contactsNodes, resourcesNodes, relations);
	}

	/**
	 * Build Opportunity PowerMap using contacts, resources and relations
	 * 
	 * @Parm contactsNodes Contacts List nodes
	 * 
	 * @Parm resourcesNodes Resources List nodes
	 * 
	 * @Parm relations Relations List
	 * 
	 * @Return PowerMap
	 */
	private PowerMap buildPowerMap(Map<String, Node> contactsNodes, Map<String, Node> resourcesNodes,
			List<Relation> relations) {
		PowerMap powerMap = new PowerMap();
		powerMap.setId(0L);
		powerMap.setName("Power Map");
		Map<String, Node> nodes = new HashMap<String, Node>();
		nodes.putAll(contactsNodes);
		nodes.putAll(resourcesNodes);
		powerMap.setNodes(new ArrayList<Node>(nodes.values()));
		powerMap.setRelations(relations);
		return powerMap;
	}

	/**
	 * Get Contact Relations (Other Contacts) and Add them to Contacts List if
	 * not exist
	 * 
	 * @Param contact Contact
	 * 
	 * @Param relations Contact Relations
	 * 
	 * @Param contactRelations Contact Relations (Other Contacts)
	 * 
	 * @Param contactsNodes Nodes List of contacts
	 */
	private void buildRelationsAndAddContactRelationsToNodes(Contact contact, List<Relation> relations,
			List<Contact> contactRelations, Map<String, Node> contactsNodes) {

		for (Contact contactRelation : contactRelations) {
			if (contactsNodes.get(contactRelation.getPartyId()) == null) {
				Node node = new Node();
				node.setType(NodeType.CONTACT);
				node.setId(contactRelation.getPartyId());
				node.setLabel(contactRelation.getContactName());
				node.setPhone(contactRelation.getFormattedPhoneNumber());
				node.setJobTitle(contactRelation.getJobTitle());
				node.setFunction(NodeFunction.valueOf(contact.getRoleCd()));
				contactsNodes.put(contactRelation.getPartyId(), node);
			}
			Relation relation = new Relation(contact.getPartyId(), contactRelation.getPartyId(), "");
			relations.add(relation);
		}
	}

	/**
	 * Get Contact Team and Add them to Resources List if not exist
	 * 
	 * @Param contact Contact
	 * 
	 * @Param relations Contact Relations
	 * 
	 * @Param contactTeam Contact Team
	 * 
	 * @Param resourcesNodes Nodes List of resources
	 */
	private void buildRelationsAndAddContactTeamToNodes(Contact contact, List<Relation> relations,
			List<Resource> contactTeam, Map<String, Node> resourcesNodes) {

		for (Resource resource : contactTeam) {
			if (resourcesNodes.get(resource.getResourceId()) == null) {
				Node node = new Node();
				node.setType(NodeType.EMPLOYE);
				node.setId(resource.getResourceId());
				node.setLabel(resource.getName());
				node.setPhone(resource.getFormattedPhoneNumber());
				resourcesNodes.put(resource.getResourceId(), node);
			}
			Relation relation = new Relation(contact.getPartyId(), resource.getResourceId(), "");
			relations.add(relation);
		}
	}

	/**
	 * Convert Contacts List to Map of PowerMap nodes
	 * 
	 * @Param contacts Contacts List
	 * 
	 * @Return Node Map
	 */
	private Map<String, Node> contactToNodes(List<Contact> contacts) {
		Map<String, Node> nodes = new HashMap<String, Node>();
		for (Contact contact : contacts) {
			Node node = new Node();
			node.setType(NodeType.CONTACT);
			node.setId(contact.getPartyId());
			node.setLabel(contact.getContactName());
			node.setPhone(contact.getFormattedPhoneNumber());
			node.setEmail(contact.getEmailAddress());
			node.setJobTitle(contact.getJobTitle());
			node.setFunction(NodeFunction.valueOf(contact.getRoleCd()));
			// Node node = new Node(contact.getPartyId(),
			// contact.getContactName(), contact.getFormattedPhoneNumber(),
			// NodeType.CONTACT, NodeFunction.valueOf(contact.getRoleCd()));
			nodes.put(contact.getPartyId(), node);
		}
		return nodes;
	}

	/**
	 * Convert Resources List to Map of PowerMap nodes
	 * 
	 * @Param resources Resources List
	 * 
	 * @Return Node Map
	 */
	private Map<String, Node> resourcesToNodes(List<Resource> resources) {
		Map<String, Node> nodes = new HashMap<String, Node>();
		for (Resource resource : resources) {
			Node node = new Node();
			node.setType(NodeType.EMPLOYE);
			node.setId(resource.getResourceId());
			node.setLabel(resource.getName());
			node.setPhone(resource.getFormattedPhoneNumber());
			node.setEmail(resource.getEmailAddress());
			nodes.put(resource.getResourceId(), node);
		}
		return nodes;
	}

	/**
	 * Get contact relations (other contacts) from OSC
	 * 
	 * @Param contact Contact
	 * 
	 * @Return Relation List (other contacts)
	 * 
	 */
	private List<Contact> getContactRelations(Contact contact) {
		List<Contact> contacts = new ArrayList<Contact>();
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor("bougsid.ayoub@accenture.com", "Khab@1960"));
			String contactsString = restTemplate
					.exchange("https://cctn-dev1.crm.em3.oraclecloud.com:443/crmCommonApi/resources/11.12.1.0/contacts/"
							+ contact.getPartyId() + "/child/Relationship", HttpMethod.GET, null, String.class)
					.getBody();

			JSONObject contactsJson = new JSONObject(contactsString);
			JSONArray contactsJSONArray = contactsJson.getJSONArray("items");

			for (int jsonArrayIndex = 0; jsonArrayIndex < contactsJSONArray.length(); jsonArrayIndex++) {
				String partyId = contactsJSONArray.getJSONObject(jsonArrayIndex).getString("ObjectPartyNumber");
				String contactName = contactsJSONArray.getJSONObject(jsonArrayIndex).getString("ObjectPartyName");
				String emailAddress = "";
				String formattedPhoneNumber = "";
				try {
					emailAddress = contactsJSONArray.getJSONObject(jsonArrayIndex).getString("EmailAddress");
					formattedPhoneNumber = contactsJSONArray.getJSONObject(jsonArrayIndex)
							.getString("FormattedPhoneNumber");
				} catch (Exception ex) {
				}
				contact.setPartyId(partyId);
				contact.setContactName(contactName);
				contact.setFormattedPhoneNumber(formattedPhoneNumber);
				contact.setEmailAddress(emailAddress);
				// contact.setRoleCd(roleCd);
				contacts.add(contact);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return contacts;
	}

	/**
	 * Get contact resources from OSC
	 * 
	 * @Param contact Contact
	 * 
	 * @Return Resources List
	 * 
	 */
	private List<Resource> getContactTeam(Contact contact) {
		List<Resource> resources = new ArrayList<Resource>();
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor("bougsid.ayoub@accenture.com", "Khab@1960"));
			String resourcesString = restTemplate
					.exchange(
							"https://cctn-dev1.crm.em3.oraclecloud.com:443/crmCommonApi/resources/11.12.1.0/contacts/"
									+ contact.getPartyId() + "/child/SalesTeamMember",
							HttpMethod.GET, null, String.class)
					.getBody();

			JSONObject resourcesJson = new JSONObject(resourcesString);
			JSONArray resourcesJSONArray = resourcesJson.getJSONArray("items");

			for (int jsonArrayIndex = 0; jsonArrayIndex < resourcesJSONArray.length(); jsonArrayIndex++) {
				String resourceId = resourcesJSONArray.getJSONObject(jsonArrayIndex).getString("ResourcePartyNumber");
				String partyName = resourcesJSONArray.getJSONObject(jsonArrayIndex).getString("ResourceName");
				String emailAddress = resourcesJSONArray.getJSONObject(jsonArrayIndex)
						.getString("ResourceEmailAddress");
				String formattedPhoneNumber = "";
				try {
					formattedPhoneNumber = resourcesJSONArray.getJSONObject(jsonArrayIndex)
							.getString("ResourcePhoneNumber");
				} catch (Exception ex) {
				}
				resources.add(new Resource(resourceId, partyName, emailAddress, formattedPhoneNumber));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resources;
	}

	@Override
	public Iterable<PowerMap> getAll() {
		return this.repository.findAll();
	}

	@Override
	public PowerMap findOne(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public PowerMap save(PowerMap powerMap) {
		return this.repository.save(powerMap);
	}

	@Override
	public Iterable<PowerMap> save(Iterable<PowerMap> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PowerMap delete(PowerMap object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PowerMap update(PowerMap powerMap) {
		return this.repository.save(powerMap);
	}

}
