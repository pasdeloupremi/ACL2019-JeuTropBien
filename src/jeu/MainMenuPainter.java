package jeu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.Game;
import engine.GamePainter;

public class MainMenuPainter implements GamePainter {

	/**
	 * la taille des cases
	 */
	protected static final int WIDTH = 1000;
	protected static final int HEIGHT = 500;

	private MainMenu game;
	
	
	BufferedImage startButtonImg;
	public MainMenuPainter(MainMenu game)
	{
		this.game = game;
		try {
			startButtonImg = ImageIO.read(new File("StartButton.png"));
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
		
	    Font font = new Font("Serif", Font.PLAIN, 96);
	    graphics.setFont(font);
		graphics.drawString("hit space to confirm", 100, 100);
	    
		graphics.drawImage(startButtonImg, null, game.boundingBoxStartButton.x, game.boundingBoxStartButton.y);
		//graphics.setColor(Color.yellow);
		//graphics.draw(game.boundingBoxStartButton);
		
		if(game.hoveringStartButton())
		{
			graphics.setColor(Color.yellow);
			graphics.draw(game.boundingBoxStartButton);
			
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
