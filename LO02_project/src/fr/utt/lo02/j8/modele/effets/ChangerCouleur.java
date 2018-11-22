package fr.utt.lo02.j8.modele.effets;

import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Talon;
/**
 * <b>Cette classe representant l'effet Changer de couleur, qui implemente l'interface Effet</b>
 * <p>
 * Cet effet consiste a choisir la future couleur du talon parmis : Coeur, Carreau, Trefle et Pique
 * </p>
 * @see Effet
 * @see Effets
 * @see Partie
 * @see Joueur
 * @see Talon
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class ChangerCouleur implements Effet{
	/**
	 * <p>
	 * Execute l'effet decrit dans la classe
	 * </p>
	 * <p>
	 * Ici : Changer la couleur du talon
	 * </p>
	 * @see Joueur#ajouterAction(int)
	 */
	public void executer() {
		Partie.getInstance().getJoueurActuel().ajouterAction(Joueur.CHOIX_COULEUR);
	}
}
