package com.intiformation.tpcomptes.service;

import java.util.List;

import com.intiformation.tpcomptes.dao.ClientDAO;
import com.intiformation.tpcomptes.dao.CompteDAO;
import com.intiformation.tpcomptes.modele.Client;
import com.intiformation.tpcomptes.modele.Compte;
import com.intiformation.tpcomptes.modele.Compte_Courant;
import com.intiformation.tpcomptes.modele.Compte_Epargne;

public class CompteService implements IDatabaseService<Compte>{

	CompteDAO compteDAO;

	/**
	 * Constructeur : initialise la DAO
	 */
	public CompteService() {
		compteDAO = new CompteDAO();
	}

	/**
	 * @return la liste des comptes
	 */
	@Override
	public List<Compte> findAll()
	{
		return compteDAO.getAll();
	}

	/**
	 * @return le compte correspondant à l'identifiant id
	 */
	@Override
	public Compte findById(int id)
	{
		return compteDAO.getById(id);
	}

	/**
	 * @return le compte correspondant à l'identifiant client id
	 */
	public Compte findByClientId(int id)
	{
		return compteDAO.getByClientId(id);
	}

	/**
	 * ajoute le compte c
	 * @return true si l'ajout a fonctionné, false sinon
	 */
	@Override
	public boolean ajouter(Compte c)
	{
		return compteDAO.add(c);
	}

	/**
	 * modifie le compte c
	 * @return true si la modification a fonctionnée, false sinon
	 */
	@Override
	public boolean modifier(Compte c)
	{
		return compteDAO.update(c);
	}

	/**
	 * supprime le commpte correspondant à l'identifiant id
	 * @return true si la suppression a fonctionnée, false sinon
	 */
	@Override
	public boolean supprimer(int id)
	{
		return compteDAO.delete(id);
	}
	
	/**
	 * Associe le Compte com au Client cl
	 * @return true si l'association a fonctionnée, false sinon
	 */
	public boolean attribuerClient(Compte co, Client cl)
	{
		return compteDAO.setClient(co,cl);
	}

	public boolean ajouterArgent(Compte compte, double depot)
	{
		return depot > 0 && compteDAO.addMoney(compte, depot);
	}
	
	public boolean retirerArgent(Compte compte, double retrait)
	{
		return retrait > 0 && compteDAO.withdrawMoney(compte, retrait);
	}
	
	public boolean transfererArgent(Compte crediteur, Compte debiteur, double transfert)
	{
		return transfert > 0 && compteDAO.transferMoney(crediteur,debiteur, transfert);
	}
	
}
