package fr.utt.lo02.j8.vue.graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.modele.moteur.Joueur;
import fr.utt.lo02.j8.modele.moteur.Notifications;
import fr.utt.lo02.j8.modele.moteur.Partie;

/**
 * <b>VuePseudo est la classe representant la fenetre des scores.</b>
 * <p>
 * Elle herite de JPanel et implemente Observer.
 * </p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>Le JPanel pan</li>
 * <li>Le JButton boutonValider</li>
 * <li>Le JTextArea nom pour permettre la saisie du pseudo par l'utilisateur</li>
 * <li>Les JLabel labelSaisitPseudo et n ("Nom :")</li>
 * </ul>
 * 
 *
 * @see JPanel
 * @see JButton
 * @see JLabel
 * @see JTextArea
 * @see Partie
 * @see Joueur
 * @see Observer
 * @see ControleurGraphique
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */ 
public class VuePseudo extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Panel Principal
	 */
	private JPanel pan = new JPanel();
	
	/**
	 * Permet la sasie du pseudo de l'utilisateur
	 */
	private JTextArea nom = new JTextArea();
	
	/**
	 * Bouton permettant a l'utilisateur de valider sa saisie
	 */
	private JButton boutonValider = new JButton("VALIDER");
	
	/**
	 * Label affichant a l'utilisateur qu'il doit saisir son pseudo pour la partie
	 */
	private JLabel labelSaisitPseudo = new JLabel("Saisir votre pseudo");
	
	/**
	 * Label pour decrire l'endroit ou l'utilisateur doit saisir son pseudo
	 */
	private JLabel n = new JLabel("Nom :");
	
	/**
	 * Attribut joueur
	 * 
	 * @see Joueur
	 */
	private Joueur joueur;
	
	/**
	 * Represente la partie actuelle
	 */
	private Partie partie = Partie.getInstance();
	
	/**
	 * Instanciation du Controleur Graphique
	 */
	private ControleurGraphique cg = new ControleurGraphique();
	
	/**
	 * Contructeur VuePseudo.
	 * 
	 * <p>
	 * L'attribut joueur correspond a l'utilisateur
	 * La vue pseudo est egalement ajoutee aux observateurs du joueurs.
	 * Elle lance egalement l'initialisation de la fenetre.
	 * </p>
	 * 
	 * @see Joueur
	 * @see Observer
	 */
	public VuePseudo() {
		this.joueur = Partie.getInstance().getJoueur(0);
		this.joueur.addObserver(this);
		initialize();
	}
	
	/**
	 * Initialise l'ensemble de la vue de la saisit du pseudo
	 */
	private void initialize() {
		
		this.setLayout(new BorderLayout());
		this.add(pan, BorderLayout.CENTER);
		
		//Nom du joueur
		nom.setText("Pseudo");
		nom.setEditable(true);
		
		// MouseListener qui verifie que les champs sont remplis avant de cacher la fenetre
		boutonValider.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (!nom.getText().equals("Pseudo"))
					cg.setNom(joueur, nom.getText());
				else
					boutonValider.setText("SAISIR VOTRE PSEUDO");
			
			}
		});
		
		//Disposition des elements
		JPanel center = new JPanel();
		center.setOpaque(false);
		
		Font police = new Font("Tahoma", Font.BOLD, 25);
		n.setFont(police);
		n.setForeground(Color.white);
		n.setHorizontalAlignment(JLabel.CENTER);
		nom.setFont(police);
		nom.setForeground(Color.BLACK);
		center.add(n);
		center.add(nom);
		this.add(center, BorderLayout.CENTER);
				
		Font police2 = new Font("Tahoma", Font.BOLD, 45);
		labelSaisitPseudo.setFont(police2);
		labelSaisitPseudo.setForeground(Color.black);
		labelSaisitPseudo.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(boutonValider, BorderLayout.SOUTH);
		this.add(labelSaisitPseudo, BorderLayout.NORTH);
			
	}

	/**
	 * Retourne le joueur correspondant a l'utilisateur
	 * 
	 * @return Le joueur correspondant a l'utilisateur
	 */
	public Joueur getJoueur() {
		return joueur;
	}
	
	/**
	 * Retourne le nom de l'utilisateur
	 * 
	 * @return Le nom de l'utilisateur
	 */
	public String getNom() {
		return nom.getText();
	}

	/**
	 * Met a jour l'affichage de la fenetre
	 *
	 * <p>
	 * Ici : Change la saisit de l'utilisateur
	 * </p>
	 */
	public void update(Observable obs, Object o) {
		if (o instanceof Notifications) {
			switch((Notifications)o) {
			case changementNom:
				nom.setText(this.joueur.getNom());
				break;
			}
		}	
	}
	
	/**
	 * Affichage de l'image de fond disponible dans ressource
	 */
	public void paintComponent(Graphics g) {
		try {
			Image img = ImageIO.read(new File("ressources/images/fonds/Menu.jpg"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
