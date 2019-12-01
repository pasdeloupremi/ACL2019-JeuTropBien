package jeu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Carte {
	
	public static int width;
	public static int height;
	public int[][] donnees;
	public int taillecase;
	public String terrain;
	
	public Carte(String fichier,int n,int m, int Tcase, String fichierTerrain) throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		terrain = fichierTerrain;
		taillecase=Tcase;
		donnees=new int[n][m];
		BufferedReader helpReader;
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
