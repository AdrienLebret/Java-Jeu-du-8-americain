package fr.utt.lo02.j8.vue.graphique;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Notifications;
import fr.utt.lo02.j8.modele.moteur.Partie;

/**
 * <b>Fenetre est la classe representant la fenetre principale du jeu.</b>
 * <p>
 * Elle herite de JFrame et implement Observer.
 * </p>
 * <p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>Les JPanel pan, container et tabScorePanels</li>
 * <li>Des panels heritant de VuePseudo, VueJeu, VueChoixParametres, VueScore</li>
 * </ul>
 * 
 * @see JFrame
 * @see VueLancementJeu
 * @see VueChoixParametres
 * @see VuePseudo
 * @see VueScore
 * @see ControleurGraphique
 * @see Observer
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */ 
public class Fenetre extends JFrame implements Observer{
	
	private JPanel container;
	private VueLancementJeu demarragePanel;
	private VuePseudo pseudoPanel;
	private VueChoixParametres parametresPanel;
	private VueJeu jeuPanel;
	private VueScore scorePanel;
	
	private ControleurGraphique controler;
	/**
	 * Constructeur Fenetre
	 * <p>
	 * Ajoute un observer sur la partie et sur le joueur. Instanciation du controleur graphique. Lancement de la methode d'initialisation de la fenetre
	 * </p>
	 */
	public Fenetre() {
		Partie.getInstance().addObserver(this);
		Partie.getInstance().getJoueur(0).addObserver(this);
		initialize();
		controler = new ControleurGraphique();
	}
	
	/**
	 * Initialise l'ensemble de la fenetre JFrame
	 */
	private void initialize(){
		this.setTitle("Jeu du 8 Americain");
		this.setIconImage(new ImageIcon("ressources/images/fonds/Logo_J8.jpg").getImage());
		this.setSize(1024, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.pseudoPanel = new VuePseudo();
		this.parametresPanel = new VueChoixParametres();
		VueLancementJeu vp = new VueLancementJeu(null, "Jeu du 8 Americain", true);
		
		
		
		this.setContentPane(pseudoPanel);
		this.setVisible(true);
	}
	
	/**
	 * Met a jour l'affichade la fenetre en fonction des notifications recue : (demarrage partie / affichage des scores)
	 */
	public void update(Observable obs, Object o) {
		if(obs instanceof Partie) {
			if(o instanceof Partie) {
				((Partie)o).addObserver(this);
				((Partie)o).getTalon().addObserver(this);
				this.controler.setPartie((Partie)o);
			}else if (o instanceof Notifications){
				switch((Notifications)o) {
				case demarragePartie:
					Partie.getInstance().getJoueur(0).deleteObserver(jeuPanel);
					this.jeuPanel = new VueJeu(Partie.getInstance(), this.controler);
					this.setContentPane(this.jeuPanel);
					this.validate();
					break;
				case affichageScore:
					this.scorePanel = new VueScore(null, "Jeu du 8 Americain - Scores", true, Partie.getInstance());
				}
			}
		}else
		if(obs instanceof Joueur) {
			if((Notifications)o == Notifications.changementNom) {
				this.setContentPane(this.parametresPanel);
				Partie.getInstance().getJoueur(0).deleteObserver(this);
				this.validate();
			}
		}
	}
	
	
}
