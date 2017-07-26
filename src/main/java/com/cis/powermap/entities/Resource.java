package com.cis.powermap.entities;

//@Entity
public class Resource {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long DID;
	private String resourceId;
	private String name;
	private String emailAddress;
	private String formattedPhoneNumber;
	private String jobTitle;
	
	

	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getFormattedPhoneNumber() {
		return formattedPhoneNumber;
	}
	public void setFormattedPhoneNumber(String formattedPhoneNumber) {
		this.formattedPhoneNumber = formattedPhoneNumber;
	}
	public Resource() {
		super();
	}
	public Resource(String resourceId, String partyName, String emailAddress,String formattedPhoneNumber) {
		super();
		this.resourceId = resourceId;
		this.name = partyName;
		this.emailAddress = emailAddress;
		this.formattedPhoneNumber = formattedPhoneNumber;
	}
//	public Long getDID() {
//		return DID;
//	}
//	public void setDID(Long dID) {
//		DID = dID;
//	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
}
