package fr.utt.lo02.j8.modele.jouabilite;

import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Talon;

/**
 * <b>JouableSurTout est la classe representant la jouabilite sur toutes les cartes (hors effet contrable)</b>
 * <p>
 * Elle implemente Jouabilite
 * </p>
 * <p>
 * La carte possedant la caracteristique JouableSurTout peut etre posee sur toutes les cartes (hors cartes avec l'effet contrable). JouableSurTout ne contre pas les cartes.
 * </p>
 * @see Jouabilite
 * @see Talon
 * @see Carte
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class JouableSurTout implements Jouabilite{
	
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
		if(Partie.getInstance().getTalon().getContre()) {
			return false;
		}else {
			return true;
		}
	}
}
