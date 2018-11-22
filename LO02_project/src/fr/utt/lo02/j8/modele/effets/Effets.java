package fr.utt.lo02.j8.modele.effets;
/**
 * <b>Effets : Enumeration de l'ensemble des effets recenses dans le projet</b>
 * 
 * @see Effet
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public enum Effets {
	any,
	/**
	 * @see ChangerCouleur
	 */
	changerCouleur,
	/**
	 * @see ChangerCouleurStopAttaques
	 */
	changerCouleurStopAttaques,
	/**
	 * @see ChangerSens
	 */
	changerSens,
	/**
	 * @see FairePiocher
	 */
	fairePiocher,
	/**
	 * @see FairePiocherContre
	 */
	fairePiocherContre,
	/**
	 * @see FairePiocherSansRecours
	 */
	fairePiocherSansRecours,
	/**
	 * @see PasserTour
	 */
	passerTour,
	/**
	 * @see Rejouer
	 */
	rejouer;
}
