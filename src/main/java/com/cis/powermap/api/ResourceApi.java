package com.cis.powermap.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cis.powermap.business.IResourceBusiness;
import com.cis.powermap.entities.Resource;

@RestController
@CrossOrigin("*")
@RequestMapping("/resources")
public class ResourceApi implements Api {
	@Autowired
	private IResourceBusiness business;

	@Override
	public ResponseEntity getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Resource> findOne(@PathVariable("id") String id) {
		return new ResponseEntity<Resource>(this.business.getResource(id), HttpStatus.OK);
	}

	@Override
	public ResponseEntity save(Object t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity update(Object t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity delete(Object t) {
		// TODO Auto-generated method stub
		return null;
	}

}
