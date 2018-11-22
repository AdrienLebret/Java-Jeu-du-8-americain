package fr.utt.lo02.j8.controleur;

import javax.swing.JPanel;

import fr.utt.lo02.j8.exceptions.CarteNonValideException;
import fr.utt.lo02.j8.exceptions.SaisieNonValideException;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.strategies.StrategieUser;
import fr.utt.lo02.j8.modele.variantes.Variante;
import fr.utt.lo02.j8.modele.variantes.Variante5;
import fr.utt.lo02.j8.modele.variantes.Variante6;
import fr.utt.lo02.j8.modele.variantes.Variante7;
import fr.utt.lo02.j8.modele.variantes.VarianteCourteAmicale;
import fr.utt.lo02.j8.modele.variantes.VarianteMinimale;
import fr.utt.lo02.j8.modele.variantes.VarianteMonclar;
import fr.utt.lo02.j8.vue.graphique.VueCarte;

/**
 * <b>ControleurGraphique est la classe representant le controleur de la vue graphique</b>
 * <p>
 * Dans le cadre de l'implementation du modele MVC, le controleur recupere les operations de l'utilisateur
 * et les traduits sur le modele.
 * </p>
 * <p>
 * Le controleur graphique est caracterise par :
 * <ul>
 * <li>Une partie</li>
 * <li>Un attribut Joueur</li>
 * <li>Un attribut nombre de joueur. Il permet de donner le nombre de joueur de la partie</li>
 * </ul>
 * 
 * @see Partie
 * @see Joueur
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class ControleurGraphique {
	
	/**
	 * Attribut correspondant a une partie.
	 * 
	 * @see Partie
	 */
	private Partie partie;
	/**
	 * Attribut correspondant a une partie
	 * 
	 * @see Joueur
	 */
	private Joueur joueur;
	
	/**
	 * Attribut correspondant au nombre de joueur de la partie
	 * <p>
	 * Initialise a 2
	 * </p>
	 */
	private int nbJ = 2; // Nombre de joueurs actuels
	
	/**
	 * Constructeur ControleurGraphique
	 * A la construction du controleur :
	 * <ul>
	 * <li>Partie est instanciee</li>
	 * <li>L'attribut joueur se voit affecter l'utilisateur</li>
	 * </ul>
	 * @see Partie
	 * @see Joueur
	 */
	public ControleurGraphique() {
		this.partie = Partie.getInstance();
		this.joueur = partie.getJoueur(0);
	}
	
	
	/**
	 * Met a jour le nom du joueur reel 
	 * 
	 * @param joueur reel
	 * @param nom saisie par l'utilisateur
	 */
	public void setNom(Joueur joueur, String nom) {
		joueur.setNom(nom);
	}
	
	/**
	 * Cree une <b>nouvelle</b> partie
	 * 
	 * @see Partie
	 */
	public void creerPartie() {
		Partie.nouvellePartie();
		partie = Partie.getInstance();
	}
	
	/**
	 * Demarre la partie
	 */
	public void demarrerPartie() {
		this.partie.demarrer();
	}
	
	/**
	 * Met a jour le nombre de joueur de la partie
	 * 
	 * @param slider : compris entre 2 et 6 (nombre de joueurs min et max)
	 */
	public void setNombreJouers(int slider) {
		if (nbJ-slider > 0) {
			//Le nombre de joueur a diminue
			nbJ = slider;
			this.partie.removeJoueur();
		} else if (nbJ-slider < 0)  {
			//Le nombre de joueur a augmente
			nbJ = slider;
			this.partie.addJoueur();
		}
	}
	
	/**
	 * Annonce carte
	 */
	public void annoncerCarte() {
		partie.getJoueur(0).annoncerCarte();
	}

	/**
	 * Annoce contre carte au joueur actuel
	 */
	public void annoncerContreCarte() {
		partie.getJoueur(0).annoncerContreCarte(partie.getJoueurActuel());
	}
	/**
	 * Definit la taille des paquets a utiliser
	 * 
	 * @param textBox la taille des paquets. Elle peut prendre les valeurs 32 et 52.
	 */
	public void setTaillePaquet(String textBox) {
		switch(textBox) {
		case "32" :
			this.partie.setTailleInitPaquet(32);
			break;
		case "52" :
			this.partie.setTailleInitPaquet(52);
			break;
		}
	}
	
	/**
	 * Change la variante utilisee par la partie, 
	 * et met a jour les cartes pour correspondre a la nouvelle variante.
	 * 
	 * @param textBox : String de la valeur affiche dans la liste deroulante
	 * 
	 * @see Variante
	 * @see Partie
	 */
	public void setVariante(String textBox) {
		switch(textBox) {
		case "VarianteCourteAmicale" :
			this.partie.setVariante(new VarianteCourteAmicale());
			break;
		case "Variante6" :
			this.partie.setVariante(new Variante6());
			break;
		case "Variante7" :
			this.partie.setVariante(new Variante7());
			break;
		case "VarianteMinimale" :
			this.partie.setVariante(new VarianteMinimale());
			break;
		case "Variante5" :
			this.partie.setVariante(new Variante5());
			break;
		case "VarianteMonclar" :
			System.out.println("JE SUIS ALLE JUSQUICI");
			this.partie.setVariante(new VarianteMonclar());
			break;
		}
	}
	
	/**
	 * Redefinition de la methode setVariante 
	 * 
	 * @param varianteChoisie est cette fois de type Variante et non une chaine de caractere
	 */
	public void setVariante (Variante varianteChoisie) {
		this.partie.setVariante(varianteChoisie);
	}
	
	/**
	 * Permet au joueur de piocher
	 */
	public void piocher() {
		if(partie.getJoueurActuel() == joueur) {
			this.joueur.ajouterAction(Joueur.PIOCHE);
			this.joueur.faireAction();
		}
	}
	
	/**
	 * Permet la pose de la carte
	 * @param evt clic de souris
	 * @throws CarteNonValideException si la carte ne peut etre posee
	 */
	public void poser(java.awt.event.MouseEvent evt) throws CarteNonValideException{
		if(partie.getJoueurActuel() == joueur) {
			Carte carte = ((VueCarte)evt.getSource()).getCarte();
			if(carte.estJouable()) {
				joueur.setCarteAPoser(joueur.getMain().getIndice(carte));
				this.joueur.ajouterAction(Joueur.POSE_CARTE);
				this.joueur.faireAction();
			}else {
				throw new CarteNonValideException();
			}
		}
	}
	/**
	 * Change la couleur du talon en fonction du choix de l'utilisateur
	 * @param evt clic de souris
	 */
	public void changerCouleur(java.awt.event.MouseEvent evt) {
		partie.getTalon().setCouleur(((JPanel)evt.getSource()).getName());
	}
	
	/**
	 * Permet de recommencer une partie
	 * @param partie actuelle
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
	}
	
	/**
	 * Permet a l'utilisateur de laisser l'ordinateur jouer a sa place
	 */
	public void passerBot(){
		Joueur joueur = partie.getJoueur(0);
		if(partie.getJoueurActuel() != joueur) {
			if(joueur.getStrategie() instanceof StrategieUser) {
				joueur.getStrategie().choisirStrategie(joueur);
			}else {
				joueur.setStrategie(new StrategieUser());
			}
		}
	}
}
