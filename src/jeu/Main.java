package jeu;

import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;


import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Monstre m=new Monstre(new float[2], "monstre48.png");
		float[] mcoord= {48,96};
		m.setCoordXY(mcoord);
		float[] hcoord= mcoord.clone();
		hcoord[0]+=48;
		hcoord[1]+=48;
		Heros h = new Heros(hcoord, null);
		h.toucher();
		Carte c = new Carte("Carte.csv",10,8,48,"terrain48.png");
		System.out.println(c.donnees[5][5]);
		PacmanGame game = new PacmanGame();
		PacmanPainter painter = new PacmanPainter();
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