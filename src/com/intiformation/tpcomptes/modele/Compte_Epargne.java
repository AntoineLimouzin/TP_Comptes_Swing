package com.intiformation.tpcomptes.modele;

public class Compte_Epargne extends Compte {
	
	private Double taux; //en pourcents

	public Double getTaux() {
		return taux;
	}

	
	public void setTaux(Double taux) {
		this.taux = taux;
	}
	
	/**
	 * Ajoute les intérêts au solde selon le taux du compte
	 */
	public void ajouterInterets()
	{
		this.setSolde(this.getSolde() * (1.0 +taux/100));
	}

	public Compte_Epargne() {
		super();
		taux = 3.0;
	}

	public Compte_Epargne(int id_compte, double taux) {
		super(id_compte);
		this.taux = taux;
	}
	
	public Compte_Epargne(int id_compte, double solde, double taux) {
		super(id_compte, solde);
		this.taux = taux;
	}
	
	public String toString()
	{
		return super.toString() + " \n Taux d'épargne : " + taux + "%";
	}

}
