package jeu;


public class Monstre extends Personnage{
	 	
	public Monstre(float[] XY, String name) {
		super(XY,name);
		listeMonstre.add(this);
	}
}
