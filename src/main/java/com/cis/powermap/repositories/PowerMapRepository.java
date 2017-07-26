package com.cis.powermap.repositories;

import com.cis.powermap.entities.PowerMap;

public interface PowerMapRepository {

	PowerMap findById(Long id);

	PowerMap save(PowerMap map);
	Iterable<PowerMap> findAll();
}
