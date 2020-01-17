package com.intiformation.tpcomptes.modele;

public class Client {
	
	int id_client;
	String nom;
	String prenom;
	String adresse;
	int code_postal;
	String ville;
	String telephone;
	int compte_id;
	
	public Client(int id_client, String nom, String prenom, String adresse, int code_postal, String ville,
			String telephone, int compte_id) {
		super();
		this.id_client = id_client;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.code_postal = code_postal;
		this.ville = ville;
		this.telephone = telephone;
		this.compte_id = compte_id;
	}
	
	public Client(String nom, String prenom, String adresse, int code_postal, String ville,
			String telephone, int compte_id) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.code_postal = code_postal;
		this.ville = ville;
		this.telephone = telephone;
		this.compte_id = compte_id;
	}
	
	public int getId_Client() {
		return id_client;
	}
	public void setId_Client(int id_client) {
		this.id_client = id_client;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getCode_postal() {
		return code_postal;
	}
	public void setCode_postal(int code_postal) {
		this.code_postal = code_postal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getCompte_id() {
		return compte_id;
	}
	public void setCompte_id(int compte_id) {
		this.compte_id = compte_id;
	}
	
	@Override
	public String toString()
	{
		return prenom + " " + nom + " - " + adresse + ", " + code_postal + ", " + ville + " - " + telephone;
	}

}
