package jeu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Sort1Autoguidee extends Sort {

	static float porteeAtk;
	//private float vitesse;
	private static ArrayList<Monstre> mtouches;
	private static ArrayList<Monstre> monstresmorts=new ArrayList<Monstre>();
	private static int ATK=15;
	int spellframe;
	private static BufferedImage spell;
	
	public Sort1Autoguidee() {
		super("Fireball",ATK);
		this.porteeAtk=50;	

		// TODO Auto-generated constructor stub
	}

	public static void lancersort1() {
		Heros h=Personnage.Joueur;
		if(h.spellframe==0) {
			try {
				spell = ImageIO.read(new File("fire.png"));
			} catch (IOException e) {
				System.out.println("pas d'image du sort 1");
			}
			mtouches=new ArrayList<Monstre>();
			h.spellframe=1;
			h.frameATK++;
			int seuilSort=80;
			monstresmorts=new ArrayList<Monstre>();
			for(Monstre m: Personnage.listeMonstre) {
				float[] coord1= {(h.getSeuilImg()[0]+h.getSeuilImg()[1])/2,(h.getSeuilImg()[2]+h.getSeuilImg()[3])/2};
				float[] coord2= {(m.getSeuilImg()[0]+m.getSeuilImg()[1])/2,(m.getSeuilImg()[2]+m.getSeuilImg()[3])/2};
				if (Personnage.distance(coord1,coord2)<=(m.getSeuilContact()+porteeAtk+seuilSort)) {
					mtouches.add(m);
					}
				}
			}
			
	}

	public static void AffichageSorts(Graphics2D crayon) {
		Heros h = Personnage.Joueur;
		if(h.spellframe>0) {
			try {
				Spell1Animation(crayon,h.spellframe);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			h.spellframe++;
			if(h.spellframe>10) {
				h.spellframe=0;
				for(Monstre m: mtouches) {
					m.setPV(m.getPV()-ATK);
					Main.playSound("Blow1.wav", -2);
					if(m.getPV()<=0) {
						monstresmorts.add(m);
					}
				}
			}
		}
		if (!monstresmorts.isEmpty()) {
			for(Monstre m:monstresmorts) {
				Personnage.listeMonstre.remove(m);
			}
		}
	}

	public static void Spell1Animation(Graphics2D crayon,float frame) throws IOException {
		Heros h=Personnage.Joueur;
		int x;
		int y;
			if(!mtouches.isEmpty()) {
				for(Monstre m : mtouches) {
					x=(int)((1-frame/10)*h.getCoordXY()[0]+(frame/10)*m.getCoordXY()[0]);
					y=(int)((1-frame/10)*h.getCoordXY()[1]+(frame/10)*m.getCoordXY()[1]);
					crayon.drawImage(spell,Carte.decalX(x), Carte.decalY(y), Carte.decalX(m.getTailleImg()[0]+x), Carte.decalY(m.getTailleImg()[1]+y),791,190,791+160,190+160, null);
				}
			}		
	}
}
