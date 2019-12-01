package jeu;

import java.util.ArrayList;

public abstract class Personnage {
	
	public static ArrayList<Monstre> listeMonstre=new ArrayList<Monstre>();
	public static Heros Joueur;
	static int compteur=0;
	private float[] coordXY= {0,0};
	
	public Personnage(float[] XY, String name) {
		this.coordXY = XY;
		this.direction = XY;
		this.nom = name;
	}
	
	public void setCoordXY(float[] coordXY) {
		this.coordXY = coordXY;
	}

	protected float[] direction;
	private String nom;
	
	public String getNom() {
		return nom;
	}



	public float[] getCoordXY() {
		return coordXY;
	}


	
}
