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

	public static int PVheros=300;
	public static int VolumeSon = 5;
	public static Clip musiquefond;
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
		reset();
		//CARTE

		Carte c = new Carte("bossroom.csv",48,"terrain48.png");

		//Carte c = new Carte("laby1.csv",48,"terrain48.png");
		//c = new Carte("Carte.csv",48,"terrain48.png");
		c = new Carte("CarteVitesse33x10.csv",48,"terrain48.png");
		
		
		//HERO
		int[] t2= {48,72};
		float[] hcoord= {48,0};
		Heros h = new Heros("joueur1", PVheros, 200, hcoord, hcoord, 10, 13, 50,t2,Personnage.listeMonstre,"heros72x48.png");
		//Carte.niveauSuivant();
		MajPersNiveau();
		
		
		//MONSTRE
		/*float[] mcoord= {1500,150};
		//MinoBoss m1 = new MinoBoss(mcoord.clone());
		 */

		
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
	
	public static void MajPersNiveau() {
		int t=Carte.taillecase;
		Heros h=Heros.Joueur;
		h.setPV(PVheros);
		if(Carte.getCarte().getNomFichier().equals("laby1.csv")) {
			h.setCoordXY(new float[] {5*t,25*t});
			//h.initCoord();
			Minotaure m = new Minotaure(new float[] {2*t,20*t});
			Minotaure m2 = new Minotaure(new float[] {14*t,3*t});
			Minotaure m3 = new Minotaure(new float[] {4*t,15*t});
			Fantome f = new Fantome(new float[] {0,0});
			MinoBoss mb = new MinoBoss(new float[] {9*t,5*t});
			}
		else if(Carte.getCarte().getNomFichier().equals("CarteVitesse33x10.csv")) {
			h.setCoordXY(new float[] {2*t,2*t});}
		else if (Carte.getCarte().getNomFichier().equals("bossroom.csv")) {
			Item.openingtime = 80;
			Item.trapdmgs = 5;
			h.setCoordXY(new float[] {6*t,0*t});
			Minotaure m = new Minotaure(new float[] {2*t,6*t});
			Minotaure m2 = new Minotaure(new float[] {15*t,6*t});
			//Minotaure m3 = new Minotaure(new float[] {2*t,7*t});
			//Minotaure m4 = new Minotaure(new float[] {15*t,7*t});
			Minotaure m5 = new Minotaure(new float[] {2*t,17*t});
			Minotaure m6 = new Minotaure(new float[] {2*t,21*t});
			Minotaure m7 = new Minotaure(new float[] {2*t,28*t});
			Fantome f = new Fantome(new float[] {0,0});
			MinoBoss mb = new MinoBoss(new float[] {22*t,6*t});
			MinoBoss mb2 = new MinoBoss(new float[] {9*t,5*t});
			MinoBoss mb3 = new MinoBoss(new float[] {34*t,20*t});
		}
		else {
			h.setCoordXY(new float[] {2*t,2*t});
			//h.initCoord();
			float[] fcoord= {155,90};
			float[] m4coord= {800,400};
			float[] m5coord= {1650,150};
			float[] m6coord= {1700,150};
			//Minotaure m4 = new Minotaure(m4coord);
			//Minotaure m5 = new Minotaure(m5coord);
			//Minotaure m6 = new Minotaure(m6coord);
			Fantome f1 = new Fantome(fcoord);
		}
	}
	
	public static void reset() {
		Carte.listeNiveaux.clear();
		Personnage.listeMonstre.clear();
		Personnage.Joueur=null;
	}
	
	public static void Update(Graphics2D crayon) {
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
	    	if(nom.equals("Dungeon1.wav")) {
	    		musiquefond=clip;
	    		musiquefond.loop(0);
	    	}
	    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    	//gainControl.setValue((1-volume)*(-10.0f));
	    	gainControl.setValue(volume*(4.0f)-40.0f);
			clip.start();
	    } catch (IllegalArgumentException e) {
	    	System.out.println("son trop fort/faible");
	    	if(Main.VolumeSon>10)Main.VolumeSon=10;
	    	if(Main.VolumeSon<-10)Main.VolumeSon=-10;
		} catch (LineUnavailableException | IOException e) {
			System.out.println("erreur son");
		} catch (UnsupportedAudioFileException e) {
			System.out.println("pas de fichier son");
		}
	}


}