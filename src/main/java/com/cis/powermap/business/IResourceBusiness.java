package com.cis.powermap.business;

import com.cis.powermap.entities.Resource;

public interface IResourceBusiness extends Business<Resource> {
	Resource getResource(String partyNumber);
}
