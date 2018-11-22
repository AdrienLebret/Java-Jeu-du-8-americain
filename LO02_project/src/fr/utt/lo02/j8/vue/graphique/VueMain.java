package fr.utt.lo02.j8.vue.graphique;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.exceptions.CarteNonValideException;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Main;

/**
 * <b>VueMain est la classe representant l'interface graphique d'une main</b>
 * <p>
 * Il s'agit d'un panel.
 * La vue main observe une main.
 * </p>
 * Une VueMain est caracterisee par :
 * <ul>
 * <li>la main qu'elle represente</li>
 * <li>une liste de vues de cartes</li>
 * <li>une echelle de taille susceptible d'etre modifiee</li>
 * <li>un booleen indiquant si les cartes doivent etre affichees de face</li>
 * <li>un booleen indiquant si les cartes sont cliquables</li>
 * <li>un controleur graphique</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueMain extends JPanel implements Observer{
	/**
	 * Main que la vue represente.
	 */
	private Main main;
	
	/**
	 * Liste des vues de carte qu'elle contient.
	 */
	private ArrayList<VueCarte> cartes;
	
	/**
	 * Longueur standard d'une vue main.
	 */
	public static final int LONGUEUR_STANDARD = 720;
	
	/**
	 * Hauteur standard d'une vue main.
	 */
	public static final int HAUTEUR_STANDARD = 120;
	
	/**
	 * Echelle a laquelle elle doit etre effichee.
	 */
	public double scale;
	
	/**
	 * Booleen indiquant si les cartes sont affichees de face ou de dos :
	 * <ul>
	 * <li><b>true :</b> affichage des faces</li>
	 * <li><b>false :</b> affichage des dos</li>
	 * </ul>
	 */
	private boolean affichageFace;
	
	/**
	 * Booleen indiquant si les cartes doivent avoir des MouseListener :
	 * <ul>
	 * <li><b>true :</b> cartes cliquables</li>
	 * <li><b>false :</b> cartes non cliquables</li>
	 * </ul>
	 */
	private boolean aListener;
	
	/**
	 * Controleur graphique quif ait le lien avec le modele.
	 */
	private ControleurGraphique controler;
	
	/**
	 * Constructeur VueMain
	 * A la construction d'une VueMain, cree la liste des vues de cartes et ajoute l'objet en tant qu'observateur de la main.
	 * Initialise egalement les attributs aux valeurs par defaut : 
	 * <ul>
	 * <li>affichage des faces : false</li>
	 * <li>cartes cliquables : false</li>
	 * <li>echelle :</li>
	 * </ul>
	 * 
	 * @param main du joueur
	 */
	public VueMain(Main main) {
		this.cartes = new ArrayList<VueCarte>();
		this.main = main;
		this.main.addObserver(this);
		this.affichageFace = false;
		this.scale = 1;
		this.aListener = false;
		initialize();
	}
	
	/**
	 * Initialise l'ensemble de la VueMain.
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(LONGUEUR_STANDARD, HAUTEUR_STANDARD));
		this.setSize(new Dimension(LONGUEUR_STANDARD, HAUTEUR_STANDARD));
		this.setOpaque(false);
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.gridheight = 1;
		contraintes.gridwidth = main.getNombreCartes();
		int insetX = (this.getWidth()-VueCarte.LONGUEUR_STANDARD*main.getNombreCartes())/(main.getNombreCartes()*2);
		contraintes.insets = new Insets(5,insetX,5,insetX);
		
		this.setLayout(layout);
		VueCarte carte;
		for(int i=0; i<main.getNombreCartes(); i++) {
			carte = new VueCarte(main.getCarte(i));
			carte.tournerVersDos();
			this.cartes.add(carte);
			this.add(carte, contraintes);
		}
	}
	
	/**
	 * Met a jour l'affichage de la VueMain : change les cartes presentes.
	 */
	public void update(Observable obs, Object o) {
		this.removeAll();
		this.cartes.clear();
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.gridwidth = main.getNombreCartes();
		contraintes.gridheight = 1;
		Iterator<Carte> it = ((ArrayList<Carte>)o).iterator();
		VueCarte carte;
		while(it.hasNext()) {
			carte = new VueCarte(it.next());
			this.cartes.add(carte);
			if(this.affichageFace) {
				carte.tournerVersFace();
			}else {
				carte.tournerVersDos();
			}
			carte.setDimension(scale);
			
			int insetX = (this.getWidth()-cartes.get(0).getWidth()*main.getNombreCartes())/(main.getNombreCartes()*2);
			contraintes.insets = new Insets(5,insetX,5,insetX);
			this.add(carte, contraintes);
		}
		if(this.aListener) {
			this.ajouterListener(controler);
		}
		this.validate();
	}
	
	/**
	 * Definit les nouvelles dimensions de la VueMain.
	 * 
	 * @param scale l'echelle a laquelle il faut dimensionner la vue
	 */
	public void setDimension(double scale) {
		int longueur = (int) (LONGUEUR_STANDARD * scale);
		int hauteur = (int) (HAUTEUR_STANDARD * scale);
		this.scale = scale;
		
		this.setPreferredSize(new Dimension(longueur, hauteur));
		this.setSize(new Dimension(longueur, hauteur));
		this.removeAll();
		
		Iterator<VueCarte> it = this.cartes.iterator();
		while(it.hasNext()) {
			it.next().setDimension(scale);
		}
		GridBagConstraints contraintes = new GridBagConstraints();
		int insetX = (this.getWidth()-cartes.get(0).getWidth()*main.getNombreCartes())/(main.getNombreCartes()*2);
		contraintes.insets = new Insets(5,insetX,5,insetX);
		contraintes.gridwidth = main.getNombreCartes();
		contraintes.gridheight = 1;
		it = this.cartes.iterator();
		while(it.hasNext()) {
			this.add(it.next(), contraintes);
		}
		
	}
	
	/**
	 * Definit le cote des cartes devant etre affiche.
	 * 
	 * @param affichage true pour afficher les faces des cartes.
	 */
	public void setAffichageFace(boolean affichage) {
		this.affichageFace = affichage;
	}
	
	/**
	 * Retourne toutes les vues de carte : si elles etaient affichees de face, ce sera leur dos qui sera affiche.
	 */
	public void tournerCartes() {
		Iterator<VueCarte> it = this.cartes.iterator();
		while(it.hasNext()) {
			if(!this.affichageFace) {
				it.next().tournerVersFace();
			}else {
				it.next().tournerVersDos();
			}
		}
		this.affichageFace = !this.affichageFace;
	}
	
	/**
	 * Rend les cartes cliquables en leur ajoutant des MouseListener.
	 * 
	 * @param controler le controleur graphique liant la vue au modele
	 */
	public void ajouterListener(ControleurGraphique controler) {
		this.aListener = true;
		this.controler = controler;
		Iterator<VueCarte> it = this.cartes.iterator();
		while(it.hasNext()) {
			it.next().addMouseListener(new java.awt.event.MouseAdapter(){
				@Override
	        	public void mouseReleased(java.awt.event.MouseEvent evt){
					try {
						controler.poser(evt);
					} catch (CarteNonValideException e) {
						((VueCarte)evt.getSource()).setInvalide();
					}
	        	}
			});
		}
	}
	
}
