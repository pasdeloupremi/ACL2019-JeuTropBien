package jeu;

import engine.Cmd;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import engine.Game;

public class MainMenu implements Game {

	private boolean quitFlag;
	Rectangle boundingBoxStartButton; 
	Rectangle boundingBoxQuitButton; 
	
	public int cursorPos;
	private int maxCursorPos;
	
	public MainMenu()
	{
		quitFlag = false;
		boundingBoxStartButton = new Rectangle(100,100,128,64);
		boundingBoxQuitButton = new Rectangle(100+200,100,128,64);
		cursorPos = 0;
		maxCursorPos = 1;
	}
	
	@Override
	public void evolve(Cmd userCmd) {
		// TODO Auto-generated method stub

		if(userCmd==Cmd.DOWN && cursorPos != 0)
		{
			
			cursorPos--;
		}

		if(userCmd==Cmd.UP && cursorPos < maxCursorPos)
		{System.out.println(cursorPos);
			cursorPos++;
		}
		

		if(userCmd==Cmd.SPACE)
		{
			switch (cursorPos) {
			case 0:
				System.out.println("StartGame");
				quitFlag = true;
				break;
			case 1:
				System.out.println("QuitGame");
				System.exit(0);
				break;

			default:
				break;
			}

		}

	}
	
	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return 	quitFlag;
	}

}
