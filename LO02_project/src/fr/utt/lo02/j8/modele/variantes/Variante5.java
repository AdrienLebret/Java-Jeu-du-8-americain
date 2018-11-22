package fr.utt.lo02.j8.modele.variantes;
import fr.utt.lo02.j8.modele.effets.ChangerCouleurStopAttaques;
import fr.utt.lo02.j8.modele.effets.ChangerSens;
import fr.utt.lo02.j8.modele.effets.Effet;
import fr.utt.lo02.j8.modele.effets.FairePiocherContre;
import fr.utt.lo02.j8.modele.effets.Rejouer;
import fr.utt.lo02.j8.modele.jouabilite.Jouabilite;
import fr.utt.lo02.j8.modele.jouabilite.JouableSurContre;
import fr.utt.lo02.j8.modele.jouabilite.JouableSurToutEtContre;
import fr.utt.lo02.j8.modele.jouabilite.Standard;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Paquet;
/**
 * <b>Cette classe represente la Variante 5, qui implemente l'interface Variante</b>
 * <p>
 * Les effets de la variante minimale sont issus du site suivante :
 *<b> https://fr.wikipedia.org/wiki/8_am%C3%A9ricain#Variante_5</b>
 * </p>
 * @see Variante
 * @see Effet
 * @see Carte
 * @see Paquet
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Variante5 implements Variante{
	/**
	 * <p>
	 * Donne un ou plusieurs effets, ainsi qu'une jouabilite a une carte precise
	 * </p>
	 *  <p>
	 * Ici : les cartes 7, 8, 10 et As
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
			case "7":
	        	paquet.getCarte(i).setEffet(new ChangerSens());
	        	paquet.getCarte(i).setJouabilite(new Standard());
	        	break;
			case "8":
				paquet.getCarte(i).setEffet(new ChangerCouleurStopAttaques());
				paquet.getCarte(i).setJouabilite(new JouableSurToutEtContre());
	        	break;
	        case "10":
	        	paquet.getCarte(i).setEffet(new Rejouer());
	        	paquet.getCarte(i).setJouabilite(new Standard());
	        	break;
	        case "As":
	        	paquet.getCarte(i).setEffet(new FairePiocherContre(2));
	        	paquet.getCarte(i).setJouabilite(new JouableSurContre());
	        	break;
			default :
				paquet.getCarte(i).setEffet(null);
				paquet.getCarte(i).setJouabilite(new Standard());
			}
		}
	}
	/**
	 * Retourne le texte descriptif de la variante avec la description de chaque effet par carte
	 * 
	 * @return Le texte descriptif de la variante
	 */
	public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("\n####################################################\n");
        sb.append(" DESCRIPTION VARIANTE 5\n");
        sb.append("- 7 : Permet de changer le sens de la Partie\n");
        sb.append("- 8 : Changer la couleur du Talon\n");
        sb.append("      Permet de stopper les attaques\n");
        sb.append("      Posable sur toutes les cartes\n");
        sb.append("- 10 : Permet de rejouer\n");
        sb.append("- As : Faire piocher 2 cartes au joueur suivant	\n");
        sb.append("       Contrable\n");
        sb.append("####################################################\n");
       return sb.toString();
    }
}
