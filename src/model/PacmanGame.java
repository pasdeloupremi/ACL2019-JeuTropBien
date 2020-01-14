package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import engine.Cmd;
import engine.Game;
import jeu.Carte;
import jeu.Fantome;
import jeu.Heros;
import jeu.Main;
import jeu.Monstre;
import jeu.Personnage;
import jeu.Sort1Autoguidee;
import jeu.Item;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class PacmanGame implements Game {


	//Partie jeu
	private boolean victoryFlag,gameOverFlag,pauseFlag,quitGameFlag;

	private ArrayList<Monstre> listeMonstre;
	private Heros joueur;
	private PacmanPainter painter;
	private PacmanController controller;
	Heros h = Personnage.Joueur;
	//Partie menu pause
	public int cursorPos;
	private int buttonNumber;
	private boolean pressedUP,pressedDOWN;
	private Item item = Item.getItem();


	//---------------------------
	//Constructeur
	//---------------------------
	public PacmanGame(ArrayList<Monstre> listeMonstre, Heros joueur, PacmanPainter painter, PacmanController controller) {
		//Partie jeu
		victoryFlag = false;
		gameOverFlag = false;
		quitGameFlag = false;
		pauseFlag = false;
		this.listeMonstre = listeMonstre;
		this.joueur = joueur;
		this.painter = painter;
		this.controller = controller;
		//Partie menu pause
		pauseFlag = false;
		pressedUP = false;
		pressedDOWN = false;
		cursorPos = 0;
		buttonNumber = 3;
	}

	//---------------------------
	//INPUTS
	//---------------------------
	public void inputJeux(Cmd commande)
	{
		//mise à jour du joueur ssi vivant
		if(joueur.isAlive())
		{
			switch(commande) {
			case UP:
			case LEFT:
			case RIGHT:
			case DOWN:
				Heros.deplacerjoueur(commande);
				break;
			case SPACE:
				Heros.attaquer();
				break;
			case SORT1:
				Sort1Autoguidee.lancersort1();
				break;
			case IDLE:
				Heros.Joueur.surPlace();
				break;
			default:
				break;
			}
		}

		switch(commande) {
		case ESC:
			if(!pauseFlag)
			{
				//met le jeu en pause
				pauseFlag = true;
				System.out.println("PAUSE");
			}
			break;
		default:
			break;
		}
	}

	public void inputPause(Cmd commande)
	{
		//-------------------
		//deplacement du curseur sur les boutons
		//--------------------
		switch(commande) {
		case UP:
			if(!pressedUP)
			{
				pressedUP = true;
				cursorPos--;
				Main.playSound("cursor.wav", Main.VolumeSon);
			}
			break;
		case DOWN:
			if(!pressedDOWN)
			{
				pressedDOWN = true;
				cursorPos++;
				Main.playSound("cursor.wav", Main.VolumeSon);
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

		cursorPos = Math.abs(cursorPos%buttonNumber);//on fait tourner les choix en boucle

		//-------------------
		//validation du choix au clavier
		//--------------------
		if(commande==Cmd.SPACE)
		{
			switch (cursorPos) {
			case 0:
				System.out.println("Reprise du jeu");
				Main.musiquefond.start();
				pauseFlag = false;
				break;
			case 1:
				System.out.println("Quitter le jeu");
				Main.musiquefond.stop();
				quitGameFlag = true;
				break;
			case 2:
				System.out.println("Retour Menu Principal");
				Main.musiquefond.stop();
				System.exit(0);
				break;

			default:
				break;
			}
		}
	}

	//---------------------------
	//Victoire et defaite
	//examine les flags de victoire et defaite et réagit de façon appropriee
	//---------------------------
	public void conditionsDeVictoireDefaite()
	{
		if(gameOverFlag == true && victoryFlag == true)
		{
			//objectif remplit, mais joueur mort
			//defaite ou victoire ?
			//j'ai mis defaite pour le moment
			painter.afficherDefaite(true);
			painter.afficherVictoire(false);
		}

		if(gameOverFlag == true)
		{
			painter.afficherDefaite(true);
			painter.afficherVictoire(false);	
			if(!Main.musiquefond.isActive()) {
				try {Thread.sleep(1000);} 
				catch (InterruptedException e) {System.out.println("fin jeu");}
				quitGameFlag=true;}
			else {
				Main.musiquefond.stop();
			}
		}

		if(victoryFlag == true)
		{
			painter.afficherDefaite(false);
			painter.afficherVictoire(true);
		}
	}



	@Override
	public boolean isFinished() {
		return quitGameFlag;
	}

	//---------------------------
	//UPDATE
	//---------------------------
	public void updateJeu(Cmd commande)
	{
		//--------------------------
		//Mise à jour du joueur
		//--------------------------
		if(joueur.isAlive())
		{

			//PIEGE
			if(joueur.contactCase(3))
			{
				item.Piege();
			}


			//POTION HP 

			if(joueur.contactCase(8)) {
				item.Heal();
			}


			//SPEED BOOST
			if(joueur.contactCase(4)) {
				item.Speedboost();
			}
			if (item.dureeitemspeed>50) {
				item.resetspeed();
			}
			else if (item.dureeitemspeed>0) {
				item.dureeitemspeed++;
			}

			//INTERRUPTEUR PORTE
			if(joueur.contactCase(5)) {
				item.interrupteur();
			}
			if(joueur.contactCase(16)) {
				item.interrupteur();
				item.openingtime = 500;
			}
			if(joueur.contactCase(14)) {
				item.interrupteur2();
				item.openingtime = 200;
			}
			if(joueur.contactCase(12)) {
				item.interrupteur3();
				item.openingtime = 200;
			}

			if (item.dureeouvertureporte>item.openingtime) {
				item.interrupteurreset();
			}
			else if (item.dureeouvertureporte>0) {
				item.dureeouvertureporte++;
			}

			//PORTE QUI OUVRE QUAND TOUT LES MONSTRES SONT MORTS
			if (listeMonstre.isEmpty()) {
				item.portemonstre();
			}
		}		
		else
		{
			//le joueur est mort, on passe en état gameover
			gameOverFlag = true;
		}


		//--------------------------
		//Mise à jour des monstres
		//--------------------------
		for(Monstre m:listeMonstre) {

			if(m.isAlive())//on ne met à jour que les monstres vivants 
			{
				m.deplacermonstre();
			}
			else
			{
				//on efface les monstre morts de la liste
				listeMonstre.remove(m);
			}
		}

		//--------------------------
		//Victoire
		//--------------------------
		if(joueur.contactCase(2)){
			if(Carte.listeNiveaux.size()<2) {
				if(victoryFlag==true ) {
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Main.musiquefond.stop();
					quitGameFlag = true;}
				victoryFlag=true;
			}
			else {
				Carte.niveauSuivant();	
			}

		}



	}

	//--------------------------
	//Fonction de Mise à jour Principale
	//
	//se scinde en deux partie: la partie jeu, et la partie menu
	//--------------------------
	@Override
	public void evolve(Cmd commande) {

		//on passe du controller menu au controller jeu suivant l'état du flag pause
		controller.switchControllerType(pauseFlag);

		//jeu
		if(!pauseFlag)
		{
			inputJeux(commande);//gestion des commandes du joueurs
			painter.menuPause(false);
			updateJeu(commande);

			conditionsDeVictoireDefaite();
		}
		//Menu de Pause
		else
		{
			inputPause(commande);//gestion des commandes du joueurs
			painter.setCurrentButton(cursorPos);
			painter.menuPause(true);
		}
	}

}
