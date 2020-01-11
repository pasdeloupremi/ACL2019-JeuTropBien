package jeu;

import engine.Cmd;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import engine.Game;
import model.PacmanController;

public class MainMenu implements Game {

	private boolean quitFlag;

	
	public int cursorPos;
	private int buttonNumber;
	private PacmanController controller;
	private Graphics2D graphics;
	
	private boolean pressedUP, pressedDOWN;
	public MainMenu(PacmanController controller)
	{
		quitFlag = false;

		cursorPos = 0;
		buttonNumber = 2;
		pressedUP = false;
		pressedDOWN = false;
		this.controller = controller;
		controller.switchControllerType(true);
		
	}

	@Override
	public void evolve(Cmd userCmd) {

		//-------------------
		//deplacement du curseur sur les boutons
		//--------------------
		switch(userCmd) {
		case UP:
			if(!pressedUP)
			{
				pressedUP = true;
				cursorPos--;
			}
			break;
		case DOWN:
			if(!pressedDOWN)
			{
				pressedDOWN = true;
				cursorPos++;
			}
			break;
		case ReleaseDOWN:
			pressedDOWN = false;
			break;
		case ReleaseUP:
			pressedUP = false;
			break;
		default:
			break;
			
		}
		
		cursorPos = Math.abs(cursorPos%buttonNumber);
		//-------------------
		//validation du choix au clavier
		//--------------------
		if(userCmd==Cmd.SPACE)
		{
			switch (cursorPos) {
			case 0:
				quitFlag = true;
				break;
			case 1:
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
