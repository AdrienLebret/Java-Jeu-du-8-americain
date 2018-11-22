package fr.utt.lo02.j8.modele.strategies;
import fr.utt.lo02.j8.modele.effets.Effets;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilites;
import fr.utt.lo02.j8.modele.moteur.Joueur;

/**
 * <b>StrategieAgressive est la classe reprensentant la strategie agressive d'un joueur virtuel</b>
 * 
 * La strategie se comporte comme suit :
 * <ol>
 * <li>pose les cartes avec effet excepte le changement de couleur</li>
 * <li>pose de n'importe qu'elle autre carte</li>
 * <li>pioche</li>
 * </ol>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class StrategieAgressive extends StrategieBot{
	
	/**
	 * 
	 */
	public void choisirAction(Joueur joueur) {
		int indiceCarte;
		
		//Criteres de selection changes a chaque recherche
		Effets[] effetsRequis;
		Effets[] effetsIndesirables;
		Jouabilites[] jouabilitesRequises;
		Jouabilites[] jouabilitesIndesirables;
		
		//Pose d'une carte avec effet, excepte changerCouleur
		effetsRequis = new Effets[] {Effets.any};
		effetsIndesirables = new Effets[] {Effets.changerCouleur};
		jouabilitesRequises = null;
		jouabilitesIndesirables = null;
		indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables, null);
		
		//Pose de n'importe quelle carte
		if(indiceCarte == -1) {
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), null, null, null, null, null);
		}
		
		//Pose de la carte ou pioche
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
	 * Retourne le type de strategie : Strategie agressive.
	 * 
	 * @return la representation string de l'objet.
	 */
	public String toString() {
		return "Strategie Agressive";
	}

}
