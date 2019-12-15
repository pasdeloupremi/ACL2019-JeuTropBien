package jeu;

import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		//MONSTRE
		
		//init du monstre 1,2
		float[] mcoord= {48,96};
		//MinoBoss m1 = new MinoBoss(mcoord);
		Minotaure m2 = new Minotaure(mcoord);
		//il faudra gerer les collisions des monstres
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
		Carte.generer();
		
		PacmanPainter painter = new PacmanPainter();
		PacmanController controller = new PacmanController();
		PacmanGame game = new PacmanGame(Personnage.listeMonstre,h,painter,controller);
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		
		//-------------------
		//Main menu
		//-------------------
		MainMenu mainMenu = new MainMenu(controller); // game contenant le menu principal
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
	
	public static void Update(BufferedImage im,Graphics2D crayon) {
		Carte.AffichageTerrain(crayon);
		Monstre.AffichageMonstre(crayon);
		Heros.AffichageHeros(crayon);
		Carte.AffichageDecors(crayon);
	}

	public static void playSound(String nom, int volume) {
		File f = new File("./"+nom);
	    try {
	    	AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
	    	Clip clip = AudioSystem.getClip();
	    	clip.open(audioIn);
	    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    	gainControl.setValue((1-volume)*(-10.0f));
			clip.start();
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
