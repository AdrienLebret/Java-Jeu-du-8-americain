package fr.utt.lo02.j8.modele.effets;

import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Pioche;
import fr.utt.lo02.j8.modele.moteur.Talon;
/**
 * <b>Cette classe representant l'effet Changer de couleur, qui peut egalement stopper les attaques, qui herite de ChangerCouleur</b>
 * <p>
 * Cet effet consiste a choisir la future couleur du talon parmis : Coeur, Carreau, Trefle et Pique
 * </p>
 * <p>
 * Cet effet possede egalement la particularite de stopper les attaques adverses (faire piocher)
 * </p>
 * @see Effet
 * @see ChangerCouleur
 * @see Partie
 * @see Pioche
 * @see Talon
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class ChangerCouleurStopAttaques extends ChangerCouleur{
	/**
	 * Execute l'effet suivant :
	 * <p>
	 * <b>Stopper les attaques adverses</b>
	 * </p>
	 * @see Talon#setContre(boolean)
	 * @see Pioche#remiseZeroNbCartesAPiocher()
	 */
	public void executer() {
		Partie.getInstance().getTalon().setContre(false); //Remise du contre a faux, car il a contre, et du nombre de cartes a piocher
		Partie.getInstance().getPioche().remiseZeroNbCartesAPiocher();
		super.executer();
	}
}
