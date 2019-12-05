package jeu;

import java.util.ArrayList;

public class Heros extends Personnage{
	
	private float porteeATK;
	
	public Heros(String nom, int pV, int aTK, ArrayList<Float> coordXY, ArrayList<Float> direction, float vitesse,
			Carte refCarte, float seuilContact, float porteeATK) {
		super(nom, pV, aTK, coordXY, direction, vitesse, refCarte, seuilContact);
		this.porteeATK = porteeATK;
	}

	
	
	public void toucher() {
		listeMonstre.get(0);
	}
	
	public void deplacerjoueur() {
		
	}
}
