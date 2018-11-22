package fr.utt.lo02.j8.modele.jouabilite;

import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Talon;

/**
 * <b>Interface Jouabilite</b>
 * <p>
 * Une carte possede des effets mais egalement une jouabilite, c'est-a-dire si elle est posable sur toute les cartes par exemple
 * <p>
 * Les classes qui implementeront l'interface Jouabilite s'engagent à implementer la methode suivante :
 * <ul>
 * <li><b>verifier(Carte carte)</b> : qui verifie si la carte peut etre posee sur le dessus du talon</li>
 * </ul>
 * 
 * @see Carte
 * @see Talon
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public interface Jouabilite {
	
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
	public boolean verifier(Carte carte);
}
