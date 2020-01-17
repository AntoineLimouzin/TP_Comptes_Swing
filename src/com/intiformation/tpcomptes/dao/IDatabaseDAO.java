package com.intiformation.tpcomptes.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.intiformation.tpcomptes.utilitaire.ConnexionMySQL;

/**
 * interface de la couche DAO
 * @author IN-MP-014
 *
 */
public interface IDatabaseDAO<T> {

	//recup connexion bdd
	public Connection connexion = ConnexionMySQL.getConnection();
	
	public ArrayList<T> getAll();
	public T getById(int id);
	public boolean add(T t);
	public boolean update(T t);
	public boolean delete(int t);
}
