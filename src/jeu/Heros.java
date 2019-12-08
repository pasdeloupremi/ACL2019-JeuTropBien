package jeu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.Cmd;

public class Heros extends Personnage{

	private float porteeATK;
	int atkframe;
	
	public Heros(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			float seuilContact, float porteeATK) {
		super(nom, pV, aTK, coordXY, direction, vitesse, seuilContact);
		this.porteeATK = porteeATK;
		Joueur=this;
	}
	
	public void toucher() {
		listeMonstre.get(0);
	}
	
	public static void deplacerjoueur(Cmd c) {
		Heros h=Heros.Joueur;
		h.debutAnimation();
		h.dirImg=c; 
		float[] coordactuelle=h.getCoordXY();
		switch (c) {
		// si on appuie sur 'q',commande joueur est gauche
		case LEFT:
			coordactuelle[0]=coordactuelle[0]-h.vitesse;
			h.setDirection(coordactuelle);
			break;
		case RIGHT:
			coordactuelle[0]=coordactuelle[0]+h.vitesse;
			h.setDirection(coordactuelle);
			break;
		case DOWN:
			coordactuelle[1]=coordactuelle[1]-h.vitesse;
			h.setDirection(coordactuelle);
			break;
		case UP:
			coordactuelle[1]=coordactuelle[1]+h.vitesse;
			h.setDirection(coordactuelle);
			break;
		default:
			break;
		}
	}
	
	public static void attaquer() {
		Heros h=Personnage.Joueur;
		h.atkframe=1;
		for(Monstre m: Personnage.listeMonstre) {
			float xm=m.getCoordXY()[0];
			float ym=m.getCoordXY()[1];
			if(Math.sqrt(Math.pow((xm-h.getCoordXY()[0]),2)+Math.pow((ym-h.getCoordXY()[1]),2))<=h.porteeATK) {
				m.setPV(m.getPV()-h.getATK());
				if(m.getPV()<=0) {
					listeMonstre.remove(m);
				}
			}
		}
	}
	
	
	public static void AttakAnimation(Graphics2D crayon, BufferedImage im, int frame) {
		Heros h=Personnage.Joueur;
		int x=(int)h.getCoordXY()[0];
		int y=(int)h.getCoordXY()[1];
		int n=0;
		switch(h.dirImg) {
		case UP:
			n=3;
			break;
		case DOWN:
			n=0;
			break;
		case LEFT:
			n=1;
			break;
		case RIGHT:
			n=2;
			break;
		default:
			break;
		}
		try {
			BufferedImage atk = ImageIO.read(new File("attaque.png"));	
			crayon.drawImage(atk,x, y, 48+x, 72+y,0+frame*48,0+n*72,48+frame*48,72+n*72, null);
		} catch (IOException e) {
			System.out.println("pas d'image d'attaque");
		}
		
	}
}
