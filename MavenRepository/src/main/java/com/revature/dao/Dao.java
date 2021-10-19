package com.revature.dao;

import java.util.List;

public interface Dao<T> {

	T getElement(int id);
	List<T> getAllElements();
	T insertElement(T element);
	T updateElement(T element);
	boolean deleteElement(int id);
	
	
}
