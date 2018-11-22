package fr.utt.lo02.j8.exceptions;

import fr.utt.lo02.j8.vue.console.VueConsole;

/**
 * <b>SaisieNonValideException est une Exception.</b>
 * <p>
 * Cette exception a ete cree pour pouvoir signaler que la saisie de l'utilisateur est incorrecte
 * </p>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class SaisieNonValideException extends Exception{
	
	/**
	 * Constructeur SaisieNonValideException par defaut
	 * 
	 * <p>
	 * Affichant sur la console un message d'erreur
	 * </p>
	 * 
	 * @see VueConsole
	 */
	public SaisieNonValideException() {
		System.out.println("Saisie incorrecte");
		System.out.print(VueConsole.PROMPT);
	}
	
	/**
	 * Constructeur SaisieNonValideException
	 * 
	 *  <p>
	 * Affichant sur la console un message d'erreur avec la saisie de l'utilisateur
	 * </p>
	 * @param message : saisie de l'utilisateur
	 */
	public SaisieNonValideException(String message) {
		System.out.println("Saisie incorrecte : " + message);
		System.out.print(VueConsole.PROMPT);
	}
}
