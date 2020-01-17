package com.intiformation.tpcomptes.test;

import java.util.List;
import java.util.Scanner;

import com.intiformation.tpcomptes.modele.Client;
import com.intiformation.tpcomptes.modele.Compte;
import com.intiformation.tpcomptes.modele.Compte_Courant;
import com.intiformation.tpcomptes.modele.Compte_Epargne;
import com.intiformation.tpcomptes.service.ClientService;
import com.intiformation.tpcomptes.service.CompteService;

public class AppTestComptesClient {

	public static void main(String[] args) {

		CompteService cos = new CompteService();
		ClientService cls = new ClientService();

		List<Client> clientList = cls.findAll();
		Scanner in = new Scanner(System.in);
		System.out.println("-------------------------");
		System.out.println("----- Choix client: -----");
		System.out.println("-------------------------");
		System.out.println("0. Nouveau Client");
		for (Client client : clientList) {
			System.out.println(client);
		}

		int choixClient = clientList.size()+1;

		while (choixClient > clientList.size()) {
			try {
				System.out.println("Qui êtes vous ?: ");
				choixClient = in.nextInt();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Entrez un nombre");
			}
		}

		if (choixClient == 0)
		{
			String prenom, nom, adresse, ville, telephone;
			int codepostal, choixTypeCompte;

			try {
				Scanner in2 = new Scanner(System.in);
				Scanner in3 = new Scanner(System.in);
				System.out.println("Quel est votre prénom ?");
				prenom = in2.nextLine();
				System.out.println("Quel est votre nom ?");
				nom = in2.nextLine();
				System.out.println("Quel est votre adresse ?");
				adresse = in2.nextLine();
				System.out.println("Quel est votre ville ?");
				ville = in2.nextLine();
				System.out.println("Quel est votre code postal ?");
				codepostal = in3.nextInt();
				System.out.println("Quel est votre numéro de téléphone ?");
				telephone = in2.nextLine();

				System.out.println("Quel type de compte voulez vous ?");
				System.out.println("1. Compte normal");
				System.out.println("2. Compte courant");
				System.out.println("3. Compte epargne");
				choixTypeCompte = in.nextInt();
				Compte nouveauCompte = null;

				switch (choixTypeCompte) {
				case 1:
					nouveauCompte = new Compte();
					break;
				case 2:
					nouveauCompte = new Compte_Courant();
					break;
				case 3:
					nouveauCompte = new Compte_Epargne();
					break;
				default:
					break;
				}

				cos.ajouter(nouveauCompte);
				
				int compte_id = cos.findAll().get(cos.findAll().size()-1).getId_Compte();
				
				cls.ajouter(new Client(nom, prenom, adresse, codepostal, ville, telephone, compte_id));
				choixClient = cls.findAll().get(cls.findAll().size()-1).getId_Client();

			} catch (java.util.InputMismatchException e) {}
		}

		Compte compte = cos.findByClientId(choixClient);
		boolean dansMenu = true;

		while (dansMenu)
		{
			System.out.println("------------------------------");
			System.out.println("----- Choisir opération: -----");
			System.out.println("------------------------------");
			System.out.println("1 - Afficher les informations du compte");
			System.out.println("2 - Déposer de l'argent");
			System.out.println("3 - Retirer de l'argent");
			System.out.println("4 - Transférer de l'argent");
			System.out.println("5 - Quitter");

			int choix2 = 6;

			try {
				choix2 = in.nextInt();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Entrez un chiffre entre 1 et 5");
				continue;
			}

			switch(choix2)
			{
			case 1:
				System.out.println("compte n°" + compte);
				dansMenu = false;
				break;
			case 2:
				System.out.println("Combien voulez vous déposer ?");
				try {
					double depot = in.nextDouble();
					boolean depotOk = cos.ajouterArgent(compte, depot);
					System.out.println(depotOk?"Dépôt effectué":"Erreur lors du dépôt");
					dansMenu = false;
				} catch (java.util.InputMismatchException e) {
					System.out.println("Entrez un nombre à virgule");
					continue;
				}
				break;
			case 3:
				System.out.println("Combien voulez vous retirer ?");
				try {
					double depot = in.nextDouble();
					boolean retraitOk = cos.retirerArgent(compte, depot);
					System.out.println(retraitOk?"Retrait effectué":"Erreur lors du retrait");
					dansMenu = false;
				} catch (java.util.InputMismatchException e) {
					System.out.println("Entrez un nombre à virgule");
					continue;
				}
				break;
			case 4:
				int choixDebiteur = clientList.size()+1;

				while (choixDebiteur > clientList.size()) {
					try {
						System.out.println("Vers qui voulez vous transférer de l'argent ?");
						for (Client client : clientList) {
							if (client.getId_Client() != choixClient) System.out.println(client);
						}
						choixDebiteur = in.nextInt();
					} catch (java.util.InputMismatchException e) {
						System.out.println("Entrez un nombre");
						continue;
					}
				}

				if (choixDebiteur>= 0 && choixDebiteur != choixClient)
				{
					System.out.println("Combien voulez vous transférer ?");
					try {
						double transfert = in.nextDouble();
						Compte compteDebiteur = cos.findByClientId(choixDebiteur);
						boolean transfertOk = cos.transfererArgent(compte, compteDebiteur, transfert);
						System.out.println(transfertOk?"Transfert effectué":"Erreur lors du transfert");
						dansMenu = false;
					} catch (java.util.InputMismatchException e) {
						System.out.println("Entrez un nombre à virgule");
						continue;
					}
				}
				else
				{
					System.out.println("Choix incorrect");
					continue;
				}
				break;
			case 5:
				dansMenu = false;
				break;
			default:
				System.out.println("Entrez un chiffre entre 1 et 5");
				break;	
			}

		}
	}


}

