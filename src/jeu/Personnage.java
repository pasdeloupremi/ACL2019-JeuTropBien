package jeu;
import engine.Cmd;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Personnage {

	public static ArrayList<Monstre> listeMonstre=new ArrayList<Monstre>();
	public static Heros Joueur;

	private String nom;
	protected int PV;
	protected int PVMAX;
	private int ATK;
	private float[] coordXY;
	protected float[] direction;
	protected float vitesse;
	private float seuilContact;
	protected Cmd dirImg;
	private int animation;
	private int[] tailleImg;
	protected String fichierImg;
	protected int frameATK;
	protected int delaiATK;

	public Personnage(String nom, int pV, int aTK, float[] coordXY, float[] direction, float vitesse,
			float seuilContact, int[] tailleImg, String fichierImg) {
		super();
		this.nom = nom;
		PV = Math.max(0,pV);
		PVMAX = Math.max(0,pV);
		ATK = aTK;
		this.coordXY = coordXY;
		this.direction = direction;
		this.vitesse = vitesse;
		this.seuilContact = seuilContact;
		this.dirImg=Cmd.DOWN;
		this.animation=0;
		this.tailleImg=tailleImg;
		this.fichierImg=fichierImg;
		this.initCoord();
	}

	//renvoie TRUE quand en vie
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
	
	public float[] initCoord() {
		ArrayList<Personnage> listePers=(ArrayList<Personnage>) listeMonstre.clone();
		listePers.add(Heros.Joueur);
		int t=Carte.taillecase;
		int largeur = (Carte.getCarte().donnees.length-1)*t;
		int hauteur = (Carte.getCarte().donnees[0].length-1)*t;
		this.coordXY[0]=Math.min((this.coordXY[0]/t)*t, largeur-t);
		this.coordXY[1]=Math.min((this.coordXY[1]/t)*t, hauteur-t);
		float[] coordbase=this.coordXY.clone();
		boolean contact;
		int[] coeff= {0,0,1,-1,1,1,-1,-1,
					  1,-1,0,0,1,-1,1,-1};
		int k=0;
		int c=1;
		int m=0;
		do {
			contact = false;
			if(this.contactMur()) {
				contact=true;
			}
			for(Personnage p:listePers) {
				if(p!=null) {
				if(!(this==p)&&this.contactPers(p)) {
					contact=true;
				}}
			}
			if(contact && coordbase[0]+coeff[k]*c*t+m>0 && coordbase[1]+coeff[k+8]*c*t+m>0 
					&& coordbase[0]+coeff[k]*c*t+m<largeur && coordbase[1]+coeff[k+8]*c*t+m<hauteur) {
				this.coordXY[0]=coordbase[0]+coeff[k]*c*t+m;
				this.coordXY[1]=coordbase[1]+coeff[k+8]*c*t-m;
			}
			k+=1;
			m=-m;
			if(c==29 && m==0) {m++;c=0;}
			if(m!=0) {m+=10;}
			if(k==8) {c+=1;k=0;}
		}while(contact==true && c<30);
		if(c>=20)System.out.println("Monstre : "+this.getNom()+" dans un mur");
		return this.coordXY;
	}
	
	public boolean regardeVersPers(Personnage p) {
		if(this.coordXY[0]<p.coordXY[0]&&(this.dirImg==Cmd.RIGHT))return true;
		if(this.coordXY[0]>p.coordXY[0]&&(this.dirImg==Cmd.LEFT))return true;
		if(this.coordXY[1]<p.coordXY[1]&&(this.dirImg==Cmd.DOWN))return true;
		if(this.coordXY[1]>p.coordXY[1]&&(this.dirImg==Cmd.UP))return true;
		return false;
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
		try {
		if (this.getClass()==Fantome.class) {
			return false; //il faudra gérer quand même les bords de map
		}
		else if ((tab[casex1][casey1]==1) || (tab[casex1][casey1]==6) || (tab[casex1][casey1]==7)|| (tab[casex1][casey1]==15)|| (tab[casex1][casey1]==13)) {
			return true;
		}
		else if ((tab[casex2][casey1]==1) || (tab[casex2][casey1]==6) || (tab[casex2][casey1]==7)|| (tab[casex2][casey1]==15)|| (tab[casex2][casey1]==13)) {
			return true;
		}
		else if ((tab[casex1][casey2]==1) || (tab[casex1][casey2]==6) || (tab[casex1][casey2]==7)|| (tab[casex1][casey2]==15)|| (tab[casex1][casey2]==13)) {
			return true;
		}
		else if ((tab[casex2][casey2]==1) || (tab[casex2][casey2]==6) || (tab[casex2][casey2]==7)|| (tab[casex2][casey2]==15)|| (tab[casex2][casey2]==13)) {
			return true;
		}
		else {
			return false;
		}
		}
		catch(ArrayIndexOutOfBoundsException e) {return true;}
	}
	
	public boolean dansCoin() {
		int[][] tab=Carte.getCarte().donnees;
		int t=Carte.taillecase;
		int casex1=(int) this.getSeuilImgCoord()[0]/t;
		int casex2=(int) this.getSeuilImgCoord()[1]/t;
		int casey1=(int) this.getSeuilImgCoord()[2]/t;
		int casey2=(int) this.getSeuilImgCoord()[3]/t;
		int k=0;
		try {
		if (tab[casex1-1][casey1-1]==1) {
			k++;
		}
		if (tab[casex2+1][casey1-1]==1) {
			k++;
		}
		if (tab[casex1-1][casey2+1]==1) {
			k++;
		}
		if (tab[casex2+1][casey2+1]==1) {
			k++;
		}
		return(k==3);
		}
		catch(ArrayIndexOutOfBoundsException e) {System.out.println("ERREUR DIRECTION MONSTRE");return true;}
	}
	
	public boolean contactCase(int numeroCase) {
		int[][] tab=Carte.getCarte().donnees;
		int t=Carte.taillecase;
		int casex1=(int) this.getSeuilImg()[0]/t;
		int casex2=(int) this.getSeuilImg()[1]/t;
		int casey1=(int) this.getSeuilImg()[2]/t;
		int casey2=(int) this.getSeuilImg()[3]/t;
		if (tab[casex1][casey1]==numeroCase) {
			return true;
		}
		else if (tab[casex2][casey1]==numeroCase) {
			return true;
		}
		else if (tab[casex1][casey2]==numeroCase) {
			return true;
		}
		else if (tab[casex2][casey2]==numeroCase) {
			return true;
		}
		else {
			return false;
		}
	}

	

	public float[] getSeuilImg() {
		float[] Seuil = {0,0,0,0}; //gauche,droite,haut,bas de l'image aux coords futurs dans direction
		Seuil[0]=(float) (this.direction[0]+0.5*this.getTailleImg()[0]+this.seuilContact);
		Seuil[1]=(float) (this.direction[0]+0.5*this.getTailleImg()[0]-this.seuilContact);
		Seuil[2]=(float) (this.direction[1]+0.7*this.getTailleImg()[1]-this.seuilContact);
		Seuil[3]=(float) (this.direction[1]+0.8*this.getTailleImg()[1]+this.seuilContact);
		return Seuil;
	}

	public float[] getSeuilImgCoord() {
		float[] Seuil = {0,0,0,0}; //gauche,droite,haut,bas de l'image aux coords actuelles
		Seuil[0]=(float) (this.coordXY[0]+0.5*this.getTailleImg()[0]+this.seuilContact);
		Seuil[1]=(float) (this.coordXY[0]+0.5*this.getTailleImg()[0]-this.seuilContact);
		Seuil[2]=(float) (this.coordXY[1]+0.7*this.getTailleImg()[1]-this.seuilContact);
		Seuil[3]=(float) (this.coordXY[1]+0.8*this.getTailleImg()[1]+this.seuilContact);
		return Seuil;
	}

	public void deplacer() {
		Heros h=Heros.Joueur;
		if (!this.contactMur() && this.frameATK==0) {
			if ((listeMonstre.isEmpty()) || (this.contactMur())) {
				this.coordXY=this.direction;
			}
			else {
				float[] dirP;
				float[] dirM;
				ArrayList<Personnage> listePers=(ArrayList<Personnage>) listeMonstre.clone();
				listePers.add(h);
				boolean contact=false;
				for(Personnage m:listePers) {
					if (!(this==m) && this.contactPers(m) && !(this.dansCoin()) && this.getClass()!=Fantome.class) {
						dirP = this.getSeuilImg();//gauche,droite,haut,bas de l'image
						dirM = m.getSeuilImg();
						switch(this.dirImg) {
						case UP:
							if(dirP[3]>dirM[3]) {contact=true;}
							break;
						case DOWN:
							if(dirP[2]<dirM[2]) {contact=true;}
							break;	
						case RIGHT:
							if(dirP[0]<dirM[0]) {contact=true;}
							break;	
						case LEFT:
							if(dirP[1]>dirM[1]) {contact=true;}
							break;	
						}
					}
				}
				if(!contact)this.coordXY=this.direction;
			}

		}		
		if(this==h) {			
		}
		else {
			if(this.contactPers(h) && this.frameATK==0) {
				this.frameATK++;
				h.setPV(h.getPV()-this.getATK());
				Main.playSound("Blow1.wav", Main.VolumeSon);	
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
	public void AffichageHPBar(Graphics2D crayon) {
		crayon.setColor(Color.black);
		crayon.fillRect(Carte.decalX((int)(this.coordXY[0])), Carte.decalY((int)(this.coordXY[1]-8)), this.tailleImg[0], 6);
		float PVf=this.PV;
		float PVMAXf=this.PVMAX;
		if(PVf/PVMAXf<0.5) {crayon.setColor(Color.orange);}
		else {crayon.setColor(Color.green);}
		crayon.fillRect(Carte.decalX((int)(this.coordXY[0])), Carte.decalY((int)(this.coordXY[1]-8)), Math.max(0,this.tailleImg[0]*this.PV/this.PVMAX), 6);
	}

	public void debutAnimation() {
		if(animation==0) {animation=1;}
	}

	public void majATK() {
		if(this.frameATK>0) {
			this.frameATK++;
			if(this.frameATK>this.delaiATK)this.frameATK=0;
		}
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
