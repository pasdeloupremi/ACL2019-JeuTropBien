package jeu;

public class Fantome extends Monstre {
	static int[] taille= {32,48};
	public static int incrementeur=0;
	public Fantome(float[] coordXY) {
		super("Fantome-"+incrementeur, 100, 15, coordXY, coordXY, 5, 15, taille, "fantome.png");
		incrementeur++;
		// TODO Auto-generated constructor stub
	}

}
