package jeu;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;

import javax.imageio.ImageIO;

public class Carte {
	
	public static int width;
	public static int height;
	public static int taillecase;
	public static int niveau_actuel=0;
	public static ArrayList<Carte> listeNiveaux=new ArrayList<Carte>();
	public int[][] donnees;
	public String terrain;
	public int[] ListeMurs;
	
	public Carte(String fichier,int n,int m, int Tcase, String fichierTerrain) throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth()/2;
		height = (int)screenSize.getHeight()/2;
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
			int numD;
			do {
				tab=ligne.split(";");
				for(int j=0;j<m;j++) {
					try {numD=Integer.parseInt(tab[j]);}catch(NumberFormatException e){numD=0;}
					donnees[k][j]=numD;						
				}
				k++;
			} while((ligne = helpReader.readLine()) != null);
			
			helpReader.close();
		} catch (IOException e) {
			System.out.println("Help not available");
		}
		listeNiveaux.add(this);
	}
	
	public static void generer() {
		int numD;
		int hauteur = 5 + (int)(Math.random()*20);
		int largeur = 5 + (int)(Math.random()*20);
		String nom = "carte"+listeNiveaux.size()+".csv";
		StringBuilder sb = new StringBuilder();
		try (PrintWriter writer = new PrintWriter(nom)) {
			for(int i=0;i<hauteur;i++) {
				for(int j=0;j<largeur;j++) {
				if(i==0||i==hauteur-1||j==0||j==largeur-1) {numD=1;}
				else {numD=0;}
				sb.append(numD);
				sb.append(';');
				}
				sb.append('\n');
			}
		      writer.write(sb.toString());

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		try {
			Carte c = new Carte(nom,hauteur,largeur,48,getCarte().terrain);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void AffichageTerrain(Graphics2D crayon) {
		BufferedImage img=null;
		Carte carte = Carte.listeNiveaux.get(niveau_actuel);
		int[][] donnees = carte.donnees;
		int caseX=0;
		int caseY=0;
		int t = Carte.taillecase;	
		//	AFFICHAGE DU TERRAIN
		try {
			img = ImageIO.read(new File(carte.terrain));
		} catch (IOException e) {
			System.out.println("pas d'image de terrain");
		}
		for(int i=0;i<donnees.length;i++) {
			for(int j=0;j<donnees[0].length;j++) {
				switch(donnees[i][j]) {
				case 0: //	SOL
					caseX=t;
					caseY=0;
					break;
				case 1: //	MUR
					caseX=2;
					caseY=8*t;
					break;
				}
				crayon.drawImage(img, t*i, t*j, t*(i+1), t*(j+1), caseX, caseY, caseX+t, caseY+t,null);			
			}		
		}
	}
	
	public static void AffichageDecors(Graphics2D crayon) {
		Carte carte = Carte.listeNiveaux.get(niveau_actuel);
		int[][] donnees = carte.donnees;
		int caseX=0;
		int caseY=0;
		int t = Carte.taillecase;	
	
		//	AFFICHAGE DU DECOR
		BufferedImage decors=null;
		try {
			decors = ImageIO.read(new File("decors48.png"));
		} catch (IOException e) {
			//System.out.println("pas d'image de decoration");
		}
		for(int i=0;i<donnees.length;i++) {
			for(int j=0;j<donnees[0].length;j++) {
				switch(donnees[i][j]) {
				default :
					decors=null;
					break;
				}
				crayon.drawImage(decors, t*i, t*j, t*(i+1), t*(j+1), caseX, caseY, caseX+t, caseY+t,null);			
			}		
		}	
		
	}
	
	public static Carte getCarte () {
		return listeNiveaux.get(niveau_actuel);
	}
	
}
