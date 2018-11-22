package fr.utt.lo02.j8.modele.effets;

import fr.utt.lo02.j8.modele.moteur.Partie;
/**
 * <b>PasserTour est la classe representant l'effet Faire Passer un Tour </b>
 * <p>
 * Elle implemente l'interface Effet
 * </p>
 * <p>
 * Cet effet consiste a faire passer le tour du joueur suivant
 * </p>
 * @see Effet
 * @see Partie
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class PasserTour implements Effet{
	/**
	 * <p>
	 * Execute l'effet decrit dans la classe
	 * </p>
	 * <p>
	 * Ici : Faire passer le tour du joueur suivant
	 * </p>
	 * @see Partie#setNombreJoueursAPasser(int)
	 */
	public void executer() {
		Partie.getInstance().setNombreJoueursAPasser(2);
	}
}
