package fr.utt.lo02.j8.vue.graphique;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.modele.moteur.Carte;
import fr.utt.lo02.j8.modele.moteur.Talon;

/**
 * <b>Plateau est la classe representant un plateau de jeu</b>
 * Un plateau de jeu est caracterise par :
 * <ul>
 * <li>un talon, attribue definitivement</li>
 * <li>un panel representant le talon attribue definitivement</li>
 * <li>un panel reprensentant la pioche attribue definitivement</li>
 * <li>un controleur graphique attribue definitivement</li>
 * </ul>
 * <p>
 * Le plateau observe le talon.
 * </p>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Plateau extends JPanel implements Observer{
	
	/**
	 * Panel reprensentant le talon.
	 * Il n'est pas modifiable.
	 */
	private VueCarte talonPanel;
	
	/**
	 * Panel representant la pioche.
	 * Il n'est pas modifiable.
	 */
	private JPanel piochePanel;
	
	/**
	 * Talon de la partie.
	 * Il n'est pas modifiable.
	 */
	private Talon talon;
	
	/**
	 * Controleur graphique qui fait le lien avec le modele.
	 * Il n'est pas modifiable.
	 */
	private ControleurGraphique controler;
	
	/**
	 * Constructeur Plateau.
	 * 
	 * <p>
	 * Lors de la construction d'un objet Plateau, l'objet est initialise et est ajoute en observateur du talon.
	 * </p>
	 * 
	 * @param talon le talon de la partie
	 * @param controler le controler graphique a utiliser
	 */
	public Plateau(Talon talon, ControleurGraphique controler) {
		this.talon = talon;
		this.talon.addObserver(this);
		this.controler = controler;
		initialize();
	}
	
	/**
	 * Initialise l'ensemble du plateau.
	 */
	private void initialize() {
		this.setSize(new Dimension(500,400));
		this.setOpaque(false);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.gridwidth = 2;
		contraintes.gridheight = 1;
		contraintes.insets = new Insets(0,10,0,10);
		this.setLayout(layout);
		
		talonPanel = new VueCarte(talon.getCarteDessus());
		talonPanel.tournerVersFace();
		this.add(talonPanel, contraintes);
		
		piochePanel = new JPanel();
		piochePanel.setLayout(new GridLayout(1,1));
		piochePanel.setPreferredSize(new Dimension(VueCarte.LONGUEUR_STANDARD, VueCarte.HAUTEUR_STANDARD));
		ImageIcon piocheIcone = new ImageIcon("ressources/images/cartes/Dos.png");
		JLabel piocheLbl = new JLabel();
		piocheLbl.setIcon(piocheIcone);
		piochePanel.add(piocheLbl, contraintes);
		piochePanel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
        	public void mouseReleased(java.awt.event.MouseEvent evt){
				controler.piocher();
        	}
        });
		this.add(piochePanel);
	}
	
	/**
	 * Met a jour l'affichage du plateau en changeant la carte du talon.
	 */
	public void update(Observable obs, Object o) {
		if(o instanceof Carte) {
			this.talonPanel.setCarte(talon.getCarteDessus());
			this.talonPanel.tournerVersFace();
		}else if(o instanceof String) {
			this.talonPanel.setImage(new String("ressources/images/cartes/" + ((String)o) + ".png"));
			this.talonPanel.tournerVersFace();
		}
	}
	
}
