package jeu;

public class Minotaure extends Monstre {
	static int[] taille= {48,48};
	public static int incrementeur=0;
	public Minotaure(float[] coordXY) {
		super("Minotaure-"+incrementeur, 200, 20, coordXY, coordXY, 3, 30, taille, "minotaure.png");
		incrementeur++;
		// TODO Auto-generated constructor stub
	}
	

}
