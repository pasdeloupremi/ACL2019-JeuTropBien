package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.GamePainter;
import jeu.Carte;
import jeu.Heros;
import jeu.Monstre;
import jeu.Personnage;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	/**
	 * la taille des cases
	 */

	Carte carte;

	/**
	 * appelle constructeur parent
	 * 
	 * @param game
	 *            le jeutest a afficher
	 */
	public PacmanPainter(Carte c) {		
		carte = c;
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		int t = carte.taillecase;
		int caseX=0;
		int caseY=0;
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		BufferedImage img=null;
		int[][] donnees = carte.donnees;
		
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
		try {
			heros = ImageIO.read(new File("heros48.png"));	
			//		CHANGER LES 4 DERNIERS ARGUMENTS SELON LA DIRECTION DU HEROS
			crayon.drawImage(heros,x, y, t+x, t+y,0,0,t,t, null);
		} catch (IOException e) {
			System.out.println("pas d'image pour le héros");
		}
		
		//	AFFICHAGE DES MONSTRES
		BufferedImage monstre;
		ArrayList<Monstre> listeM = Personnage.listeMonstre;
		for(Monstre m:listeM) {
			x = (int)m.getCoordXY()[0];
			y = (int)m.getCoordXY()[1];
			try {
				monstre = ImageIO.read(new File(m.getNom()));	
				//	CHANGER LES 4 DERNIERS ARGUMENTS SELON LA DIRECTION DU MONSTRE
				crayon.drawImage(monstre,x, y, t+x, t+y,0,0,t,t, null);
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

	@Override
	public int getWidth() {
		return Carte.width;
	}

	@Override
	public int getHeight() {
		return Carte.height;
	}

}
