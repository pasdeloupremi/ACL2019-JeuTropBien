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
	
	public MainMenu()
	{
		quitFlag = false;
		boundingBoxStartButton = new Rectangle(100,100,128,64);
	}
	
	@Override
	public void evolve(Cmd userCmd) {
		// TODO Auto-generated method stub
		
		
		//System.out.println("CLICK");
		// && userCmd==Cmd.LEFTCLICK
		if(hoveringStartButton() && userCmd==Cmd.SPACE)
		{
			System.out.println("StartGame");
			quitFlag = true;
		}
		
		
	}

	public boolean hoveringStartButton()
	{
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		System.out.println(mousePos.toString());
		if(boundingBoxStartButton.contains(mousePos))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return 	quitFlag;
	}

}
