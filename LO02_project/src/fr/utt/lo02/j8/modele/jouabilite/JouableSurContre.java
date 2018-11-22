package fr.utt.lo02.j8.modele.jouabilite;

import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Talon;

/**
 * <b>JouableSurContre est la classe representant la jouabilite de la carte sur une carte "FairePiocher"</b>
 * <p>
 * Elle implemente Jouabilite
 * </p>
 * <p>
 * La carte possedant la caracteristique JouableSurContre peut etre posee sur des cartes ayant un effet contrable
 * </p>
 * @see Jouabilite
 * @see Talon
 * @see Carte
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class JouableSurContre implements Jouabilite{
	
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
		Talon talon = Partie.getInstance().getTalon();
		boolean jouable = false;
		if(!talon.getContre()) {
			jouable = (new Standard()).verifier(carte);
		}else if(carte.getHauteur() == talon.getCarteDessus().getHauteur() || carte.getCouleur() == talon.getCouleur()) {
			jouable = true;
		}
		return jouable;
	}

}
