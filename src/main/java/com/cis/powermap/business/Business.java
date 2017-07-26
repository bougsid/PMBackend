package com.cis.powermap.business;

public interface Business<T> {
	Iterable<T> getAll();

	T findOne(Long id);

	T save(T object);
	T update(T object);
	Iterable<T> save(Iterable<T> objects);

	T delete(T object);

}
