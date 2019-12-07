package jeu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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

	private MainMenu game;
	
	
	BufferedImage startButtonImg,quitButtonImg;
	public MainMenuPainter(MainMenu game)
	{
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
		Rectangle background =new Rectangle(0,0,10000,10000);
		graphics.setColor(Color.black);
		graphics.fill(background);
		
		graphics.setColor(Color.yellow);
	    Font font = new Font("Serif", Font.PLAIN, 20);
	    graphics.setFont(font);
		graphics.drawString("hit space to confirm", 100, 50);
		
		graphics.drawImage(startButtonImg, null, game.boundingBoxStartButton.x, game.boundingBoxStartButton.y);
		graphics.drawImage(quitButtonImg, null, game.boundingBoxQuitButton.x, game.boundingBoxQuitButton.y);
		//graphics.setColor(Color.yellow);
		//graphics.draw(game.boundingBoxStartButton);
		
		graphics.setColor(Color.yellow);
			switch (game.cursorPos) {
			case 0:
				graphics.draw(game.boundingBoxStartButton);
				break;
			case 1:
				graphics.draw(game.boundingBoxQuitButton);
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
