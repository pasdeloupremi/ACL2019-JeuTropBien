package jeu;

import java.util.ArrayList;

public class Personnage {
	
	

	private String nom;
	protected int PV;
	private int ATK;
	private ArrayList<Float> coordXY;
	protected ArrayList<Float> direction;
	protected float vitesse;
	private Carte refCarte;
	private float seuilContact;
	
	public static ArrayList<Monstre> listeMonstre=new ArrayList<Monstre>();
	static int compteur=0;
	
	public Personnage(String nom, int pV, int aTK, ArrayList<Float> coordXY, ArrayList<Float> direction, float vitesse,
			Carte refCarte, float seuilContact) {
		super();
		this.nom = nom;
		PV = pV;
		ATK = aTK;
		this.coordXY = coordXY;
		this.direction = direction;
		this.vitesse = vitesse;
		this.refCarte = refCarte;
		this.seuilContact = seuilContact;
	}
	
	public void deplacer(ArrayList <Float> newcoord) {
		this.coordXY=newcoord;
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPV() {
		return PV;
	}

	public void setPV(int pV) {
		PV = pV;
	}

	public int getATK() {
		return ATK;
	}

	public void setATK(int aTK) {
		ATK = aTK;
	}

	public ArrayList<Float> getCoordXY() {
		return coordXY;
	}

	public void setCoordXY(ArrayList<Float> coordXY) {
		this.coordXY = coordXY;
	}

	public ArrayList<Float> getDirection() {
		return direction;
	}

	public void setDirection(ArrayList<Float> direction) {
		this.direction = direction;
	}

	public float getVitesse() {
		return vitesse;
	}

	public void setVitesse(float vitesse) {
		this.vitesse = vitesse;
	}

	public Carte getRefCarte() {
		return refCarte;
	}

	public void setRefCarte(Carte refCarte) {
		this.refCarte = refCarte;
	}

	public float getSeuilContact() {
		return seuilContact;
	}

	public void setSeuilContact(float seuilContact) {
		this.seuilContact = seuilContact;
	}

	public static ArrayList<Monstre> getListeMonstre() {
		return listeMonstre;
	}

	public static void setListeMonstre(ArrayList<Monstre> listeMonstre) {
		Personnage.listeMonstre = listeMonstre;
	}
	
}
