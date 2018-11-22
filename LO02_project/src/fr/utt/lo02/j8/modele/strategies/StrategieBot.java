package fr.utt.lo02.j8.modele.strategies;

import fr.utt.lo02.j8.modele.effets.Effets;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilites;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Main;
import fr.utt.lo02.j8.modele.moteur.Partie;

/**
 * <b>StrategieBot est la classe representant une strategie de joueur virtuel</b>
 * 
 * Une strategie de bot est caracterisee par :
 * <ul>
 * <li>le nombre de tours qu'elle est en place</li>
 * </ul>
 * <p>
 * StrategieBot est une classe abstraite. Les classes qui l'implementeront s'engagent a implementer la methode suivante :
 * <ul>
 * <li>choisirAction(Joueur) : determine l'action que le joueur effectue</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 * @see Strategie
 */
public abstract class StrategieBot implements Strategie, Runnable{
	
	/**
	 * Nombre de tours depuis lequel la strategie est en place.
	 * Il est positif.
	 * Il est modifiable.
	 * 
	 * @see StrategieBot#choisirStrategie(Joueur)
	 */
	private int nombreToursStrategie;
	
	/**
	 * Joueur sur lequel la methode {@link StrategieBot#run()} s'applique.
	 */
	private Joueur joueur;
	
	
	//************* Jeu **************
	
	/**
	 * Lance le thread executant les actions que le joueur effectue.
	 * 
	 * @param joueur le joueur qui joue
	 * 
	 * @see StrategieBot#run()
	 */
	public void jouer(Joueur joueur) {
		this.joueur = joueur;
		Thread t = new Thread(this);
		t.start();
	}
	
	/**
	 * Execution d'un thread.
	 * Execute les differentes actions effectuees par le joueur pendant son tour : choix de la strategie, pose d'une carte ou pioche, annonce.
	 * 
	 * @see StrategieBot#choisirStrategie(Joueur)
	 * @see StrategieBot#choisirAction(Joueur)
	 */
	public void run() {
		//Remise de la valeur de carte annoncee a fausse si le joueur a plus d'une carte en main (pour permettre une nouvelle annonce)
		if(joueur.getMain().getNombreCartes() > 1 && joueur.aAnnonceCarte()) {
			joueur.setCarteAnnonceDefault();
		}
		
		//Appel de la strategie
		this.choisirStrategie(joueur);
		try {							//Délai, pour temporiser le jeu
			if(!Partie.getInstance().aFini(Partie.getInstance().getJoueur(0))) {
				Thread.sleep(1500);
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		if(Partie.getInstance().getJoueurPrecedent().getMain().getNombreCartes() == 1 && ! Partie.getInstance().getJoueurPrecedent().aAnnonceCarte()) {
			if((int)(Math.random()*10) >= 5) {
				joueur.annoncerContreCarte(Partie.getInstance().getJoueurPrecedent());
			}
		}
		
		try {							//Délai, pour temporiser le jeu
			if(!Partie.getInstance().aFini(Partie.getInstance().getJoueur(0))) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		this.choisirAction(joueur);
		this.nombreToursStrategie++;
		
		if(joueur.getMain().getNombreCartes() == 1) {
			joueur.annoncerCarte();
		}
	}
	
	/**
	 * Determine l'action que le joueur effectue : pose d'une carte, pioche.
	 * 
	 * @param joueur le joueur devant effectuer une action
	 */
	public abstract void choisirAction(Joueur joueur);
	
	//Teste si une carte de la main repond aux criteres specifies et  renvoit la position de cette carte dans la main
	/**
	 * Recherche dans la main une carte correspondant aux criteres specifies.
	 * 
	 * @param main la main dans laquelle il faut rechercher une carte
	 * @param effetsRequis les effets que la carte doit posseder
	 * @param effetsIndesirables les effets que la carte ne doit pas posseder
	 * @param jouabilitesRequises les jouabilites que la carte doit posseder
	 * @param jouabilitesIndesirables les jouabilites que la carte ne doit pas posseder
	 * @param conditionSupplementaire la condition supplementaire a respecter. Peut prendre les valeurs "Couleur Talon" et "Cartes Jouables Apres"
	 * @return l'indice d'une carte repondant aux criteres, ou -1 si aucune carte ne correspond
	 * 
	 */
	public int chercherCarteSpecifique(Main main, Effets[] effetsRequis, Effets[] effetsIndesirables, Jouabilites[] jouabilitesRequises, Jouabilites[] jouabilitesIndesirables, String conditionSupplementaire) {
		boolean valide;
		int carte = -1;
		
		//Parcours chaque carte de la main tant qu'elle n'en a pas trouve une
		int i=0;
		Carte carteTestee;
		while(carte == -1 && i<main.getNombreCartes()) {
			carteTestee = main.getCarte(i);
			if(carteTestee.estJouable()) {
				//teste chacun de parametres
				valide = true;
				
				//Effets Requis
				if(effetsRequis != null) {
					for(int j=0; j<effetsRequis.length; j++) {
						if( ! carteTestee.aEffet(effetsRequis[j])) {
							valide = false;
						}
					}
				}
				//Effets Indesirables
				if(effetsIndesirables != null) {
					for(int j=0; j<effetsIndesirables.length; j++) {
						if(carteTestee.aEffet(effetsIndesirables[j])) {
							valide = false;
						}
					}
				}
				//Jouabilites Requises
				if(jouabilitesRequises != null) {
					for(int j=0; j<jouabilitesRequises.length; j++) {
						if( ! carteTestee.aJouabilite(jouabilitesRequises[j])) {
							valide = false;
						}
					}
				}
				//Jouabilites indesirables
				if(jouabilitesIndesirables != null) {
					for(int j=0; j<jouabilitesIndesirables.length; j++) {
						if(carteTestee.aJouabilite(jouabilitesIndesirables[j])) {
							valide = false;
						}
					}
				}
				
				//Conditions supplementaires
				if(conditionSupplementaire != null) {
				if(conditionSupplementaire == "Couleur Talon") {	//meme couleur que le talon
					if(carteTestee.getCouleur() != Partie.getInstance().getTalon().getCouleur()) {
						valide = false;
					}
				}
				if(conditionSupplementaire == "Cartes Jouables apres") {	//il possede des cartes pouvant etre posees sur celle-ci
					boolean aCartesAJouerApres = false;
					int k = 0;
					while(!aCartesAJouerApres && k<main.getNombreCartes()) {
						if((carteTestee.getCouleur() == main.getCarte(k).getCouleur() || carteTestee.getHauteur() == main.getCarte(k).getHauteur()) && carteTestee != main.getCarte(k)) {
							aCartesAJouerApres = true;
						}
						k++;
					}
					if(!aCartesAJouerApres) {
						valide = false;
					}
				}
				}
				
				if(valide) {
					carte = i;
				}
			}
			i++;
		}
		return carte;
	}
	
	
	
	//************ Choix *************
	
	/**
	 * Change la strategie du joueur s'il a trop de cartes en main ou s'il a beaucoup plus de cartes que les autres joueurs ou encore de façon aleatoire, la probabilite augmentant avec la duree depuis laquelle la strategie est en place.
	 */
	public void choisirStrategie(Joueur joueur) {
		boolean changerStrategie = false;
		
		//Trop de cartes en main
		if(joueur.getMain().getNombreCartes() > 10) {
			changerStrategie = true;
		}
		//Plus de cartes que les autres
		int max = 0;
		for(int i=0; i<Partie.getInstance().getNombreJoueur(); i++) {
			if(Partie.getInstance().getJoueur(i).getMain().getNombreCartes() > max && Partie.getInstance().getJoueur(i) != joueur) {
				max = Partie.getInstance().getJoueur(i).getMain().getNombreCartes();
			}
		}
		if(joueur.getMain().getNombreCartes() > max + 3) {
			changerStrategie = true;
		}
		
		//Possibilite aleatoire de changer de strategie. Proba augmente avec le nombre de tours et est nulle au debut
		if((int)(Math.random()*10) + this.nombreToursStrategie >= 15) {
			changerStrategie = true;
		}
		
		//Changement de strategie
		if(changerStrategie) {
			int iNouvelleStrategie;
			do {
				iNouvelleStrategie = (int) (Math.random()*(joueur.getStrategiesDisponibles().size()-1));
			}while(joueur.getStrategiesDisponibles().get(iNouvelleStrategie) == joueur.getStrategie());
			joueur.setStrategie(joueur.getStrategiesDisponibles().get(iNouvelleStrategie));
			this.nombreToursStrategie = 0;
		}
	}
	
	/**
	 * 
	 */
	public void choisirCouleur(Joueur joueur) {
		//Compte le nombre de cartes de chaque couleur
		int[] nbCartesCouleur = {0,0,0,0};
    	for(int i=0; i < Partie.getInstance().getJoueurActuel().getMain().getNombreCartes(); i++) {
    		switch(Partie.getInstance().getJoueurActuel().getMain().getCarte(i).getCouleur()) {
    		case "Coeur":
    			nbCartesCouleur[0]++;
    			break;
    		case "Carreau":
    			nbCartesCouleur[1]++;
    			break;
    		case "Trefle":
    			nbCartesCouleur[2]++;
    			break;
    		case "Pique":
    			nbCartesCouleur[3]++;
    			break;
    		}
    	}
    	//Trouver le max
    	int max = 0;
    	int imax = 0;
    	for(int i=0; i < nbCartesCouleur.length; i++) {
    		if(nbCartesCouleur[i] > max) {
    			max = nbCartesCouleur[i];
    			imax = i;
    		}
    	}
    	Partie.getInstance().getTalon().setCouleur(Carte.COULEURS[imax]);
	}
}
