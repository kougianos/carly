package com.carly.service;

import java.util.List;

public interface CrudService<T> {
	T findById(String id);

	List<T> findAll();

	T patchById(String id, T resource);

	T deleteById(String id);
}
