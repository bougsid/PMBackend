package com.cis.powermap.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Entity
@JsonIgnoreProperties(ignoreUnknown = true) 

public class Relation {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long DID;
	private Long id;
//	@Column(name = "form_column")
	private String from;
	private String to;
	private String label;
//	private String color;
	
	//TODO
	private static Long autoId = 0L;

	
	public Relation(String from, String to, String label) {
		super();
		this.from = from;
		this.to = to;
		this.label = label;
	}

	public Relation() {
		super();
		this.id = autoId;
	}

//	public String getColor() {
//		return color;
//	}
//
//	public void setColor(String color) {
//		this.color = color;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//
//	public Long getDID() {
//		return DID;
//	}
//
//	public void setDID(Long dID) {
//		DID = dID;
//	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "ContactRelation [id=" + id + ", from=" + from + ", to=" + to + ", label=" + label + "]";
	}

}
