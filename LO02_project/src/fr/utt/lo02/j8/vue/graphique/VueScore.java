package fr.utt.lo02.j8.vue.graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Partie;

/**
 * <b>VueScore est la classe representant la fenetre des scores.</b>
 * <p>
 * Elle herite de JDialog.
 * </p>
 * <p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>Les JPanel pan, container et tabScorePanels</li>
 * <li>Les JButton boutonRecommencer et boutonQuitter</li>
 * <li>Les JLabel labelTitre et labelScores</li>
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
public class VueScore extends JDialog{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Sous panel
	 */
	private JPanel pan = new JPanel();
	
	/**
	 * Panel principal
	 */
	private JPanel container = new JPanel();
	
	/**
	 * Panel recessant le tableau des scores
	 */
	private JPanel tabScorePanel = new JPanel();
	
	/**
	 * Bouton qui permettra a l'utilisateur de recommencer une partie.
	 */
	private JButton boutonRecommencer = new JButton("RECOMMENCER UNE PARTIE");
	
	/**
	 * Bouton qui permettra a l'utilisateur de quitter une partie.
	 */
	private JButton boutonQuitter = new JButton("QUITTER");
	
	/**
	 * Affichage du texte indiquant la fin d'une partie
	 */
	private JLabel labelTitre = new JLabel("FIN DE LA PARTIE");
	/**
	 * Label indiquant les differents scores de la partie
	 */
	private JLabel labelScores;
	
	/**
	 * Instanciation du Controleur Graphique
	 */
	private ControleurGraphique cg = new ControleurGraphique();
	
	/**
	 * Attribut correspondant a une partie.
	 * 
	 * @see Partie
	 */
	private Partie partie;

	
	/**
	 * Controleur VueScore
	 * 
	 * @param parent : Transmission de la fenetre JFrame
	 * @param title : Nom de la fenetre
	 * @param modal : Boolean pour l'affichage
	 * @param partie : Partie actuelle
	 */
	public VueScore(JFrame parent, String title, boolean modal, Partie partie){
		super(parent, title, modal);
		this.partie = partie;
		initialize();
		this.setVisible(true);
	}
	
	/**
	 * Initialise l'ensemble de la fenetre JDialog
	 */
	private void initialize() {
		this.setSize(450, 280);
		
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		container.add(pan, BorderLayout.CENTER);
		
		// Ajouts des actions sur les boutons
		boutonRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cg.creerPartie();
				cg.demarrerPartie();
				dispose();
			}
		});
		
		boutonQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		// Creation de la vue des scores
		tabScorePanel.setLayout(new GridLayout(6, 1, 10, 10));
		
		Font police2 = new Font("Tahoma", Font.BOLD, 14);
		this.setContentPane(container);
		ArrayList<Joueur> classement = partie.getClassement();
		Iterator<Joueur> it = classement.iterator();
		
		while(it.hasNext()) {
			Joueur joueur = it.next();
			this.labelScores = new JLabel("<html>" + joueur.getNom() + " : " + joueur.getScore()  + "</html>");
			this.labelScores.setFont(police2);
			if(partie.getJoueur(0).getNom() == joueur.getNom()) {
				labelScores.setForeground(Color.red);
			} else {
				labelScores.setForeground(Color.black);
			}
			labelScores.setHorizontalAlignment(JLabel.CENTER);
			container.add(labelScores, BorderLayout.CENTER);
			this.tabScorePanel.add(labelScores);
		}
		
		//Disposition des elements
		JPanel south = new JPanel();
		south.add(boutonRecommencer);
		south.add(boutonQuitter);
		container.add(south, BorderLayout.SOUTH);
		
		Font police = new Font("Tahoma", Font.BOLD, 20);
		labelTitre.setFont(police);
		labelTitre.setForeground(Color.black);
		labelTitre.setHorizontalAlignment(JLabel.CENTER);
		container.add(labelTitre, BorderLayout.NORTH);
		container.add(tabScorePanel, BorderLayout.CENTER);
	}
	

}
