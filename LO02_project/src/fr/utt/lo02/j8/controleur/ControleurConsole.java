package fr.utt.lo02.j8.controleur;

import fr.utt.lo02.j8.exceptions.CarteNonValideException;
import fr.utt.lo02.j8.exceptions.SaisieNonValideException;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Talon;
import fr.utt.lo02.j8.modele.variantes.Variante;
import fr.utt.lo02.j8.vue.console.VueConsole;

/**
 * <b>ControleurConsole est la classe representant le controleur de la vue console</b>
 * <p>
 * Dans le cadre de l'implementation du modele MVC, le controleur recupere les operations de l'utilisateur
 * et les traduits sur le modele.
 * </p>
 * Le controleur console est caracterise par :
 * <ul>
 * <li>Une partie</li>
 * </ul>
 * 
 * @see Partie
 * @see VueConsole
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class ControleurConsole {
	
	/**
	 * Attribut correspondand a une partie.
	 * 
	 * @see Partie
	 */
	private Partie partie;
	
	/**
	 * Constructeur ControleurConsole
	 * <p>
	 * A la construction du controleur, la partie est instanciee
	 * </p>
	 * @see Partie
	 */
	public ControleurConsole() {
		this.partie = Partie.getInstance();
	}
	
	//************ MENU *************
	
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
	 * Met a jour le nom du joueur reel 
	 * 
	 * @param joueur reel
	 * @param nom saisie par l'utilisateur
	 * @throws SaisieNonValideException si le nom n'est pas valide
	 */
	public void setNom(Joueur joueur, String nom) throws SaisieNonValideException{
		if(nom.length() == 0) {
			throw new SaisieNonValideException("nom vide");
		}else if(nom.length() > 20) {
			throw new SaisieNonValideException("maximum 20 caracteres");
		}
		joueur.setNom(nom);
	}
	
	/**
	 * Ajoute un joueur a la partie.
	 * 
	 * @throws SaisieNonValideException si l'utilisateur souhaite depasser le nombre maximum de joueur = 6
	 */
	public void ajouterJoueur() throws SaisieNonValideException{
		if(this.partie.getNombreJoueur() >= Partie.NOMBRE_JOUEURS_MAX) {
			throw new SaisieNonValideException("maximum " + Partie.NOMBRE_JOUEURS_MAX + " joueurs");
		}
		this.partie.addJoueur();
	}
	
	/**
	 * Supprime un joueur a la partie.
	 * 
	 * @throws SaisieNonValideException si le l'utilisateur souhaite aller en dessous du nombre minimum de joueur = 2
	 */
	public void supprimerJoueur() throws SaisieNonValideException{
		if(this.partie.getNombreJoueur() <= 2) {
			throw new SaisieNonValideException("minimum 2 joueurs");
		}
		this.partie.removeJoueur();
	}
	
	/**
	 * Definit la taille des paquets a utiliser
	 * 
	 * @param taille la taille des paquets. Elle peut prendre les valeurs 32 et 52.
	 * @throws SaisieNonValideException si erreur de saisie
	 */
	public void setTaillePaquet(int taille) throws SaisieNonValideException{
		if(taille != 32 && taille != 52) {
			throw new SaisieNonValideException("choisir 32 ou 52");
		}
		this.partie.setTailleInitPaquet(taille);
	}
	
	/**
	 * Change la variante utilisee par la partie, 
	 * et met a jour les cartes pour correspondre a la nouvelle variante.
	 * 
	 * @param variante la nouvelle variante. Elle doit utiliser des paquets de meme taille que ceux de la variante precedente.
	 * 
	 * @see Variante
	 * @see Partie
	 */
	public void setVariante(Variante variante) {
		this.partie.setVariante(variante);
	}
	
	/**
	 * Change la couleur du talon.
	 * 
	 * @param indice du tableau COULEURS de la classe Carte
	 * 
	 * @see Talon
	 * @see Carte
	 */
	public void setCouleur(int indice) {
		this.partie.getTalon().setCouleur(Carte.COULEURS[indice]);
	}
	
	/**
	 * Permet de recommencer une partie
	 * @param partie actuelle
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
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
	//****** EN COURS DE PARTIE ******
	
	/**
	 * Controle si le joueur joue ou pioche une carte.
	 * 
	 * @param entree : saisie de l'utilisateur
	 * @throws SaisieNonValideException si l'utilisateur n'a pas respectee ce qui etait demande
	 * @throws CarteNonValideException si l'utilisateur s'est trompe lors de la pose de la 
	 */
	public void controlActionJeu(String entree) throws SaisieNonValideException, CarteNonValideException{
		//Si l'entree n'est pas un nombre, exception
		if (!entree.matches("^[0-9]+$")){
			throw new SaisieNonValideException("entrer un nombre");
		}
		
		int saisie = Integer.parseInt(entree);
		//Si l'entree ne correspond pas aux valeurs possibles
		Joueur joueur = Partie.getInstance().getJoueurActuel();
		if (saisie < 0 || saisie > joueur.getMain().getNombreCartes()) {
			throw new SaisieNonValideException("entrer un des nombres proposes");
		}
		
		//Actions
		if(saisie == 0) {
			joueur.ajouterAction(Joueur.PIOCHE);;
		}else {
			if(! joueur.getMain().getCarte(saisie-1).estJouable()) {
				throw new CarteNonValideException();
			}
			joueur.setCarteAPoser(saisie-1);
			joueur.ajouterAction(Joueur.POSE_CARTE);
		}
		joueur.faireAction();
	}
	
}
