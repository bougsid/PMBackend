package com.cis.powermap.entities;

public class Contact {
	private String PartyId;
	private String contactName;
	private String AccountName;
	private String FormattedPhoneNumber;
	private String EmailAddress;
	private String roleCd;
	private String jobTitle;
	
	public String getPartyId() {
		return PartyId;
	}
	public void setPartyId(String partyId) {
		PartyId = partyId;
	}

	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getAccountName() {
		return AccountName;
	}
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public String getFormattedPhoneNumber() {
		return FormattedPhoneNumber;
	}
	public void setFormattedPhoneNumber(String formattedPhoneNumber) {
		FormattedPhoneNumber = formattedPhoneNumber;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getRoleCd() {
		return roleCd;
	}
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	
	
}
