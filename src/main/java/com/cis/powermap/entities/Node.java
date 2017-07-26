package com.cis.powermap.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Entity
@JsonIgnoreProperties(ignoreUnknown = true)

public class Node {
	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Long DID;
	private String id;
	private String label;
//	private String image = "assets/images/1.jpg";
//	private String shape = "circularImage";
	private String phone;
	private String email;
	private String jobTitle;
	private NodeType type;
	private NodeFunction function;
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public NodeFunction getFunction() {
		return function;
	}

	public void setFunction(NodeFunction function) {
		this.function = function;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

//	public Node() {
//		super();
//	}

//	public Node(String id, String label, String phone,NodeType type,NodeFunction function) {
//		this(id,label,title,type);
//		this.function = function;
//	}
//	public Node(String id, String label, String phone,NodeType type) {
//		super();
//		this.id = id;
//		this.label = label;
//		this.phone = title;
//		this.type = type;
//	}

//	public String getImage() {
//		return image;
//	}
//
//	public void setImage(String image) {
//		this.image = image;
//	}
//
//	public String getShape() {
//		return shape;
//	}
//
//	public void setShape(String shape) {
//		this.shape = shape;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	//
	// public Long getDID() {
	// return DID;
	// }
	//
	// public void setDID(Long dID) {
	// DID = dID;
	// }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}


}
