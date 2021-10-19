package com.revature.dao;

public interface Dao<T> {

	T getElement(int id);
	T insertElement(T element);
	T updateElement(T element);
	boolean deleteElement(int id);
	
	
}
