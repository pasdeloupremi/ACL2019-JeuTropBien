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

	public static int PVheros=1000;
	public static void main(String[] args) throws IOException, InterruptedException {
		
		while(true)//fait boucler le jeux et le menu principal
		{
			//j'aimerai que quand je relance le jeux, ça le reset
			Initialisation();
		}


		//MONSTRE
		
		/*///Je comprend pas à quoi ça sert, ca faisait des interractions cheloux mon code. ça m'empèche de reset le jeux 
		
		
		//init du monstre 1,2
		float[] mcoord= {55,150};
		//MinoBoss m1 = new MinoBoss(mcoord);
		float[] fcoord= {155,90};
		Minotaure m2 = new Minotaure(mcoord);
		Fantome f1 = new Fantome(fcoord);
		//il faudra gerer les collisions des monstres
		//HERO
		int[] t2= {48,72};
		float[] hcoord= {55,250};
		
		Heros h = new Heros("joueur1", PVheros, 20, hcoord, hcoord, 10, 13, 40,t2,Personnage.listeMonstre,"heros72x48.png");
		h.toucher();*/

	}
	public static void Initialisation() throws InterruptedException, IOException {
		//CARTE
		Carte c = new Carte("Carte.csv",48,"terrain48.png");
		//Carte.generer();
		
		
		//HERO
		int[] t2= {48,72};
		float[] hcoord= {0,0};
		Heros h = new Heros("joueur1", PVheros, 20, hcoord, hcoord, 10, 13, 40,t2,Personnage.listeMonstre,"heros72x48.png");
		
		//MONSTRE
		float[] mcoord= {1500,150};
		//MinoBoss m1 = new MinoBoss(mcoord.clone());
		//MinoBoss m2 = new MinoBoss(mcoord.clone());
		//MinoBoss m3 = new MinoBoss(mcoord.clone());
		float[] fcoord= {155,90};
		float[] m4coord= {80,200};
		float[] m5coord= {1650,150};
		float[] m6coord= {1700,150};
		Minotaure m4 = new Minotaure(m4coord);
		//Minotaure m5 = new Minotaure(m5coord);
		//Minotaure m6 = new Minotaure(m6coord);
		//Fantome f1 = new Fantome(fcoord);

		
		//-------------------
		//Game
		//-------------------
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
		System.out.println("Demarrer partie");
		engine.run();
		
		
		
	}
	
	public static void Update(BufferedImage im,Graphics2D crayon) {
		Carte.AffichageTerrain(crayon);
		Carte.AffichageDecors(crayon);
		Monstre.AffichageMonstre(crayon);
		Heros.AffichageHeros(crayon);
		Sort1Autoguidee.AffichageSorts(crayon);
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