package jeu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.Cmd;

public class Monstre extends Personnage{
	 	
	public Monstre(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			float seuilContact,int[] tailleImg,String fichierImg) {
		super(nom, pV, aTK, coordXY, direction, vitesse, seuilContact,tailleImg,fichierImg);
		listeMonstre.add(this);
		this.delaiATK=15;
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
		this.debutAnimation();
		switch (c) {
		// si on appuie sur 'q',commande joueur est gauche
		case 1:
			float[] test1 = {coordactuelle[0]-this.vitesse/2+x,coordactuelle[1]+y};
			this.setDirection(test1);
			this.dirImg = Cmd.LEFT;
			break;
		case 2:
			float[] test2 = {coordactuelle[0]+this.vitesse/2+x,coordactuelle[1]+y};
			this.setDirection(test2);
			this.dirImg = Cmd.RIGHT;
			break;
		case 3:
			float[] test3 = {coordactuelle[0]+x,coordactuelle[1]+this.vitesse/2+y};
			this.setDirection(test3);
			this.dirImg = Cmd.DOWN;
			break;
		case 4:
			float[] test4 = {coordactuelle[0]+x,coordactuelle[1]-this.vitesse/2+y};
			this.setDirection(test4);
			this.dirImg = Cmd.UP;
			break;
		default:
			break;
		}
		this.deplacer();	
	}
	
	
	public static void AffichageMonstre(Graphics2D crayon) {
		//	AFFICHAGE DES MONSTRES
		BufferedImage monstre;
		int[] tailleM;
		ArrayList<Monstre> listeM = Personnage.listeMonstre;
		for(Monstre m:listeM) {
			m.majATK();
			int x = (int)m.getCoordXY()[0];
			int y = (int)m.getCoordXY()[1];
			int n = m.getdirectionIMG();
			int frame = m.getAnimation();
			tailleM = m.getTailleImg();
			try {
				monstre = ImageIO.read(new File(m.fichierImg));	
				//	CHANGER LES 4 DERNIERS ARGUMENTS SELON LA DIRECTION DU MONSTRE
				crayon.drawImage(monstre,Carte.decalX(x), Carte.decalY(y), Carte.decalX(tailleM[0]+x), Carte.decalY(tailleM[1]+y),frame*tailleM[0],n*tailleM[1],(frame+1)*tailleM[0],tailleM[1]*(n+1), null);
			} catch (IOException e) {
				System.out.println("pas d'image pour le mosntre : "+m.getNom());
			}
			m.AffichageHPBar(crayon);
		}
	}
	
}
