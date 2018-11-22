package fr.utt.lo02.j8.modele.strategies;
import fr.utt.lo02.j8.modele.moteur.Joueur;

/**
 * <b>StrategieSimple est la classe reprensentant la strategie basique d'un joueur virtuel</b>
 * 
 * La strategie se comporte comme suit :
 * <ol>
 * <li>pose n'importe quelle carte</li>
 * <li>pioche</li>
 * </ol>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class StrategieSimple extends StrategieBot{
	
	/**
	 * 
	 */
	public void choisirAction(Joueur joueur) {
		
		int indiceCarte;
		indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), null, null, null, null, null);
		
		if(indiceCarte != -1) {
			try {
				joueur.setCarteAPoser(indiceCarte);
				joueur.ajouterAction(Joueur.POSE_CARTE);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}else {
			joueur.ajouterAction(Joueur.PIOCHE);
		}
		joueur.faireAction();
	}
	
	/**
	 * Retourne le type de strategie : Strategie simple.
	 * 
	 * @return la representation string de l'objet.
	 */
	public String toString() {
		return "Strategie Simple";
	}
}
