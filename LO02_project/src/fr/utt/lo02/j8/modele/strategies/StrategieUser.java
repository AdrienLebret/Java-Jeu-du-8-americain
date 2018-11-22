package fr.utt.lo02.j8.modele.strategies;

import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Notifications;

/**
 * <b>StrategieUser est la classe representant la strategie de l'utilisateur</b>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class StrategieUser implements Strategie{
	
	//************* Jeu **************
	
	/**
	 * 
	 */
	public void jouer(Joueur joueur) {
		//Remise de la valeur de carte annoncee a fausse si le joueur a plus d'une carte en main (pour permettre une nouvelle annonce)
		if(joueur.getMain().getNombreCartes() > 1 && joueur.aAnnonceCarte()) {
			joueur.setCarteAnnonceDefault();
		}
	}
	
	//************ Choix *************
	
	/**
	 * 
	 */
	public void choisirStrategie(Joueur joueur) {
		joueur.setStrategie(joueur.getStrategiesDisponibles().get((int) (Math.random()*(joueur.getStrategiesDisponibles().size()-1))));
	}
	
	/**
	 * 
	 */
	public void choisirCouleur(Joueur joueur) {
		joueur.notifyObservers(Notifications.changementCouleur);
	}
	
}
