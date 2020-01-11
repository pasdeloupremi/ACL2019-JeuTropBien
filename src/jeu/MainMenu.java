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
	private int buttonNumber, buttonNumberMainMenu ,buttonNumberSettings;
	private PacmanController controller;
	private Graphics2D graphics;
	
	private boolean pressedUP, pressedDOWN,pressedSPACE;
	
	public boolean flagSetting;

	
	
	
	
	public MainMenu(PacmanController controller)
	{
		quitFlag = false;

		cursorPos = 0;
		
		buttonNumberMainMenu = 3;
		buttonNumberSettings = 3;
		buttonNumber = buttonNumberMainMenu;
		
		pressedUP = false;
		pressedDOWN = false;
		pressedSPACE = false;
		this.controller = controller;
		controller.switchControllerType(true);
		flagSetting = false;
		

		
	}

	@Override
	public void evolve(Cmd userCmd) {
		
		
		if(flagSetting)
		{
			buttonNumber = buttonNumberSettings;
			
			//-------------------
			//validation du choix au clavier
			//--------------------
			if(userCmd==Cmd.SPACE)
			{
				if(!pressedSPACE)
				{
					pressedSPACE = true;
					switch (cursorPos) {
					case 0:
						flagSetting = false;
						break;
					case 1:
						Main.VolumeSon = Main.VolumeSon + 1;
						break;
					case 2:
						Main.VolumeSon = Main.VolumeSon - 1;
						break;

					default:
						break;
					}
				}
			}
			
			if(userCmd==Cmd.ReleaseSPACE)
			{
				pressedSPACE = false;
			}
		}
		
		else 
		{
			buttonNumber = buttonNumberMainMenu;
			
			
			//-------------------
			//validation du choix au clavier
			//--------------------
			if(userCmd==Cmd.SPACE)
			{
				if(!pressedSPACE)
				{
					pressedSPACE = true;
					
					switch (cursorPos) {
					case 0:
						quitFlag = true;
						break;
					case 1:
						flagSetting = true;
						break;
					case 2:
						System.exit(0);
						break;

					default:
						break;
					}
				}
				
				
			}
			else
			{
				pressedSPACE = false;
			}
		}
		
		
		//-------------------
		//deplacement du curseur sur les boutons
		//--------------------
		switch(userCmd) {
		case UP:
			if(!pressedUP)
			{
				pressedUP = true;
				if(cursorPos == 0) cursorPos=buttonNumber-1;
				else cursorPos--;
				

			}
			break;
		case DOWN:
			if(!pressedDOWN)
			{
				pressedDOWN = true;
				
				cursorPos++;
			}
			break;
		case SPACE:
			
		case ReleaseDOWN:
			pressedDOWN = false;
			break;
		case ReleaseUP:
			pressedUP = false;
			break;
		case ReleaseSPACE:
			pressedSPACE = false;
			
		default:
			break;
			
		}
		cursorPos = Math.abs(cursorPos%(buttonNumber));
	}
	
	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return 	quitFlag;
	}

}
