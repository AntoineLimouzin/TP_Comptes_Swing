package com.intiformation.tpcomptes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.intiformation.tpcomptes.modele.Client;
import com.intiformation.tpcomptes.utilitaire.ConnexionMySQL;

public class ClientDAO implements IDatabaseDAO<Client> {

	private Connection co;

	/**
	 * Constructeur : initialise la connection à la BDD
	 */
	public ClientDAO() {
		co = ConnexionMySQL.getConnection();
	}

	/**
	 * @return la liste des clients
	 */
	@Override
	public ArrayList<Client> getAll() {
		String requeteSQL = "SELECT * FROM clients";
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


		ArrayList<Client> listeClients = new ArrayList<>();

		try {
			while (resultats.next()) {
				int id_client = resultats.getInt(1);
				String nom = resultats.getString(2);
				String prenom = resultats.getString(3);
				String adresse = resultats.getString(4);
				int code_postal = resultats.getInt(5);
				String ville = resultats.getString(6);
				String telephone = resultats.getString(7);
				int compte_id = resultats.getInt(8);

				listeClients.add(new Client(id_client, nom, prenom, adresse, code_postal, ville,
						telephone, compte_id));

			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listeClients;
	}

	/**
	 * @return le client correspondant à l'identifiant id
	 */
	@Override
	public Client getById(int id) {
		PreparedStatement reqJDBC = null;

		try {
			String req = "SELECT * FROM clients WHERE id_client=?";
			reqJDBC = co.prepareStatement(req);
			reqJDBC.setInt(1, id);

			ResultSet resultats = reqJDBC.executeQuery(); 

			resultats.next();

			int id_client = resultats.getInt(1);
			String nom = resultats.getString(2);
			String prenom = resultats.getString(3);
			String adresse = resultats.getString(4);
			int code_postal = resultats.getInt(5);
			String ville = resultats.getString(6);
			String telephone = resultats.getString(7);
			int compte_id = resultats.getInt(8);

			return new Client(id_client, nom, prenom, adresse, code_postal, ville,
					telephone, compte_id);


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
	 * ajoute le client c
	 * @return true si l'ajout a fonctionné, false sinon
	 */
	@Override
	public boolean add(Client c) {

		PreparedStatement reqJDBC = null;
		try {
			
			String req = "INSERT INTO clients (nom, prenom, adresse, code_postal, ville, telephone, compte_id) VALUES (?,?,?,?,?,?,?)";
			reqJDBC = co.prepareStatement(req);
			reqJDBC.setString(1, c.getNom());
			reqJDBC.setString(2, c.getPrenom());
			reqJDBC.setString(3, c.getAdresse());
			reqJDBC.setInt(4,c.getCode_postal());
			reqJDBC.setString(5, c.getVille());
			reqJDBC.setString(6, c.getTelephone());
			reqJDBC.setInt(7,c.getCompte_id());

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
	 * modifie le client c
	 * @return true si la modification a fonctionnée, false sinon
	 */
	@Override
	public boolean update(Client c) {
		PreparedStatement reqJDBC = null;
		try {

			String req = "UPDATE clients SET nom=?, prenom=?, adresse=?, code_postal=?, ville=?, telephone=?, compte_id=? WHERE id_client=?";
			reqJDBC = co.prepareStatement(req);
			reqJDBC.setInt(8,c.getId_Client());
			reqJDBC.setString(1, c.getNom());
			reqJDBC.setString(2, c.getPrenom());
			reqJDBC.setString(3, c.getAdresse());
			reqJDBC.setInt(4,c.getCode_postal());
			reqJDBC.setString(5, c.getVille());
			reqJDBC.setString(6, c.getTelephone());
			reqJDBC.setInt(7,c.getCompte_id());

			int res = 0;
			
			try {
				res = reqJDBC.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			

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
	 * supprime le client correspondant à l'identifiant id
	 * @return true si la suppression a fonctionnée, false sinon
	 */
	@Override
	public boolean delete(int id) {
		PreparedStatement reqJDBC = null;
		try {

			String req = "DELETE FROM clients WHERE id_client=?";
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

}
