package com.intiformation.tpcomptes.modele;

public class Compte {
	
	private int id_compte;
	private Double solde;
	
	public Compte()
	{
		solde = 0.0;
	}
	
	public Compte(int id_compte)
	{
		this.id_compte = id_compte;
	}
	
	
	
	public Compte(int id_compte, double solde) {
		super();
		this.id_compte = id_compte;
		this.solde = solde;
	}

	/**
	 * Rajoute la quantité correspondant à la variable depot au solde
	 * @param depot
	 */
	public void deposer(double depot)
	{
		if(depot>0)
		{
			solde += depot;
			System.out.println("Vous avez déposé " + depot + "€");
		}
		else
		{
			System.out.println("Le dépot doit être supérieur à 0");
		}
	}
	
	/**
	 * Retire la quantité correspondant à la variable retrait au solde s'il y a assez sur le compte
	 * @param retrait
	 */
	/*public void retirer(double retrait)
	{
		if(retrait > 0 && solde - retrait > 0)
		{
			solde -= retrait;
			System.out.println("Vous avez retiré " + retrait + "€");
		}
		else if(retrait < 0)
		{
			System.out.println("Le retrait doit être supérieur à 0");
		}
		else
		{
			System.out.println("Trop pauvre pour retirer");
		}
	}*/

	/**
	 * @return the solde
	 */
	public Double getSolde() {
		return solde;
	}

	/**
	 * @param solde the solde to set
	 */
	public void setSolde(Double solde) {
		this.solde = solde;
	}

	/**
	 * @return the id_compte
	 */
	public int getId_Compte() {
		return id_compte;
	}

	/**
	 * @param id_compte the id_compte to set
	 */
	public void setId_Compte(int id_compte) {
		this.id_compte = id_compte;
	}

	/**
	 * @return the nom_titulaire
	 */
	
	public String toString()
	{
		return "Solde : " + solde + "€";		
	}

}
