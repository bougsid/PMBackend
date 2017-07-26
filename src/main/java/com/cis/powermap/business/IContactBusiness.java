package com.cis.powermap.business;

import com.cis.powermap.entities.Contact;

public interface IContactBusiness extends Business<Contact>{
	Contact getContact(String partyNumber);
}
