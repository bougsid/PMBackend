package com.cis.powermap.business;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cis.powermap.entities.Resource;
@Service
public class ResourceBusiness implements IResourceBusiness {

	@Override
	public Resource getResource(String partyNumber) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors()
				.add(new BasicAuthorizationInterceptor("bougsid.ayoub@accenture.com", "Khab@1960"));
		String contactString = restTemplate
				// .exchange("https://cctn-dev1.crm.em3.oraclecloud.com/crmCommonApi/resources/11.12.1.0/contacts",
				.exchange("https://cctn-dev1.crm.em3.oraclecloud.com:443/crmCommonApi/resources/11.12.1.0/resources/"
						+ partyNumber, HttpMethod.GET, null, String.class)
				.getBody();

		JSONObject contactJson = new JSONObject(contactString);

		String PartyId = contactJson.getString("PartyNumber");
		String ContactName = contactJson.getString("PartyName");
		String FormattedPhoneNumber = "";
		String EmailAddress = "";
		try {
			EmailAddress = contactJson.getString("EmailAddress");
			FormattedPhoneNumber = contactJson.getString("FormattedPhoneNumber");
		} catch (Exception ex) {
		}
		Resource resource = new Resource(PartyId, ContactName,EmailAddress, FormattedPhoneNumber );
		return resource;
	}

	
	@Override
	public Iterable<Resource> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource save(Resource object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource update(Resource object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Resource> save(Iterable<Resource> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource delete(Resource object) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
