package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.GamePainter;
import jeu.Carte;
import jeu.Heros;
import jeu.Main;
import jeu.Monstre;
import jeu.Personnage;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	private boolean bAfficherEcranVictoire,bAfficherEcranDefaite,bAfficherPause;
	private BufferedImage imgVictoire,imgDefaite;
	private Graphics2D graphics;

	
	private int currentButton;
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
		bAfficherPause = false;
		
		currentButton = 0;
		
		
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
	
	//liaison avec le PacmanGame
	//PacmanGame appel cette fonction quand on veut afficher le menu de pause
	public void menuPause(boolean b)
	{
		bAfficherPause = b;
		
	}

	private void drawButton(Rectangle boundingBox,String Text)
	{

		//Box
		graphics.setColor(Color.GRAY);
		graphics.fill(boundingBox);
		
		
		//text
		int sizeFont = 20;
		graphics.setColor(Color.white);
		 Font font = new Font("Serif", Font.PLAIN, sizeFont);
		 graphics.setFont(font);
		 
		 
		 
		 int sizeTextX = graphics.getFontMetrics().stringWidth(Text);
		 int sizeTextY = graphics.getFontMetrics().getHeight();
		 int blankSpaceX = Math.abs(boundingBox.width - sizeTextX);
		 int blankSpaceY = Math.abs(boundingBox.height - sizeTextY);
		 
		 graphics.drawString(Text,boundingBox.x + blankSpaceX/2, boundingBox.y + blankSpaceY);
	}
	private void drawButtonHighLight(Rectangle boundingBox)
	{
			graphics.draw(boundingBox);
	}
	
	

	
	
	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		graphics = (Graphics2D) im.getGraphics();
		Main.Update(graphics); // MISE A JOUR DE L'AFFICHAGE
		
		if(!bAfficherPause)
		{
			if(bAfficherEcranVictoire)
			{
				graphics.drawImage(imgVictoire, null, 0, 0);

			}
			if(bAfficherEcranDefaite)
			{
				graphics.drawImage(imgDefaite, null, 0, 0);
			}
		}
		
		else
		{
			//affichage du menu de pause
			graphics.setColor(Color.white);
		    Font font = new Font("Serif", Font.PLAIN, 20);
		    graphics.setFont(font);
			graphics.drawString("PAUSE", 100, 50);
			
			Rectangle R0 = new Rectangle(100,100,150,64);
			Rectangle R1 = new Rectangle(100,200,150,64);
			Rectangle R2 = new Rectangle(100,300,150,64);

			drawButton(R0,"reprendre");
			drawButton(R2,"quitter");
			drawButton(R1,"Retour Menu");
			
			switch (currentButton) {
			case 0:
				drawButtonHighLight(R0);
				break;
			case 1:
				drawButtonHighLight(R1);
				break;
			case 2:
				drawButtonHighLight(R2);
				break;


			default:
				break;
			}
			
		}
		
	
		
	}

	public void setCurrentButton(int button)
	{
		currentButton = button;
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
