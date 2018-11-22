package fr.utt.lo02.j8.vue.graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fr.utt.lo02.j8.modele.effets.ChangerSens;
import fr.utt.lo02.j8.modele.effets.FairePiocher;
import fr.utt.lo02.j8.modele.effets.PasserTour;
import fr.utt.lo02.j8.modele.effets.Rejouer;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Notifications;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Pioche;
import fr.utt.lo02.j8.modele.moteur.Talon;
import fr.utt.lo02.j8.modele.strategies.StrategieUser;

public class InformationsPanel extends JScrollPane implements Observer/*, Runnable*/{
	
	private JLabel infosLabel;
	private JTextArea infosText;
	
	public InformationsPanel() {
		Partie partie = Partie.getInstance();
		for(int i=0; i<partie.getNombreJoueur(); i++) {
			partie.getJoueur(i).addObserver(this);
		}
		partie.getTalon().addObserver(this);
		partie.getPioche().addObserver(this);
		for(int i=0; i<partie.getPaquet().getTaille();i++) {
			partie.getPaquet().getCarte(i).addObserver(this);
		}
		this.initialize();
	}
	
	private void initialize() {
		this.setPreferredSize(new Dimension(200, 400));
		//this.infosLabel = new JLabel();
		Font police = new Font("Tahoma", Font.BOLD, 12);
		//infosLabel.setFont(police);
		//infosLabel.setForeground(Color.RED);
		//infosLabel.setHorizontalAlignment(JLabel.CENTER);
		//this.add(this.infosLabel);
		
		this.infosText = new JTextArea();
		this.infosText.setFont(police);
		this.infosText.setForeground(Color.DARK_GRAY);
		this.infosText.setPreferredSize(new Dimension(200,400));
		this.infosText.setLineWrap(true);
		this.infosText.setEditable(false);
		this.setViewportView(infosText);
		
		/*this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});*/
		//this.add(infosText);
	}
	
	/*public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.infosLabel.setText("");
	}*/
	
	public void update(Observable obs, Object o) {
		//Thread thread = new Thread(this);
		if(obs instanceof Talon) {
			if(o instanceof String) {
				//this.infosLabel.setText("Changement de couleur : " + (String)o);
				this.infosText.append("Changement de couleur : \n  " + (String)o + "\n");
			}
		}
		if(obs instanceof Carte) {
			if(o instanceof ChangerSens) {
				//this.infosLabel.setText("Changement de Sens");
				this.infosText.append("Changement de Sens\n");
			}else if(o instanceof FairePiocher) {
				//this.infosLabel.setText("Le joueur suivant piochera " + Partie.getInstance().getPioche().getNBCartesAPiocher() + " cartes");
				this.infosText.append(Partie.getInstance().getJoueurSuivant().getNom() +" pioche\n");
			}else if(o instanceof PasserTour) {
				//this.infosLabel.setText("Le joueur suivant passe son tour");
				this.infosText.append(Partie.getInstance().getJoueurSuivant().getNom() + " passe son tour\n");
			}else if(o instanceof Rejouer) {
				//this.infosLabel.setText("Le joueur rejoue");
				this.infosText.append(Partie.getInstance().getJoueurActuel().getNom() + " rejoue\n");
			}
		}
		if(obs instanceof Pioche) {
			this.infosText.append("  + " + o + " cartes\n");
		}
		if(obs instanceof Joueur) {
			if(o == Notifications.debutTour && ((Joueur)obs).getStrategie() instanceof StrategieUser) {
				this.infosText.append("A VOUS\n");
				if(Partie.getInstance().getTalon().getContre()) {
					this.infosText.append("VOUS DEVEZ CONTRER\n");
				}
			}else if(o == Notifications.annonceCarte) {
				this.infosText.append(((Joueur)obs).getNom() + " : \"Carte\"\n");
			}else if( o == Notifications.annonceContreCarte) {
				this.infosText.append(((Joueur)obs).getNom() + " : \"Contre-Carte\"\n");
			}else if(o == Notifications.estContre) {
				this.infosText.append(((Joueur)obs).getNom() + " a ete contre\n");
			}
		}
		//thread.start();
	}
	
	/*public Dimension getPreferredScrollableViewportSize() {
		
	}*/
	
}
