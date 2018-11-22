package fr.utt.lo02.j8.modele.strategies;
import fr.utt.lo02.j8.modele.moteur.Joueur;

/**
 * <b>Strategie est l'interface representant une strategie qu'un joueur peut utiliser</b>
 * <p>
 * Le joueur a la choix de plusieurs strategies qui affecteront sa maniere de jouer.
 * </p>
 * Les classes qui implementeront l'interface Straegie s'engagent à implementer les methodes suivantes :
 * <ul>
 * <li><b>jouer(Joueur)</b> : determine les actions que le joueur effectue</li>
 * <li><b>choisirStrategie(Joueur)</b> : change la strategie du joueur</li>
 * <li><b>choisirCouleur(Joueur)</b> : choisi une couleur</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public interface Strategie {
	
	/**
	 * Determine les actions que le joueur effectue et les met en pratique.
	 * 
	 * @param joueur le joueur qui joue
	 */
	public void jouer(Joueur joueur);
	
	/**
	 * Change la strategie du joueur.
	 * 
	 * @param joueur le joueur changeant de strategie
	 */
	public void choisirStrategie(Joueur joueur);
	
	/**
	 * Selectionne une couleur parmi coeur, carreau, trfle et pique.
	 * 
	 * @param joueur le joueur changeant la couleur
	 */
	public void choisirCouleur(Joueur joueur);
}
