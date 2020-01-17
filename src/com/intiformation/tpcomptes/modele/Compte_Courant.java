package com.intiformation.tpcomptes.modele;

public class Compte_Courant extends Compte {
	
	private Double decouvert_autorise;
	
	public Double getDecouvert_autorise() {
		return decouvert_autorise;
	}

	public void setDecouvert_autorise(Double decouvert_autorise) {
		this.decouvert_autorise = decouvert_autorise;
	}

	public Compte_Courant() {
		super();
		decouvert_autorise = 100.0;
	}

	public Compte_Courant(int numero, double solde, double decouvert_autorise) {
		super(numero, solde);
		this.decouvert_autorise = decouvert_autorise;
	}
	
	public String toString()
	{
		return super.toString() + "\n Découvert max : " + decouvert_autorise + "€"; 
	}

}
