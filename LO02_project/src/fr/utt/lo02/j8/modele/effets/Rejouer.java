package fr.utt.lo02.j8.modele.effets;

import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Main;
import fr.utt.lo02.j8.modele.moteur.Partie;
/**
 * <b>Rejouer est la classe representant l'effet permettant au joueur de Rejouer, qui implemente l'interface Effet</b>

 * Cet effet possede 2 possibilites :
 * <ul>
 * <li>Si ce n'est pas sa derniere carte : Rejouer </li>
 * <li>Sinon : Piocher</li>
 * </ul>
 * @see Effet
 * @see Partie
 * @see Joueur
 * @see Rejouer
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Rejouer implements Effet{
	/**
	 * <p>
	 * Execute l'effet decrit dans la classe
	 * </p>
	 * Ici :
	 * <ul>
	 * <li><b>Rejouer</b>, si ce n'est pas sa derniere carte en main </li>
	 * <li>Sinon : <b>piocher</b></li>
	 * </ul>
	 * @see Partie
	 * @see Joueur
	 * @see Main
	 * @see Rejouer
	 */
	public void executer() {
		if(Partie.getInstance().getJoueurActuel().getMain().getNombreCartes() > 0) {	
			Partie.getInstance().setNombreJoueursAPasser(0);
		}else {
			Partie.getInstance().getJoueurActuel().ajouterAction(Joueur.PIOCHE);
		}
	}
}
