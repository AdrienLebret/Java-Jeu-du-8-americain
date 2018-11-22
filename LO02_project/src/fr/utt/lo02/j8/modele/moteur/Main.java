package fr.utt.lo02.j8.modele.moteur;
import java.util.ArrayList;
import java.util.Observable;
/**
 * <b>Main est la classe representant la main d'un joueur.</b>
 * <p>
 * Elle herite d'Observable.
 * </p>
 * Une main est caracterise par :
 * <ul>
 * <li>Un ArrayList de Cartes, qui contient les cartes que possedent le joueur</li>
 * </ul>
 * @see Carte
 * @see Observable
 * @see ArrayList
 * 
 * @author Launois Jean-Baptiste, Lebret Adrien
 *
 */
public class Main extends Observable{
	/**
     * Liste de Cartes
     * 
     * @see Carte
     * @see ArrayList
     */
	private ArrayList<Carte> cartes = new ArrayList<Carte>();
	
	//*********** METHODES ***********
	
	//------------- Jeu --------------
	
	/**
	 * Tirer et donner une carte, la retirant ainsi de la main
	 * 
	 * @param indice : position de la carte dans la main
	 * @return la carte a donner
	 * 
	 * @see Main#getCarte(int)
	 * @see Main#supprimerCarte(int)
	 */
	public Carte tirerCarte(int indice) {
		Carte carteADonner = this.getCarte(indice);
		this.supprimerCarte(indice);
		return carteADonner;
	}
	
	
	//----- Modification main ------
	
	/**
	 * Ajoute UNE carte a la main
	 * @param carte qui sera ajoute dans la main (ArrayList cartes)
	 * @see Main#setChanged()
	 */
	public void addCarte(Carte carte) {
		this.cartes.add(carte);
		this.setChanged();
		this.notifyObservers(this.cartes);
	}
	
	/**
	 * Ajoute une LISTE de cartes a la main
	 * 
	 * @param cartes : ArrayList comportant les cartes a ajouter a la main du joueur
	 */
	public void addCarte(ArrayList<Carte> cartes) {
		this.cartes.addAll(cartes);
		this.setChanged();
		this.notifyObservers(this.cartes);
	}
	
	/**
	 * Supprime une carte de la main
	 * @param position de la carte dans la main
	 */
	public void supprimerCarte(int position) {
		this.cartes.remove(position);
		this.setChanged();
		this.notifyObservers(this.cartes);
	}
	
	/**
	 * Vide l'ensemble des cartes presentes dans la main
	 * @see ArrayList#clear()
	 */
	public void vider() {
		this.cartes.clear();
	}
	
	//********** ACCESSEURS **********
	
	/**
	 * Retourne le nombre de Cartes dans la main
	 * 
	 * @return le nombre de cartes dans la main
	 */
	public int getNombreCartes() {
		return this.cartes.size();
	}
	
	/**
	 * Retourne la carte a l'indice precise
	 * 
	 * @param position de la carte dans la main
	 * @return La carte a l'indice precise
	 * @throws IllegalArgumentException : exception lorsque la position donnee en parametre est <b>inferieur a zero</b> ou <b>superieur au nombre de cartes presentes dans la main</b>
	 */
	public Carte getCarte(int position) throws IllegalArgumentException{
		if(position < 0 || position >= this.getNombreCartes()) {
			throw new IllegalArgumentException("Vous n'avez que " + this.getNombreCartes() + " cartes dans votre main");
		}
		return this.cartes.get(position);
	}
	
	/**
	 * Retourne l'indice d'une carte dans la main
	 * @param carte a rechercher dans la main
	 * @return L'indice d'une carte dans la main
	 */
	public int getIndice(Carte carte) {
		int i=0;
		boolean trouve = false;
		while(i<this.getNombreCartes() && !trouve) {
			if(this.cartes.get(i) == carte) {
				trouve = true;
			}else {
				i++;
			}
		}
		if(trouve) {
			return i;
		}else {
			return -1;
		}
	}
	
	//********** AFFICHAGE ***********
	
	/**
     * Retourne le texte descriptif de la main.
     * 
     * @return Le texte descriptif de la main.
     */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<this.getNombreCartes()-1; i++) {
			sb.append("(");
			sb.append(i+1);
			sb.append(") ");
			sb.append(this.getCarte(i).toString());
			sb.append("\n");
		}
		sb.append("(");
		sb.append(this.getNombreCartes());
		sb.append(") ");
		sb.append(this.getCarte(this.getNombreCartes()-1).toString());
		return sb.toString();
	}
}
