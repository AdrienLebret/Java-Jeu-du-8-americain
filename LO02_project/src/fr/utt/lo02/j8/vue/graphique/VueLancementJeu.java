package fr.utt.lo02.j8.vue.graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.lo02.j8.controleur.ControleurGraphique;

/**
 * <b>VueLancementJeu est la classe representant la fenetre de lancement.</b>
 * <p>
 * Elle herite de JDialog.
 * </p>
 * <p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>Les JPanel pan, container et tabScorePanels</li>
 * <li>Les JButton boutonOui et boutonNon</li>
 * <li>Les JLabel icon qui montre l'image de fond</li>
 * </ul>
 * 
 * @see JDialog
 * @see JPanel
 * @see JButton
 * @see JLabel
 * @see ControleurGraphique
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */ 
public class VueLancementJeu extends JDialog{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Panel
	 */
	private JPanel pan = new JPanel();
	
	/**
	 * Panel Principal
	 */
	private JPanel container = new JPanel();
	
	/**
	 * Bouton qui permettra au joueur de lancer une partie
	 */
	private JButton boutonOui = new JButton("LANCER LE JEU");
	
	/**
	 * Bouton qui permettra au joueur de quitter, ce qui fermera le jeu.
	 */
	private JButton boutonNon = new JButton("QUITTER");

	/**
	 * Label permettant l'affichage de l'image de demarrage disponible dans le dossier ressource
	 */
	private JLabel icon;

	/**
	 * Constructeur VueLancementJeu : Transmission de la fenetre JFrame
	 * <p>
	 * Lance l'initialisation de la fenetre
	 * </p>
	 * @param parent : Transmission de la fenetre JFrame
	 * @param title : Nom de la fenetre
	 * @param modal : Boolean pour l'affichage
	 * 
	 * @see VueLancementJeu#initialize()
	 */
	public VueLancementJeu(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialise l'ensemble de la fenetre JDialog
	 */
	private void initialize() {
		
		this.setSize(1024, 720);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		container.add(pan, BorderLayout.CENTER);
		
		// Ajouts des actions sur les boutons
		boutonOui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		boutonNon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		//Disposition des boutons
		JPanel south = new JPanel();
		south.setOpaque(false);
		south.add(boutonOui);
		south.add(boutonNon);
		container.add(south, BorderLayout.SOUTH);
		
		this.setContentPane(container);
	    
	    icon = new JLabel(new ImageIcon("ressources/images/fonds/Demarrage Jeu.jpg"));
	    JPanel panIcon = new JPanel();
	    panIcon.setBackground(Color.white);
	    panIcon.setLayout(new BorderLayout());
	    panIcon.add(icon);
		this.getContentPane().add(panIcon, BorderLayout.CENTER);
	}
}
