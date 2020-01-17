package com.intiformation.presentation;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.intiformation.tpcomptes.modele.Client;
import com.intiformation.tpcomptes.modele.Compte;
import com.intiformation.tpcomptes.service.ClientService;
import com.intiformation.tpcomptes.service.CompteService;

public class PartieCentrale extends JPanel implements ActionListener{

	private ClientService clientServ = new ClientService();
	private CompteService compteServ = new CompteService();

	Client client;
	Compte compte;

	JButton infosB = new JButton("Infos du compte");
	JButton depotB = new JButton("Dépôt");
	JButton retraitB = new JButton("Retrait");
	JButton transfertB = new JButton("Transfert");

	public PartieCentrale() {
		GridLayout gl = new GridLayout(2, 2);
		gl.setHgap(10);
		gl.setVgap(10);
		this.setLayout(gl);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		infosB.addActionListener(this);
		depotB.addActionListener(this);
		retraitB.addActionListener(this);
		transfertB.addActionListener(this);

		/*try {
			ImageIcon icon = new ImageIcon(this.getClass().getResource("/res/money-bag.png"));
		    transfertB.setIcon(icon);
		  } catch (NullPointerException ex) {
		    System.out.println(ex);
		  }*/

		this.add(retraitB);
		this.add(depotB);
		this.add(transfertB);
		this.add(infosB);
	}

	public Client getClient() {
		return client;
	}

	public Compte getCompte() {
		return compte;
	}



	public void setClient(Client c)
	{
		this.client = c;
	}

	public void setCompte(Compte c)
	{
		this.compte = c;
	}

	public void retrait()
	{
		String retraitS = JOptionPane.showInputDialog(null, "Combien ?", JOptionPane.QUESTION_MESSAGE);
		Double retrait = null;

		if (retraitS != null)
		{
			try {
				retrait = Double.parseDouble(retraitS);
			} catch (NumberFormatException nfe2) {
				retrait = null;
				JOptionPane.showMessageDialog(null, "Vous devez entrer un nombre", "Info", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (retrait != null)
		{
			if (compteServ.retirerArgent(compte, retrait))
			{
				JOptionPane.showMessageDialog(null, retrait +"€ ont été retirés", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Retrait impossible", "Info", JOptionPane.WARNING_MESSAGE);
			}
		}

	}

	public void depot()
	{
		String depotS = JOptionPane.showInputDialog(null, "Combien ?", JOptionPane.QUESTION_MESSAGE);
		Double depot = null;

		if (depotS != null)
		{
			try {
				depot = Double.parseDouble(depotS);
			} catch (NumberFormatException nfe) {
				depot = null;
				JOptionPane.showMessageDialog(null, "Vous devez entrer un nombre", "Info", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (depot != null)
		{
			if (compteServ.ajouterArgent(compte, depot))
			{
				JOptionPane.showMessageDialog(null, depot +"€ ont été deposés", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Dépôt impossible", "Info", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public void transfert()
	{
		List<Client> toutLesClients = clientServ.findAll();
		List<String> identites = new ArrayList<String>();
		List<Integer> ids = new ArrayList<Integer>();

		for (Client c : toutLesClients) {
			identites.add(c.getPrenom() + " " + c.getNom());
			ids.add(c.getId_Client());
		}

		int indexRM = identites.indexOf(this.client.getPrenom() + " " + this.client.getNom());
		identites.remove(this.client.getPrenom() + " " + this.client.getNom());
		ids.remove(indexRM);

		String[] identitesArr = new String[identites.size()];
		identitesArr = identites.toArray(identitesArr);

		String nom = (String) JOptionPane.showInputDialog(null, 
				"Vers qui ?",
				"Transfert",
				JOptionPane.QUESTION_MESSAGE,
				null,
				identitesArr, identitesArr[0]);

		if (nom != null)
		{
			String transfertS = JOptionPane.showInputDialog(null, "Combien ?", JOptionPane.QUESTION_MESSAGE);

			Double transfert = null;
			if (transfertS != null)
			{
				try {
					transfert = Double.parseDouble(transfertS);
				} catch (NumberFormatException nfe) {
					transfert = null;
					JOptionPane.showMessageDialog(null, "Vous devez entrer un nombre", "Info", JOptionPane.ERROR_MESSAGE);
				}
			}

			int idDebiteur = ids.get(identites.indexOf(nom));

			if (transfert != null)
			{
				if (compteServ.transfererArgent(this.compte, compteServ.findByClientId(idDebiteur),transfert))
				{
					JOptionPane.showMessageDialog(null, transfert +"€ ont été transférés", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Transfert impossible", "Info", JOptionPane.WARNING_MESSAGE);
				}
			}
		}

	}

	public void actionPerformed(ActionEvent e) {

		String labelBouton = ((JButton)e.getSource()).getText();

		switch (labelBouton) {
		case "Infos du compte":
			String msg = "Client : " + client.toString() + "\n" + compte.toString();
			JOptionPane.showMessageDialog(null, msg, "Infos du compte", JOptionPane.INFORMATION_MESSAGE);
			break;
		case "Dépôt":
			this.depot();
			break;
		case "Retrait":
			this.retrait();
			break;
		case "Transfert":
			this.transfert();
			break;
		default:
			break;
		}
	}
}               



