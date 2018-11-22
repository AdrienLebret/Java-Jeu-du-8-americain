package fr.utt.lo02.j8.modele.moteur;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <b>Paquet est la classe representant un paquet de carte.</b>
 * <p>
 * Un paquet est caracterise par les informations suivantes :
 * <ul>
 * <li>Une taille qui correspond au nombre de cartes du paquet : 52 / 54 / 32 /34</li>
 * <li>Un ArrayList de Carte, qui contient l'ensemble du jeu de cartes</li>
 * </ul>
 * 
 * @see Carte
 * @see Partie
 * @see Joueur
 * @see Talon
 * @see Pioche
 * @see ArrayList
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Paquet {
	/**
	 * Nombre de cartes du paquet.
	 * Elle peut prendre les valeurs 52 ou 32 ou 54 (avec Jokers) ou 34 (avec Jokers)
	 */
    private int taille;
    
    /**
     * Liste de Cartes.
     * 
     * @see Carte
     * @see ArrayList
     */
    private ArrayList<Carte> jeuCarte;
    
    //******** CONSTRUCTEUR *********
    
    /**
     * Constructeur Paquet.
     * A la construction d'un objet Paquet, nous initialisons le paquet en fonction du parametre. 
     * Il cree ainsi le nombre de cartes en question a l'aide de 2 boucles "For" imbriquees
     * <ul>
	 * <li>la 1ere : permet d'affecter une hauteur(valeur) a la carte,</li>
	 * <li>la 2nde (imbriquee a la 1ere) affecte une couleur a la carte et ajoute la carte au paquet.</li>
	 * </ul>
     * @param tailleP taille du paquet dont les valeurs acceptees sont : 52 / 32 / 54 / 34
     * @see Carte#Carte(int, int)
     * 
     */
    public Paquet (int tailleP) {
    	this.jeuCarte = new ArrayList<Carte> ();
    	this.taille = tailleP;
    	
    	if (tailleP == 52 || tailleP == 54) {
    		
    		// Paquet de 52 cartes.
    		for(int hauteur = 2; hauteur < Carte.HAUTEURS.length-1; hauteur++) {
    			for(int couleur = 0; couleur < Carte.COULEURS.length-1; couleur++) {
    				this.jeuCarte.add(new Carte(hauteur, couleur));
    			}
    		}
    		//Paquet de 54 cartes : ajout des jokers
    		if (tailleP == 54) {
    			this.jeuCarte.add(new Carte(15, 4));
    			this.jeuCarte.add(new Carte(15, 4));
    		}
    	} else if (tailleP == 32 || tailleP == 34) {
       		//Paquet de 32 cartes
    		for(int hauteur = 7; hauteur < Carte.HAUTEURS.length-1; hauteur++) {
    			for(int couleur = 0; couleur < Carte.COULEURS.length-1; couleur++) {
    				this.jeuCarte.add(new Carte(hauteur, couleur));
    			}
    		}
    		//Paquet de 34 cartes : ajout des jokers
    		if (tailleP == 54) {
    			this.jeuCarte.add(new Carte(15, 4));
    			this.jeuCarte.add(new Carte(15, 4));
    		}
   		} else {
    		throw new IllegalArgumentException();
    	}
    }
    
    
    //*********** METHODES ***********

    
    /**
     * Melange les cartes
     * 
     * @see Collections
     */
    public void melanger() {
    	Collections.shuffle(this.jeuCarte);
    }
    
    /**
     * <p>
     * Methode permettant la distribution des cartes aux joueurs de la partie
     * </p>
     * <p>
     * Distribue <b>8 cartes</b> par joueur et depose <b>1 carte</b> sur le dessus du talon.
     * </p>
     * <p>
     * Le <b>reste</b> constituera la pioche
     * </p>
     * @param listeJoueur : joueurs qui composent la partie
     * 
     * @see Main
     * @see Joueur
     * @see Talon
     * @see Pioche
     */
   public void distribuer(List<Joueur> listeJoueur) {
    	int nbCartesDonnees = 0;
    	
    	for(int i=1; i <= 8; i++) {
    		for(int j=0; j<listeJoueur.size(); j++) {
    			listeJoueur.get(j).getMain().addCarte(this.jeuCarte.get(this.jeuCarte.size()-1 - nbCartesDonnees));
    			nbCartesDonnees++;
    		}
    	}
    	
    	Partie.getInstance().getTalon().addCarte(jeuCarte.get(this.jeuCarte.size()-1 - nbCartesDonnees));
    	nbCartesDonnees++;
    	
    	for(int i = 0; i < this.jeuCarte.size()-nbCartesDonnees; i++) {
    		Partie.getInstance().getPioche().addCarte(this.jeuCarte.subList(0, this.jeuCarte.size() - nbCartesDonnees));
    	}
    	
    }
    
    /**
     * <b>Fusion de deux paquets </b>: le paquet en parametre est ajoute dans le paquet courant
     * 
     * @param paquet qui sera rajoutee au paquet actuel
     * 
     * @see ArrayList
     */
    public void fusionnerPaquet(Paquet paquet){
    	this.jeuCarte.addAll(paquet.getPaquet());
    	this.taille = this.jeuCarte.size();
    }
    
    
    //********** ACCESSEURS **********	
   
    /**
     * Retourne la taille du paquet.
     * 
     * @return La taille du paquet.
     */
    public int getTaille() {
    	return this.taille;
    }
    
    /**
     * Retourne une carte a  une position donnee.
     * 
     * @param position de la carte que l'on souhaite retourner
     * 
     * @return Une carte a  une position donnee
     */
    public Carte getCarte(int position) {
    	return this.jeuCarte.get(position);
    }
    
    /**
     * Retourne le paquet de carte.
     * 
     * @return Le paquet de carte.
     */
    public List<Carte> getPaquet() {
    	return this.jeuCarte;
    }    
    

}
