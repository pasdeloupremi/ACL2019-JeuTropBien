package jeu;

import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;


import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Monstre m=new Monstre("monstre48.png", 0, 0, null, null, 0, 0);
		float[] mcoord= {48,96};
		m.setCoordXY(mcoord);
		float[] hcoord= mcoord.clone();
		hcoord[0]+=48;
		hcoord[1]+=48;
		Heros h = new Heros(null, 0, 0, hcoord, null, 0, 0, 0);
		h.toucher();
		Carte c = new Carte("Carte.csv",10,8,48,"terrain48.png");
		System.out.println(c.donnees[5][5]);
		PacmanGame game = new PacmanGame();
		PacmanPainter painter = new PacmanPainter();
		PacmanController controller = new PacmanController();
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		Scanner sc = new Scanner(System.in);
			//-------------------
			//Main menu
			//-------------------
			MainMenu mainMenu = new MainMenu(); // game contenant le menu principal
			MainMenuPainter mainMenuPainter = new MainMenuPainter(mainMenu);
			GameEngineGraphical mainMenuEngine = new GameEngineGraphical(mainMenu,mainMenuPainter,controller);

			mainMenuEngine.run();
			//-------------------
			//Game
			//-------------------
			System.out.println("Demarrer partie");
			engine.run();
				
				
			//Partie Quentin Menu Principal <--
				
		}
	
	

	}
