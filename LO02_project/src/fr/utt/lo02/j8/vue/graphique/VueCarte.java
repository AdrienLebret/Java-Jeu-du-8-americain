package fr.utt.lo02.j8.vue.graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.lo02.j8.modele.moteur.Carte;

/**
 * <b>VueCarte est la classe representant l'interface graphique d'une carte.</b>
 * 
 * <p>
 * Une carte est caracterisee par :
 * <ul>
 * <li>une carte qu'elle represente</li>
 * <li>une image de la face de la carte</li>
 * <li>une image de dos de la carte</li>
 * <li>un label affichant l'image de la carte</li>
 * <li>un etat definissant le cote a afficher</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueCarte extends JPanel{
	
	/**
	 * Carte que la VueCarte represente.
	 */
	private Carte carte;
	
	/**
	 * Chemin de l'image de la face de la carte.
	 */
	private String imageFace;
	
	/**
	 * Chemin de l'image du dos de la carte.
	 */
	private String imageDos;
	
	/**
	 * Label pour l'affichage de l'image.
	 */
	private JLabel photoLbl;
	
	/**
	 * Etat de la carte : correspond au cote affiche. Il peut prendre les valeurs 0 (dos) et 1 (face).
	 */
	private int etat;
	
	/**
	 * Longueur standard de la vue carte.
	 */
	public static final int LONGUEUR_STANDARD = 72;
	
	/**
	 * Hauteur standard de la vue carte.
	 */
	public static final int HAUTEUR_STANDARD = 96;
	
	/**
	 * Constructeur VueCarte.
	 * 
	 * <p>
	 * A la construction d'une carte, l'objet est intialise.
	 * </p>
	 * 
	 * @param carte la carte a representer
	 */
	public VueCarte(Carte carte) {
		this.setCarte(carte);
		this.initialize();
	}
	
	/**
	 * Definit la carte correspondante a cette vue et associe les images correspondantes.
	 * 
	 * @param carte la carte que la vue represente
	 */
	public void setCarte(Carte carte) {
		this.carte = carte;
		this.imageDos = new String("ressources/images/cartes/Dos.png");
		this.imageFace = new String("ressources/images/cartes/" + carte.getHauteur() + carte.getCouleur() + ".png");
	}
	
	/**
	 * Definit l'image de la face de la VueCarte.
	 * 
	 * @param chemin chemin de l'image de face
	 */
	public void setImage(String chemin) {
		this.imageFace = chemin;
	}
	
	/**
	 * Affiche l'image.
	 * 
	 * @param image le chemin de l'image a afficher
	 */
	private void afficherImage(String image) {
		ImageIcon icon = this.dimensionnerImage(new ImageIcon(image));
		photoLbl.setIcon(icon);
	}
	
	/**
	 * Retourne la cartes vers la face. Modifie l'affichage en consequence.
	 */
	public void tournerVersFace() {
		this.afficherImage(imageFace);
		this.etat = 1;
	}
	
	/**
	 * Retourne la carte vers le dos. Modifie l'affichage en consequence.
	 */
	public void tournerVersDos() {
		this.afficherImage(imageDos);
		this.etat = 0;
	}
	
	/**
	 * Intialise l'ensemble de la vue carte.
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(LONGUEUR_STANDARD, HAUTEUR_STANDARD));
		this.setSize(new Dimension(LONGUEUR_STANDARD, HAUTEUR_STANDARD));
		this.setLayout(new GridLayout(1,1));
		
		photoLbl = new JLabel();
		photoLbl.setSize(new Dimension(LONGUEUR_STANDARD, HAUTEUR_STANDARD));
		this.add(photoLbl);
	}
	
	/**
	 * Retourne la carte associee a la vue.
	 * 
	 * @return la carte reprensentee par la VueCarte
	 */
	public Carte getCarte() {
		return this.carte;
	}
	
	/**
	 * Definit les nouvelles dimensions de la VueCarte.
	 * 
	 * @param scale l'echelle a laquelle il faut dimensionner la vue
	 */
	public void setDimension(double scale) {
		int longueur = (int) (LONGUEUR_STANDARD * scale);
		int hauteur = (int) (HAUTEUR_STANDARD * scale);
		
		photoLbl.setSize(new Dimension(longueur, hauteur));
		ImageIcon image;
		if(this.etat == 0) {
			image = new ImageIcon(this.imageDos);
		}else {
			image = new ImageIcon(this.imageFace);
		}
		ImageIcon icon = this.dimensionnerImage(image);
		photoLbl.setIcon(icon);
		this.setPreferredSize(new Dimension(longueur, hauteur));
		this.setSize(new Dimension(longueur, hauteur));
	}
	
	/**
	 * Dimensionne une icone selon les dimensions du label affichant l'image.
	 * 
	 * @param img l'icone a dimensionner
	 * @return l'icone dimensionnee
	 */
	private ImageIcon dimensionnerImage(ImageIcon img) {
		ImageIcon dimg = new ImageIcon(img.getImage().getScaledInstance(photoLbl.getWidth(), photoLbl.getHeight(), Image.SCALE_SMOOTH));
		return dimg;
	}
	
	/**
	 * Indique que cette carte n'est pas valide au sens qu'elle n'est pas jouable.
	 * Affiche les bordures de la carte en rouge.
	 */
	public void setInvalide() {
		this.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	}
}
