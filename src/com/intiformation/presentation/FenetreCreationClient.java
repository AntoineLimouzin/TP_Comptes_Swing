package com.intiformation.presentation;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import com.intiformation.tpcomptes.modele.Client;
import com.intiformation.tpcomptes.modele.Compte;
import com.intiformation.tpcomptes.modele.Compte_Courant;
import com.intiformation.tpcomptes.modele.Compte_Epargne;
import com.intiformation.tpcomptes.service.ClientService;
import com.intiformation.tpcomptes.service.CompteService;



public class FenetreCreationClient {

	private CompteService compteServ = new CompteService();
	private ClientService clientServ = new ClientService();

	public FenetreCreationClient() {
		
	}

	public boolean ajouterClient()
	{
		String prenom = JOptionPane.showInputDialog(null, "Prénom ?", "Infos", JOptionPane.QUESTION_MESSAGE);
		
		String nom = null;
		if (prenom != null)
		{
			nom = JOptionPane.showInputDialog(null, "Nom ?", "Infos", JOptionPane.QUESTION_MESSAGE);
		}
		
		String adresse = null;
		if (nom != null)
		{
			adresse = JOptionPane.showInputDialog(null, "Adresse ?", "Infos", JOptionPane.QUESTION_MESSAGE);
		}
		
		String ville = null;
		if (adresse !=null)
		{
			ville = JOptionPane.showInputDialog(null, "Ville ?", "Infos", JOptionPane.QUESTION_MESSAGE);
		}
		
		Integer codePostal = null;

		if (ville != null)
		{
			String postalCodeStr = JOptionPane.showInputDialog(null, "Code postal ?", "Infos", JOptionPane.QUESTION_MESSAGE);
			try {
				codePostal = Integer.parseInt(postalCodeStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Ajout impossible", "Info", JOptionPane.ERROR_MESSAGE);
				codePostal = null;
				return false;
			}
		}

		String telephone = null;
		if (codePostal != null)
		{
			telephone = JOptionPane.showInputDialog(null, "Numéro de téléphone ?", "Infos", JOptionPane.QUESTION_MESSAGE);
		}

		String types[] = {"Compte normal", "Compte courant", "Compte epargne"};

		String type = null;
		if (telephone != null)
		{
			type = (String) JOptionPane.showInputDialog(null, 
					"Quel type de compte ?",
					"Infos",
					JOptionPane.QUESTION_MESSAGE,
					null,
					types, types[0]);
		}
		
		
		Compte nouveauCompte = null;

		if (type != null)
		{
			switch (type) {
			case "Compte normal":
				nouveauCompte = new Compte();
				break;
			case "Compte courant":
				nouveauCompte = new Compte_Courant();
				break;
			case "Compte epargne":
				nouveauCompte = new Compte_Epargne();
				break;
			default:
				break;
			}

			compteServ.ajouter(nouveauCompte);

			int compte_id = compteServ.findAll().get(compteServ.findAll().size()-1).getId_Compte();

			clientServ.ajouter(new Client(nom, prenom, adresse, codePostal, ville, telephone, compte_id));
			
			JOptionPane.showMessageDialog(null, "Ajout effectué avec succès", "Infos du compte", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		else
		{
			//JOptionPane.showMessageDialog(null, "Ajout impossible", "Info", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

}
