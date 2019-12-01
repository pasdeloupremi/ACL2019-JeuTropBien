package jeu;

import java.util.ArrayList;

public class Heros extends Personnage{

	public Heros(float[] XY, String name) {
		super(XY,name);
		Joueur=this;
	};
	
	public void toucher() {
		listeMonstre.get(0);
	}
}
