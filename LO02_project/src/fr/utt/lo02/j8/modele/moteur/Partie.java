package fr.utt.lo02.j8.modele.moteur;
import java.util.ArrayList;
import java.util.Observable;

import fr.utt.lo02.j8.modele.strategies.StrategieUser;
import fr.utt.lo02.j8.modele.variantes.Variante;
import fr.utt.lo02.j8.modele.variantes.VarianteMinimale;

/**
 * <b>Partie est la classe representant une partie de 8 americain.</b>
 * 
 * Il s'agit d'un singleton, deux parties ne pouvant etre lancees simultanement.
 * Une partie est caracterisee par les informations suivantes:
 * <ul>
 * <li>Une liste de joueurs modifiable tant que la partie n'est pas demarree. Elle peut contenir entre 2 et 6 joueurs.</li>
 * <li>Un paquet de cartes.</li>
 * <li>Un talon attribue definitvement.</li>
 * <li>Une pioche attribuee definitivement</li>
 * <li>Une variante susceptible d'etre changee</li>
 * <li>Le joueur dont c'est actuellement le tour. Il est change a chaque tour de jeu.</li>
 * <li>Un sens, susceptible d'etre change. </li>
 * <li>Le classement de la partie. Il contient les joueurs qui ont fini de jouer.</li>
 * <li>Un numero de partie attribue definitivement</li>
 * </ul>
 * 
 * @see Joueur
 * @see Paquet
 * @see Talon
 * @see Pioche
 * @see Variante
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Partie extends Observable{
	/**
	 * Instance de la partie. Il ne peut n'y avoir qu'une partie a la fois.
	 * Elle est modifiable.
	 * 
	 * @see Partie#getInstance()
	 */
	private static Partie instance = null;
	
	/**
	 * Liste des joueurs de la partie.
	 * Il est possible d'ajouter et de supprimer des joueurs tant que la partie n'est pas demarree.
	 * 
	 * @see Joueur
	 * @see Partie#addJoueur()
	 * @see Partie#removeJoueur()
	 */
	private ArrayList<Joueur> joueurs;
	
	/**
	 * Joueur dont c'est actuellement le tour.
	 * Elle est modifiable et change a chaque tour de jeu.
	 * 
	 * @see Partie#changerJoueur()
	 */
	private int joueurActuel;
	
	/**
	 * Quantite maximale de joueurs dans une partie.
	 */
	public static final int NOMBRE_JOUEURS_MAX = 6;
	
	/**
	 * Liste des joueurs ayant fini de jouer, c'est a dire n'ayant plus aucune carte dans la main.
	 * Cette liste permet de connaitre les joueurs ayant fini et leur classement.
	 * Elle est modifiable.
	 * 
	 * @see Partie#addGagnant(Joueur)
	 * @see Partie#attribuerScore()
	 */
	private ArrayList<Joueur> classement = new ArrayList<Joueur>();
	
	/**
	 * Paquet utilise dans la partie.
	 * Il n'est pas modifiable.
	 * 
	 * @see Partie#getPaquet()
	 */
	private Paquet paquet;
	
	/**
	 * Taille du paquet a utiliser. Elle peut prendre les valeurs 32, 34, 52 et 54.
	 * Elle est modifiable tant que la partie n'est pas demarree
	 * 
	 * @see Partie#getTailleInitPaquet()
	 * @see Partie#setTailleInitPaquet(int)
	 */
	private int tailleInitPaquet;
	
	/**
	 * Talon de la partie.
	 * Elle n'est pas modifiable.
	 * 
	 * @see Partie#getTalon()
	 */
	private Talon talon;
	
	/**
	 * Pioche de la partie.
	 * Elle n'est pas modifiable.
	 * 
	 * @see Partie#getPioche()
	 */
	private Pioche pioche;
	
	/**
	 * Variante utilisee.
	 * Elle est modifiable.
	 * 
	 * @see Partie#getVariante()
	 * @see Partie#setVariante(Variante)
	 */
	private Variante variante;
	
	/**
	 * Sens dans lequel la partie est actuellement. Il determine l'ordre de jeu des joueurs.
	 * Il est modifiable.
	 * 
	 * @see Sens
	 * @see Partie#getSens()
	 * @see Partie#changerSens()
	 */
	private Sens sensActuel;
	
	/**
	 * Le nombre de joueurs a passer.
	 * Il est utilise pour determiner le joueur qui prend la main a la fin d'un tour.
	 * <ul>
	 * <li>0 : aucun changement de joeuur</li>
	 * <li>1 : passage au joueur suivant</li>
	 * <li>2 : un joueur passe son tour</li>
	 * <li>...</li>
	 * </ul>
	 * Il est modifiable.
	 * 
	 * @see Partie#changerJoueur()
	 */
	private int nombreJoueursAPasser;
	
	/**
	 * Etat de la partie : true si la partie est demarree.
	 * Elle n'est modifiable qu'une fois : passage a vrai au demarrage.
	 * 
	 * @see Partie#demarrer()
	 */
	private boolean demarree;
	
	/**
	 * Numero de la partie.
	 * Il est definitif.
	 */
	private int numeroPartie;
	
	
	//******** CONSTRUCTEURS *********
	
	/**
	 * Constructeur Partie par defaut.
	 * 
	 * A la construction d'un objet Partie, deux joueurs sont crees, dont un auquel on attribue la strategie utilisateur.
	 * Les differents parametres sont initialises a la valeur standard :
	 * <ul>
	 * <li>taille de paquet de 52</li>
	 * <li>variante minimale</li>
	 * <li>sens horaire</li>
	 * </ul>
	 * Le talon et la pioche sont egalement crees, et la partie est a l'etat non demarree.
	 * 
	 * @see Partie#joueurs
	 * @see Variante
	 * @see Sens
	 */
	private Partie() {
		this.demarree = false;
		this.sensActuel = Sens.horaire;
		this.joueurs = new ArrayList<Joueur>();
		this.joueurs.add(new Joueur("Joueur 1"));
		this.joueurs.get(0).setStrategie(new StrategieUser());
		this.joueurs.add(new Joueur("Joueur 2"));
		this.numeroPartie = 0;
		this.nombreJoueursAPasser = 1;
		this.tailleInitPaquet = 52;
		this.variante = new VarianteMinimale();
		
		this.talon = new Talon();
		this.pioche = new Pioche();
	}
	
	/**
	 * Constructeur Partie permettant l'entree directe des parametres.
	 * Il est utlise pour creer une nouvelle partie en reprenant les parametres de la precedente.
	 * 
	 * <p>
	 * A la construction d'un objet Partie, le sens est initialise a sens horaire, et talon et pioche sont crees.
	 * De plus, les mains des joueurs sont vides.
	 * </p>
	 * 
	 * @param listeJoueurs la liste des joueurs en jeu
	 * @param jeuDemarre l'etat de la partie : vrai si la partie est demarree
	 * @param numPartie le numero de la partie
	 * @param taillePaquets la taille des paquets. Elle peut prendre les valeurs 32, 34, 52 et 54
	 * @param variante la variante a utiliser
	 * 
	 * @see Sens
	 */
	private Partie(ArrayList<Joueur> listeJoueurs, boolean jeuDemarre, int numPartie,int taillePaquets, Variante variante) {
		this.demarree = jeuDemarre;
		this.sensActuel = Sens.horaire;
		this.joueurs = listeJoueurs;
		this.numeroPartie = numPartie;
		for(int i=0; i<this.joueurs.size(); i++) {
			this.joueurs.get(i).getMain().vider();
		}
		this.nombreJoueursAPasser = 1;
		this.tailleInitPaquet = taillePaquets;
		this.variante = variante;
		
		this.talon = new Talon();
		this.pioche = new Pioche();
	}
	
	/**
	 * Retourne l'instance de la partie.
	 * S'il n'existe encore aucune partie, en cree une.
	 * 
	 * @return l'instance de Partie
	 */
	public static Partie getInstance() {
		if(Partie.instance == null) {
			Partie.instance = new Partie();
		}
		return Partie.instance;
	}
	
	/**
	 * Cree une partie s'il n'en existe pas, ou cree une nouvelle partie en transmettant les donnees de l'ancienne sinon.
	 */
	public static void nouvellePartie() {
		if(Partie.instance == null) {
			Partie.instance = new Partie();
		}else {
			Partie partie = new Partie(Partie.instance.joueurs, true, Partie.instance.numeroPartie++, Partie.instance.tailleInitPaquet, Partie.instance.variante);
			//Notification que la partie est cree
			instance.setChanged();
			instance.notifyObservers(partie);
			Partie.instance = partie;
		}
	}
	
	//*********** METHODES ***********
	
	//------------ Partie ------------
	
	//Demarrage de la partie : creation des differents objets et mise en place
	/**
	 * Met en place la partie et la lance. A partir de l'appel de cette methode, les parametres ne sont plus modifiables.
	 * 
	 * <p>
	 * Cree les objets que la partie utilisera (paquets de cartes) en fonction des parametres choisis.
	 * Les cartes sont egalement distribuees et le premier tour est lance.
	 * </p>
	 * 
	 * @see Partie#demarree
	 */
	public void demarrer() {
		this.demarree = true;
		
		//Creation des paquets
		int nombrePaquets = 1;
		while(this.tailleInitPaquet * nombrePaquets / this.getNombreJoueur() < 12) {
			nombrePaquets++;
		}
		
		this.paquet = new Paquet(this.tailleInitPaquet);
		for(int i=0; i<nombrePaquets-1; i++) {
			this.paquet.fusionnerPaquet(new Paquet(this.tailleInitPaquet));
		}
		this.setChanged();
		this.notifyObservers(this.paquet);
		
		//Association des effets aux cartes
		this.variante.donnerEffet(paquet);
		
		
		//Melange et distribution des paquets
		this.paquet.melanger();
		this.paquet.distribuer(this.joueurs);
		
		//Notification
		this.setChanged();
		this.notifyObservers(Notifications.demarragePartie);
		
		//Definition du joueur qui va commencer a ce tour en fonction de la xieme partie
		this.joueurActuel = this.numeroPartie % this.joueurs.size();
		this.getJoueurActuel().jouer();
	}
	
	/**
	 * Passe au prochain joueur devant jouer et demarre son tour.
	 * Dans le cas ou il ne reste plus q'un joueur en jeu, termine la partie.
	 * 
	 * @see Partie#nombreJoueursAPasser
	 * @see Partie#joueurs
	 * @see Partie#joueurActuel
	 * @see Partie#sensActuel
	 * @see Partie#terminer()
	 */
	public void changerJoueur() {
		//Verification du nombre de joueurs en jeu : si 1 seul, il faut arreter
		if(this.getNombreJoueurFinis() < this.getNombreJoueur()-1) {
			for(int i=0; i<this.nombreJoueursAPasser; i++) {
				do {
					if(this.sensActuel == Sens.horaire) {
						this.joueurActuel = (this.joueurActuel + 1) % this.joueurs.size();
					}else {
						this.joueurActuel = (this.joueurActuel - 1 + this.joueurs.size()) % this.joueurs.size();
					}
				}while(this.aFini(this.getJoueurActuel()));
			}
			this.nombreJoueursAPasser = 1;
			this.getJoueurActuel().jouer();
		}else {
			this.terminer();
		}
	}
	
	/**
	 * Termine la partie et attribue a chaque joueur son score.
	 * 
	 * @see Partie#attribuerScore()
	 */
	public void terminer() {
		//Les joueurs sont indiques comme ayant fini
		for(int i=0; i<this.getNombreJoueur(); i++) {
			if(!this.aFini(this.getJoueur(i))) {
				this.addGagnant(this.getJoueur(i));
			}
		}
		this.attribuerScore();
		this.setChanged();
		this.notifyObservers(Notifications.affichageScore);
	}
	
	/**
	 * Change le sens dans lequel la partie tourne.
	 * Si le sens etait horaire, il passe a anti-horaire et vice-versa.
	 * 
	 * @see Partie#sensActuel
	 */
	public void changerSens() {
		if(this.sensActuel == Sens.horaire) {
			this.sensActuel = Sens.antiHoraire;
		}else {
			this.sensActuel = Sens.horaire;
		}
	}

	//-------- Gestion Score ---------
	
	/**
	 * Ajoute un joueur au classement. Le joueur est alors considere comme ayant termine de jouer.
	 * 
	 * @param joueur le joueur ayant termine
	 * 
	 * @see Partie#classement
	 */
	public void addGagnant(Joueur joueur) {
		this.classement.add(joueur);
	}
	
	/**
	 * Augmente le score des trois premiers joueurs du classement de respectivement 50, 20 et 10 points.
	 * 
	 * @see Partie#classement
	 */
	public void attribuerScore() {
		this.classement.get(0).augmenterScore(50);
		this.classement.get(1).augmenterScore(20);
		if(this.joueurs.size() > 2) {
			this.classement.get(2).augmenterScore(10);
		}
	}
	
	//------- Gestion Joueurs --------
	
	/**
	 * Ajoute un joueur a la partie. Cette action n'est possible que tant que la partie n'est pas demarree.
	 * Aucune confirmation n'est retournee.
	 * 
	 * @see Partie#joueurs
	 */
	public void addJoueur() {
		if(!this.demarree) {
			int numero = this.getNombreJoueur()+1;
			this.joueurs.add(new Joueur(new String("Joueur " + numero)));
			
			//Notification
			this.setChanged();
			this.notifyObservers(Notifications.newJoueur);
		}
	}
	
	/**
	 * Retire un joueur a la partie. Cette action n'est possible que tant que la partie n'est pas demarree.
	 * Aucune confirmation n'est retournee.
	 * 
	 * @throws IllegalArgumentException si le nombre de joueurs minimum (2) est deja atteint
	 */
	public void removeJoueur() throws  IllegalArgumentException{
		if(!this.demarree) {
			if(this.joueurs.size() > 2) {
				this.joueurs.remove(this.joueurs.size() - 1);
			}else {
				throw new IllegalArgumentException();
			}
			
			//Notification
			this.setChanged();
			this.notifyObservers(Notifications.removeJoueur);
		}
	}
	
	
	//********** ACCESSEURS **********
	
	/**
	 * Retourne le sens dans lequel la partie tourne.
	 * 
	 * @return le sens actuel de la partie
	 */
	public Sens getSens() {
		return this.sensActuel;
	}
	
	/**
	 * Retourne le classement des joueurs.
	 * 
	 * @return le classement
	 */
	public ArrayList<Joueur> getClassement() {
		return this.classement;
	}
	
	/**
	 * Retourne si le joueur a fini de jouer ou non.
	 * 
	 * @param joueur le joueur a verifier
	 * @return true si le joueur a fini de jouer
	 */
	public boolean aFini(Joueur joueur) {
		boolean aFini = false;
		for(int i=0; i<this.getNombreJoueurFinis(); i++) {
			if(joueur == this.classement.get(i)) {
				aFini = true;
			}
		}
		return aFini;
	}
	
	/**
	 * Retourne le joueur present a la position de la liste des joueurs demandee.
	 * 
	 * @param numero l'indice du joueur dans la liste. Il doit etre compris entre 0 et le nombre de joueurs dans la partie
	 * @return le joueur a l'indice entre
	 * 
	 * @see Partie#joueurs
	 */
	public Joueur getJoueur(int numero) {
		return this.joueurs.get(numero);
	}
	
	/**
	 * Retourne le joueur dont c'est actuellement le tour.
	 * 
	 * @return le joueur en train de jouer
	 */
	public Joueur getJoueurActuel() {
		return this.joueurs.get(joueurActuel);
	}
	
	/**
	 * Retourne le joueur qui jouera au prochain tour.
	 * 
	 * @return le joueur suivant
	 */
	public Joueur getJoueurSuivant() {
		int joueurSuivant;
		if(this.sensActuel == Sens.horaire) {
			joueurSuivant = (this.joueurActuel + 1) % this.joueurs.size();
		}else {
			joueurSuivant = (this.joueurActuel - 1 + this.joueurs.size()) % this.joueurs.size();
		}
		return this.joueurs.get(joueurSuivant);
	}
	
	/**
	 * Retourne le joueur ayant joue au tour precedent.
	 * 
	 * @return le joueur precedent
	 */
	public Joueur getJoueurPrecedent() {
		int joueurPrecedent;
		if(this.sensActuel == Sens.antiHoraire) {
			joueurPrecedent = (this.joueurActuel + 1) % this.joueurs.size();
		}else {
			joueurPrecedent = (this.joueurActuel - 1 + this.joueurs.size()) % this.joueurs.size();
		}
		return this.joueurs.get(joueurPrecedent);
	}
	
	
	/**
	 * Retourne l'indice dans la liste des joueurs du joueur dont c'est actuellement le tour.
	 * 
	 * @return le numero du joueur en train de jouer. Il est compris entre 0 et le nombre de joueurs de la partie
	 */
	public int getPositionJoueurActuel() {
		return this.joueurActuel;
	}
	
	
	/**
	 * Retourne la quantite de joueurs presents dans la partie.
	 * 
	 * @return le nombre de joueurs. Il est compris entre 2 et le nombre maximum de joueurs
	 */
	public int getNombreJoueur() {
		return this.joueurs.size();
	}
	
	/**
	 * Retourne la taille des paquets utilises dans la partie.
	 * 
	 * @return la taille des paquets. Elle peut prendre les valeurs 32, 34, 52 et 54
	 */
	public int getTailleInitPaquet() {
		return this.tailleInitPaquet;
	}
	
	/**
	 * Retourne le nombre de joueurs ayant termine de jouer.
	 * 
	 * @return le nombre de joueurs finis. Il est compris entre 0 et le nombre de joueurs de la partie.
	 */
	public int getNombreJoueurFinis() {
		return this.classement.size();
	}
	
	/**
	 * Retourne le talon utilise dans la partie.
	 * 
	 * @return le talon de la partie
	 */
	public Talon getTalon() {
		return this.talon;
	}
	
	/**
	 * Retourne la pioche utilisee dans la partie.
	 * 
	 * @return la pioche de la partie
	 */
	public Pioche getPioche() {
		return this.pioche;
	}
	
	/**
	 * Retourne l'etat de la partie.
	 * 
	 * @return true si la partie est demarree.
	 */
	public boolean isStarted() {
		return this.demarree;
	}
	
	/**
	 * Retourne la variante utilisee actuellement par la partie.
	 * 
	 * @return la variante actuelle
	 */
	public Variante getVariante() {
		return this.variante;
	}
	
	/**
	 * Retourne le paquet cartes utilise par la partie.
	 * 
	 * @return le paquet de la partie
	 */
	public Paquet getPaquet() {
		return this.paquet;
	}
	
	//********** MUTATEURS ***********
	
	/**
	 * Definit la taille des paquets a utiliser.
	 * 
	 * @param t la taille des paquets. Elle peut prendre les valeurs 32, 34, 52 et 54.
	 */
	public void setTailleInitPaquet(int t) {
		this.tailleInitPaquet = t;
		this.setChanged();
		this.notifyObservers(Notifications.taillePaquet);
	}
	
	/**
	 * Met a jour la quantite de joueurs a passer. 
	 * 
	 * @param nombre le nombre de joueurs a passer. Il doit etre positif et inferieur au nombre de joueurs
	 * 
	 * @see Partie#nombreJoueursAPasser
	 */
	public void setNombreJoueursAPasser(int nombre) {
		this.nombreJoueursAPasser = nombre;
	}
	
	/**
	 * Change la variante utilisee par la partie, et met a jour les cartes pour correspondre a la nouvelle variante.
	 * 
	 * @param v la nouvelle variante. Elle doit utiliser des paquets de meme taille que ceux de la variante precedente.
	 */
	public void setVariante(Variante v) {
		this.variante = v;
		if(this.demarree) {
			this.variante.donnerEffet(paquet);
		}
		this.setChanged();
		this.notifyObservers(Notifications.changementVariante);
	}
}
