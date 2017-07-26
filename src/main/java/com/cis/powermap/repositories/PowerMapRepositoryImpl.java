package com.cis.powermap.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import com.cis.powermap.entities.PowerMap;
@Repository
public class PowerMapRepositoryImpl implements PowerMapRepository {
	Map<Long, PowerMap> powermaps = new HashMap<Long, PowerMap>();

	@Override
	public PowerMap findById(Long id) {
		return powermaps.get(id);
	}

	@Override
	public PowerMap save(PowerMap map) {
		return this.powermaps.put(map.getId(), map);
	}
	@Override
	public Iterable<PowerMap> findAll() {
		List<PowerMap> maps = new ArrayList<PowerMap>();
		for(Entry<Long, PowerMap> entry : this.powermaps.entrySet())
			maps.add(entry.getValue());
		return maps;
	}

}
