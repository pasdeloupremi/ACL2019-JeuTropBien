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

	private boolean bAfficherEcranVictoire,bAfficherEcranDefaite;
	private BufferedImage imgVictoire,imgDefaite;
	
	/**
	 * la taille des cases
	 */


	/**
	 * appelle constructeur parent
	 * 
	 * @param game
	 *            le jeutest a afficher
	 */
	public PacmanPainter() {	
		bAfficherEcranVictoire = false;
		bAfficherEcranDefaite = false;
		
		try {
			imgVictoire = ImageIO.read(new File("Victoire.png"));
			imgDefaite = ImageIO.read(new File("Defaite.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//liaison avec le PacmanGame
	//PacmanGame appel cette fonction lorsque les objectifs sont remplis
	public void afficherVictoire(boolean b)
	{
		bAfficherEcranVictoire = b;
		
	}
	
	//liaison avec le PacmanGame
	//PacmanGame appel cette fonction lorsque le joueur est mort
	public void afficherDefaite(boolean b)
	{
		bAfficherEcranDefaite = b;
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		Carte.Update(im, crayon); // MISE A JOUR DE L'AFFICHAGE
		
		if(bAfficherEcranVictoire)
		{
			crayon.drawImage(imgVictoire, null, 0, 0);
		}
		if(bAfficherEcranDefaite)
		{
			crayon.drawImage(imgDefaite, null, 0, 0);
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
