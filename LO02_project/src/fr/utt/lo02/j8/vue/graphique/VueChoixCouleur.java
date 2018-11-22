package fr.utt.lo02.j8.vue.graphique;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Talon;

/**
 * <b>VueChoixCouleur est la classe representant la fenetre des choix de couleurs.</b>
 * <p>
 * Elle herite de JDialog et implemente Observer.
 * Elle est appelee lorsque l'utilisateur pose une carte possedant l'effet changerCouleur.
 * </p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>Le controleur graphique</li>
 * <li>La VueJeu</li>
 * </ul>
 * 
 * @see VueJeu
 * @see JDialog
 * @see Observer
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueChoixCouleur extends JDialog implements Observer{
	
	/**
	 * Controleur graphique qui fait le lien le modele
	 * Il n'est pas modifiable
	 */
	private ControleurGraphique controler;
	
	/**
	 * Vue Jeu qui correspond au panel affichant la partie en cours
	 */
	private VueJeu vueJeu;
	
	/**
	 * Constructeur VueChoixCouleur
	 * 
	 * <p>
	 * Lors de la consctruction de l'objet, celui ajoute un observer sur le talon de la partie
	 * </p>
	 * 
	 * @param controler graphique a utiliser
	 * @param talon de la partie
	 * @param vueJeu affecte a l'attribut vueJeu
	 */
	public VueChoixCouleur(ControleurGraphique controler, Talon talon, VueJeu vueJeu) {
		talon.addObserver(this);
		this.controler = controler;
		this.vueJeu = vueJeu;
		initialize();
	}
	
	/**
	 * Initialise l'ensemble de la vue de choix de couleur
	 */
	public void initialize() {
		this.requestFocus();
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setSize(180, 240);
		this.getContentPane().setLayout(new GridLayout(2,2));
		for(int i=0; i<4; i++) {
			JPanel couleurPanel = new JPanel();
			JLabel couleurLabel = new JLabel();
			couleurLabel.setIcon(new ImageIcon("ressources/images/cartes/" + Carte.COULEURS[i] + ".png"));
			couleurPanel.add(couleurLabel);
			couleurPanel.setName(Carte.COULEURS[i]);
			couleurPanel.addMouseListener(new java.awt.event.MouseAdapter(){
				@Override
	        	public void mouseReleased(java.awt.event.MouseEvent evt){
					controler.changerCouleur(evt);
	        	}
			});
			this.getContentPane().add(couleurPanel);
		}
	}
	
	/**
	 * Met a jour l'affichage de la fenetre.
	 * <p>
	 * Lorsque l'utilisateur a fait son choix, la fenetre se ferme et le changement a ete effectue.
	 * </p>
	 */
	public void update(Observable obs, Object o) {
		if(o instanceof String) {
			this.dispose();
		}
	}
}
