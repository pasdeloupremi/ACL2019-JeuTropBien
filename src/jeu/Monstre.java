package jeu;

import java.util.ArrayList;

public class Monstre extends Personnage{
	 	
	public Monstre(String nom, int pV, int aTK, ArrayList<Float> coordXY, ArrayList<Float> direction, float vitesse,
			Carte refCarte, float seuilContact) {
		super(nom, pV, aTK, coordXY, direction, vitesse, refCarte, seuilContact);
		
	}
}
