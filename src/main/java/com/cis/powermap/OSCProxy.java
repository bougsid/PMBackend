package com.cis.powermap;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cis.powermap.business.IPowerMapBusiness;
import com.cis.powermap.entities.Contact;
import com.cis.powermap.entities.Opportunity;
import com.cis.powermap.entities.Resource;

@RestController
@CrossOrigin("*")
public class OSCProxy {

	@Autowired
	private IPowerMapBusiness business;
	
	@GetMapping("/opportunity/contacts/{optyNumber}")
	private List<Contact> getAllContacts(@PathVariable(name = "optyNumber") String optyNumber) {
		return this.business.getContactsOfOpportunity(optyNumber);
	}

	@GetMapping("/opportunity/resources/{optyNumber}")
	private List<Resource> getAllResources(@PathVariable(name = "optyNumber") String optyNumber) {
		return this.business.getTeamOfOpportunity(optyNumber);
	}

	@GetMapping("/opportunities")
	private List<Opportunity> getAllOpportunities() {
		List<Opportunity> opportunities = new ArrayList<Opportunity>();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors()
				.add(new BasicAuthorizationInterceptor("bougsid.ayoub@accenture.com", "Khab@1960"));
		String opportunitiesString = restTemplate
				.exchange("https://cctn-dev1.crm.em3.oraclecloud.com:443/salesApi/resources/11.12.1.0/opportunities",
						HttpMethod.GET, null, String.class)
				.getBody();

		JSONObject opportunitiesJson = new JSONObject(opportunitiesString);
		JSONArray opportunitiesJSONArray = opportunitiesJson.getJSONArray("items");

		for (int jsonArrayIndex = 0; jsonArrayIndex < opportunitiesJSONArray.length(); jsonArrayIndex++) {
			String optyNumber = opportunitiesJSONArray.getJSONObject(jsonArrayIndex).getString("OptyNumber");
			String name = opportunitiesJSONArray.getJSONObject(jsonArrayIndex).getString("Name");
			String targetPartyName = opportunitiesJSONArray.getJSONObject(jsonArrayIndex).getString("TargetPartyName");
			String primaryContactPartyName = opportunitiesJSONArray.getJSONObject(jsonArrayIndex)
					.getString("PrimaryContactPartyName");
			opportunities.add(new Opportunity(optyNumber, name, targetPartyName, primaryContactPartyName));
		}
		return opportunities;
	}
}
