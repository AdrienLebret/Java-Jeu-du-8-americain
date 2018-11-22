package fr.utt.lo02.j8.modele.moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * <b>Pioche est la classe representant la pioche lors d'une partie.</b>
 * <p>
 * Elle herite d'Observable.
 * </p>
 * Une pioche est caracterisee par les informations suivantes :
 * <ul>
 * <li>Un <b>LinkedList</b> de Carte</li>
 * <li>Le nombre de cartes a piocher</li>
 * </ul>
 * 
 * @see Observable
 * @see LinkedList
 * @see Carte
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Pioche extends Observable{
    
	/**
	 * Liste de Cartes constituant la pioche.
	 * 
	 * @see LinkedList
	 * @see Carte
	 */
	private LinkedList<Carte> pioche;
   
    /**
     * Nombre de cartes a piocher
     */
    private int nombreCartesAPiocher;
    
    //******** CONSTRUCTEURS *********
    
    /**
     * Constructeur Pioche.
     * A la construction d'un objet Pioche :
     * <ul>
     * <li><b>pioche</b> est cree,</li>
     * <li><b>nombreCartesAPiocher</b> est initialise a zero.</li>
     * </ul>
     * 
     * @see LinkedList
     */
    public Pioche() {
    	this.pioche = new LinkedList<Carte> ();
    	this.nombreCartesAPiocher = 0;
    }
    
    
    //*********** METHODES ***********    
    
    /**
     * Fait piocher une ou plusieurs cartes au joueur appelant cette methode
     * 
     * @return un ArrayList de Carte
     * 
     * @see ArrayList
     * @see Carte
     * @see Collections
     * @see Talon
     */
    public ArrayList<Carte> piocher() {
    	
    	int quantite; // Definit la quantite de cartes a piocher
    	if(this.nombreCartesAPiocher == 0) {
    		quantite = 1;
    	}else {
    		quantite = this.nombreCartesAPiocher;
    	}
    	this.nombreCartesAPiocher = 0; 
    	
    	ArrayList<Carte> don = new ArrayList<Carte>();
    	
    	for(int i=0; i<quantite; i++) {
    		don.add(this.pioche.pollLast());
    		if(this.pioche.size() <= 0) {
    			Partie.getInstance().getTalon().giveTalonToPioche();
    			Collections.shuffle(this.pioche);
    			System.out.println("Remise du talon dans la pioche");
    		}
    	}
    	
    	//Si l'effet contre etait applique, piocher retire cet effet
    	if(Partie.getInstance().getTalon().getContre()) {
    		Partie.getInstance().getTalon().setContre(false);
    	}
    	return don;
    }
    
    /**
     * Ajoute une <b>Liste de cartes</b> a la pioche.
     * <p>
     * Methode utilisee notamment pour <b>remplir la pioche</b>
     * </p>
     * @param cartes de la liste
     * 
     * @see LinkedList
     * @see Carte
     */
    public void addCarte(List<Carte> cartes) {
    	this.pioche.addAll(cartes);
    }
    
    /**
     * Ajoute <b>une</b> carte a la pioche.
     * 
     * @param carte qui sera ajoute en derniere position dans la liste 
     * 
     * @see LinkedList
     * @see Carte
     */
    public void addCarte(Carte carte) {
    	this.pioche.add(carte);
    }
    
    //********** ACCESSEURS **********	
    
    /**
     * Retourne la taille de la pioche.
     * 
     * @return La taille de la pioche.
     */
    public int getTaille() {
    	return this.pioche.size();
    }
    
    /**
     * Retourne le nombre de cartes a piocher.
     * 
     * @return Le nombre de cartes a piocher.
     */
    public int getNBCartesAPiocher() {
    	return this.nombreCartesAPiocher;
    }
    
    //********** MUTATEURS ***********
    
    /**
     * Augmente le nombre de cartes a pioche de la quantite passee en parametre
     * 
     * @param quantite de cartes a piocher supplementaires.
     * 
     * @see Observable
     */
    public void increaseNbCartesAPiocher(int quantite) {
    	this.nombreCartesAPiocher += quantite;
    	this.setChanged();
    	this.notifyObservers(this.nombreCartesAPiocher);
    }
    
    /**
     * Remet a zero le nombre de cartes a piocher
     */
    public void remiseZeroNbCartesAPiocher() {
    	this.nombreCartesAPiocher = 0;
    }
    
}
