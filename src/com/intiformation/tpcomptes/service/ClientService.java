package com.intiformation.tpcomptes.service;

import java.util.List;
import com.intiformation.tpcomptes.dao.ClientDAO;
import com.intiformation.tpcomptes.modele.Client;

public class ClientService implements IDatabaseService<Client>{
	
	ClientDAO clientDAO;
	
	/**
	 * Constructeur : initialise la DAO
	 */
	public ClientService() {
		clientDAO = new ClientDAO();
	}

	/**
	 * @return la liste des clients
	 */
	@Override
	public List<Client> findAll()
	{
		return clientDAO.getAll();
	}
	
	/**
	 * @return le client correspondant à l'identifiant id
	 */
	@Override
	public Client findById(int id)
	{
		return clientDAO.getById(id);
	}
	
	/**
	 * ajoute le client c
	 * @return true si l'ajout a fonctionné, false sinon
	 */
	@Override
	public boolean ajouter(Client c)
	{
		return clientDAO.add(c);
	}
	
	/**
	 * modifie le client c
	 * @return true si la modification a fonctionnée, false sinon
	 */
	@Override
	public boolean modifier(Client c)
	{
		return clientDAO.update(c);
	}
	
	/**
	 * supprime le client correspondant à l'identifiant id
	 * @return true si la suppression a fonctionnée, false sinon
	 */
	@Override
	public boolean supprimer(int id)
	{
		return clientDAO.delete(id);
	}

}
