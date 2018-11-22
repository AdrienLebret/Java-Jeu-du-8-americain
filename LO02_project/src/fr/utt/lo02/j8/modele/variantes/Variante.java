package fr.utt.lo02.j8.modele.variantes;
import fr.utt.lo02.j8.modele.effets.Effet;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilite;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Paquet;
/**
 * <b>Interface Variante</b>
 * <p>
 * Dans le jeu du 8 americain, il existe de tres nombreuses variantes, notamment ce qui concerne le pouvoir donne a chaque carte.
 * </p>
 * Les classes qui implementeront l'interface Variante s'engagent à implementer les methodes suivantes :
 * <ul>
 * <li><b>donnerEffet(Paquet paquet)</b> : qui attribuera un ou plusieurs effets a une carte precise</li>
 * <li><b>toString()</b> : qui retourne le texte descriptif de la variante</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public interface Variante {
	/**
	 * <p>
	 * Donne un ou plusieurs effets, ainsi qu'une jouabilite a une carte precise
	 * </p>
	 * @param paquet : paquet de cartes ou seront appliques les effets
	 * 
	 * @see Effet
	 * @see Jouabilite
	 * @see Paquet
	 * @see Carte
	 */
	public void donnerEffet(Paquet paquet);
	/**
	 * Retourne le texte descriptif de la variante avec la description de chaque effet par carte
	 * 
	 * @return Le texte descriptif de la variante
	 */
	public String toString();
}
