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
	 * Rajoute la quantit� correspondant � la variable depot au solde
	 * @param depot
	 */
	public void deposer(double depot)
	{
		if(depot>0)
		{
			solde += depot;
			System.out.println("Vous avez d�pos� " + depot + "�");
		}
		else
		{
			System.out.println("Le d�pot doit �tre sup�rieur � 0");
		}
	}
	
	/**
	 * Retire la quantit� correspondant � la variable retrait au solde s'il y a assez sur le compte
	 * @param retrait
	 */
	/*public void retirer(double retrait)
	{
		if(retrait > 0 && solde - retrait > 0)
		{
			solde -= retrait;
			System.out.println("Vous avez retir� " + retrait + "�");
		}
		else if(retrait < 0)
		{
			System.out.println("Le retrait doit �tre sup�rieur � 0");
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
		return "Solde : " + solde + "�";		
	}

}
