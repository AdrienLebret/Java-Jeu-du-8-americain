package fr.utt.lo02.j8.modele.effets;

import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Pioche;
import fr.utt.lo02.j8.modele.moteur.Talon;
/**
 * <b>FairePiocherContre est la classe representant l'effet permettant de faire piocher une ou plusieurs cartes au joueur suivant, avec contre possible.</b>
 * <p>
 * Elle herite de la classe FairePiocher
 * </p>
 * <p>
 * Cet effet consiste a faire piocher un certain nombre de cartes au joueur suivant, avec possibilite de contrer
 * </p>
 * 
 * @see Effet
 * @see FairePiocher
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class FairePiocherContre extends FairePiocher{
	
	//******** CONSTRUCTEURS *********
	/**
	 * Constructeur FairePiocherSansRecours
	 * <p>
	 * Il appelle le constructeur de la classe mere
	 * </p>
	 * @param nombreCartes a faire piocher au joueur suivant
	 * 
	 * @see FairePiocher#FairePiocher(int)
	 */
	public FairePiocherContre(int nombreCartes) {
		super(nombreCartes);
	}
	
	//*********** METHODES ***********
	/**
	 * <p>
	 * Execute l'effet decrit dans la classe
	 * </p>
	 * <p>
	 * Ici : Faire piocher un certain nombre de cartes au joueur suivant, avec possibilite de contre
	 * </p>
	 * @see Pioche#increaseNbCartesAPiocher(int)
	 * @see Talon#setContre(boolean)
	 */
	public void executer() {
		//Augmentation du nombre de cartes a piocher
		Partie.getInstance().getPioche().increaseNbCartesAPiocher(this.getNombreCartes());
		//Indication au talon qu'il faudra contrer
		Partie.getInstance().getTalon().setContre(true);
		//System.out.println("** Le joueur suivant piochera " + Partie.getInstance().getPioche().getNBCartesAPiocher() + " carte(s) **");
	}
}
