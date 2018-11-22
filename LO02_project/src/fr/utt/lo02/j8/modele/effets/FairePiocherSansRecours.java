package fr.utt.lo02.j8.modele.effets;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Pioche;
/**
 * <b>FairePiocherSansRecours est la classe representant l'effet permettant de faire piocher une ou plusieurs cartes au joueur suivant, sans de contre possible.</b>
 * <p>
 * Elle herite de la classe FairePiocher
 * </p>
 * <p>
 * Cet effet consiste a faire piocher un certain nombre de cartes au joueur suivant, sans possibilite de contrer
 * </p>
 * 
 * @see Effet
 * @see FairePiocher
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class FairePiocherSansRecours extends FairePiocher{
	
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
	public FairePiocherSansRecours(int nombreCartes) {
		super(nombreCartes);
	}
	
	//*********** METHODES ***********
	/**
	 * <p>
	 * Execute l'effet decrit dans la classe
	 * </p>
	 * <p>
	 * Ici : Faire piocher un certain nombre de cartes au joueur suivant, sans recours
	 * </p>
	 * @see Joueur#ajouterAction(int)
	 * @see Joueur#faireAction()
	 * @see Pioche#increaseNbCartesAPiocher(int)
	 */
	public void executer() {
		//Augmentation du nombre de cartes a piocher
		Partie.getInstance().getPioche().increaseNbCartesAPiocher(this.getNombreCartes());
		//Le joueur suivant doit piocher
		Partie.getInstance().getJoueurSuivant().ajouterAction(Joueur.PIOCHE);
		Partie.getInstance().getJoueurSuivant().faireAction();
		//Il passera egalement son tour
		Partie.getInstance().setNombreJoueursAPasser(2);
	}
}
