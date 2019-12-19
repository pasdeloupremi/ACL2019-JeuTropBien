package jeu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import javax.imageio.ImageIO;


import engine.Cmd;

public class Heros extends Personnage{

	private float porteeATK;
	int atkframe;
	int spellframe;
	static ArrayList<Monstre> mtouches;
	
	public Heros(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			float seuilContact, float porteeATK,int[] tailleImg,ArrayList<Monstre> listeMonstre,String fichierImg) {
		super(nom, pV, aTK, coordXY, direction, vitesse, seuilContact,tailleImg,fichierImg);
		this.porteeATK = porteeATK;
		Joueur=this;
		this.listeMonstre = listeMonstre;
		this.delaiATK=8;
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
			float[] test1 = {coordactuelle[0]-h.vitesse,coordactuelle[1]};
			h.setDirection(test1);
			break;
		case RIGHT:
			float[] test2 = {coordactuelle[0]+h.vitesse,coordactuelle[1]};
			h.setDirection(test2);
			break;
		case DOWN:
			float[] test3 = {coordactuelle[0],coordactuelle[1]+h.vitesse};
			h.setDirection(test3);
			break;
		case UP:
			float[] test4 = {coordactuelle[0],coordactuelle[1]-h.vitesse};
			h.setDirection(test4);
			break;
		default:
			break;
		}
		h.deplacer();	
	}
	
	public static void attaquer() {
		Heros h=Personnage.Joueur;
		if(h.frameATK==0) {
		h.atkframe=1;
		h.frameATK++;
		ArrayList<Monstre> monstresmorts=new ArrayList<Monstre>();
		for(Monstre m: Personnage.listeMonstre) {
			float[] coord1= {(h.getSeuilImg()[0]+h.getSeuilImg()[1])/2,(h.getSeuilImg()[2]+h.getSeuilImg()[3])/2};
			float[] coord2= {(m.getSeuilImg()[0]+m.getSeuilImg()[1])/2,(m.getSeuilImg()[2]+m.getSeuilImg()[3])/2};
			if (distance(coord1,coord2)<=(m.getSeuilContact()+h.porteeATK)) {
				m.setPV(m.getPV()-h.getATK());
				Main.playSound("Slash1.wav", -2);
				if(m.getPV()<=0) {
					monstresmorts.add(m);
					if(listeMonstre.isEmpty()) {
						return ;
					}
				}
			}
		}
		for(Monstre m:monstresmorts) {
			listeMonstre.remove(m);
		}
		}
	}
	
	public static void lancersort1() {
		mtouches=new ArrayList<Monstre>();
		Heros h=Personnage.Joueur;
		if(h.spellframe==0) {
		h.spellframe=1;
		h.frameATK++;
		int seuilSort=80;
		ArrayList<Monstre> monstresmorts=new ArrayList<Monstre>();
		for(Monstre m: Personnage.listeMonstre) {
			float[] coord1= {(h.getSeuilImg()[0]+h.getSeuilImg()[1])/2,(h.getSeuilImg()[2]+h.getSeuilImg()[3])/2};
			float[] coord2= {(m.getSeuilImg()[0]+m.getSeuilImg()[1])/2,(m.getSeuilImg()[2]+m.getSeuilImg()[3])/2};
			if (distance(coord1,coord2)<=(m.getSeuilContact()+h.porteeATK+seuilSort)) {
				m.setPV(m.getPV()-h.getATK()*2);
				mtouches.add(m);
				Main.playSound("Blow1.wav", -2);
				if(m.getPV()<=0) {
					monstresmorts.add(m);
					if(listeMonstre.isEmpty()) {
						return ;
					}
				}
			}
		}
		for(Monstre m:monstresmorts) {
			listeMonstre.remove(m);
		}
		}
	}
	
	public static void AffichageHeros(Graphics2D crayon) {
//		AFFICHAGE DU HEROS
			BufferedImage heros;
			Heros h = Personnage.Joueur;
			int x = (int)h.getCoordXY()[0];
			int y = (int)h.getCoordXY()[1];
			int n = h.getdirectionIMG();
			int frame=h.getAnimation();
			h.majATK();
			if(h.atkframe>0) {
				Heros.AttakAnimation(crayon,h.atkframe-1);
				h.atkframe++;
				if(h.atkframe>3) {h.atkframe=0;}
			}
			else {
			try {
				heros = ImageIO.read(new File(h.fichierImg));	
				crayon.drawImage(heros,Carte.decalX(x), Carte.decalY(y), Carte.decalX(48+x), Carte.decalY(72+y),frame*48,n*72,48+frame*48,72+n*72, null);
				
			} catch (IOException e) {
				System.out.println("pas d'image pour le heros");
			}
			}
			h.AffichageHPBar(crayon);
	}
	
	public static void AffichageSorts(Graphics2D crayon) {
		Heros h = Personnage.Joueur;
		if(h.spellframe>0) {
			Heros.Spell1Animation(crayon,h.spellframe);
			h.spellframe++;
			if(h.spellframe>10) {h.spellframe=0;}
		}
	}
	
	
	public static void AttakAnimation(Graphics2D crayon,int frame) {
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
			crayon.drawImage(atk,Carte.decalX(x), Carte.decalY(y), Carte.decalX(48+x), Carte.decalY(72+y),0+frame*48,0+n*72,48+frame*48,72+n*72, null);
		} catch (IOException e) {
			System.out.println("pas d'image d'attaque");
		}
		
	}
	
	public static void Spell1Animation(Graphics2D crayon,float frame) {
		Heros h=Personnage.Joueur;
		System.out.println(Heros.mtouches.size());
		int x;
		int y;
		try {
		if(!Heros.mtouches.isEmpty()) {
			for(Monstre m : mtouches) {
				x=(int)((1-frame/10)*h.getCoordXY()[0]+(frame/10)*m.getCoordXY()[0]);
				y=(int)((1-frame/10)*h.getCoordXY()[1]+(frame/10)*m.getCoordXY()[1]);
				BufferedImage spell = ImageIO.read(new File("fire.png"));	
				crayon.drawImage(spell,Carte.decalX(x), Carte.decalY(y), Carte.decalX(m.getTailleImg()[0]+x), Carte.decalY(m.getTailleImg()[1]+y),791,190,791+160,190+160, null);
			}
		}		
		} catch (IOException e) {
			System.out.println("pas d'image de sort");
		}
		
	}
}
