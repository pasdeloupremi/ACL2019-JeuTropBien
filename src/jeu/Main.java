package jeu;

import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		//MONSTRE
		
		//init du monstre 1,2
		float[] mcoord= {48,96};
		MinoBoss m1 = new MinoBoss(mcoord);
		Minotaure m2 = new Minotaure(mcoord);
		//il faudra gérer les collisions des monstres
		//HERO
		int[] t2= {48,72};
		float[] hcoord= mcoord.clone();
		hcoord[0]+=96;
		hcoord[1]+=96;
		Heros h = new Heros("joueur1", 100, 20, hcoord, hcoord, 10, 13, 40,t2,Personnage.listeMonstre,"heros72x48.png");
		h.toucher();

		//CARTE
		Carte c = new Carte("Carte.csv",10,8,48,"terrain48.png");
		System.out.println(c.donnees[5][5]);

		
		PacmanPainter painter = new PacmanPainter();
		PacmanGame game = new PacmanGame(Personnage.listeMonstre,h,painter);
		PacmanController controller = new PacmanController();
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		
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
