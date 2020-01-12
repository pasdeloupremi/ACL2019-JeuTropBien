package jeu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.Cmd;

public class Monstre extends Personnage{
	
	private float[] tabIA= {0,0,0,0,1,0,0};//{direction du detour, etape ou frame,dirX,dirY,1-aller 2-retour,blocage,blocagePrecedent}

	public Monstre(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			float seuilContact,int[] tailleImg,String fichierImg) {
		super(nom, pV, aTK, coordXY, direction, vitesse, seuilContact,tailleImg,fichierImg);
		listeMonstre.add(this);
		this.delaiATK=15;
	}

	public void deplacermonstre() {
		float[] coordactuelle = this.getCoordXY();
		float[] coordheros = Heros.Joueur.getCoordXY();
		float distanceactuelle=distance(coordactuelle, coordheros);
		int c=0;
		float[] meilleurcoord = {0,0};
		for (int i=0;i<4;i++) {
			float[] coordfutur = {0,0};
			if (i==0) { //gauche
				coordfutur[0] = coordactuelle[0]-this.vitesse;
				coordfutur[1] = coordactuelle[1];
				if(tabIA[0]-1==i) {tabIA[2]=coordfutur[0];tabIA[3]=coordfutur[1];}}
			else if (i==1) { //droite
				coordfutur[0] = coordactuelle[0]+this.vitesse;
				coordfutur[1] = coordactuelle[1];
				if(tabIA[0]-1==i) {tabIA[2]=coordfutur[0];tabIA[3]=coordfutur[1];}}		
			else if (i==2) { //bas
				coordfutur[0] = coordactuelle[0];
				coordfutur[1] = coordactuelle[1]-this.vitesse;	
				if(tabIA[0]-1==i) {tabIA[2]=coordfutur[0];tabIA[3]=coordfutur[1];}}
			else if (i==3) { //haut
				coordfutur[0] = coordactuelle[0];
				coordfutur[1] = coordactuelle[1]+this.vitesse;	
				if(tabIA[0]-1==i) {tabIA[2]=coordfutur[0];tabIA[3]=coordfutur[1];}}
			float nouvelledistance=distance(coordheros,coordfutur);
			if (distanceactuelle>nouvelledistance) {
				distanceactuelle=nouvelledistance;
				meilleurcoord[0] = coordfutur[0];
				meilleurcoord[1] = coordfutur[1];
				c=i+1;
			}
		}
		this.debutAnimation();
		if(tabIA[0]>0) {meilleurcoord[0] =tabIA[2];meilleurcoord[1] =tabIA[3];c=(int)tabIA[0];}
		switch (c) {
		case 1:
			float[] test1 = {meilleurcoord[0],meilleurcoord[1]};
			this.setDirection(test1);
			this.dirImg = Cmd.LEFT;
			break;
		case 2:
			float[] test2 = {meilleurcoord[0],meilleurcoord[1]};
			this.setDirection(test2);
			this.dirImg = Cmd.RIGHT;
			break;
		case 3:
			float[] test3 = {meilleurcoord[0],meilleurcoord[1]};
			this.setDirection(test3);
			this.dirImg = Cmd.UP;
			break;
		case 4:
			float[] test4 = {meilleurcoord[0],meilleurcoord[1]};
			this.setDirection(test4);
			this.dirImg = Cmd.DOWN;
			break;
		default:
			break;
		}
		if (this.contactMur()) { //si je touche un mur (à améliorer)
			distanceactuelle=distance(coordactuelle, coordheros);
			float[] coordfutur = {0,0};
			if (c==1) { //si le mur est à gauche
				for (int i=3;i<5;i++) {
					if (i==3) { //bas
						coordfutur[0] = coordactuelle[0];
						coordfutur[1] = coordactuelle[1]-this.vitesse;
					}
					else if (i==4) { //haut
						coordfutur[0] = coordactuelle[0];
						coordfutur[1] = coordactuelle[1]+this.vitesse;			}

					float nouvelledistance=distance(coordheros,coordfutur);
					if (distanceactuelle>nouvelledistance) {
						distanceactuelle=nouvelledistance;
						meilleurcoord[0] = coordfutur[0];
						meilleurcoord[1] = coordfutur[1];
						c=i;					
					}
				}
			}
			else if (c==2) { //si le mur est à droite
				for (int i=3;i<5;i++) {
					if (i==3) { //bas
						coordfutur[0] = coordactuelle[0];
						coordfutur[1] = coordactuelle[1]-this.vitesse;
					}
					else if (i==4) { //haut
						coordfutur[0] = coordactuelle[0];
						coordfutur[1] = coordactuelle[1]+this.vitesse;			}

					float nouvelledistance=distance(coordheros,coordfutur);
					if (distanceactuelle>nouvelledistance) {
						distanceactuelle=nouvelledistance;
						meilleurcoord[0] = coordfutur[0];
						meilleurcoord[1] = coordfutur[1];
						c=i;
					}
				}
			}
			else if (c==3) { //si le mur est en bas
				for (int i=1;i<3;i++) {
					if (i==1) { //gauche
						coordfutur[0] = coordactuelle[0]-this.vitesse;
						coordfutur[1] = coordactuelle[1];
					}
					else if (i==2) { //droite
						coordfutur[0] = coordactuelle[0]+this.vitesse;
						coordfutur[1] = coordactuelle[1];			}

					float nouvelledistance=distance(coordheros,coordfutur);
					if (distanceactuelle>nouvelledistance) {
						distanceactuelle=nouvelledistance;
						meilleurcoord[0] = coordfutur[0];
						meilleurcoord[1] = coordfutur[1];
						c=i;
					}
				}
			}
			else if (c==4) { //si le mur est en haut
				for (int i=1;i<3;i++) {
					if (i==1) { //gauche
						coordfutur[0] = coordactuelle[0]-this.vitesse;
						coordfutur[1] = coordactuelle[1];
					}
					else if (i==2) { //droite
						coordfutur[0] = coordactuelle[0]+this.vitesse;
						coordfutur[1] = coordactuelle[1];			}

					float nouvelledistance=distance(coordheros,coordfutur);
					if (distanceactuelle>nouvelledistance) {
						distanceactuelle=nouvelledistance;
						meilleurcoord[0] = coordfutur[0];
						meilleurcoord[1] = coordfutur[1];
						c=i;
					}
				}
			}
			if(tabIA[0]>0) {
				if(tabIA[4]==2){tabIA[0]=0;tabIA[4]=1;}
				else {if(tabIA[5]<5) {}
				else{tabIA[0]=DirOp((int)tabIA[0]);tabIA[5]=0;}
				tabIA[4]=2;}
			}
			else {tabIA[0]=c;
			if(tabIA[6]==c) {tabIA[5]++;}
			else{tabIA[6]=c;}}
			this.setDirection(meilleurcoord);
			switch (c) {
			case 1:
				float[] test1 = {meilleurcoord[0],meilleurcoord[1]};
				this.setDirection(test1);
				this.dirImg = Cmd.LEFT;
				break;
			case 2:
				float[] test2 = {meilleurcoord[0],meilleurcoord[1]};
				this.setDirection(test2);
				this.dirImg = Cmd.RIGHT;
				break;
			case 3:
				float[] test3 = {meilleurcoord[0],meilleurcoord[1]};
				this.setDirection(test3);
				this.dirImg = Cmd.UP;
				break;
			case 4:
				float[] test4 = {meilleurcoord[0],meilleurcoord[1]};
				this.setDirection(test4);
				this.dirImg = Cmd.DOWN;
				break;
			default:
				break;
			}
		}
		if(tabIA[1]++>tabIA[4]*100) {tabIA[0]=0;tabIA[1]=0;tabIA[4]=1;}
		this.deplacer();	
	}

	public static int DirOp(int c) {
		switch(c) {
		case 1:
			return(2);
		case 2: 
			return(1);
		case 3:
			return(4);
		case 4:
			return(3);
		default:
			return(0);
		}
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
