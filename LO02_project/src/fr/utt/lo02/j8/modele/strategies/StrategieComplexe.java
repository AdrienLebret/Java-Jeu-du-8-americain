package fr.utt.lo02.j8.modele.strategies;
import fr.utt.lo02.j8.modele.effets.Effets;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilites;
import fr.utt.lo02.j8.modele.moteur.Joueur;

/**
 * <b>StrategieComplexe est la classe reprensentant la strategie plus avancee d'un joueur virtuel</b>
 * 
 * La strategie se comporte comme suit :
 * <ol>
 * <li>pose les cartes avec un effet qui peut se reveler negatif si joue en derniere carte (effets faisant rejouer) lorsque le joueur a peu de cartes</li>
 * <li>pose les cartes avec l'effet rejouer s'il a des cartes a poser apres</li>
 * <li>pose les cartes de la couleur du talon, excepte celles avec effet</li>
 * <li>pose n'importe quelle carte excepte celles permettant de contrer s'il n'en a qu'une et celles avec l'effet couleur</li>
 * <li>pose les cartes permettant de contrer, excepte celles changeant la couleur</li>
 * <li>pose les cartes avec l'effet de changement de couleur</li>
 * <li>pioche</li>
 * </ol>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class StrategieComplexe extends StrategieBot{
	
	/**
	 * 
	 */
	public void choisirAction(Joueur joueur) {
		int indiceCarte = -1;
		
		//Criteres de selection changes a chaque recherche
		Effets[] effetsRequis;
		Effets[] effetsIndesirables;
		Jouabilites[] jouabilitesRequises;
		Jouabilites[] jouabilitesIndesirables;
		
		//Effet Critique lorsque peu de cartes : Rejouer et changerSens
		if(joueur.getMain().getNombreCartes() < 3) {
			effetsRequis = new Effets[] {Effets.rejouer, Effets.changerSens};
			effetsIndesirables = null;
			jouabilitesRequises = null;
			jouabilitesIndesirables = null;
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables, null);
		}
		
		//S'il peut jouer une carte rejouer et qu'il a de quoi jouer après
		if(indiceCarte == -1) {
			effetsRequis = new Effets[] {Effets.rejouer};
			effetsIndesirables = null;
			jouabilitesRequises = null;
			jouabilitesIndesirables = null;
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables, "Cartes Jouables apres");
		}
		
		//Cartes de même couleur que le talon, mais pas d'effet
		if(indiceCarte == -1) {
			effetsRequis = null;
			effetsIndesirables = new Effets[] {Effets.any};
			jouabilitesRequises = null;
			jouabilitesIndesirables = null;
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables, "Couleur Talon");
		}
		
		//Compte des Cartes ayant l'effet Contre
		int nbCartesContre = 0;
		for(int j=0; j < joueur.getMain().getNombreCartes(); j++) {
			if(joueur.getMain().getCarte(j).aJouabilite(Jouabilites.surContre) || joueur.getMain().getCarte(j).aJouabilite(Jouabilites.surToutEtContre)) {
				nbCartesContre++;
			}
		}
		
		//N'importe quelle carte, sauf changer Couleur, et cartes posables lors des contres
		if(indiceCarte == -1) {
			effetsRequis = null;
			effetsIndesirables = new Effets[] {Effets.changerCouleur};
			jouabilitesRequises = null;
			if(nbCartesContre > 1) {
				jouabilitesIndesirables = null;
			}else {
				jouabilitesIndesirables = new Jouabilites[] {Jouabilites.surContre, Jouabilites.surToutEtContre};
			}
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables,null);
		}
		
		//Si le joueur n'a toujours pas joué, pose les cartes placeables sur contre, a condition qu'elle ne change pas la couleur(2 cas possibles : seule carte qu'il peut poser de la couleur de la carte du talon ; carte a contrer)
		if(indiceCarte == -1) {
			effetsRequis = null;
			effetsIndesirables = new Effets[] {Effets.changerCouleur};
			jouabilitesRequises = new Jouabilites[] {Jouabilites.surContre, Jouabilites.surToutEtContre};
			jouabilitesIndesirables = null;
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables,null);
		}
		
		//S'il a une carte pour stopper une attaque, la pose
		
		//Si le joueur n'a toujours pas joue il n' d'autres choix que de changer de couleur
		if(indiceCarte == -1) {
			effetsRequis = new Effets[] {Effets.changerCouleur};
			effetsIndesirables = null;
			jouabilitesRequises = null;
			jouabilitesIndesirables = null;
			indiceCarte = this.chercherCarteSpecifique(joueur.getMain(), effetsRequis, effetsIndesirables, jouabilitesRequises, jouabilitesIndesirables,null);
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
	 * Retourne le type de strategie : Strategie complexe.
	 * 
	 * @return la representation string de l'objet.
	 */
	public String toString() {
		return "Strategie Complexe";
	}
}
