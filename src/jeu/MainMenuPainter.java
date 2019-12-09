package jeu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.Cmd;
import engine.Game;
import engine.GamePainter;

public class MainMenuPainter implements GamePainter {

	/**
	 * la taille des cases
	 */
	protected static final int WIDTH = 1000;
	protected static final int HEIGHT = 500;

	Point positionStartButton; 
	Point positionQuitButton; 
	
	Rectangle highlightStart;
	Rectangle highlightQuit;
	
	private MainMenu game;
	
	
	BufferedImage startButtonImg,quitButtonImg;
	public MainMenuPainter(MainMenu game)
	{
		positionStartButton = new Point(100,100);
		positionQuitButton = new Point(100,100+200);
		
		highlightStart = new Rectangle(positionStartButton.x,positionStartButton.y,128,64);
		highlightQuit = new Rectangle(positionQuitButton.x,positionQuitButton.y,128,64);
		
		this.game = game;
		try {
			startButtonImg = ImageIO.read(new File("StartButton.png"));
			quitButtonImg = ImageIO.read(new File("QuitButton.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(BufferedImage image) {
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		
		//background noir
		Rectangle background =new Rectangle(0,0,10000,10000);
		graphics.setColor(Color.black);
		graphics.fill(background);
		
		//affichage du menu
		graphics.setColor(Color.white);
	    Font font = new Font("Serif", Font.PLAIN, 20);
	    graphics.setFont(font);
		graphics.drawString("hit space to confirm", 100, 50);
		
		graphics.drawImage(startButtonImg, null,positionStartButton.x,positionStartButton.y);
		graphics.drawImage(quitButtonImg, null, positionQuitButton.x,positionQuitButton.y);

		//effet de surbrillance sur la selection
		
		graphics.setColor(Color.white);
		switch (game.cursorPos) {
			case 0:
				graphics.draw(highlightStart);
				break;
			case 1:
				graphics.draw(highlightQuit);
				break;
	
			default:
				break;
		}

	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return WIDTH;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return HEIGHT;
	}

}
