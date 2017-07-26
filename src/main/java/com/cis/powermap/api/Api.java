package com.cis.powermap.api;

import org.springframework.http.ResponseEntity;

public interface Api<T> {

	ResponseEntity<? extends Iterable<T>> getAll();

	ResponseEntity<? extends T> findOne(String id);

	ResponseEntity<? extends T> save(T t);

	ResponseEntity<? extends T> update(T t);

	ResponseEntity<? extends T> delete(T t);

}
