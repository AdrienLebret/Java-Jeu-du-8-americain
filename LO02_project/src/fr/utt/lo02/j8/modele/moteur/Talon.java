package fr.utt.lo02.j8.modele.moteur;
import java.util.LinkedList;
import java.util.Observable;

import fr.utt.lo02.j8.exceptions.CarteNonValideException;

/**
 * <b>Talon est la classe representant le talon lors d'une partie.</b>
 * <p>
 * Elle herite d'Observable.
 * </p>
 * Un talon est caracterise par les informations suivantes :
 * <ul>
 * <li>Un <b>LinkedList</b> de Carte, qui contient les cartes jouees par le joueur</li>
 * <li>La <b>couleur</b> de la carte sur le dessus du talon</li>
 * <li>Un booleen <b>contre</b> indiquant s'il y a ou non besoin de contrer la carte sur le dessus du talon</li>
 * </ul>
 * 
 * @see Observable
 * @see LinkedList
 * @see Pioche
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Talon extends Observable{
	/**
	 * Liste de Cartes constituant le talon.
	 * 
	 * @see LinkedList
	 * @see Carte
	 */
    private LinkedList<Carte> talon;
    
    /**
     * String indiquant la couleur de la carte situee sur le dessus du talon
     */
    private String couleur;
    
    /**
     * Booleen indiquant s'il y a ou non besoin de contrer la carte sur le dessus du talon
     */
    private boolean contre;
    
    //******** CONSTRUCTEURS *********
    
    /**
     * Constructeur Talon.
     * A la construction d'un objet Talon :
     * <ul>
     * <li><b>talon</b> est cree,</li>
     * <li><b>contre</b> est initialise a false car aucune carte ne constitue le talon a sa creation.</li>
     * </ul>
     * 
     * @see LinkedList
     */
    public Talon() {
    	this.talon = new LinkedList<Carte>();
    	this.contre = false;
    }

    
    //*********** METHODES ***********
    
    /**
     * Ajoute une carte au talon.
     * 
     * @param carte posee sur le dessus du talon
     * @throws CarteNonValideException si la carte n'est pas valide
     * 
     * @see CarteNonValideException
     */
    public void poserCarte(Carte carte) throws CarteNonValideException {
    	if (carte.estJouable()) {
    		this.talon.add(carte);
    		this.couleur = carte.getCouleur();
    		this.setChanged();
    		this.notifyObservers(carte);
    	}
    	else {
    		throw new CarteNonValideException();
    	}
    }
    
    
    /**
     * <p>
     * Cas ou la pioche est <b>vide</b> : 
     * </p>
     * <p>
     * Le talon devient la pioche. Tout en laissant la derniere carte posee sur le dessus du talon.
	 * </p>
	 * 
	 * @see Pioche
     */
    public void giveTalonToPioche() {
    	while(this.talon.size() > 1) {
    		Partie.getInstance().getPioche().addCarte(this.talon.pollFirst());
    	}
    }
    
    /**
     * Ajoute <b>une</b> carte sur le dessus du talon.
     * <p>
     * Couleur prend ainsi la couleur de la carte posee. 
     * </p>
     * 
     * @param carte qui sera ajoute en derniere position dans la liste 
     * 
     * @see LinkedList
     * @see Carte
     * @see Talon#setCouleur(String)
     */
    public void addCarte(Carte carte) {
    	this.talon.add(carte);
    	this.couleur = carte.getCouleur();
    }
    
    //********** ACCESSEURS **********	
    
    /**
     * Retourne la carte situee sur le dessus du talon.
     * @return la carte situee sur le dessus du talon.
     */
    public Carte getCarteDessus() {
    	return this.talon.peekLast();
    }
    
    /**
     * Retourne vraie si la couleur a ete changee.
     * 
     * @return <ul>
	 * <li><b>true :</b> la couleur a change</li>
	 * <li><b>false :</b> la couleur n'a pas change </li>
	 * </ul>
     */
    public boolean isCouleurChangee() {
    	if(this.couleur != this.getCarteDessus().getCouleur()) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    /**
     * Retourne la couleur du talon.
     * <p>
     * C'est-a-dire la couleur de la carte situee sur le dessus du talon
     * </p>
     * 
     * @return La couleur du talon.
     * 
     */
    public String getCouleur() {
    	return this.couleur;
    }
    
    /**
     * Retourne s'il y a ou non besoin de contrer
     * 
     * @return <ul>
	 * <li><b>true :</b> le joueur suivant peut contrer</li>
	 * <li><b>false :</b> pas de contre possible</li>
	 * </ul>
     */
    public boolean getContre() {
    	return this.contre;
    }
    
    //********* MUTATEURS *********
    
    /**
     * Change la couleur du talon.
     * 
     * @param couleur qu'aura le talon suite a l'appel de cette methode
     */
    public void setCouleur(String couleur) {
    	this.couleur = couleur;
    	this.setChanged();
    	this.notifyObservers(couleur);
    }
    
    /**
     * Met a jour si la carte du dessus est contrable
     * 
     * @param bool true si contre
     */
    public void setContre(boolean bool){
    	this.contre = bool;
    }

}
