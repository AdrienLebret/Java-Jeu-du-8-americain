package fr.utt.lo02.j8.modele.effets;

/**
 * <b>Interface Effet</b>
 * <p>
 * Dans le jeu du 8 americain, il existe plusieurs variantes qui changent les effets que possedent chaque carte
 * </p>
 * Les classes qui implementeront l'interface Effet s'engagent à implementer la methode suivante :
 * <ul>
 * <li><b>executer()</b> : qui execute l'effet en question lors de la partie</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public interface Effet {
	/**
	 * Execute l'effet decrit dans la classe
	 */
	public void executer();
}
