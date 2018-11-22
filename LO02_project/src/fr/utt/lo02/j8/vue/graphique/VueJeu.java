package fr.utt.lo02.j8.vue.graphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Notifications;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.strategies.StrategieUser;

/**
 * <b>VueJeu est la classe representant l'interface graphique d'une partie</b>
 * 
 * Une vue de jeu est caracterisee par :
 * <ul>
 * <li>une partie attribuee definitivement</li>
 * <li>un plateau</li>
 * <li>une vue de la main de l'utilisateur</li>
 * <li>un panel comprenant les vues des differents bots</li>
 * <li>un panel d'information</li>
 * <li>un bouton pour le changement de variante</li>
 * <li>un bouton pour l'annonce de "Carte"</li>
 * <li>un bouton pour l'annonce de "Contre-Carte"</li>
 * <li>un bouton pour passer en mode Bot</li>
 * <li>un controleur graphique</li>
 * </ul>
 * Ils ne sont pas modifiables.
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueJeu extends JPanel implements Observer{
	
	/**
	 * Partie en cours.
	 * Elle n'est pas modifiable.
	 */
	Partie partie;
	
	/**
	 * Plateau de jeu.
	 */
	Plateau plateau;
	
	/**
	 * Vue de la main de l'utilisateur.
	 * Il n'est pas modifiable.
	 */
	VueMain mainUser;
	
	/**
	 * Panel comprenant les vues des differents bots.
	 */
	JPanel botsPanel;
	
	/**
	 * Panel d'informations.
	 */
	InformationsPanel infosPanel;
	
	/**
	 * Panel rassemblant les boutons.
	 */
	JPanel boutonsPanel;
	
	/**
	 * Bouton pour le changement de variante.
	 */
	JButton varianteBtn;
	
	/**
	 * Bouton pour l'annonce de "Carte".
	 */
	JButton carteBtn;
	
	/**
	 * Bouton pour l'annonce de "Contre-Carte".
	 */
	JButton contreCarteBtn;
	
	/**
	 * Bouton pour passer en mode Bot
	 */
	JButton botBtn;
	
	/**
	 * Controleur graphique qui fait le lien avec le modele.
	 * Il n'est pas modifiable.
	 */
	private ControleurGraphique controler;
	
	/**
	 * Constructeur VueJeu.
	 * <p>
	 * Initialise l'objet VueJeu et l'ajoute en tant qu'observateur du joueur correspondant a l'utilisateur.
	 * </p>
	 * 
	 * @param partie actuelle 
	 * @param controler graphique
	 */
	public VueJeu(Partie partie, ControleurGraphique controler) {
		this.partie = partie;
		this.controler = controler;
		partie.getJoueur(0).addObserver(this);
		initialize();
	}
	
	/**
	 * Initialise l'ensemble de la vue de jeu.
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		
		this.plateau = new Plateau(this.partie.getTalon(), this.controler);
		this.add(plateau, BorderLayout.CENTER);
		
		this.infosPanel = new InformationsPanel();
		this.add(infosPanel, BorderLayout.WEST);
		
		//Creation des vues mains en fonction du nombre de joueur
		this.mainUser = new VueMain(partie.getJoueur(0).getMain());
		this.mainUser.ajouterListener(controler);
		this.mainUser.tournerCartes();
		this.add(mainUser, BorderLayout.SOUTH);
		
		this.botsPanel = new JPanel();
		this.botsPanel.setOpaque(false);
		VueBot bot;
		for(int i=1; i<partie.getNombreJoueur(); i++) {
			bot = new VueBot(partie.getJoueur(i));
			this.botsPanel.add(bot);
		}
		this.add(botsPanel, BorderLayout.NORTH);
		
		//Boutons : variante, annonce
		this.boutonsPanel = new JPanel();
		this.boutonsPanel.setSize(200, 500);
		this.boutonsPanel.setOpaque(false);
		this.boutonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.gridwidth = 1;
		contraintes.gridheight = 3;
		contraintes.gridx = 0;
		contraintes.gridy = GridBagConstraints.RELATIVE;
		contraintes.insets = new Insets(10,10,10,10);
		
		this.botBtn = new JButton("Passer en Bot");
		this.botBtn.setPreferredSize(new Dimension(150, 40));
		this.botBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controler.passerBot();
			}
		});
		this.boutonsPanel.add(botBtn, contraintes);
		
		this.varianteBtn = new JButton("Variante");
		this.varianteBtn.setPreferredSize(new Dimension(150, 40));
		this.varianteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VueChoixVariante vp = new VueChoixVariante(null, "CHOIX DE LA NOUVELLE VARIANTE", true, partie);
			}  
		});
		this.boutonsPanel.add(varianteBtn, contraintes);
		
		this.carteBtn = new JButton("Carte");
		this.carteBtn.setPreferredSize(new Dimension(150, 40));
		this.boutonsPanel.add(carteBtn, contraintes);
		this.carteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controler.annoncerCarte();
			}  
		});
		
		this.contreCarteBtn = new JButton("Contre-Carte");
		this.contreCarteBtn.setPreferredSize(new Dimension(150, 40));
		this.contreCarteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controler.annoncerContreCarte();
			}  
		});
		this.boutonsPanel.add(contreCarteBtn, contraintes);
		
		this.add(boutonsPanel, BorderLayout.EAST);
	}
	
	/**
	 * Met a jour l'affichage de la vue de jeu en changeant le texte du bouton perettant de passer en bot et affichant la jdialog de changement de couleur lorsqu'il y en a besoin.
	 *  
	 */
	public void update(Observable obs, Object o) {
		if(o instanceof Notifications) {
			if((Notifications)o == Notifications.changementCouleur) {
				VueChoixCouleur choixCouleur = new VueChoixCouleur(this.controler, this.partie.getTalon(), this);
				choixCouleur.setVisible(true);
			}
			if((Notifications)o == Notifications.changementStrategie) {
				if(((Joueur)obs).getStrategie() instanceof StrategieUser) {
					this.botBtn.setText("Passer en Bot");
				}else if(obs == partie.getJoueur(0)){
					this.botBtn.setText("Reprendre");
				}
			}
		}
	}
	
	/**
	 * 
	 */
	public void paintComponent(Graphics g) {
		try {
			Image img = ImageIO.read(new File("ressources/images/fonds/Menu.jpg"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
