package fr.utt.lo02.j8.exceptions;

import fr.utt.lo02.j8.vue.console.VueConsole;

/**
 * <b>CarteNonValideException est une Exception.</b>
 * <p>
 * Cette exception a ete cree pour pouvoir signaler a l'utilisateur que la 
 * carte qu'il souhaite poser est incorrecte.
 * </p>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class CarteNonValideException extends Exception {
	
	/**
	 * Constructeur CarteNonValideException
	 * 
	 * <p>
	 * Affichant sur la console un message d'erreur lui indiquant qu'il est impossible de poser cette carte
	 * </p>
	 */
	public CarteNonValideException() {
		System.out.println("\nImpossible de jouer cette carte");
		System.out.print(VueConsole.PROMPT);	
	}
}
