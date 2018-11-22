package fr.utt.lo02.j8.vue.graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Notifications;

/**
 * <b>VueBot est la classe representant la fenetre d'affichage d'un Bot, joueur virtuel</b>
 * <p>
 * Elle herite de JPanel et implemente Observer
 * L'objectif de cette vue est d'afficher la main d'un joueur (carte retournee) et plus particulierement le nombre de cartes restantes du bot
 * </p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>Le joueur c'est-a-dire le bot</li>
 * <li>La vue de sa main</li>
 * <li>Le nom du bot</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueBot extends JPanel implements Observer{
	
	/**
	 * Attribut qui se verra affecter les autres joueurs de la partie (autre que l'utilisateur (joueur reel)
	 */
	Joueur joueur;
	
	/**
	 * Vue de la main du bot, qui affiche les cartes face cache (verso)
	 */
	VueMain main;
	
	/**
	 * Label correspondant au nom du bot
	 */
	JLabel nomLbl;
	
	
	/**
	 * Constructeur VueBot
	 * <p>
	 * Lors de la consctruction de l'objet, celui ajoute un observer sur le joueur mis en parametre
	 * </p>
	 * @param joueur : adversaire de l'utilisateur
	 */
	public VueBot(Joueur joueur) {
		this.joueur = joueur;
		this.joueur.addObserver(this);
		initialize();
	}
	
	/**
	 * Initialise l'ensemble de la vue Bot
	 */
	private void initialize() {
		this.setBackground(Color.orange);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(150, 40));
		
		this.nomLbl = new JLabel(this.joueur.getNom());
		Font police = new Font("Tahoma", Font.BOLD, 11);
		nomLbl.setFont(police);
		nomLbl.setForeground(Color.BLUE);
		nomLbl.setHorizontalAlignment(JLabel.LEFT);
		this.add(nomLbl,BorderLayout.NORTH);
	
		main = new VueMain(this.joueur.getMain());

		main.setDimension(0.2);

		this.add(main, BorderLayout.CENTER);
	}
	
	/**
	 * Met a jour l'affichage de la fenetre
	 * <p>
	 * Lorsque le bot joue, son rectangle devient vert. Sinon il est orange
	 * </p>
	 */
	public void update(Observable obs, Object o) {
		if(o instanceof Notifications) {
			if((Notifications)o == Notifications.debutTour) {
				this.setBackground(Color.GREEN);
			}else
			if((Notifications)o == Notifications.finTour) {
				this.setBackground(Color.ORANGE);
			}
		}
	}
}
