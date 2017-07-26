package com.cis.powermap.business;

import java.util.List;

import com.cis.powermap.entities.Contact;
import com.cis.powermap.entities.PowerMap;
import com.cis.powermap.entities.Resource;

public interface IPowerMapBusiness extends Business<PowerMap>{
	
	List<Contact> getContactsOfOpportunity(String optyNumber);
	List<Resource> getTeamOfOpportunity(String optyNumber);
	PowerMap getOpportunityPowerMap(String optyNumber);

}
