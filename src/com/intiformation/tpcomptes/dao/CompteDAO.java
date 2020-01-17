package com.intiformation.tpcomptes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.intiformation.tpcomptes.modele.Client;
import com.intiformation.tpcomptes.modele.Compte;
import com.intiformation.tpcomptes.modele.Compte_Courant;
import com.intiformation.tpcomptes.modele.Compte_Epargne;
import com.intiformation.tpcomptes.utilitaire.ConnexionMySQL;

public class CompteDAO implements IDatabaseDAO<Compte> {

	private Connection co;

	/**
	 * Constructeur : initialise la connection à la BDD
	 */
	public CompteDAO() {
		co = ConnexionMySQL.getConnection();
	}

	/**
	 * @return la liste des comptes
	 */
	@Override
	public ArrayList<Compte> getAll() {
		String requeteSQL = "SELECT * FROM comptes";
		Statement requeteJDBC = null;

		try {
			requeteJDBC = co.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResultSet resultats = null;
		try {
			resultats = requeteJDBC.executeQuery(requeteSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		ArrayList<Compte> listeComptes = new ArrayList<>();

		try {
			while (resultats.next()) {
				int id_compte = resultats.getInt(1);//ou getInt("id_hotel")
				double solde = resultats.getDouble(2);
				double decouvert_autorise = resultats.getDouble(3);
				double taux = resultats.getDouble(4);
				String typeCompte = resultats.getString(5);

				switch (typeCompte) {
				case "standard":
					listeComptes.add(new Compte(id_compte, solde));
					break;
				case "epargne":
					listeComptes.add(new Compte_Epargne(id_compte, solde, taux));
					break;
				case "courant":
					listeComptes.add(new Compte_Courant(id_compte, solde, decouvert_autorise));
					break;
				default:
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listeComptes;
	}

	/**
	 * @return le compte correspondant à l'identifiant id
	 */
	@Override
	public Compte getById(int id) {
		PreparedStatement reqJDBC = null;

		try {
			String req = "SELECT * FROM comptes WHERE id_compte=?";
			reqJDBC = co.prepareStatement(req);
			reqJDBC.setInt(1, id);

			//exec° requête + recup resultat
			ResultSet res = reqJDBC.executeQuery(); 

			res.next();

			int id_compte = res.getInt(1);
			double solde = res.getDouble(2);
			double decouvert_autorise = res.getDouble(3);
			double taux = res.getDouble(4);
			String typeCompte = res.getString(5);

			switch (typeCompte) {
			case "standard":
				return new Compte(id_compte, solde);
			case "epargne":
				return new Compte_Epargne(id_compte, solde, taux);
			case "courant":
				return new Compte_Courant(id_compte, solde, decouvert_autorise);
			default:
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(reqJDBC != null)
				try {
					reqJDBC.close();
					if(connexion != null) connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}
		return null;
	}

	/**
	 * @return le compte correspondant à l'identifiant client id
	 */
	public Compte getByClientId(int id) {
		PreparedStatement reqJDBC = null;

		try {
			String req =  "select id_compte, solde, decouvert_autorise, taux, typeCompte, id_client "
					+ "from comptes as co inner join clients as cl on co.id_compte = cl.compte_id where id_client=?";
			//id_compte, solde, dec, taux, type, id_client
			reqJDBC = co.prepareStatement(req);
			reqJDBC.setInt(1, id);

			//exec° requête + recup resultat
			ResultSet res = reqJDBC.executeQuery(); 

			res.next();

			int id_compte = res.getInt(1);
			double solde = res.getDouble(2);
			double decouvert_autorise = res.getDouble(3);
			double taux = res.getDouble(4);
			String typeCompte = res.getString(5);

			switch (typeCompte) {
			case "standard":
				return new Compte(id_compte, solde);
			case "epargne":
				return new Compte_Epargne(id_compte, solde, taux);
			case "courant":
				return new Compte_Courant(id_compte, solde, decouvert_autorise);
			default:
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(reqJDBC != null)
				try {
					reqJDBC.close();
					if(connexion != null) connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}
		return null;
	}

	/**
	 * ajoute le compte c
	 * @return true si l'ajout a fonctionné, false sinon
	 */
	@Override
	public boolean add(Compte c) {

		PreparedStatement reqJDBC = null;
		try {
			
			String nomClasse = c.getClass().getName();
			

			//String req = "INSERT INTO comptes (id_compte, solde, decouvert_autorise, taux, typeCompte) VALUES (?,?,?,?,?)";

			// executer requete dans switch

			String req = "";

			switch (nomClasse) {
			case "com.intiformation.tpcomptes.modele.Compte":
				req = "INSERT INTO comptes (solde, typeCompte) VALUES (?,?)";
				reqJDBC = co.prepareStatement(req);
				reqJDBC.setDouble(1, c.getSolde());
				reqJDBC.setString(2, "standard");
				break;
			case "com.intiformation.tpcomptes.modele.Compte_Epargne":
				Compte_Epargne ce2 = (Compte_Epargne) c;
				req = "INSERT INTO comptes (solde, taux, typeCompte) VALUES (?,?,?)";
				reqJDBC = co.prepareStatement(req);
				reqJDBC.setDouble(1, c.getSolde());
				reqJDBC.setDouble(2, ce2.getTaux());
				reqJDBC.setString(3, "epargne");
				break;
			case "com.intiformation.tpcomptes.modele.Compte_Courant":
				Compte_Courant cc2 = (Compte_Courant) c;
				req = "INSERT INTO comptes (solde, decouvert_autorise, typeCompte) VALUES (?,?,?)";
				reqJDBC = co.prepareStatement(req);
				reqJDBC.setDouble(1, c.getSolde());
				reqJDBC.setDouble(2, cc2.getDecouvert_autorise());
				reqJDBC.setString(3, "courant");
				break;
			default:
				return false;
			}
			
			
			

			int res = reqJDBC.executeUpdate();

			if (res != 0)
			{
				return true;
			}
			else
			{
				return false;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(reqJDBC != null)
				try {
					reqJDBC.close();
					if(connexion != null) connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}

		return false;
	}

	/**
	 * modifie le compte c
	 * @return true si la modification a fonctionnée, false sinon
	 */
	@Override
	public boolean update(Compte c) {
		PreparedStatement reqJDBC = null;
		try {

			String req = "";

			String nomClasse = c.getClass().getName();

			switch (nomClasse) {
			case "com.intiformation.tpcomptes.modele.Compte":
				req = "UPDATE comptes SET solde=?, typeCompte=? WHERE id_compte=?";
				reqJDBC = co.prepareStatement(req);
				reqJDBC.setDouble(1, c.getSolde());
				reqJDBC.setString(2, "standard");
				reqJDBC.setInt(3, c.getId_Compte());
				break;
			case "com.intiformation.tpcomptes.modele.Compte_Epargne":
				Compte_Epargne ce2 = (Compte_Epargne) c;
				req = "UPDATE comptes SET solde=?, taux = ?, typeCompte=? WHERE id_compte=?";
				reqJDBC = co.prepareStatement(req);
				reqJDBC.setDouble(1, c.getSolde());
				reqJDBC.setDouble(2, ce2.getTaux());
				reqJDBC.setString(3, "epargne");
				reqJDBC.setInt(4, c.getId_Compte());
				break;
			case "com.intiformation.tpcomptes.modele.Compte_Courant":
				Compte_Courant cc2 = (Compte_Courant) c;
				req = "UPDATE comptes SET solde=?, decouvert_autorise = ?, typeCompte=? WHERE id_compte=?";
				reqJDBC = co.prepareStatement(req);
				reqJDBC.setDouble(1, c.getSolde());
				reqJDBC.setDouble(2, cc2.getDecouvert_autorise());
				reqJDBC.setString(3, "courant");
				reqJDBC.setInt(4, c.getId_Compte());
				break;
			default:
				return false;
			}

			int res = reqJDBC.executeUpdate();

			if (res != 0)
			{
				return true;
			}
			else
			{
				return false;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(reqJDBC != null)
				try {
					reqJDBC.close();
					if(connexion != null) connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}
		return false;
	}

	/**
	 * supprime le commpte correspondant à l'identifiant id
	 * @return true si la suppression a fonctionnée, false sinon
	 */
	@Override
	public boolean delete(int id) {
		PreparedStatement reqJDBC = null;
		try {

			String req = "DELETE FROM comptes WHERE id_comptes=?";
			reqJDBC = co.prepareStatement(req);
			reqJDBC.setInt(1,id);

			int res = reqJDBC.executeUpdate();

			if (res != 0)
			{
				return true;
			}
			else
			{
				return false;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(reqJDBC != null)
				try {
					reqJDBC.close();
					if(connexion != null) connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}
		return false;
	}

	/**
	 * Associe le Compte com au Client cl
	 * @return true si l'association a fonctionnée, false sinon
	 */
	public boolean setClient(Compte com, Client cl)
	{
		PreparedStatement reqJDBC = null;
		try {

			String req = "UPDATE client SET nom=?, prenom=?, adresse=?, code_postal=?, ville=?, telephone=?, compte_id=? WHERE id_client=?";
			reqJDBC = co.prepareStatement(req);
			reqJDBC.setInt(8,cl.getId_Client());
			reqJDBC.setString(1, cl.getNom());
			reqJDBC.setString(2, cl.getPrenom());
			reqJDBC.setString(3, cl.getAdresse());
			reqJDBC.setInt(4,cl.getCode_postal());
			reqJDBC.setString(5, cl.getVille());
			reqJDBC.setString(6, cl.getTelephone());
			reqJDBC.setInt(7,com.getId_Compte());

			int res = reqJDBC.executeUpdate();

			if (res != 0)
			{
				return true;
			}
			else
			{
				return false;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(reqJDBC != null)
				try {
					reqJDBC.close();
					if(connexion != null) connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}
		return false;
	}

	/**
	 * Retire la somme retrait au Compte compte
	 * @return true si le retrait a fonctionné, false sinon
	 */
	public boolean withdrawMoney(Compte compte, double retrait) {
		double solde = compte.getSolde();
		String nomClasse = compte.getClass().getName();
		double decouvert_autorise = 0.0;

		if (nomClasse == "com.intiformation.tpcomptes.modele.Compte_Courant")
		{
			Compte_Courant cc = (Compte_Courant) compte;
			decouvert_autorise = cc.getDecouvert_autorise();
		}

		if(solde - retrait > -decouvert_autorise)
		{
			compte.setSolde(solde-retrait);
			return update(compte);
		}
		else
		{
			return false;
		}
	}

	/**
	 * Ajoute la somme retrait au Compte compte
	 * @return true si l'ajout a fonctionné, false sinon
	 */
	public boolean addMoney(Compte compte, double depot)
	{
		return withdrawMoney(compte, -depot);
	}

	/**
	 * transfère la somme transfert du Compte crediteur vers le compte débiteur
	 * @return true si le transfert a fonctionné, false sinon
	 */
	public boolean transferMoney(Compte crediteur, Compte debiteur, double transfert)
	{
		if (withdrawMoney(crediteur, transfert)) 
		{
			return addMoney(debiteur, transfert);
		}
		else
		{
			return false;
		}
	}
}
