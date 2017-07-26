package com.cis.powermap.entities;

//@Entity
public class Opportunity {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long DID;
	private String optyNumber;
	private String name;
	private String targetPartyName;
	private String primaryContactPartyName;
	
	public Opportunity() {
		super();
	}
	public Opportunity(String optyNumber, String name, String targetPartyName, String primaryContactPartyName) {
		super();
		this.optyNumber = optyNumber;
		this.name = name;
		this.targetPartyName = targetPartyName;
		this.primaryContactPartyName = primaryContactPartyName;
	}
//	public Long getDID() {
//		return DID;
//	}
//	public void setDID(Long dID) {
//		DID = dID;
//	}
	public String getOptyNumber() {
		return optyNumber;
	}
	public void setOptyNumber(String optyNumber) {
		this.optyNumber = optyNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTargetPartyName() {
		return targetPartyName;
	}
	public void setTargetPartyName(String targetPartyName) {
		this.targetPartyName = targetPartyName;
	}
	public String getPrimaryContactPartyName() {
		return primaryContactPartyName;
	}
	public void setPrimaryContactPartyName(String primaryContactPartyName) {
		this.primaryContactPartyName = primaryContactPartyName;
	}
	
	
	

}
