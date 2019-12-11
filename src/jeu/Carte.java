package jeu;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
		listeNiveaux.add(this);
	}
	
	
	public static void Update(BufferedImage im,Graphics2D crayon) {
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
		//	AFFICHAGE DU HEROS
		BufferedImage heros;
		Heros h = Personnage.Joueur;
		int x = (int)h.getCoordXY()[0];
		int y = (int)h.getCoordXY()[1];
		int n = h.getdirectionIMG();
		int frame=h.getAnimation();
		if(h.atkframe>0) {
			Timer timer = new Timer();
			Heros.AttakAnimation(crayon, img, h.atkframe-1);
			h.atkframe++;
			if(h.atkframe>3) {h.atkframe=0;}
		}
		else {
		try {
			heros = ImageIO.read(new File(h.fichierImg));	
			//		CHANGER LES 4 DERNIERS ARGUMENTS SELON LA DIRECTION DU HEROS
			crayon.drawImage(heros,x, y, 48+x, 72+y,frame*48,n*72,48+frame*48,72+n*72, null);
		} catch (IOException e) {
			System.out.println("pas d'image pour le heros");
		}
		}
		
		//	AFFICHAGE DES MONSTRES
		BufferedImage monstre;
		int[] tailleM;
		ArrayList<Monstre> listeM = Personnage.listeMonstre;
		for(Monstre m:listeM) {
			x = (int)m.getCoordXY()[0];
			y = (int)m.getCoordXY()[1];
			n = m.getdirectionIMG();
			frame = m.getAnimation();
			tailleM = m.getTailleImg();
			try {
				monstre = ImageIO.read(new File(m.fichierImg));	
				//	CHANGER LES 4 DERNIERS ARGUMENTS SELON LA DIRECTION DU MONSTRE
				crayon.drawImage(monstre,x, y, tailleM[0]+x, tailleM[1]+y,frame*tailleM[0],n*tailleM[1],(frame+1)*tailleM[0],tailleM[1]*(n+1), null);
			} catch (IOException e) {
				System.out.println("pas d'image pour le mosntre : "+m.getNom());
			}
		}
		
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
