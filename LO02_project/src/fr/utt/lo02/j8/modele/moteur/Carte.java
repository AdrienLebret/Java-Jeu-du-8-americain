package fr.utt.lo02.j8.modele.moteur;
import java.util.Observable;

import fr.utt.lo02.j8.modele.effets.ChangerCouleur;
import fr.utt.lo02.j8.modele.effets.ChangerCouleurStopAttaques;
import fr.utt.lo02.j8.modele.effets.ChangerSens;
import fr.utt.lo02.j8.modele.effets.Effet;
import fr.utt.lo02.j8.modele.effets.Effets;
import fr.utt.lo02.j8.modele.effets.FairePiocher;
import fr.utt.lo02.j8.modele.effets.FairePiocherContre;
import fr.utt.lo02.j8.modele.effets.FairePiocherSansRecours;
import fr.utt.lo02.j8.modele.effets.PasserTour;
import fr.utt.lo02.j8.modele.effets.Rejouer;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilite;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilites;
import fr.utt.lo02.j8.modele.jouabilite.JouableSurContre;
import fr.utt.lo02.j8.modele.jouabilite.JouableSurTout;
import fr.utt.lo02.j8.modele.jouabilite.JouableSurToutEtContre;
import fr.utt.lo02.j8.modele.jouabilite.Standard;

/**
 * <b>Carte est la classe representant une carte de jeu.</b>
 * <p>
 * Elle herite d'Observable.
 * </p>
 * Une carte est caracterisee par les informations suivantes :
 * <ul>
 * <li>Une hauteur (valeur) attribuee definitivement. Elle est comprise entre 2 et As</li>
 * <li>Une couleur attribuee definitivement. Elle peut prendre les valeurs coeur, carreau, trefle ou pique</li>
 * <li>Un effet susceptible d'etre change.</li>
 * <li>Une jouabilite susceptible d'etre changee</li>
 * </ul>
 * 
 * @see Effet
 * @see Jouabilite
 * @see Observable
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Carte extends Observable{
	/**
	 * Tableau recensant les valeurs possibles des cartes
	 */
    public final static String HAUTEURS[] = {null, null, "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi", "As", "Joker"};
    //											0	 1		2	 3	  4	   5	6	 7    8    9    10      11      12		13	  14  	  15		
    /**
     * Tableau recensant les couleurs possibles des cartes
     */
    public final static String COULEURS[] = {"Coeur", "Carreau", "Trefle", "Pique", ""};
    //							 	  			0		  1			2		  3		 4
    
    /**
     * Hauteur de la carte.
     * Elle est comprise entre 2 et As.
     * Elle n'est pas modifiable.
     * 
     * @see Carte#Carte(int, int)
     * @see Carte#getHauteur()
     * @see Carte#HAUTEURS
     */
    private String hauteur;
    
    /**
     * Couleur de la carte.
     * Elle peut prendre les valeurs Coeur, Carreau, Trefle et Pique.
     * Elle n'est pas modifiable.
     * 
     * @see Carte#Carte(int, int)
     * @see Carte#getCouleur()
     * @see Carte#COULEURS
     */
    private String couleur;
    
    /**
     * Effet de la carte.
     * Cet effet est modifiable.
     * 
     * @see Effet
     * @see Carte#getEffet()
     * @see Carte#setEffet(Effet)
     * @see Carte#aEffet()
     * @see Carte#aEffet(Effets)
     */
    private Effet effet;
    
    /**
     * Jouabilite de la carte.
     * Cette jouabilite est modifiable.
     * 
     * @see Jouabilite
     * @see Carte#getJouabilite()
     * @see Carte#setJouabilite(Jouabilite)
     * @see Carte#aJouabilite(Jouabilites)
     */
    private Jouabilite jouabilite;
    
    
    //******** CONSTRUCTEUR *********
    
    /**
     * 
     * Constructeur Carte.
     * 
     * <p>
     * A la construction d'un objet Carte, la jouabilite est fixee a Standard et l'effet est nul.
     * </p>
     * 
     * @param h indice de la hauteur (valeur) de la carte, compris entre 2 et 15 inclus
     * @param c indice de la couleur de la carte, compris entre 0 et 4 inclus
     * 
     * @see Carte#hauteur
     * @see Carte#couleur
     * @see Carte#effet
     * @see Carte#jouabilite
     * @see Standard
     */
    public Carte(int h, int c){
	    this.hauteur = Carte.HAUTEURS[h];
	    this.couleur = Carte.COULEURS[c];
	    this.effet = null;
	    this.jouabilite = new Standard();
    }
    
    //*********** METHODES ***********
    
    //------------ Effets -----------
    
    
    
    /**
     * Applique l'effet de la carte lorsque celui-ci n'est pas nul.
     * 
     * @see Effet
     */
    public void appliquerEffet() {
    	if(this.effet != null) {
    		this.setChanged();
    		this.notifyObservers(this.effet);
    		this.effet.executer();
    	}
    }
    
    /**
     * Verifie si la carte a un effet.
     * 
     * @return true si la carte possede un effet.
     */
    public boolean aEffet() {
    	if(this.effet == null) {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    /**
     * Verifie si la carte a l'effet indique.
     * 
     * @param effet L'effet a verifier
     * @return true si la carte possede l'effet
     * 
     * @see Effets
     */
    public boolean aEffet(Effets effet) {
    	boolean aEffet = false;
    	switch(effet) {
    	case any :	//Pour savoir si elle a un effet
    		if(this.aEffet()) {aEffet = true;}
    	case changerCouleur:
    		if(this.getEffet() instanceof ChangerCouleur) {aEffet = true;}
    		break;
    	case changerCouleurStopAttaques:
    		if(this.getEffet() instanceof ChangerCouleurStopAttaques) {aEffet = true;}
    		break;
    	case changerSens:
    		if(this.getEffet() instanceof ChangerSens) {aEffet = true;}
    		break;
    	case fairePiocher:
    		if(this.getEffet() instanceof FairePiocher) {aEffet = true;}
    		break;
    	case fairePiocherContre:
    		if(this.getEffet() instanceof FairePiocherContre) {aEffet = true;}
    		break;
    	case fairePiocherSansRecours:
    		if(this.getEffet() instanceof FairePiocherSansRecours) {aEffet = true;}
    		break;
    	case passerTour:
    		if(this.getEffet() instanceof PasserTour) {aEffet = true;}
    		break;
    	case rejouer:
    		if(this.getEffet() instanceof Rejouer) {aEffet = true;}
    		break;
    	}
    	return aEffet;
    }
    
  //----------- Jouabilite ----------
    
    /**
     * Verifie si la carte est jouable
     * 
     * @return true si la carte est jouable
     * 
     * @see Jouabilite
     */
    public boolean estJouable() {
    	return this.jouabilite.verifier(this);
    }
    
    
    /**
     * Verifie si la carte a la jouabilite indiquee.
     * 
     * @param jouabilite La jouabilite a verifier
     * @return true si la carte possede la jouabilite
     * 
     * @see Jouabilites
     */
    public boolean aJouabilite(Jouabilites jouabilite) {
    	boolean aJouabilite = false;
    	switch(jouabilite) {
    	case standard:
    		if(this.getJouabilite() instanceof Standard) {aJouabilite = true;}
    		break;
    	case surTout:
    		if(this.getJouabilite() instanceof JouableSurTout) {aJouabilite = true;}
    		break;
    	case surContre:
    		if(this.getJouabilite() instanceof JouableSurContre) {aJouabilite = true;}
    		break;
    	case surToutEtContre:
    		if(this.getJouabilite() instanceof JouableSurToutEtContre) {aJouabilite = true;}
    		break;
    	}
    	return aJouabilite;
    }
    
    //********** ACCESSEURS **********
    
    /**
     * Retourne la hauteur de la carte.
     * 
     * @return La hauteur de la carte.
     */
    public String getHauteur() {
    	return this.hauteur;
    }
    
    /**
     * Retourne la couleur de la carte.
     * 
     * @return La couleur de la carte.
     */
    public String getCouleur() {
    	return this.couleur;
    }
    
    /**
     * Retourne l'effet de la carte.
     * 
     * @return L'effet de la carte.
     * 
     * @see Effet
     */
    public Effet getEffet() {
    	return this.effet;
    }
    
    /**
     * Retourne la jouabilite de la carte.
     * 
     * @return La jouabilite de la carte.
     * 
     * @see Jouabilite
     */
    public Jouabilite getJouabilite() {
    	return this.jouabilite;
    }
    
    //********** MUTATEURS **********
    
    /**
     * Met a jour l'effet de la carte.
     * 
     * @param effet Le nouvel effet
     */
    public void setEffet(Effet effet) {
    	this.effet = effet;
    }
    
    /**
     * Met a jour la jouabilite de la carte
     * 
     * @param jouabilite La nouvelle jouabilite
     */
    public void setJouabilite(Jouabilite jouabilite) {
    	this.jouabilite = jouabilite;
    }
    
    //********** AFFICHAGE ***********
    
    /**
     * Retourne le texte descriptif de la carte.
     * 
     * @return Le texte descriptif de la carte.
     */
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.hauteur + " de ");
        switch (this.couleur) {
        case "Coeur":
        	sb.append("Coeur");
        	break;
        case "Carreau" :
        	sb.append("Carreau");
        	break;
        case "Trefle" :
        	sb.append("Trefle");
        	break;
        case "Pique":
        	sb.append("Pique");
        	break;
        default:
        	sb.append(this.couleur);
        };
        return sb.toString();
    }
}
