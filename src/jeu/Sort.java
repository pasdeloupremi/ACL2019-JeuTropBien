package jeu;

import java.util.ArrayList;

public class Sort {

	private String nom;
	private int atK;
	protected static ArrayList<Monstre> monstresmorts;

	
	public Sort(String nom, int atK) {
		this.atK=atK;
		this.nom=nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getAtK() {
		return atK;
	}

	public void setAtK(int atK) {
		this.atK = atK;
	}
}
