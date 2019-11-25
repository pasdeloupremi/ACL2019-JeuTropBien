package jeu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Carte {
	
	public int[][] donnees;
	public int taillecase;
	
	public Carte(String fichier,int n,int m) throws IOException {
		BufferedReader helpReader;
		taillecase=32;
		donnees=new int[n][m];
		String[] tab;
		try {
			helpReader = new BufferedReader(new FileReader(fichier));
			String ligne;
			ligne = helpReader.readLine();
			int k=0;
			do {
				tab=ligne.split(";");
				for(int j=0;j<m;j++) {
					donnees[k][j]=Integer.parseInt(tab[j]);						
				}
				k++;
			} while((ligne = helpReader.readLine()) != null);
			
			helpReader.close();
		} catch (IOException e) {
			System.out.println("Help not available");
		}
	}
	
	
}
