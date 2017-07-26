package com.cis.powermap.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Entity
@JsonIgnoreProperties(ignoreUnknown = true) 

public class PowerMap {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long DID;
	private Long id;
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@OneToMany(cascade = CascadeType.MERGE)
	private List<Relation> relations;
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@OneToMany(cascade = CascadeType.MERGE )
	private List<Node> nodes;
	private String name;
	
	



	public PowerMap() {
		this.nodes = new ArrayList<Node>();
		this.relations = new ArrayList<Relation>();
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//
//	public Long getDID() {
//		return DID;
//	}
//
//	public void setDID(Long dID) {
//		DID = dID;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

//	public List<Node> getContacts() {
//		return nodes;
//	}
//
//	public void setContacts(List<Node> contacts) {
//		this.nodes = contacts;
//	}

	public void addNode(Node node){
		this.nodes.add(node);
	}
	public void addRelation(Relation relation){
		this.relations.add(relation);
	}
	@Override
	public String toString() {
		return "PowerMap [id=" + id  + ", relations=" + relations + ", contacts=" + nodes
				+ ", mapName=" + name + "]";
	}




	

}
