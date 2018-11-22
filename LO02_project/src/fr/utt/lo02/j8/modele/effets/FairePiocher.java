package fr.utt.lo02.j8.modele.effets;

/**
 * <b>FairePiocher est la classe Abstraite representant l'effet permettant de faire piocher une ou plusieurs cartes au joueur suivant, qui implemente l'interface Effet</b>
 * <p>
 * Cet effet consiste a faire piocher un certain nombre de cartes au joueur suivant
 * </p>
 * <p>
 * Cet effet est caracterisee par le <b>nombre de cartes</b> a faire piocher
 * </p>
 * @see Effet
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public abstract class FairePiocher implements Effet{
	/**
	 * Nombre de Cartes que le joueur suivant piochera.
	 * Ce nombre est modifiable
	 * 
	 * @see FairePiocher#getNombreCartes()
	 */
	private int nombreCartes;
	
	//******** CONSTRUCTEURS *********
	/**
	 * Constructeur FairePiocher
	 * @param nombreCartes a faire piocher au joueur suivant que l'on affecte a l'entier prive de la classe
	 * 
	 * @see FairePiocher#nombreCartes
	 */
	public FairePiocher(int nombreCartes) {
		this.nombreCartes = nombreCartes;
	}
	
	//********** ACCESSEURS **********
	/**
	 * Retourne le nombre de cartes a faire piocher
	 * 
	 * @return le nombre de cartes a faire piocher
	 * 
	 * @see FairePiocher#nombreCartes
	 */
	public int getNombreCartes() {
		return this.nombreCartes;
	}
}
