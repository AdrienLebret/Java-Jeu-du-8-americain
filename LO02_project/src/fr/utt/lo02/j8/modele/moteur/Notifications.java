package fr.utt.lo02.j8.modele.moteur;

/**
 * <b>Notifications : Enumeration des differentes notifications utilisees pour les vues console et graphique</b>
 * <p>
 * Deux categories de notifications :
 * </p>
 * <ul>
 * <li>Notifications concernant la <b>Partie</b>,</li>
 * <li>Notifications concernant le <b>Joueur</b>.</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public enum Notifications {
	
	//*********PARTIE*********
	creationPartie,
	demarragePartie,
	newJoueur,
	removeJoueur,
	taillePaquet,
	changementVariante,
	affichageScore,
	
	//*********JOUEUR*********
	changementNom,
	debutTour,
	finTour,
	annonceCarte,
	annonceContreCarte,
	estContre,
	faireAnnonceCarte,
	faireAnnonceContreCarte,
	changementCouleur,
	changementStrategie,
	aFini,
	;
}
