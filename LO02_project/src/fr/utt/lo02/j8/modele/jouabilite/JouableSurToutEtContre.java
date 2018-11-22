package fr.utt.lo02.j8.modele.jouabilite;

import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Talon;

/**
 * <b>JouableSurToutEtContre est la classe representant la jouabilite sur toutes les cartes (effet contrable egalement)</b>
 * <p>
 * Elle implemente Jouabilite
 * </p>
 * <p>
 * La carte possedant la caracteristique JouableSurToutEtContre peut etre posee sur toutes les cartes et peut notamment contrer si possible.
 * </p>
 * @see Jouabilite
 * @see Talon
 * @see Carte
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class JouableSurToutEtContre implements Jouabilite{
	
	/**
	 * <p>
	 * Verifie si la carte donnee en parametre peut etre posee sur la carte situee sur le dessus du talon.
	 * </p>
	 * @param carte que le joueur souhaite jouer.
	 * @return <ul>
	 * 		<li><b>true :</b> la carte peut etre posee sur le dessus du talon,</li>
	 * 		<li><b>false :</b> la carte ne peut pas etre posee sur le dessus du talon.</li>
	 * 		</ul>
	 */
	public boolean verifier(Carte carte) {
		return true;
	}
}
