package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.GamePainter;
import jeu.Carte;

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
	protected static final int WIDTH = 1000;
	protected static final int HEIGHT = 500;
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
		BufferedImage img;
		int[][] donnees = carte.donnees;
		for(int i=0;i<donnees.length;i++) {
			for(int j=0;j<donnees[0].length;j++) {
				switch(donnees[i][j]) {
				case 0:
					caseX=0;
					caseY=2*t;
					break;
				case 1:
					caseX=2;
					caseY=8*t;
					break;
				}
				try {
					img = ImageIO.read(new File("terrain.png"));
					crayon.drawImage(img, t*i, t*j, t*(i+1), t*(j+1), caseX, caseY, caseX+t, caseY+t,null);
				} catch (IOException e) {
					System.out.println("pas d'image");
				}
			}
			
		}
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}
