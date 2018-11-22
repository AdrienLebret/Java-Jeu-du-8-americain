package fr.utt.lo02.j8.modele.strategies;
import fr.utt.lo02.j8.modele.effets.Effets;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilites;
import fr.utt.lo02.j8.modele.moteur.Joueur;

/**
 * <b>StrategiePrudente est la classe reprensentant la strategie prudente d'un joueur virtuel</b>
 * 
 * La strategie se comporte comme suit :
 * <ol>
 * <li>pose les cartes ne permettant pas de contrer</li>
 * <li>pose les cartes permettant de contrer</li>
 * <li>pose les cartes stoppant les attaques</li>
 * <li>pioche</li>
 * </ol>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class StrategiePrudente extends StrategieBot{
	
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
		
		//Pose des cartes ne pouvant pas être placees lors des contres
		effetsRequis = null;
		effetsIndesirables = null;
		jouabilitesRequises = null;
		jouabilitesIndesirables = new Jouabilites[] {Jouabilites.surContre, Jouabilites.surToutEtContre};
		indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables, null);
		
		//Pose des cartes pouvant contrer sinon (carte à contrer ou rien d'autre à jouer)
		if(indiceCarte == -1) {
			effetsRequis = new Effets[] {Effets.fairePiocherContre};
			effetsIndesirables = null;
			jouabilitesRequises = null;
			jouabilitesIndesirables = null;
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables, null);
		}
		
		//Pose une carte stoppant les attaques en dernier recours
		if(indiceCarte == -1) {
			effetsRequis = new Effets[] {Effets.changerCouleurStopAttaques};
			effetsIndesirables = null;
			jouabilitesRequises = null;
			jouabilitesIndesirables = null;
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables, null);
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
	 * Retourne le type de strategie : Strategie prudente.
	 * 
	 * @return la representation string de l'objet.
	 */
	public String toString() {
		return "Strategie Prudente";
	}
}
