package fr.utt.lo02.j8.vue.graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
 * <b>VueParametres est la classe representant la fenetre des choix de parametres de la partie en cours.</b>
 * <p>
 * Elle herite de JPanel et implemente Observer.
 * </p>
 * Cette fenetre est caracterisee par :
 * <ul>
 * <li>Un slider pour le choixdu nombre de joueur entre 2 et 6</li>
 * <li>Un combo box pour selectionner la variante</li>
 * <li>Un combo box pour selectionner la taille du paquet</li>
 * <li>Un JButton boutonValider pour valider son choix de variante</li>
 * <li>L'ensemble des variantes de la partie</li>
 * </ul>
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class VueParametres extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Label decrivant le nombre de joueurs, qui changera en fonction de l'ajout ou suppression de joueur
	 */
	private JLabel labelNbJ = new JLabel("Nombre de joueurs : 2");
	
	/**
	 * Slider pour choisir les parametres
	 */
	private JSlider slide = new JSlider();
	
	/**
	 * Label indiquant la taille du paquet de la future partie
	 */
	private JLabel labelTaille = new JLabel("Taille d'un paquet :");
	
	/**
	 * ComboBox qui propose 2 choix de taille pour le paquet : 32 ou 52
	 */
	private JComboBox boxChoixTaille = new JComboBox();
	
	/**
	 * Label indiquant le choix de la variante
	 */
	private JLabel labelVariante = new JLabel("Variante :");
	
	/**
	 * ComboBox qui propose un choix de variante pour la partie si la taille de paquet choisie est 32
	 */
	private JComboBox boxChoixVariante32 = new JComboBox();
	
	/**
	 * ComboBox qui propose un choix de variante pour la partie si la taille de paquet choisie est 52
	 */
	private JComboBox boxChoixVariante52 = new JComboBox();

	
	/**
	 * Controleur graphique faisant le lien avec le modele
	 */
	private ControleurGraphique cg = new ControleurGraphique();
	
	/**
	 * Attribut decrivant la partie en cours
	 */
	private Partie partie;
	
	/**
	 * Constructeur VueParametres
	 * <p>
	 * Lors de la consctruction de l'objet, celui ajoute un observer sur la partie en cours
	 * </p>
	 * 
	 */
	public VueParametres() {
		this.partie = Partie.getInstance();
		this.partie.addObserver(this);
		initialize();
	}

	/**
	 * Initialise l'ensemble de la vue des Parametres
	 */
	private void initialize() {
		this.setBackground(Color.white);
	    this.setOpaque(false);
	    this.setLayout(new GridLayout(3, 2, 40, 40));
	    
	    //Police d'ecriture
	    Font police = new Font("Tahoma", Font.BOLD, 20);
	    
	    labelNbJ.setFont(police);
	    labelNbJ.setForeground(Color.white);
	    labelTaille.setFont(police);
	    labelTaille.setForeground(Color.white);
	    labelVariante.setFont(police);
	    labelVariante.setForeground(Color.white);
		
	    /*
	    * Gestion du slide
	    */
	    slide.setMaximum(6);
	    slide.setMinimum(2);
	    slide.setValue(2);
	    slide.setPaintTicks(true);
	    slide.setPaintLabels(true);
	    slide.setMinorTickSpacing(1);
	    slide.setMajorTickSpacing(1);
	    slide.setFont(police);
	    slide.addChangeListener(new ChangeListener(){
	      public void stateChanged(ChangeEvent event){
	    	 if(slide.getValueIsAdjusting())
	    	  cg.setNombreJouers(slide.getValue());
	      }
	    });
	    labelNbJ.setHorizontalAlignment(JLabel.RIGHT);
	    
	    /*
	     * Gestion du combo box - Tailles Paquet
	     */
	    boxChoixTaille.setPreferredSize(new Dimension(100, 20));
	    boxChoixTaille.addItem(52);
	    boxChoixTaille.addItem(32);
	    boxChoixTaille.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		cg.setTaillePaquet(String.valueOf(boxChoixTaille.getSelectedItem()));
	    	}
	    });
	    boxChoixTaille.setFont(police);
	    labelTaille.setHorizontalAlignment(JLabel.RIGHT);
	    
	    /*
	     * Gestion du combo box - Choix Variante - 32 Cartes
	     */
	    boxChoixVariante32.setPreferredSize(new Dimension(100, 20));
	    boxChoixVariante32.addItem("VarianteCourteAmicale");
	    boxChoixVariante32.addItem("Variante6");
	    boxChoixVariante32.addItem("Variante7");
	    boxChoixVariante32.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		cg.setVariante(String.valueOf(boxChoixVariante32.getSelectedItem()));
	    	}
	    });
	    boxChoixVariante32.setFont(police);
	    
	    /*
	     * Gestion du combo box - Choix Variante - 52 Cartes
	     */
	    boxChoixVariante52.setPreferredSize(new Dimension(100, 20));
	    boxChoixVariante52.addItem("VarianteMinimale");
	    boxChoixVariante52.addItem("Variante5");
	    boxChoixVariante52.addItem("VarianteMonclar");
	    boxChoixVariante52.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		cg.setVariante(String.valueOf(boxChoixVariante52.getSelectedItem()));
	    	}
	    });
	    boxChoixVariante52.setFont(police);
	    labelVariante.setHorizontalAlignment(JLabel.RIGHT);
	    
	    /*
	     * Ajout des composants dans la fenetre
	     */
	    
	    this.add(labelNbJ);
	    this.add(slide);
	    this.add(labelTaille);
	    this.add(boxChoixTaille);
	    this.add(labelVariante);
	    this.add(boxChoixVariante52);
	}
	
	@Override
	/**
	 * Met a jour l'affichage en fonction des differentes actions effectuees par l'utilisateur dans ses choix de parametres
	 */
	public void update(Observable obs, Object o) {
		if (o instanceof Notifications) {
			switch((Notifications)o) {
			case newJoueur:
				slide.setValue(this.partie.getNombreJoueur());//Changer la position du slider
				labelNbJ.setText("Nombre de joueurs : " + slide.getValue()); //Changer le texte du label
				break;
			case removeJoueur:
				slide.setValue(this.partie.getNombreJoueur());//Changer la position du slider
				labelNbJ.setText("Nombre de joueurs : " + slide.getValue()); //Changer le texte du label
				break;
			case taillePaquet:
				if(this.partie.getTailleInitPaquet() == 32) {
					boxChoixTaille.setSelectedItem(32);//Changer la box
					this.remove(boxChoixVariante52);//changer �galement le contenu de la box "variante"
					this.add(boxChoixVariante32);
					this.validate();
				} else if ((this.partie.getTailleInitPaquet() == 52)) {
					boxChoixTaille.setSelectedItem(52);//Changer la box
					this.remove(boxChoixVariante32);//changer egalement le contenu de la box "variante"
					this.add(boxChoixVariante52);
					this.validate();
				}
				break;
			case changementVariante:
				this.updateBoxVariante(this.partie.getTailleInitPaquet(), this.partie.getVariante());
				break;
			}
		}
	}

	/**
	 * Methode selectionnant automatiquement la variante actuelle lors de l'appel du changement de variante
	 * varianteChoisie correspondant a la future variante que choisit l'utilisateur
	 * @param tailleInitPaquet de la partie en cours
	 * @param varianteChoisie selectionne auparavant
	 */
	private void updateBoxVariante(int tailleInitPaquet, Variante varianteChoisie) {
		
		if(tailleInitPaquet == 32) {
			if(varianteChoisie instanceof VarianteCourteAmicale) {
				boxChoixVariante32.setSelectedItem("VarianteCourteAmicale");
			} else if (varianteChoisie instanceof Variante6) {
				boxChoixVariante32.setSelectedItem("Variante6");
			} else if (varianteChoisie instanceof Variante7) {
				boxChoixVariante32.setSelectedItem("Variante7");
			}
		} else if (tailleInitPaquet == 52) {
			if(varianteChoisie instanceof VarianteMinimale) {
				boxChoixVariante52.setSelectedItem("VarianteMinimale");
			} else if (varianteChoisie instanceof Variante5) {
				boxChoixVariante52.setSelectedItem("Variante5");
			} else if (varianteChoisie instanceof VarianteMonclar) {
				 boxChoixVariante52.setSelectedItem("VarianteMonclar");
			}
		}
	}
	
}
