package com.cis.powermap.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cis.powermap.business.IPowerMapBusiness;
import com.cis.powermap.entities.PowerMap;

@RestController
@CrossOrigin("*")
@RequestMapping("/powermaps")
public class PowerMapApi implements Api<PowerMap> {

	@Autowired
	private IPowerMapBusiness business;

	@Override
	@GetMapping
	public ResponseEntity<? extends Iterable<PowerMap>> getAll() {
		return new ResponseEntity<Iterable<PowerMap>>(this.business.getAll(), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<? extends PowerMap> findOne(@PathVariable(name = "id") String id) {
		PowerMap map = this.business.getOpportunityPowerMap(id);
		if (map != null)
			return new ResponseEntity<PowerMap>(map, HttpStatus.OK);
		return new ResponseEntity<PowerMap>(HttpStatus.NOT_FOUND);
	}

	@Override
	@PostMapping
	public ResponseEntity<? extends PowerMap> save(@RequestBody PowerMap t) {
		return new ResponseEntity(this.business.save(t), HttpStatus.CREATED);
	}

	@Override
	@PutMapping
	public ResponseEntity<? extends PowerMap> update(@RequestBody PowerMap t) {
		return new ResponseEntity(this.business.update(t), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<? extends PowerMap> delete(PowerMap t) {
		return null;
	}

}
