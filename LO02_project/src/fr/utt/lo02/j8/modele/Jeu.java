package fr.utt.lo02.j8.modele;
import java.util.Scanner;

import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.vue.console.VueConsole;
import fr.utt.lo02.j8.vue.graphique.Fenetre;
/**
 * <b>Jeu est la classe comportant le main principal du projet</b>
 * <p>
 * Classe de demarrage de l'application
 * </p>
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Jeu{
	
	/**
	 * La methode main a les caracteristiques suivantes :
	 * <ul>
	 * <li>Visibilite : <b>publique</b>, la methode est accessible par d'autres classes</li>
	 * <li>Membre <b>statique</b>, nous pouvons invoquer cette methode sans instancier d'objets</li>
	 * <li>Pas de retour : <b>void</b></li>
	 * </ul>
	 * @param args : cette methode accepte un seul argument sous la forme d'un tableau de chaine de caracteres
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Partie partie;
		
		VueConsole vue = new VueConsole();
		Fenetre fenetre = new Fenetre();
		
	}
}
