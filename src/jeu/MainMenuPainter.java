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
	private Graphics2D graphics;
	
	
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
	
	@Override
	public void draw(BufferedImage image) {
		graphics = (Graphics2D) image.getGraphics();
		
		//background noir
		Rectangle background =new Rectangle(0,0,10000,10000);
		graphics.setColor(Color.black);
		graphics.fill(background);
		
		//affichage du menu
		Rectangle R0 = new Rectangle(positionStartButton.x,positionStartButton.y,128,64);
		Rectangle R1 = new Rectangle(positionQuitButton.x,positionQuitButton.y,128,64);

		drawButton(R0,"START");
		drawButton(R1,"QUIT");
		
		//graphics.drawImage(startButtonImg, null,positionStartButton.x,positionStartButton.y);
		//graphics.drawImage(quitButtonImg, null, positionQuitButton.x,positionQuitButton.y);

		//effet de surbrillance sur la selection
		
		graphics.setColor(Color.white);
		switch (game.cursorPos) {
			case 0:
				drawButtonHighLight(R0);
				break;
			case 1:
				drawButtonHighLight(R1);
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
