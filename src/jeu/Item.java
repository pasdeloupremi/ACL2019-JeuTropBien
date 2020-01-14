package jeu;

import java.util.ArrayList;

public class Item {
	
	public static int openingtime;
	public static int trapdmgs;
	private Heros joueur;
	private int soins=50;
	private float vitesseinitiale;
	public int dureeitemspeed=0;
	private int encours=0;
	public int dureeouvertureporte=0;
	private ArrayList<int[]> memoirecases = new ArrayList<int[]>();
	private int [] memoirecase= new int[2];
	private Carte memoire = Carte.getCarte();
	private static Item item=new Item();

	public Item () {
		this.joueur=Personnage.Joueur;
	}
	
	public void Heal () {
		int test=0;
		if (joueur.getPV()==Main.PVheros) {
			test=0;
		}
		else if (joueur.getPV()+soins>Main.PVheros) {
			joueur.setPV(Main.PVheros);
			test=1;
		}
		else  {
			joueur.setPV(joueur.getPV()+soins);
			test=1;
		}
		if (test==1) {
			int[][] tab=Carte.getCarte().donnees;
			int t=Carte.taillecase;
			int casex1=(int) joueur.getSeuilImg()[0]/t;
			int casex2=(int) joueur.getSeuilImg()[1]/t;
			int casey1=(int) joueur.getSeuilImg()[2]/t;
			int casey2=(int) joueur.getSeuilImg()[3]/t;
			if (tab[casex1][casey1]==8) {
				tab[casex1][casey1]=0;
			}
			else if (tab[casex2][casey1]==8) {
				tab[casex2][casey1]=0;
				}
			else if (tab[casex1][casey2]==8) {
				tab[casex1][casey2]=0;
				}
			else if (tab[casex2][casey2]==8) {
				tab[casex2][casey2]=0;
				}
		}	
	}
	
	public void Piege() {
		joueur.setPV(joueur.getPV()-trapdmgs);
	}
	
	public void Speedboost() {
		if (encours==0) {
		vitesseinitiale=joueur.getVitesse();
		joueur.setVitesse(joueur.getVitesse()+5);
		}
		encours=1;
		dureeitemspeed=1;
	}
	
	public void resetspeed() {
		dureeitemspeed=0;
		joueur.setVitesse(vitesseinitiale);
		encours=0;
	}
	
	public void interrupteur() {
		dureeouvertureporte=1;
		for (int i=0;i<memoire.donnees.length;i++) {
			for (int j=0;j<memoire.donnees[0].length;j++) {
				if (memoire.donnees[i][j]==6) {
					memoirecase = new int[2];
					memoirecase[0]= i;
					memoirecase[1]= j;
					memoirecases.add(memoirecase);
					memoire.donnees[i][j]=0;
				}
			}
		}
	}
	
	public void interrupteur2() {
		dureeouvertureporte=1;
		for (int i=0;i<memoire.donnees.length;i++) {
			for (int j=0;j<memoire.donnees[0].length;j++) {
				if (memoire.donnees[i][j]==15) {
					memoirecase = new int[2];
					memoirecase[0]= i;
					memoirecase[1]= j;
					memoirecases.add(memoirecase);
					memoire.donnees[i][j]=0;
				}
			}
		}
	}
	
	public void interrupteur3() {
		dureeouvertureporte=1;
		for (int i=0;i<memoire.donnees.length;i++) {
			for (int j=0;j<memoire.donnees[0].length;j++) {
				if (memoire.donnees[i][j]==14) {
					memoirecase = new int[2];
					memoirecase[0]= i;
					memoirecase[1]= j;
					memoirecases.add(memoirecase);
					memoire.donnees[i][j]=0;
				}
			}
		}
	}
	public void interrupteurreset() {
		boolean test=false;
		int t=Carte.taillecase;
		int casex1=(int) joueur.getSeuilImg()[0]/t;
		int casex2=(int) joueur.getSeuilImg()[1]/t;
		int casey1=(int) joueur.getSeuilImg()[2]/t;
		int casey2=(int) joueur.getSeuilImg()[3]/t;
		for (int j=0;j<memoirecases.size();j++) {
			try {
				if ((casex1==memoirecases.get(j)[0]) && (casey1==memoirecases.get(j)[1])) {
					test=true;
				}
				else if ((casex1==memoirecases.get(j)[0]) && (casey2==memoirecases.get(j)[1])){
					test=true;
				}
				else if ((casex2==memoirecases.get(j)[0]) && (casey1==memoirecases.get(j)[1])) {
					test=true;
				}
				else if ((casex2==memoirecases.get(j)[0]) && (casey2==memoirecases.get(j)[1])) {
					test=true;
				}
				else {
					test=false;
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("joueur dans la porte");
			}
			if (!test) {
				
				memoire.donnees[memoirecases.get(j)[0]][memoirecases.get(j)[1]]=6;
			}
			if (memoirecases.isEmpty()) {
				dureeouvertureporte=0;
			}
		}
	}
	
	public void portemonstre() {
		for (int i=0;i<memoire.donnees.length;i++) {
			for (int j=0;j<memoire.donnees[0].length;j++) {
				if (memoire.donnees[i][j]==7) {
					memoire.donnees[i][j]=0;
				}
			}
		}
	}
	
	public static void clear() {
		item.memoirecase=new int[2];
		item.memoirecases.clear();
		item.dureeouvertureporte=0;
		item.memoire=Carte.getCarte();
		item.dureeitemspeed=0;
		item.encours=0;
		Item.openingtime=30; 
		}
	
	public static Item getItem() {
		return item;
	}
}


	


	
