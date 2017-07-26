package com.cis.powermap.business;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cis.powermap.entities.Contact;

@Service
public class ContactBusiness implements IContactBusiness {

	public Contact getContact(String partyNumber) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors()
				.add(new BasicAuthorizationInterceptor("bougsid.ayoub@accenture.com", "Khab@1960"));
		String contactString = restTemplate
				// .exchange("https://cctn-dev1.crm.em3.oraclecloud.com/crmCommonApi/resources/11.12.1.0/contacts",
				.exchange("https://cctn-dev1.crm.em3.oraclecloud.com:443/crmCommonApi/resources/11.12.1.0/contacts/"
						+ partyNumber, HttpMethod.GET, null, String.class)
				.getBody();

		JSONObject contactJson = new JSONObject(contactString);

		String partyId = contactJson.getString("PartyNumber");
		String contactName = contactJson.getString("ContactName");
		String jobTitle = contactJson.getString("JobTitle");
		String roleCd ="";
		String formattedPhoneNumber = "";
		String emailAddress = "";
		try {
		//	roleCd = contactJson.getString("RoleCd");
			emailAddress = contactJson.getString("EmailAddress");
			formattedPhoneNumber = contactJson.getString("FormattedWorkPhoneNumber");
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
		return contact;
	}

	@Override
	public Iterable<Contact> getAll() {
		return null;
	}

	@Override
	public Contact findOne(Long id) {
		return null;
	}

	@Override
	public Contact save(Contact object) {
		return null;
	}

	@Override
	public Contact update(Contact object) {
		return null;
	}

	@Override
	public Iterable<Contact> save(Iterable<Contact> objects) {
		return null;
	}

	@Override
	public Contact delete(Contact object) {
		return null;
	}

}
