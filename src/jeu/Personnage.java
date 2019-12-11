package jeu;
import engine.Cmd;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Personnage {

	public static ArrayList<Monstre> listeMonstre=new ArrayList<Monstre>();
	public static Heros Joueur;
	
	private String nom;
	protected int PV;
	private int ATK;
	private float[] coordXY;
	protected float[] direction;
	protected float vitesse;
	private float seuilContact;
	public Cmd dirImg;
	private int animation;
	private int[] tailleImg;
	
	public Personnage(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			 float seuilContact, int[] tailleImg) {
		super();
		this.nom = nom;
		PV = pV;
		ATK = aTK;
		this.coordXY = coordXY;
		this.direction = direction;
		this.vitesse = vitesse;
		this.seuilContact = seuilContact;
		this.dirImg=Cmd.DOWN;
		this.animation=0;
		this.tailleImg=tailleImg;
		
	}
	
	//renvoie TRUE quand plus de PV
	public boolean isAlive()
	{
		if(PV <= 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
	public boolean contactPers(Personnage p) {
		float[] coord1= {(this.getSeuilImg()[0]+this.getSeuilImg()[1])/2,(this.getSeuilImg()[2]+this.getSeuilImg()[3])/2};
		float[] coord2= {(p.getSeuilImg()[0]+p.getSeuilImg()[1])/2,(p.getSeuilImg()[2]+p.getSeuilImg()[3])/2};
		if (distance(coord1,coord2)<=(p.seuilContact+this.seuilContact)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean contactMur() {
		int[][] tab=Carte.getCarte().donnees;
		int t=Carte.taillecase;
		int casex1=(int) this.getSeuilImg()[0]/t;
		int casex2=(int) this.getSeuilImg()[1]/t;
		int casey1=(int) this.getSeuilImg()[2]/t;
		int casey2=(int) this.getSeuilImg()[3]/t;
		if (tab[casex1][casey1]==1) {
			return true;
		}
		else if (tab[casex2][casey1]==1) {
			return true;
		}
		else if (tab[casex1][casey2]==1) {
			return true;
		}
		else if (tab[casex2][casey2]==1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public float[] getSeuilImg() {
		float[] Seuil = {0,0,0,0}; //gauche,droite,haut,bas de l'image
		Seuil[0]=(float) (this.direction[0]+0.5*this.getTailleImg()[0]+this.seuilContact);
		Seuil[1]=(float) (this.direction[0]+0.5*this.getTailleImg()[0]-this.seuilContact);
		Seuil[2]=(float) (this.direction[1]+0.7*this.getTailleImg()[1]-this.seuilContact);
		Seuil[3]=(float) (this.direction[1]+0.8*this.getTailleImg()[1]+this.seuilContact);
		return Seuil;
	}
	
	public void deplacer() {
		if (!this.contactMur()) {
			this.coordXY=this.direction;
		}		
		Heros h=Heros.Joueur;
		if(this==h) {			
		}
		else {
			if(this.contactPers(h)) {
				h.setPV(h.getPV()-this.getATK());
				//System.out.println(h.getPV());
			}
		}
	}
	
	public int getdirectionIMG() {
		int n=0;
		switch(dirImg) {
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
		return n;
	}
	
	public int getAnimation() { //0:sur place, 1:pas droit, 2:milieu, 3:pas gauche, 4:milieu
		int a;
		switch(animation) {
		case 0:
			a=1;
			break;
		case 1:
			a=0;
			animation=2;
			break;
		case 2:
			a=1;
			animation=3;
			break;
		case 3:
			a=2;
			animation=4;
			break;
		case 4:
			a=1;
			animation=1;
			break;
		default:
			a=1;
			animation=0;
			break;
		}
		return a;
	}
	
	public void debutAnimation() {
		if(animation==0) {animation=1;}
	}
	
	public void surPlace() {
		animation=0;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPV() {
		return PV;
	}

	public void setPV(int pV) {
		PV = pV;
	}

	public int getATK() {
		return ATK;
	}

	public void setATK(int aTK) {
		ATK = aTK;
	}

	public float[] getCoordXY() {
		return coordXY;
	}

	public void setCoordXY(float[] coordXY) {
		this.coordXY = coordXY;
	}

	public float[] getDirection() {
		return direction;
	}

	public void setDirection(float[] direction) {
		this.direction = direction;
	}

	public float getVitesse() {
		return vitesse;
	}

	public void setVitesse(float vitesse) {
		this.vitesse = vitesse;
	}


	public float getSeuilContact() {
		return seuilContact;
	}

	public void setSeuilContact(float seuilContact) {
		this.seuilContact = seuilContact;
	}
	public static float distance(float[]coord1,float[]coord2) {
		return (float) Math.sqrt(Math.pow((coord1[0]-coord2[0]),2)+Math.pow((coord1[1]-coord2[1]),2));
	}

	public int[] getTailleImg() {
		return tailleImg;
	}

	public void setTailleImg(int[] tailleImg) {
		this.tailleImg = tailleImg;
	}
	
}
