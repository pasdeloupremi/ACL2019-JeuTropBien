package jeu;

import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;


import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		int[] t1= {48,48};
		Monstre m=new Monstre("monstre48.png", 100, 5, null, null, 10, 30,t1);
		float[] mcoord= {48,96};
		m.setCoordXY(mcoord);
		m.setDirection(mcoord);
		float[] hcoord= mcoord.clone();
		hcoord[0]+=48;
		hcoord[1]+=48;
		int[] t2= {48,72};
		Heros h = new Heros("joueur1", 100, 20, hcoord, hcoord, 10, 13, 20,t2);
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
