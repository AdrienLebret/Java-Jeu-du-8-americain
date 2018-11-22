package fr.utt.lo02.j8.vue.console;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import fr.utt.lo02.j8.controleur.ControleurConsole;
import fr.utt.lo02.j8.exceptions.CarteNonValideException;
import fr.utt.lo02.j8.exceptions.SaisieNonValideException;
import fr.utt.lo02.j8.modele.effets.ChangerCouleur;
import fr.utt.lo02.j8.modele.effets.ChangerSens;
import fr.utt.lo02.j8.modele.effets.FairePiocher;
import fr.utt.lo02.j8.modele.effets.PasserTour;
import fr.utt.lo02.j8.modele.effets.Rejouer;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Main;
import fr.utt.lo02.j8.modele.moteur.Notifications;
import fr.utt.lo02.j8.modele.moteur.Paquet;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.moteur.Pioche;
import fr.utt.lo02.j8.modele.moteur.Talon;
import fr.utt.lo02.j8.modele.strategies.StrategieUser;
import fr.utt.lo02.j8.modele.variantes.Variante5;
import fr.utt.lo02.j8.modele.variantes.Variante6;
import fr.utt.lo02.j8.modele.variantes.Variante7;
import fr.utt.lo02.j8.modele.variantes.VarianteCourteAmicale;
import fr.utt.lo02.j8.modele.variantes.VarianteMinimale;
import fr.utt.lo02.j8.modele.variantes.VarianteMonclar;

/**
 * <b> VueConsole est la classe s'occupant des entrees/sorties de la console</b>
 * 
 * Une vue console correspond au mode console du jeu.
 * Elle gere l'affichage des donnees dont l'utilisateur a besoin pour jouer ainsi que les interactions avec celui-ci : elle recupere les entrees et les traite.
 * Cette vue execute un thread charge des entrees utilisateurs.
 * 
 * Une vue console est caracterisee par les informations suivantes :
 * <ul>
 * <li>Un scanner attribue definitivement</li>
 * <li>Un controleur attribue definitivement</li>
 * <li>Un booleen autorisant ou non les entrees utilisateur</li>
 * <li>Un environnement modifiable. Il est compris entre 0 et 13 inclus</li>
 * </ul>
 * 
 * @see ControleurConsole
 * @see java.util.Scanner
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueConsole implements Observer, Runnable {
	
	/**
	 * Scanner recuperant les entrees utilisateurs.
	 * Ce scanner n'est pas changeable.
	 * 
	 * @see VueConsole#run()
	 */
	private Scanner sc;
	
	/**
	 * Controleur faisant le lien avec le modele.
	 * Ce controleur n'est pas changeable.
	 * 
	 * @see ControleurConsole
	 */
	private ControleurConsole controleur;
	
	/**
	 * Booleen caracterisant l'etat de la saisie : vrai si elle est activee.
	 */
	private boolean doSaisie;
	
	/**
	 * Environnement dans lequel on est actuellement (menu, menu joueurs, menu variantes, partie, ...)
	 * Cet environnement correspond a l'etat actuel de la vue. Il est compris entre 0 et 13 inclus et est modifiable tout au long du jeu.
	 * 
	 */
	private int environnement;
	
	/**
	 * Environnement de demarrage : l'ecran de demarrage est affiche.
	 */
	private final static int DEMARRAGE = 0;
	
	/**
	 * Environnement du choix de pseudo.
	 */
	private final static int PSEUDO = 1;
	
	/**
	 * Environnement du menu changer les parametres : l'utilisateur decide s'il souhaite modifier les parametres.
	 */
	private final static int MENU_CHANGER_PARAMETRES = 2;
	
	/**
	 * Environnement du menu des parametres : choix des parametres a modifier.
	 */
	private final static int MENU_PARAMETRES = 3;
	
	/**
	 * Environnement du menu des joueurs : choix du nombre de joueurs.
	 */
	private final static int MENU_JOUEURS = 4;
	
	/**
	 * Environnement du menu du paquet : choix de la taille des paquets.
	 */
	private final static int MENU_TAILLE_PAQUETS = 5;
	
	/**
	 * Environnement du menu des variantes : choix de la variante.
	 */
	private final static int MENU_VARIANTES = 6;
	
	/**
	 * Environnement du tour utilisateur : l'utilisateur a la main.
	 */
	private final static int TOUR_USER_JEU = 8;
	
	/**
	 * Environnement du changement de variante en cours de partie.
	 */
	private final static int TOUR_USER_VARIANTE = 9;
	
	/**
	 * Environnement du changement de couleur : choix de la nouvelle couleur.
	 */
	private final static int TOUR_USER_CHANGEMENT_COULEUR = 11;
	
	/**
	 * Environnement du tour d'un bot.
	 */
	private final static int TOUR_BOT = 12;
	
	/**
	 * Environnement de fin de partie, en attente d'une nouvelle.
	 */
	private final static int NOUVELLE_PARTIE = 13;
	
	/**
	 * Textes du menu des parametres.
	 */
	private final static String[] TEXTE_MENU_PARAMETRES = {"Que souhaitez-vous faire ?","Changer le nombre de joueurs","Changer la taille de paquet","Choisir la variante","Demarrer la partie"};
	
	/**
	 * Textes du menu des joueurs.
	 */
	private final static String[] TEXTE_MENU_JOUEURS = {"Souhaitez-vous","Ajouter un joueur","Retirer un joueur","Retour"};
	
	/**
	 * Textes du menu de la taille des paquets.
	 */
	private final static String[] TEXTE_MENU_TAILLE_PAQUETS = {"Selectionnez la taille de paquet desiree","32 cartes","52 cartes","Retour"};
	
	/**
	 * Textes du menu des variantes pour un paquet de 32 cartes.
	 */
	private final static String[] TEXTE_MENU_VARIANTES_32 = {"Selectionnez la variante desiree","Variante Courte Amicale","Variante 6","Variante 7","Retour"};
	
	/**
	 * Textes du menu des variantes pour un paquet de 52 cartes.
	 */
	private final static String[] TEXTE_MENU_VARIANTES_52 = {"Selectionnez la variante desiree","Variante Minimale","Variante 5","Variante Monclar","Retour"};
	
	/**
	 * Textes pour le changement de couleur.
	 */
	private final static String[] TEXTE_CHANGEMENT_COULEUR = {"Choisissez la nouvelle couleur",Carte.COULEURS[0],Carte.COULEURS[1],Carte.COULEURS[2],Carte.COULEURS[3]};
	
	/**
	 * Caractere indiquant que la console est prete a recevoir une entree clavier.
	 */
	public final static String PROMPT = "> ";
	
	/**
	 * Contructeur VueConsole.
	 * 
	 * <p>
	 * A la construction d'un objset VueConsole, un controleur et un scanner sont crees.
	 * De plus, l'environnement est initialise a l'etat demarrage, et le thread s'occupant de la recuperation des entrees utilisateur est demarre.
	 * La vue console est egalement ajoutee aux observateurs de la partie, du talon, de la pioche, et des joueurs et de leurs mains.
	 * </p>
	 * 
	 * @see VueConsole#controleur
	 * @see VueConsole#sc
	 * @see VueConsole#run()
	 */
	public VueConsole() {
		this.controleur = new ControleurConsole();
		sc = new Scanner(System.in);
		this.doSaisie = true;
		this.environnement = DEMARRAGE;
		
		Thread t = new Thread(this);
		this.afficherDemarrage();
		t.start();
		
		//Mise en observer
		Partie.getInstance().addObserver(this);
		Partie.getInstance().getTalon().addObserver(this);
		Partie.getInstance().getPioche().addObserver(this);
		for(int i=0; i<Partie.getInstance().getNombreJoueur(); i++) {
			Partie.getInstance().getJoueur(i).addObserver(this);
			Partie.getInstance().getJoueur(i).getMain().addObserver(this);
		}
	}
	
	/**
	 * Verifie les saisies dans le cas des menus : correspondance avec les caracteres valides.
	 * 
	 * @param saisie l'entree utilisateur a verifier
	 * @param caracteresValides les caracteres pour lesquels l'entree est valide
	 * @return true si l'entree est valide
	 */
	private boolean verifierSaisieMenu(String saisie, char[] caracteresValides){
		char caractere;
		boolean valide = false;
		
		if(saisie.length() == 1) {
			caractere = saisie.charAt(0);
			for(int i=0; i<caracteresValides.length; i++) {
				if(caractere == caracteresValides[i]) {
					valide = true;
				}
			}
		}
		if(!valide) {
			System.out.println("\nSaisie incorrecte");
			System.out.print(PROMPT);
		}
		return valide;
	}
	
	/**
	 * Affiche les menus possedant plusieurs options.
	 * 
	 * @param entete un texte a afficher avant le menu
	 * @param texte le texte du menu et les differentes options possibles
	 */
	private void afficherOptions(String entete, String[] texte) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n");
		sb.append("*****************************\n\n");
		if(entete != null) {
			sb.append(entete);
			sb.append('\n');
		}
		sb.append(texte[0] + "\n");
		for(int i=1; i<texte.length; i++) {
			sb.append("(" + i + ") ");
			sb.append(texte[i] + "\n");
		}
		System.out.println(sb);
	}
	
	/**
	 * Recupere les caracteres que l'utilisateur doit saisir a partir des tableaux de texte des menus :
	 * 1 pour la premiere option, 2 pour la deuxieme, ...
	 * 
	 * @param texte le texte du menu et ses options
	 * @return les caracteres d'entree valides
	 */
	private char[] getCaracteresValides(String[] texte) {
		char[]caracteres = new char[texte.length-1];
		for(int i=1; i<texte.length; i++) {
			caracteres[i-1] = Integer.toString(i).charAt(0);
		}
		return caracteres;
	}
	
	/**
	 * Execution d'un thread.
	 * Gere les entrees utilisateur.
	 * Recupere l'entree et apelle la methode correspondant a l'etat actuel de la vue console pour le traitement de la saisie.
	 */
	public void run() {
		while(doSaisie) {
			//Saisie
			String saisie = sc.nextLine();
			
			//Selection des actions a effectuer selon l'environnement actuel
			switch(this.environnement) {
			case DEMARRAGE:
				this.executerSaisieDemarrage(saisie);
				break;
			case PSEUDO:
				this.executerSaisiePseudo(saisie);
				break;
			case MENU_CHANGER_PARAMETRES:
				this.executerSaisieMenuChangerParametres(saisie);
				break;
			case MENU_PARAMETRES:
				this.executerSaisieMenuParametres(saisie);
				break;
			case MENU_JOUEURS:
				this.executerSaisieMenuJoueurs(saisie);
				break;
			case MENU_TAILLE_PAQUETS:
				this.executerSaisieMenuTaillePaquets(saisie);
				break;
			case MENU_VARIANTES:
				this.executerSaisieMenuVariante(saisie);
				break;
			case TOUR_USER_JEU:
				this.executerSaisieTourUser(saisie);
				break;
			case TOUR_USER_VARIANTE:
				this.executerSaisieVariante(saisie);
				break;
			case TOUR_USER_CHANGEMENT_COULEUR:
				System.out.println("ho ho ho");
				this.executerSaisieChangementCouleur(saisie);
				break;
			case NOUVELLE_PARTIE:
				this.executerSaisieNouvellePartie(saisie);
				break;
			}
			this.executerSaisieDefault(saisie);
		}
	}
	
	/**
	 * Met a jour l'affichage lors de la reception de notifications des differents observables que la vue console observe.
	 * 
	 * @see Partie
	 * @see Talon
	 * @see Pioche
	 * @see Joueur
	 * @see Main
	 */
	public void update(Observable obs, Object o) {
		//Notification de la partie
		if(obs instanceof Partie) {
			if(o instanceof Partie) {
				((Partie)o).addObserver(this);
				((Partie)o).getTalon().addObserver(this);
				((Partie)o).getPioche().addObserver(this);
				this.controleur.setPartie((Partie)obs);
			}else if(o instanceof Paquet){
				for(int i=0; i<((Paquet)o).getTaille(); i++) {
					((Paquet)o).getCarte(i).addObserver(this);
				}
			}else {	
			
			switch((Notifications) o) {
			
			case newJoueur:
				((Partie)obs).getJoueur(((Partie)obs).getNombreJoueur()-1).addObserver(this);
				((Partie)obs).getJoueur(((Partie)obs).getNombreJoueur()-1).getMain().addObserver(this);
				if(this.environnement == MENU_JOUEURS) {
					this.afficherMenuJoueur();
				}else {
					System.out.println("\nNombre de joueurs change : " + ((Partie)obs).getNombreJoueur());
					System.out.print(PROMPT);
				}
				break;
			case removeJoueur:
				if(this.environnement == MENU_JOUEURS) {
					this.afficherMenuJoueur();
				}else {
					System.out.println("\nNombre de joueurs change : " + ((Partie)obs).getNombreJoueur());
					System.out.print(PROMPT);
				}
				break;
			case taillePaquet :
				if(this.environnement == MENU_TAILLE_PAQUETS) {
					this.afficherMenuTaillePaquet();
				}else {
					System.out.println("\nTaille du paquet changee : " + ((Partie)obs).getTailleInitPaquet());
					System.out.print(PROMPT);
				}
				break;
			case changementVariante :
				if(this.environnement == MENU_VARIANTES) {
					this.afficherMenuVariante();
				}else {
					System.out.println("\nChangement de la Variante : " + ((Partie)obs).getVariante());
					System.out.print(PROMPT);
				}
				break;
			case affichageScore :
				this.afficherScore(Partie.getInstance());
				break;
			case demarragePartie :
				System.out.println("Appuyer sur C pour annoncer carte et sur V pour annoncer Contre-Carte");
			}
			}
		}else
		
		//Notifications des joueurs
		if(obs instanceof Joueur) {
			switch((Notifications) o) {
			case changementNom :
				if(this.environnement == PSEUDO || this.environnement == DEMARRAGE) {
					this.afficherMenuChangerParametres();
				}
				break;
			case debutTour :
				if(((Joueur)obs).getStrategie() instanceof StrategieUser) {
					this.environnement = TOUR_USER_JEU;
					this.afficherTourUser((Joueur)obs);
				}else {
					this.environnement = TOUR_BOT;
					System.out.printf("\n	Tour de " + ((Joueur)obs).getNom() + "\n\n");
				}
				break;
			case changementCouleur:
				this.afficherChangementCouleur();
				break;
			case annonceCarte :
				System.out.println(((Joueur)obs).getNom() + " a annonce \"Carte\"");
				break;
			case annonceContreCarte :
				System.out.println(((Joueur)obs).getNom() + " a annonce \"Contre-Carte\"");
				break;
			case estContre :
				System.out.println(((Joueur)obs).getNom() + " a ete contre");
				break;
			case faireAnnonceCarte :
				break;
			case faireAnnonceContreCarte :
				break;
			case aFini:
				System.out.println(((Joueur)obs).getNom() + " A TERMINE");
			}
		}else 
		
		if(obs instanceof Talon) {
			if(o instanceof Carte) {
				System.out.println("Talon : " + o);
			}else if(o instanceof String) {
				System.out.println("Nouvelle Couleur : " + o);
			}
		}else
			
		if(obs instanceof Carte) {
			if(o instanceof ChangerCouleur) {
				System.out.println("Changement de couleur");
			}else if(o instanceof ChangerSens) {
				System.out.println("Changement de Sens");
			}else if(o instanceof FairePiocher) {
				System.out.println(Partie.getInstance().getJoueurSuivant().getNom() +" pioche");
			}else if(o instanceof PasserTour) {
				System.out.println("Le joueur suivant passe son tour");
			}else if(o instanceof Rejouer) {
				System.out.println("Le joueur rejoue");
			}
		}else
		
		if(obs instanceof Pioche) {
			System.out.println("+ " + o + " cartes");
		}else
			
		if(obs instanceof Main && this.environnement != MENU_PARAMETRES && this.environnement != MENU_CHANGER_PARAMETRES && this.environnement != NOUVELLE_PARTIE) {
			System.out.println(Partie.getInstance().getJoueurActuel().getNom() + " a " + ((Main)obs).getNombreCartes() + " carte(s)");
		}
	
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat demarrage.
	 * Les caracteres acceptes sont O, o, N, n.
	 * O cree la partie, N quitte le jeu.
	 * 
	 * @param saisie l'entree utilisateur a traiter.
	 * 
	 * @see VueConsole#DEMARRAGE
	 */
	
	//Methodes pour : verifier saisie et appliquer les actions qu'elles entrainent suivant l'environnemnet
	
	private void executerSaisieDemarrage(String saisie) {
		char[] caracteresValides = {'O','o','N','n'};
		if(this.verifierSaisieMenu(saisie, caracteresValides)) {
			if(saisie.matches("^[Oo]$")) {
				this.afficherMenuPseudo();
			}else {
				System.exit(0);
			}
		}
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat pseudo.
	 * Accepte n'importe quel nom.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 * @see VueConsole#PSEUDO
	 * @see ControleurConsole#setNom(Joueur, String)
	 */
	
	private void executerSaisiePseudo(String saisie) {
		try {
			controleur.setNom(Partie.getInstance().getJoueur(0), saisie);
		} catch (SaisieNonValideException e) {
		}
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat changer les parametres.
	 * Les caracteres acceptes sont O, o, N, n.
	 * O amene sur les parametres, N demarre la partie.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 * @see VueConsole#MENU_CHANGER_PARAMETRES
	 * @see ControleurConsole#demarrerPartie()
	 */
	
	private void executerSaisieMenuChangerParametres(String saisie) {
		char[] caracteresValides = {'O','o','N','n'};
		if(this.verifierSaisieMenu(saisie, caracteresValides)) {
			if(saisie.matches("^[Oo]$")) {
				this.afficherMenuParametres();
			}else {
				this.controleur.demarrerPartie();
			}
		}
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat parametres.
	 * Accepte les chiffres correspondant aux differentes options possibles.
	 * Amene au menu correspondant ou demarra la partie.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 * @see VueConsole#MENU_PARAMETRES
	 * @see VueConsole#getCaracteresValides(String[])
	 */
	private void executerSaisieMenuParametres(String saisie) {
		char[]caracteresValides = this.getCaracteresValides(TEXTE_MENU_PARAMETRES);
		if(this.verifierSaisieMenu(saisie, caracteresValides)) {
			switch(saisie) {
			case "1" :
				this.afficherMenuJoueur();
				break;
			case "2" :
				this.afficherMenuTaillePaquet();
				break;
			case "3" :
				this.afficherMenuVariante();
				break;
			case "4" :
				this.controleur.demarrerPartie();
			}
		}
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat du choix du nombre de joueurs.
	 * Accepte les chiffres correspondant aux differentes options possibles.
	 * Ajoute ou supprime un joueur.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 * @see VueConsole#MENU_JOUEURS
	 * @see ControleurConsole#ajouterJoueur()
	 * @see ControleurConsole#supprimerJoueur()
	 * @see VueConsole#getCaracteresValides(String[])
	 */
	private void executerSaisieMenuJoueurs(String saisie) {
		char[] caracteresValides = this.getCaracteresValides(TEXTE_MENU_JOUEURS);
		if(this.verifierSaisieMenu(saisie, caracteresValides)) {
			switch(saisie) {
			case "1" :
				try {
					this.controleur.ajouterJoueur();
				} catch (SaisieNonValideException e) {
				}
				break;
			case "2" :
				try {
					this.controleur.supprimerJoueur();
				} catch (SaisieNonValideException e) {
				}
				break;
			case "3" :
				this.afficherMenuParametres();
				break;
			}
		}
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat du choix de la taille de paquets.
	 * Accepte les chiffres correspondant aux differentes options possibles.
	 * Change la taille du paquet et modifie la variante pour y correspondre.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 * @see VueConsole#MENU_TAILLE_PAQUETS
	 * @see ControleurConsole#setTaillePaquet(int)
	 * @see VueConsole#getCaracteresValides(String[])
	 */
	private void executerSaisieMenuTaillePaquets(String saisie) {
		char[] caracteresValides = this.getCaracteresValides(TEXTE_MENU_TAILLE_PAQUETS);
		if(this.verifierSaisieMenu(saisie, caracteresValides)) {
			switch(saisie) {
			case "1" :
				try {
					this.controleur.setVariante(new VarianteCourteAmicale());
					this.controleur.setTaillePaquet(32);
				} catch (SaisieNonValideException e) {
				}
				break;
			case "2" :
				try {
					this.controleur.setVariante(new VarianteMinimale());
					this.controleur.setTaillePaquet(52);
				} catch (SaisieNonValideException e) {
				}
				break;
			case "3" :
				this.afficherMenuParametres();
				break;
			}
		}
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat du choix de variante.
	 * Accepte les chiffres correspondant aux differentes options possibles.
	 * Change la variante.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 */
	private void executerSaisieMenuVariante(String saisie) {
		char[] caracteresValides;
		if(Partie.getInstance().getTailleInitPaquet() == 32) {
			caracteresValides = this.getCaracteresValides(TEXTE_MENU_VARIANTES_32);
			if(this.verifierSaisieMenu(saisie, caracteresValides)) {
				switch(saisie) {
				case "1" :
					this.controleur.setVariante(new VarianteCourteAmicale());
					break;
				case "2" :
					this.controleur.setVariante(new Variante6());
					break;
				case "3" :
					this.controleur.setVariante(new Variante7());
					break;
				case "4" :
					this.afficherMenuParametres();
					break;
				}
			}
			
		}else if(Partie.getInstance().getTailleInitPaquet() == 52){
			caracteresValides = this.getCaracteresValides(TEXTE_MENU_VARIANTES_52);
			if(this.verifierSaisieMenu(saisie, caracteresValides)) {
				switch(saisie) {
				case "1" :
					this.controleur.setVariante(new VarianteMinimale());
					break;
				case "2" :
					this.controleur.setVariante(new Variante5());
					break;
				case "3" :
					this.controleur.setVariante(new VarianteMonclar());
					break;
				case "4" :
					this.afficherMenuParametres();
					break;
				}
			}
		}
	}
	
	/**
	 * Traite la saisie lorsque la console est a l'etat tour utilisateur
	 * Accepte un chiffre entre 0 et le nombre de cartes dans la main de l'utilisateur inclus, ou *.
	 * Pioche, pose la carte indiquee ou amene au menu des varaiantes.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 * @see VueConsole#TOUR_USER_JEU
	 * @see ControleurConsole#controlActionJeu(String)
	 */
	private void executerSaisieTourUser(String saisie) {
		if(saisie.length() > 0) {
			if(saisie.matches("^\\*$")) {
				this.afficherMenuVariante();
			}else {
				try {
					this.controleur.controlActionJeu(saisie);
				} catch (SaisieNonValideException e) {
				} catch (CarteNonValideException e) {
				}
			}
		}
	}
	
	/**
	 * Traite la saisie lorsque la console est a l'etat variantes tour utilisateur.
	 * Accepte les chiffres correspondant aux differentes options possibles.
	 * Change la variante.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 * @see VueConsole#TOUR_USER_VARIANTE
	 * @see VueConsole#getCaracteresValides(String[])
	 */
	private void executerSaisieVariante(String saisie) {
		char[] caracteresValides;
		if(Partie.getInstance().getTailleInitPaquet() == 32) {
			caracteresValides = this.getCaracteresValides(TEXTE_MENU_VARIANTES_32);
			if(this.verifierSaisieMenu(saisie, caracteresValides)) {
				switch(saisie) {
				case "1" :
					this.controleur.setVariante(new VarianteCourteAmicale());
					break;
				case "2" :
					this.controleur.setVariante(new Variante6());
					break;
				case "3" :
					this.controleur.setVariante(new Variante7());
					break;
				case "4" :
					this.afficherTourUser(Partie.getInstance().getJoueur(0));
					this.environnement = TOUR_USER_JEU;
					break;
				}
			}
			
		}else if(Partie.getInstance().getTailleInitPaquet() == 52){
			caracteresValides = this.getCaracteresValides(TEXTE_MENU_VARIANTES_52);
			if(this.verifierSaisieMenu(saisie, caracteresValides)) {
				switch(saisie) {
				case "1" :
					this.controleur.setVariante(new VarianteMinimale());
					break;
				case "2" :
					this.controleur.setVariante(new Variante5());
					break;
				case "3" :
					this.controleur.setVariante(new VarianteMonclar());
					break;
				case "4" :
					this.afficherTourUser(Partie.getInstance().getJoueur(0));
					this.environnement = TOUR_USER_JEU;
					break;
				}
			}
		}
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat changement couleur.
	 * Accepte les chiffres correspondant aux differentes options possibles.
	 * Change la couleur.
	 * 
	 * @param saisie de l'utilisateur
	 * 

	 */
	private void executerSaisieChangementCouleur(String saisie) {
		char[] caracteresValides = this.getCaracteresValides(TEXTE_CHANGEMENT_COULEUR);
		if(this.verifierSaisieMenu(saisie, caracteresValides)) {
			this.controleur.setCouleur(Integer.parseInt(saisie)-1);
		}
	}
	
	/**
	 * Traite la saisie lorsque la vue console est a l'etat nouvelle partie.
	 * Accete les caracteres O, o, N, n.
	 * Cree et demarre une nouvelle partie ou quitte le jeu.
	 * 
	 * @param saisie de l'utilisateur
	 * 
	 * @see VueConsole#NOUVELLE_PARTIE
	 * @see ControleurConsole#creerPartie()
	 * @see ControleurConsole#demarrerPartie()
	 */
	private void executerSaisieNouvellePartie(String saisie) {
		char[] caracteresValides = {'O','o','N','n'};
		if(this.verifierSaisieMenu(saisie, caracteresValides)) {
			if(saisie.matches("^[Oo]$")) {
				this.controleur.creerPartie();
				this.controleur.demarrerPartie();
			}else {
				System.out.println("\nMERCI D'AVOIR JOUE ET A BIENTOT !");
				System.exit(0);
			}
		}
	}
	
	/**
	 * Traite d'autres types de saisies pouvant arriver lors de plusieurs etats : Annonces
	 * Accepte C, c, V et v.
	 * Annonce carte ou contre carte.
	 * 
	 * @param saisie l'entree utilisateur
	 * 
	 * @see ControleurConsole#annoncerCarte()
	 * @see ControleurConsole#annoncerContreCarte()
	 */
	private void executerSaisieDefault(String saisie) {
		if(saisie.length() == 1) {
			if(saisie.matches("^[cC]$") && Partie.getInstance().isStarted()){
				controleur.annoncerCarte();
			}else
			if(saisie.matches("^[vV]$") && Partie.getInstance().isStarted()){
				controleur.annoncerContreCarte();
			}
		}
	}
	
	//Methodes pour l'affichage
	
	/**
	 * Affiche le demarrage sur la console : jeu et auteurs, demarrage de partie.
	 */
	private void afficherDemarrage() {
		this.environnement = DEMARRAGE;
		
		System.out.println("*****************************************");
		System.out.println("            JEU DU 8 AMERICAIN           ");
		System.out.println("Par Jean-Baptiste LAUNOIS & Adrien LEBRET");
		System.out.println("*****************************************");
		
		System.out.println("\n\nVoulez-vous commencer une Partie ? (O/N)");
		System.out.print(PROMPT);
	}
	
	/**
	 * Affiche la saisie du pseudo sur la console : propose à l'utilisateur d'entrer un nom.
	 */
	private void afficherMenuPseudo() {
		this.environnement = PSEUDO;
		
		//Saisie du pseudo
		System.out.println("\n\nSaisissez votre pseudo : ");
		System.out.print(PROMPT);
	}
	
	/**
	 * Affiche sur la console les parametres par défaut et une proposition de changer les parametres.
	 */
	private void afficherMenuChangerParametres() {
		this.environnement = MENU_CHANGER_PARAMETRES;
		
		System.out.println("\n\nVoulez-vous modifier les parametres ? (O/N)\nParametres par défaut : 2 Joueurs ; 1 paquet de 52 cartes");
		System.out.print(PROMPT);
	}
	
	/**
	 * Affiche sur la console les differents parametres pouvant etre modifies.
	 */
	private void afficherMenuParametres() {
		this.environnement = MENU_PARAMETRES;
		
		this.afficherOptions(null,TEXTE_MENU_PARAMETRES);
		System.out.print(PROMPT);
	}
	
	/**
	 * Affiche sur la console le nombre de joueurs presents dans la partie et propose d'ajouter ou supprimer un joueur.
	 */
	private void afficherMenuJoueur() {
		this.environnement = MENU_JOUEURS;
		//entete
		StringBuffer sb = new StringBuffer();
		sb.append("Joueurs actuels : ");
		sb.append(Partie.getInstance().getNombreJoueur());
		
		//Affichage
		this.afficherOptions(sb.toString(),TEXTE_MENU_JOUEURS);
		System.out.print(PROMPT);
	}
	
	/**
	 * Affiche sur la console la taille de paquet selectionnee et les differentes tailles de paquets disponibles.
	 */
	private void afficherMenuTaillePaquet() {
		this.environnement = MENU_TAILLE_PAQUETS;
		
		//entete
		StringBuffer sb = new StringBuffer();
		sb.append("Taille actuelle des paquets : ");
		sb.append(Partie.getInstance().getTailleInitPaquet());
		
		//Affichage
		this.afficherOptions(sb.toString(),TEXTE_MENU_TAILLE_PAQUETS);
		System.out.print(PROMPT);
	}
	
	/**
	 * Affiche sur la console les differentes variantes possibles selon la taille du paquet.
	 */
	private void afficherMenuVariante() {
		if(this.environnement != TOUR_USER_JEU) {
			this.environnement = MENU_VARIANTES;
		}else {
			this.environnement = TOUR_USER_VARIANTE;
		}
		
		//entete
		StringBuffer sb = new StringBuffer();
		sb.append("Variante actuelle : ");
		sb.append(Partie.getInstance().getVariante());
		
		//Deux cas : 32 et 52 cartes
		if(Partie.getInstance().getTailleInitPaquet() == 32) {
			this.afficherOptions(sb.toString(),TEXTE_MENU_VARIANTES_32);
		}else if(Partie.getInstance().getTailleInitPaquet() == 52){
			this.afficherOptions(sb.toString(),TEXTE_MENU_VARIANTES_52);
		}
		System.out.print(PROMPT);
	}
	
	/**
	 * Affiche ce dont l'utilisateur a besoin pour jouer son tour : le talon ou la couleur si celle-ci a ete changee, s'il doit contrer, et sa main.
	 * 
	 * @param joueur le joueur dont il faut afficher la main
	 */
	private void afficherTourUser(Joueur joueur) {
		StringBuffer sb = new StringBuffer();
		if(Partie.getInstance().getTalon().isCouleurChangee()) {
			sb.append("\n*****************************\n");
			sb.append("/!\\ Changement de couleur /!\\\n");
			sb.append("COULEUR TALON : " + Partie.getInstance().getTalon().getCouleur() + "\n");
		}
		else{
			sb.append("\n***********************\n\n");
			sb.append("TALON : " + Partie.getInstance().getTalon().getCarteDessus().toString() + "\n");
		}
		
		
		//Indication si la carte posee juste avant necessite d'etre contree
		if(Partie.getInstance().getTalon().getContre()) {
			sb.append("--------------------------------\n");
			sb.append("  Vous devez contrer ou passer  \n");
			sb.append("--------------------------------\n");
		}
		//Affichage de la main + choix de l'action
		sb.append("\nSelectionner la carte a jouer :\n" + joueur.getMain().toString() + "\n");
		sb.append("(0) OU : Piocher\n(*) OU : Variante\n");
		sb.append(PROMPT);
		System.out.print(sb);
	}
	
	/**
	 * Affiche les differentes couleurs que l'utilisateur peut commencer
	 */
	private void afficherChangementCouleur() {
		this.environnement = TOUR_USER_CHANGEMENT_COULEUR;
		this.afficherOptions(null, TEXTE_CHANGEMENT_COULEUR);
		System.out.print(PROMPT);
	}
	
	/**
	 * Affiche le score de chaque joueur et propose de recommencer une partie.
	 * 
	 * @param partie la partie en cours
	 */
	private void afficherScore(Partie partie) {
		this.environnement = NOUVELLE_PARTIE;
		StringBuffer sb = new StringBuffer();
		
		ArrayList<Joueur> classement = partie.getClassement();
		Iterator<Joueur> it = classement.iterator();
		
		sb.append("************* SCORE *************\n\n");
		while(it.hasNext()) {
			Joueur joueur = it.next();
			sb.append(joueur.getNom() + " : " + joueur.getScore() + "\n");
		}
		sb.append("\n*********************************\n\n");
		sb.append("Voulez-vous continuer ? (O/N)\n");
		sb.append(PROMPT);
		
		System.out.print(sb);
	}
	
}
