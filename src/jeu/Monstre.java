package jeu;

import engine.Cmd;

public class Monstre extends Personnage{
	 	
	public Monstre(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			float seuilContact,int[] tailleImg) {
		super(nom, pV, aTK, coordXY, direction, vitesse, seuilContact,tailleImg);
		listeMonstre.add(this);
	}
	
	public void deplacermonstre() {
		float[] coordactuelle = this.getCoordXY();
		float[] coordheros = Heros.Joueur.getCoordXY();
		float xdif = coordactuelle[0] - coordheros[0]; 
		float ydif = coordactuelle[1] - coordheros[1]; 
		float dif = Math.abs(xdif + ydif);
		float x = -xdif/dif*(vitesse/2);
		float y = -ydif/dif*(vitesse/2);
		int c = 1+(int)(Math.random()*4);
		switch (c) {
		// si on appuie sur 'q',commande joueur est gauche
		case 1:
			float[] test1 = {coordactuelle[0]-this.vitesse/2+x,coordactuelle[1]+y};
			this.setDirection(test1);
			break;
		case 2:
			float[] test2 = {coordactuelle[0]+this.vitesse/2+x,coordactuelle[1]+y};
			this.setDirection(test2);
			break;
		case 3:
			float[] test3 = {coordactuelle[0]+x,coordactuelle[1]+this.vitesse/2+y};
			this.setDirection(test3);
			break;
		case 4:
			float[] test4 = {coordactuelle[0]+x,coordactuelle[1]-this.vitesse/2+y};
			this.setDirection(test4);
			break;
		default:
			break;
		}
		this.deplacer();	
	}
	
}
