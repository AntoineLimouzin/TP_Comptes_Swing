package com.intiformation.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.intiformation.tpcomptes.modele.Client;
import com.intiformation.tpcomptes.modele.Compte;
import com.intiformation.tpcomptes.modele.Compte_Courant;
import com.intiformation.tpcomptes.modele.Compte_Epargne;
import com.intiformation.tpcomptes.service.ClientService;
import com.intiformation.tpcomptes.service.CompteService;

public class Fenetre extends JFrame {

	//declaration des éléments d'interface
	private JPanel container = new JPanel();
	private JComboBox<String> combo = new JComboBox<>();
	private JLabel clientLabel = new JLabel("Vous êtes : ");
	private ClientService clientServ = new ClientService();
	private CompteService compteServ = new CompteService();
	private PartieCentrale centre = new PartieCentrale();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu gClient = new JMenu("Gérer les clients");
	private JMenu gComptes = new JMenu("Gérer les comptes");
	private JMenuItem addClient = new JMenuItem("Ajouter un client");
	private JMenuItem rmClient = new JMenuItem("Supprimer un client");
	private JMenuItem changeAdresse = new JMenuItem("Modifier une adresse");
	private JMenuItem changeTaux = new JMenuItem("Modifier un taux d'épargne");
	private JMenuItem changeDecouvert = new JMenuItem("Modifier un découvert autorisé");

	private List<Client> listeClients;

	public Fenetre(){

		//Propriétés de la fenêtre
		this.setTitle("Gestion Comptes");
		this.setMinimumSize(new Dimension(300, 300));
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
		this.setVisible(true);

		//Eléments de l'interface
		BorderLayout bl = new BorderLayout();
		container.setLayout(bl);
		container.add(combo);

		JPanel top = new JPanel();
		top.add(clientLabel);
		top.add(combo);

		container.add(top, BorderLayout.NORTH);
		container.add(centre, BorderLayout.CENTER);

		this.setContentPane(container);  

		//Menu
		gClient.add(addClient);
		gClient.add(rmClient);

		gClient.add(changeAdresse);
		gComptes.add(changeTaux);
		gComptes.add(changeDecouvert);

		menuBar.add(gClient);
		menuBar.add(gComptes);

		this.setJMenuBar(menuBar);

		//ActionListeners
		combo.addActionListener(new ComboListener());
		addClient.addActionListener(new AddClientListener());
		rmClient.addActionListener(new RmClientListener());
		changeAdresse.addActionListener(new changeAdressListener());
		changeTaux.addActionListener(new changeTauxListener());
		changeDecouvert.addActionListener(new changeDecouvertListener());
		//TODO taille mini voire taile fixe

		listeClients = clientServ.findAll();

		for (Client client : listeClients) {
			combo.addItem(client.getPrenom() + " " + client.getNom());
		}


	}


	class ComboListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			centre.setClient(listeClients.get(combo.getSelectedIndex()));
			centre.setCompte(compteServ.findByClientId(listeClients.get(combo.getSelectedIndex()).getId_Client()));
		}  
	}    

	class RmClientListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			listeClients = clientServ.findAll();
			List<String> listeNoms = new ArrayList<String>();

			for (Client client : listeClients) {
				listeNoms.add(client.getPrenom() + " " + client.getNom());
			}

			String aSupp = (String) JOptionPane.showInputDialog(null, 
					"Qui?",
					"Infos",
					JOptionPane.QUESTION_MESSAGE,
					null,
					listeNoms.toArray(), listeNoms.get(0));

			if (aSupp != null){
				Client cl = listeClients.get(listeNoms.indexOf(aSupp));
				if (clientServ.supprimer(cl.getId_Client()))
				{
					combo.removeItemAt(listeNoms.indexOf(aSupp));
					JOptionPane.showMessageDialog(null, "Suppression effectuée", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Suppression impossible", "Info", JOptionPane.WARNING_MESSAGE);
				}
			}

			System.out.println();
		} 
	}

	class AddClientListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			FenetreCreationClient fcc = new FenetreCreationClient();
			if (fcc.ajouterClient())
			{
				Client newClient = clientServ.findAll().get(clientServ.findAll().size()-1);
				combo.addItem(newClient.getPrenom() + " " + newClient.getNom());
				listeClients = clientServ.findAll();
			}

		}  
	}    

	class changeAdressListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			listeClients = clientServ.findAll();
			List<String> listeNoms = new ArrayList<String>();

			for (Client client : listeClients) {
				listeNoms.add(client.getPrenom() + " " + client.getNom());
			}

			String qui = (String) JOptionPane.showInputDialog(null, 
					"De qui?",
					"Infos",
					JOptionPane.QUESTION_MESSAGE,
					null,
					listeNoms.toArray(), listeNoms.get(0));
			String adresse = null;
			if (qui != null)
			{
				adresse = JOptionPane.showInputDialog(null, "Nouvelle adresse ?", "Infos", JOptionPane.QUESTION_MESSAGE);
			}

			if (qui != null && adresse != null)
			{
				Client cl = listeClients.get(listeNoms.indexOf(qui));
				cl.setAdresse(adresse);
				if (clientServ.modifier(cl)) {
					JOptionPane.showMessageDialog(null, "Modification effectuée", "Info", JOptionPane.INFORMATION_MESSAGE);
					if (cl.getNom().equals(centre.getClient().getNom()) && cl.getPrenom().equals(centre.getClient().getPrenom()))
					{
						centre.setClient(cl);
						centre.setCompte(compteServ.findByClientId(cl.getId_Client()));
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Modification impossible", "Info", JOptionPane.WARNING_MESSAGE);
				}

			}
		}  
	}    

	class changeTauxListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			listeClients = clientServ.findAll();
			List<String> listeNoms = new ArrayList<String>();
			List<Client> listeEpargnants = new ArrayList<Client>();

			for (Client client : listeClients) {
				if (compteServ.findByClientId(client.getId_Client()).getClass().getName() == "com.intiformation.tpcomptes.modele.Compte_Epargne")
				{
					listeNoms.add(client.getPrenom() + " " + client.getNom());
					listeEpargnants.add(client);
				}
			}

			String qui = (String) JOptionPane.showInputDialog(null, 
					"De qui?",
					"Infos",
					JOptionPane.QUESTION_MESSAGE,
					null,
					listeNoms.toArray(), listeNoms.get(0));

			String tauxS = "";
			Double taux = null;
			Client cl = null;
			Compte_Epargne compte = null;
			if (qui != null)
			{
				cl = listeEpargnants.get(listeNoms.indexOf(qui));
				compte = (Compte_Epargne) compteServ.findByClientId(cl.getId_Client());
				try {
					tauxS = JOptionPane.showInputDialog(null, "Quel taux ?", "Infos", JOptionPane.QUESTION_MESSAGE);
					taux = Double.parseDouble(tauxS);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Entrez un nombre", "Info", JOptionPane.ERROR_MESSAGE);
					taux = null;
				}
			}

			if (taux != null)
			{
				compte.setTaux(taux);
				if (compteServ.modifier(compte))
				{
					JOptionPane.showMessageDialog(null, "Modification effectuée", "Info", JOptionPane.INFORMATION_MESSAGE);
					if (cl.getNom().equals(centre.getClient().getNom()) && cl.getPrenom().equals(centre.getClient().getPrenom()))
					{
						centre.setClient(cl);
						centre.setCompte(compteServ.findByClientId(cl.getId_Client()));
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Modification impossible", "Info", JOptionPane.WARNING_MESSAGE);
				}
			}


		}  
	}

	class changeDecouvertListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			listeClients = clientServ.findAll();
			List<String> listeNoms = new ArrayList<String>();
			List<Client> listeCourants = new ArrayList<Client>();

			for (Client client : listeClients) {
				if (compteServ.findByClientId(client.getId_Client()).getClass().getName() == "com.intiformation.tpcomptes.modele.Compte_Courant")
				{
					listeNoms.add(client.getPrenom() + " " + client.getNom());
					listeCourants.add(client);
				}
			}

			String qui = (String) JOptionPane.showInputDialog(null, 
					"De qui?",
					"Infos",
					JOptionPane.QUESTION_MESSAGE,
					null,
					listeNoms.toArray(), listeNoms.get(0));

			String decS = "";
			Double dec = null;
			Client cl = null;
			Compte_Courant compte = null;

			if (qui != null)
			{
				cl = listeCourants.get(listeNoms.indexOf(qui));
				compte = (Compte_Courant) compteServ.findByClientId(cl.getId_Client());
				try {
					decS = JOptionPane.showInputDialog(null, "Quel découvert ?", "Infos", JOptionPane.QUESTION_MESSAGE);
					dec = Double.parseDouble(decS);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Entrez un nombre", "Info", JOptionPane.ERROR_MESSAGE);
				}
			}


			if (dec != null)
			{
				if (compte.getSolde()>-dec)
				{
					compte.setDecouvert_autorise(dec);
					if (compteServ.modifier(compte))
					{
						JOptionPane.showMessageDialog(null, "Modification effectuée", "Info", JOptionPane.INFORMATION_MESSAGE);
						if (cl.getNom().equals(centre.getClient().getNom()) && cl.getPrenom().equals(centre.getClient().getPrenom()))
						{
							centre.setClient(cl);
							centre.setCompte(compteServ.findByClientId(cl.getId_Client()));
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Modification impossible", "Info", JOptionPane.WARNING_MESSAGE);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Solde < découvert autorisé", "Info", JOptionPane.WARNING_MESSAGE);
				}
			}
		}  
	}

}
