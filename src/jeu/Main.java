package jeu;

import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;


import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		//Monstre m=new Monstre();
		//Heros h = new Heros();
		//h.toucher();
		Carte c = new Carte("Carte.csv",10,8);
		System.out.println(c.donnees[5][5]);
		PacmanGame game = new PacmanGame("helpFilePacman.txt");
		PacmanPainter painter = new PacmanPainter(c);
		PacmanController controller = new PacmanController();
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		Scanner sc = new Scanner(System.in);
		System.out.println("Demarrer partie ?");{
			System.out.println("Oui : 0 // Non : 1");
			String str = sc.nextLine();
			if(Integer.parseInt(str)==0) {
				engine.run();
			}
		}


	}

}