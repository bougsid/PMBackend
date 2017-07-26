package com.cis.powermap.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cis.powermap.business.IContactBusiness;
import com.cis.powermap.entities.Contact;

@RestController
@CrossOrigin("*")
@RequestMapping("/contacts")
public class ContactApi implements Api {
	
	@Autowired
	private IContactBusiness business;

	@Override
	public ResponseEntity getAll() {
		return null;
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Contact> findOne(@PathVariable("id") String id) {
		return new ResponseEntity<Contact>(this.business.getContact(id),HttpStatus.OK);
	}

	@Override
	public ResponseEntity save(Object t) {
		return null;
	}

	@Override
	public ResponseEntity update(Object t) {
		return null;
	}

	@Override
	public ResponseEntity delete(Object t) {
		return null;
	}

}
