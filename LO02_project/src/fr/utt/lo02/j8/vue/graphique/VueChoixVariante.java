package fr.utt.lo02.j8.vue.graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import fr.utt.lo02.j8.controleur.ControleurGraphique;
import fr.utt.lo02.j8.modele.moteur.Notifications;
import fr.utt.lo02.j8.modele.moteur.Partie;
import fr.utt.lo02.j8.modele.variantes.Variante;
import fr.utt.lo02.j8.modele.variantes.Variante5;
import fr.utt.lo02.j8.modele.variantes.Variante6;
import fr.utt.lo02.j8.modele.variantes.Variante7;
import fr.utt.lo02.j8.modele.variantes.VarianteCourteAmicale;
import fr.utt.lo02.j8.modele.variantes.VarianteMinimale;
import fr.utt.lo02.j8.modele.variantes.VarianteMonclar;

/**
 * <b>VueChoixVariante est la classe representant la fenetre des choix de la variante de la partie en cours.</b>
 * <p>
 * Elle herite de JDialog et implemente Observer.
 * Elle est appelee lorsque l'utilisateur clique sur le bouton "Variante" disponible sur le plateau. Il peut le faire a tout moment de la partie
 * Les choix proposes changeront en fonction de la taille initiale du paquet.
 * </p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>les JRadioButton pour selectionner la variante</li>
 * <li>Un JButton boutonValider pour valider son choix de variante</li>
 * <li>L'ensemble des variantes de la partie</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueChoixVariante extends JDialog implements Observer{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Controleur graphique qui fait le lien le modele
	 * Il n'est pas modifiable
	 */
	private ControleurGraphique cg = new ControleurGraphique();
	
	/**
	 * Attribut decrivant la partie.
	 */
	private Partie partie;
	
	/**
	 * Panel
	 */
	private JPanel pan = new JPanel();
	
	/**
	 * Panel principal
	 */
	private JPanel container = new JPanel();
	
	/**
	 * Bouton pour valider le choix finale de la variante
	 */
	private JButton boutonValider = new JButton("VALIDER");
	
	// Taille 32
	
	/**
	 * Panel regroupant les differents boutons de selections
	 */
	private JPanel boutonRadio32 = new JPanel();
	
	/**
	 * Groupe de boutons permettant d'avoir un seul bouton selectionne
	 */
	private ButtonGroup bouton32 = new ButtonGroup();
	
	/**
	 * Radio Bouton correspondant a la Variante Courte Amicale
	 */
	private JRadioButton jr1 = new JRadioButton("VarianteCourteAmicale");
	
	/**
	 * Radio Bouton correspondant a la Variante 6
	 */
	private JRadioButton jr2 = new JRadioButton("Variante6");
	
	/**
	 * Radio Bouton correspondant a la Variante 7
	 */
	private JRadioButton jr3 = new JRadioButton("Variante7");
	
	// Taille 52
	/**
	 * Panel regroupant les differents boutons de selections
	 */
	private JPanel boutonRadio52 = new JPanel();
	
	/**
	 * Groupe de boutons permettant d'avoir un seul bouton selectionne
	 */
	private ButtonGroup bouton52 = new ButtonGroup(); // Permet d'avoir un seul bouton selectionne
	
	/**
	 * Radio Bouton correspondant a la Variante Minimale
	 */
	private JRadioButton jr4 = new JRadioButton("VarianteMinimale");
	
	/**
	 * Radio Bouton correspondant a la Variante 5
	 */
	private JRadioButton jr5 = new JRadioButton("Variante5");
	
	/**
	 * Radio Bouton correspondant a la Variante Monclar
	 */
	private JRadioButton jr6 = new JRadioButton("VarianteMonclar");
	
	
	// Description Variante
	/**
	 * Label affichant la description de la variante selectionne
	 */
	private JLabel descriptionV = new JLabel();
	
	/**
	 * Creation de la Variante 5 pour permettre l'affichage de sa description
	 * 
	 */
	private Variante5 v5 = new Variante5();
	
	/**
	 * Creation de la Variante 6 pour permettre l'affichage de sa description
	 * 
	 */
	private Variante6 v6 = new Variante6();
	
	/**
	 * Creation de la Variante 7 pour permettre l'affichage de sa description
	 * 
	 */
	private Variante7 v7 = new Variante7();
	
	/**
	 * Creation de la Variante Courte Amicale pour permettre l'affichage de sa description
	 * 
	 */
	private VarianteCourteAmicale vCA = new VarianteCourteAmicale();
	
	/**
	 * Creation de la Variante Minimale pour permettre l'affichage de sa description
	 * 
	 */
	private VarianteMinimale vMi = new VarianteMinimale();
	
	/**
	 * Creation de la Variante Monclar pour permettre l'affichage de sa description
	 * 
	 */
	private VarianteMonclar vMo = new VarianteMonclar();
	
	/**
	 * Variante choisie par l'utilisateur
	 * 
	 */
	private Variante varianteChoisie;
	
	/**
	 * Constructeur VueChoixVariante
	 * 
	 * <p>
	 * Lors de la consctruction de l'objet, celui ajoute un observer sur la partie en cours
	 * </p>
	 * 
	 * @param parent = null
	 * @param title : tritre de la fenetre
	 * @param modal = true
	 * @param partie en cours
	 */
	public VueChoixVariante(JFrame parent, String title, boolean modal, Partie partie) { 
		super(parent, title, modal);
		this.partie = partie;
		this.partie.addObserver(this);
		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialise l'ensemble de la vue de choix de variante
	 */
	private void initialize() {
		this.setSize(450, 280);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		container.add(pan, BorderLayout.CENTER);
		/**
		 * Gestion du bouton VALIDER
		 */
		boutonValider.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				cg.setVariante(varianteChoisie);
				dispose();
			}
		});
		/*
		 * Texte de description des variantes
		 */
		Font police = new Font("Tahoma", Font.BOLD, 12);
		descriptionV.setFont(police);
		descriptionV.setForeground(Color.BLACK);
		descriptionV.setHorizontalAlignment(JLabel.CENTER);
	
		
		
		/*
		 * Gestion des radios boutons / Affichage de la desription des variantes
		 */
		jr1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		varianteChoisie = vCA;
	    		descriptionV.setText(mettreTextePlusLignes(vCA.toString()));
	    	}
	    });
		jr2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		varianteChoisie = v6;
	    		descriptionV.setText(mettreTextePlusLignes(v6.toString()));
	    	}
	    });
		jr3.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		varianteChoisie = v7;
	    		descriptionV.setText(mettreTextePlusLignes(v7.toString()));
	    	}
	    });
		jr4.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		varianteChoisie = vMi;
	    		descriptionV.setText(mettreTextePlusLignes(vMi.toString()));
	    	}
	    });
		jr5.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		varianteChoisie = v5;
	    		descriptionV.setText(mettreTextePlusLignes(v5.toString()));
	    	}
	    });
		jr6.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		varianteChoisie = vMo;
	    		descriptionV.setText(mettreTextePlusLignes(vMo.toString()));
	    	}
	    });
		

		boutonRadio32.add(jr1);
		boutonRadio32.add(jr2);
		boutonRadio32.add(jr3);
		
		bouton32.add(jr1);
		bouton32.add(jr2);
		bouton32.add(jr3);
		
		boutonRadio52.add(jr4);
		boutonRadio52.add(jr5);
		boutonRadio52.add(jr6);
		
		bouton52.add(jr4);
		bouton52.add(jr5);
		bouton52.add(jr6);
		
		
		/*
	     * Ajout des composants dans la fenetre
	     */
		
		this.updateBoxVariante(this.partie.getTailleInitPaquet(), this.partie.getVariante());
		container.add(descriptionV, BorderLayout.CENTER);
		container.add(boutonValider, BorderLayout.SOUTH);
		this.setContentPane(container);
	}


	@Override
	/**
	 * Met a jour l'affichage de la fenetre
	 * <p>
	 * Lorsque l'utilisateur a fait son choix de variante, le JRadioBouton affectue a son choix est coche.
	 * </p>
	 */
	public void update(Observable obs, Object o) {
		if (o instanceof Notifications) {
			switch((Notifications)o) {
			case changementVariante:
				if(this.partie.getTailleInitPaquet() == 32) {
					container.add(boutonRadio32, BorderLayout.NORTH); //change le contenu des boutons radio "variante"
					container.validate();
				} else if ((this.partie.getTailleInitPaquet() == 52)) {
					container.add(boutonRadio52, BorderLayout.NORTH); //change le contenu des boutons radio "variante"
					container.validate();
				}
				this.updateBoxVariante(this.partie.getTailleInitPaquet(), this.partie.getVariante());
				break;
			}
		}
	}
	
	/**
	 * Methode selectionnant automatiquement la variante actuelle lors de l'appel du changement de variante
	 * varianteChoisie correspondant a la future variante que choisit l'utilisateur
	 * @param tailleInitPaquet de la partie en cours
	 * @param varianteActuelle selectionne auparavant
	 */
	private void updateBoxVariante(int tailleInitPaquet, Variante varianteActuelle) {
		
		// la boite de dialogue affichee doit correspondre aux variantes associees a la taille initiale du paquet
		// elle affiche ainsi la variante actuelle
		
		this.varianteChoisie = varianteActuelle;
		
		if(tailleInitPaquet == 32) {
			container.add(boutonRadio32, BorderLayout.NORTH);
			if(varianteActuelle instanceof VarianteCourteAmicale) {
				jr1.setSelected(true);
				descriptionV.setText( mettreTextePlusLignes(vCA.toString())); // Mise a jour du texte
			} else if (varianteActuelle instanceof Variante6) {
				jr2.setSelected(true);
				descriptionV.setText( mettreTextePlusLignes(v6.toString()));
			} else if (varianteActuelle instanceof Variante7) {
				jr3.setSelected(true);
				descriptionV.setText( mettreTextePlusLignes(v7.toString()));
			}
		} else if (tailleInitPaquet == 52) {
			container.add(boutonRadio52, BorderLayout.NORTH);
			if(varianteActuelle instanceof VarianteMinimale) {
				jr4.setSelected(true);
				descriptionV.setText( mettreTextePlusLignes(vMi.toString()));
			} else if (varianteActuelle instanceof Variante5) {
				jr5.setSelected(true);
				descriptionV.setText( mettreTextePlusLignes(v5.toString()));
			} else if (varianteActuelle instanceof VarianteMonclar) {
				jr6.setSelected(true);
				descriptionV.setText( mettreTextePlusLignes(vMo.toString()));
			}
		}
		
		container.validate();
	}

	/**
	 * Permet un affichage plus claire a l'aide des balises HTML
	 * 
	 * @param texte : description de la variante
	 * @return le texte a afficher
	 */
	private String mettreTextePlusLignes(String texte) {
		texte = "<html>" + texte.replaceAll("#", "").replaceAll("- ", "").replaceAll("&", "&amp;" ).replaceAll("<", "&lt;" ).replaceAll(">", "&gt;" ).replaceAll("\n", "<br />" ) + "</html>" ;
		return texte;
	}
	
}