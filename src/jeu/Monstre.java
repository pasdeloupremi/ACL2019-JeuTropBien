package jeu;


public class Monstre extends Personnage{
	 	
	public Monstre(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			float seuilContact) {
		super(nom, pV, aTK, coordXY, direction, vitesse, seuilContact);
		listeMonstre.add(this);
	}
}
