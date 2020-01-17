package com.intiformation.tpcomptes.service;

import java.util.List;

public interface IDatabaseService<T> {
	
	public List<T> findAll();
	
	public T findById(int id);
	
	public boolean modifier(T t);
	
	public boolean ajouter(T t);
	
	public boolean supprimer(int id);

}
