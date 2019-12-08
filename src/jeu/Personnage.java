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
	
	public Personnage(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			 float seuilContact) {
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
	}
	
	public void deplacer() {
		
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

	
}
