package fr.utt.lo02.j8.modele.moteur;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import fr.utt.lo02.j8.exceptions.CarteNonValideException;
import fr.utt.lo02.j8.modele.strategies.Strategie;
import fr.utt.lo02.j8.modele.strategies.StrategieAgressive;
import fr.utt.lo02.j8.modele.strategies.StrategieComplexe;
import fr.utt.lo02.j8.modele.strategies.StrategiePrudente;
import fr.utt.lo02.j8.modele.strategies.StrategieSimple;

/**
 * <b>Joueur est la classe representant un joueur.</b>
 * <p>
 * Un joueur est caracterise par :
 * <ul>
 * <li>Un nom. Il est susceptible d'etre change en debut de partie.</li>
 * <li>Une main attribue definitivement</li>
 * <li>Un score. Il est susceptible d'etre change</li>
 * <li>Une strategie. Elle est susceptible d'etre changee</li>
 * </ul>
 * 
 * @see Main
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Joueur extends Observable implements Observer{
	
	/**
	 * Nom du joueur.
	 * Ce nom est modifiable.
	 * 
	 * @see Joueur#Joueur(String)
	 * @see Joueur#getNom()
	 * @see Joueur#setNom(String)
	 */
	private String nom;
	
	/**
	 * Main du joueur.
	 * Elle n'est pas modifiable;
	 * 
	 * @see Main
	 * 
	 * @see Joueur#getMain()
	 */
	private Main main;
	
	/**
	 * Score du joueur.
	 * Ce score est modifiable.
	 * 
	 * @see Joueur#getScore()
	 * @see Joueur#augmenterScore(int)
	 */
	private int score;
	
	
	/**
	 * Strategie du joueur.
	 * Cette strategie est modifiable.
	 * 
	 * @see Strategie
	 * 
	 * @see Joueur#getStrategie()
	 * @see Joueur#setStrategie(Strategie)
	 */
	private Strategie strategie;
	
	/**
	 * Liste des strategies que le joueur peut utiliser.
	 * 
	 * @see Strategie
	 * @see Joueur#getStrategiesDisponibles()
	 */
	private ArrayList<Strategie> strategiesDisponibles;
	
	/**
	 * Carte que le joueur devra poser.
	 * 
	 * @see Joueur#setCarteAPoser(int)
	 */
	private int carteAPoser;
	
	/**
	 * Liste des actions que le joueur devra effectuer.
	 * 
	 * @see Joueur#ajouterAction(int)
	 * @see Joueur#supprimerAction()
	 * @see Joueur#faireAction()
	 */
	private LinkedList<Integer> actionsAFaire;
	
	/**
	 * Action de poser une carte.
	 */
	public final static int POSE_CARTE = 0;
	/**
	 * Action de piocher.
	 */
	public final static int PIOCHE = 1;
	/**
	 * Action du choix de couleur
	 */
	public final static int CHOIX_COULEUR = 2;
	
	/**
	 * Attribut indiquant si l'annonce "Carte" a ete faite.
	 * 
	 * @see Joueur#aAnnonceCarte()
	 * @see Joueur#annoncerCarte()
	 * @see Joueur#contrerCarte()
	 */
	private boolean carteAnnonce = false;
	
	//******** CONSTRUCTEURS *********
	/**
	 * Constructeur Joueur
	 * 
	 * <p>
	 * A la construction d'un objet Joueur, les strategies possibles sont ajoutees, et sa strategie est choisie aleatoirement parmi celles disponibles.
	 * De plus, la liste des actions est cree.
	 * </p>
	 * 
	 * @param nom nom du joueur
	 */
	public Joueur(String nom){
		this.nom = nom;
		this.score = 0;
		this.main = new Main();
		
		strategiesDisponibles = new ArrayList<Strategie>();
		strategiesDisponibles.add(new StrategieSimple());
		strategiesDisponibles.add(new StrategieAgressive());
		strategiesDisponibles.add(new StrategiePrudente());
		strategiesDisponibles.add(new StrategieComplexe());
		
		this.strategie = strategiesDisponibles.get((int) (Math.random()*(strategiesDisponibles.size()-1)));
		this.actionsAFaire = new LinkedList<Integer>();
	}
		
	
	//*********** METHODES ***********
	
	/**
	 * Demarre le tour du joueur.
	 * Appelle la methode {@link Strategie#jouer(Joueur)} de la Strategie que le joueur utilise.
	 * 
	 * @see Strategie
	 */
	public void jouer() {
		this.setChanged();
		this.notifyObservers(Notifications.debutTour);
		this.strategie.jouer(this);
	}
	
	/**
	 * Pose une carte sur le talon et applique son effet.
	 * 
	 * @param indiceCarte l'indice de la carte a poser. Il correspond a la position de la carte dans la main. Il doit etre compris entre 0 et le nombre de cartes dans la main du joueur non inclus.
	 * @throws IllegalArgumentException si l'indice entre ne correspond a aucune carte.
	 * @throws CarteNonValideException si la carte n'est pas jouable.
	 * 
	 * @see Talon
	 * @see Main
	 */
	private void poserCarte(int indiceCarte)  throws IllegalArgumentException, CarteNonValideException{
		//Verification des arguments
		if(indiceCarte < 0 || indiceCarte >= this.main.getNombreCartes()) {
			throw new IllegalArgumentException("Vous n'avez que " + this.main.getNombreCartes() + " cartes dans votre main");
		}
		Carte carte = main.tirerCarte(indiceCarte);
		Partie.getInstance().getTalon().poserCarte(carte);
		Partie.getInstance().getTalon().getCarteDessus().appliquerEffet();
	}
	
	/**
	 * Prend une ou plusieurs cartes de la pioche pour l'ajouter a la main du joueur.
	 * 
	 * @see Pioche
	 * @see Main
	 */
	private void piocher() {
		ArrayList<Carte> cartes = Partie.getInstance().getPioche().piocher();
		this.main.addCarte(cartes);
		Partie.getInstance().getTalon().setContre(false);
	}
	
	/**
	 * Termine le tour du joueur et passe au joueur suivant.
	 * Ajoute egalement le joueur dans le classement s'il n'a plus aucune carte.
	 * 
	 *@see Partie
	 */
	private void finirTour() {
		Partie partie = Partie.getInstance();
		if(this.getMain().getNombreCartes() == 0) {
			partie.addGagnant(partie.getJoueurActuel());
			this.setChanged();
			this.notifyObservers(Notifications.aFini);
		}
		this.setChanged();
		this.notifyObservers(Notifications.finTour);
		partie.changerJoueur();
	}
	
	
	//------- Gestion Actions --------
	
	/**
	 * Ajoute une action a la liste d'actions que le joueur doit effectuer.
	 * 
	 * @param action l'action a effectuer.
	 * 
	 * @see Joueur#actionsAFaire
	 */
	public void ajouterAction(int action) {
		this.actionsAFaire.add(action);
	}
	/**
	 * Supprime la premiere action de la liste. Cette methode est appelee apres completion de ladite action.
	 * 
	 * @see Joueur#actionsAFaire
	 */
	public void supprimerAction() {
		this.actionsAFaire.removeFirst();
	}
	
	/**
	 * Applique les actions que le joueur doit encore effectuer et finit le tour du joueur une fois toutes les actions accomplies.
	 * 
	 * @see Joueur#actionsAFaire
	 * @see Joueur#ajouterAction(int)
	 * @see Joueur#supprimerAction()
	 * @see Joueur#finirTour()
	 */
	public void faireAction() {
		boolean stop = false;
		while(this.actionsAFaire.size() > 0 && !stop) {
			switch(this.actionsAFaire.peekFirst()) {
			case POSE_CARTE:
				try {
					this.poserCarte(this.carteAPoser);
					this.setCarteAPoser(-1);
					this.supprimerAction();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (CarteNonValideException e) {
					e.printStackTrace();
				}
				break;
			case PIOCHE:
				this.piocher();
				this.supprimerAction();
				break;
			case CHOIX_COULEUR:
				stop = true;
				Partie.getInstance().getTalon().addObserver(this);
				this.choisirCouleur();
				break;
			}
		}
		if(this.actionsAFaire.size() == 0 && Partie.getInstance().getJoueurActuel() == this) {
			this.finirTour();
		}
	}
	

	//----------- Annonces -----------
	
	/**
	 * Le joueur annonce "Carte" a condition qu'il ne possede plus qu'une carte.
	 * L'etat de l'annonce est alors indique comme fait.
	 * 
	 * @see Joueur#carteAnnonce
	 */
	public void annoncerCarte() {
		if(this.main.getNombreCartes() == 1 && !this.carteAnnonce) {
			this.carteAnnonce = true;
			this.setChanged();
			this.notifyObservers(Notifications.annonceCarte);
		}
	}
	
	/**
	 * Le joueur annonce "Contre-Carte" au joueur specifie, et le contre s'il n'a plus qu'une carte et n'a pas fait d'annonce.
	 * 
	 * @param joueur le joueur a contrer
	 * 
	 * @see Joueur#contrerCarte()
	 */
	public void annoncerContreCarte(Joueur joueur) {
		this.setChanged();
		this.notifyObservers(Notifications.annonceContreCarte);
		if(! joueur.aAnnonceCarte() && joueur.getMain().getNombreCartes() == 1) {
			joueur.contrerCarte();
		}
	}
	
	/**
	 * Specifie que le joueur a ete contre et le fait piocher deux cartes.
	 * Cette methode met a jour l'etat de l'annonce.
	 * 
	 * @see Joueur#carteAnnonce
	 * @see Joueur#piocher()
	 */
	public void contrerCarte() {
		this.carteAnnonce = true;
		this.setChanged();
		this.notifyObservers(Notifications.estContre);
		Partie.getInstance().getPioche().increaseNbCartesAPiocher(2);
		this.piocher();
	}
	
	//------------ Scores ------------
	
	//Incremente le score du nombre de points gagn�s dans la partie
	/**
	 * Incremente le score du joueur d'autant de points qu'indique.
	 * 
	 * @param point le nombre de points a ajouter.
	 * 
	 * @see Joueur#score
	 */
	public void augmenterScore(int point) { 
		this.score += point;
	}
	
	//------------- Choix ------------
	
	/**
	 * Choisi la couleur qui devra etre jouee au prochain tour.
	 * Le choix est effectue selon la strategie du joueur.
	 * 
	 * @see Strategie
	 */
	private void choisirCouleur() {
		this.setChanged();
		this.strategie.choisirCouleur(this);
	}
	
	/**
	 * Detecte lorsque le joueur a choisi une couleur. Cette methode est appelee lorsqu'un changement de couleur est effectue.
	 * 
	 * @see Joueur#choisirCouleur()
	 */
	public void update(Observable obs, Object o) {
		if(obs instanceof Talon && o instanceof String) {
			this.supprimerAction();
			Partie.getInstance().getTalon().deleteObserver(this);
			this.faireAction();
		}
	}
	
	//********** ACCESSEURS **********
		
	/**
	 * Retourne le nom du joueur.
	 * 
	 * @return le nom du joueur
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Retourne le score du joueur.
	 * 
	 * @return le score du joueur
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Retourne l'etat de l'annonce.
	 * 
	 * @return true si l'annonce a ete faite
	 */
	public boolean aAnnonceCarte() {
		return this.carteAnnonce;
	}
	
	/**
	 * Retourne la main du joueur.
	 * 
	 * @return la main du joueur
	 * 
	 * @see Main
	 */
	public Main getMain() {
		return this.main;
	}
	
	/**
	 * Retourne la strategie utilisee actuellement par le joueur.
	 * 
	 * @return la strategie du joueur
	 * @see Strategie
	 */
	public Strategie getStrategie() {
		return this.strategie;
	}
	
	/**
	 * Retourne l'ensemble des strategies que le joueur peut utiliser.
	 * 
	 * @return la liste des strategies possibles
	 * @see Strategie
	 */
	public ArrayList<Strategie> getStrategiesDisponibles() {
		return this.strategiesDisponibles;
	}
	
	
	//********** MUTATEURS ***********
	
	/**
	 * Met a jour le nom du joueur.
	 * 
	 * @param nom le nouveau nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
		this.setChanged();
		this.notifyObservers(Notifications.changementNom);
	}
	
	/**
	 * Remet l'etat de l'annonce a sa valeur par defaut, a savoir false.
	 */
	public void setCarteAnnonceDefault() {
		this.carteAnnonce = false;
	}
	
	/**
	 * Met a jour la strategie utilisee par le joueur.
	 * 
	 * @param nouvelleStrategie la strategie a utiliser.
	 */
	public void setStrategie(Strategie nouvelleStrategie) {
		this.strategie = nouvelleStrategie;
		this.setChanged();
		this.notifyObservers(Notifications.changementStrategie);
	}
	
	/**
	 * Met a jour la carte que le joueur devra poser.
	 * 
	 * @param indiceCarte l'indice de la carte a poser.
	 * 
	 * @see Joueur#POSE_CARTE
	 * @see Joueur#faireAction()
	 */
	public void setCarteAPoser(int indiceCarte) {
		this.carteAPoser = indiceCarte;
	}
}
