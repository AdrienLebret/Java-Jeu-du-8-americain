package fr.utt.lo02.j8.vue.graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.modele.moteur.Partie;

/**
 * <b>VueChoixParametres est la classe representant le panel de choix de parametres.</b>
 * <p>
 * Elle herite de JPanel et implemente Observer.
 * Elle est appelee lorsque l'utilisateur pose une carte possedant l'effet changerCouleur.
 * </p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>Un bouton valider</li>
 * <li>La vue qui permettra de choisir les parametres de la future partie</li>
 * </ul>
 * 
 * @see JPanel
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueChoixParametres extends JPanel {

	/**
	 * Bouton valider permettant a l'utilisateur de valider ses differents choix
	 */
	private JButton boutonValider;
	
	/**
	 * Label qui correspond a la description de la fenetre
	 */
	private JLabel labelPA;
	
	/**
	 * Vue permettant de selectionner les parametres de la future partie
	 */
	private VueParametres vp;
	
	/**
	 * Partie en cours
	 */
	private Partie partie;
	
	/**
	 * Controleur graphique qui fait le lien avec le modele
	 */
	private ControleurGraphique cg;
	
	/**
	 * Constructeur VueChoixParametres
	 * <p>
	 * Lors de la construction de l'objet, on initialise le controleur graphique et la partie actuelle.
	 * </p>
	 */
	public VueChoixParametres() {
		cg = new ControleurGraphique();
		this.partie = Partie.getInstance();
		initialize();
	}

	/**
	 * Initialise l'ensemble de la vue des choix de parametres
	 */
	private void initialize() {
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		
		/*
		 * NORTH
		 */
		labelPA = new JLabel("CHOIX DES PARAMETRES");
		Font policeNorth = new Font("Tahoma", Font.BOLD, 30);
		labelPA.setFont(policeNorth);
		labelPA.setForeground(Color.black);
		labelPA.setHorizontalAlignment(JLabel.CENTER);
		labelPA.setOpaque(false);
		this.add(labelPA, BorderLayout.NORTH);
		
		/*
		 * CENTER
		 */
		vp = new VueParametres();
		this.add(vp, BorderLayout.CENTER);
		
		/*
		 * SOUTH
		 */
		boutonValider = new JButton("DEMARRER PARTIE");
		this.boutonValider.setOpaque(false);
		this.add(boutonValider, BorderLayout.SOUTH);
		boutonValider.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				cg.demarrerPartie(); 		
			}
		});
		
	}
	
	/**
	 * Permet l'affichage de l'image de fond present dans le dossier ressource
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
