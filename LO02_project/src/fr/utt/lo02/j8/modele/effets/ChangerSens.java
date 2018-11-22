package fr.utt.lo02.j8.modele.effets;

import fr.utt.lo02.j8.modele.moteur.Partie;
/**
 * <b>ChangerSens est la classe representant l'effet permettant de changer le sens de la partie, qui implemente l'interface Effet</b>
 * Cet effet possede 2 possibilites :
 * <ul>
 * <li>Changer de sens de la partie, </li>
 * <li>Ou rejouer s'il y a 2 joueurs qui jouent</li>
 * </ul>
 * @see Effet
 * @see Partie
 * @see Rejouer
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class ChangerSens implements Effet{
	/**
	 * <p>
	 * Execute l'effet decrit dans la classe
	 * </p>
	 * Ici :
	 * <ul>
	 * <li>Changer de sens de la partie, </li>
	 * <li>Ou rejouer s'il y a 2 joueurs qui jouent</li>
	 * </ul>

	 * @see Partie#getNombreJoueur()
	 * @see Partie#getNombreJoueurFinis()
	 * @see Partie#changerSens()
	 * @see Rejouer
	 */
	public void executer() {
		if(Partie.getInstance().getNombreJoueur() - Partie.getInstance().getNombreJoueurFinis() == 2) {
			Effet rejouer = new Rejouer();
			rejouer.executer();
		} else {
			Partie.getInstance().changerSens();
		}
	}
}
