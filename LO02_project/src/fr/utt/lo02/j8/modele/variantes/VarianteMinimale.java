package fr.utt.lo02.j8.modele.variantes;
import fr.utt.lo02.j8.modele.effets.ChangerCouleur;
import fr.utt.lo02.j8.modele.effets.Effet;
import fr.utt.lo02.j8.modele.effets.Rejouer;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilite;
import fr.utt.lo02.j8.modele.jouabilite.JouableSurTout;
import fr.utt.lo02.j8.modele.jouabilite.Standard;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Paquet;
/**
 * <b>Cette classe represente la Variante Minimale, qui implemente l'interface Variante</b>
 * <p>
 * Les effets de la variante minimale sont issus du site suivante :
 *<b> https://fr.wikipedia.org/wiki/8_am%C3%A9ricain#Version_%C2%AB_minimale_%C2%BB </b>
 * </p>
 * @see Variante
 * @see Effet
 * @see Carte
 * @see Paquet
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VarianteMinimale implements Variante{
	/**
	 * <p>
	 * Donne un ou plusieurs effets, ainsi qu'une jouabilite a une carte precise
	 * </p>
	 *  <p>
	 * Ici : les cartes 8 et 10
	 * </p>
	 * @param paquet : paquet de cartes ou seront appliques les effets
	 * 
	 * @see Effet
	 * @see Jouabilite
	 * @see Paquet
	 * @see Carte
	 */
	public void donnerEffet(Paquet paquet) {
		for(int i=0; i<paquet.getTaille(); i++) {
			switch (paquet.getCarte(i).getHauteur()) {
			case "8":
				paquet.getCarte(i).setEffet(new ChangerCouleur());
				paquet.getCarte(i).setJouabilite(new JouableSurTout());
	        	break;
	        case "10":
	        	paquet.getCarte(i).setEffet(new Rejouer());
	        	paquet.getCarte(i).setJouabilite(new Standard());
	        	break;
	        default :
	        	paquet.getCarte(i).setEffet(null);
	        	paquet.getCarte(i).setJouabilite(new Standard());
	        }
		}
	}
	/**
	 *Retourne le texte descriptif de la variante avec la description de chaque effet par carte
	 * 
	 * @return Le texte descriptif de la variante
	 */
	public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("\n#########################################\n");
        sb.append(" DESCRIPTION VARIANTE MINIMALE\n");
        sb.append("- 8 : Changer la couleur du Talon\n");
        sb.append("      Posable sur toutes les cartes\n");
        sb.append("- 10 : Permet de rejouer\n");
        sb.append("#########################################\n");
       return sb.toString();
    }
}