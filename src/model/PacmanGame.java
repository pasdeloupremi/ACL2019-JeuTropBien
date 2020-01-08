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
	private float vitesseinitiale;
	private int dureeitemspeed=0;
	private int soins=50;
	private int dureeouvertureporte=0;
	private ArrayList<int[]> memoirecases = new ArrayList<int[]>();
	private int [] memoirecase= new int[2];
	Heros h = Personnage.Joueur;
	//Partie menu pause
	public int cursorPos;
	private int buttonNumber;
	private boolean pressedUP,pressedDOWN;
	private Carte memoire = Carte.getCarte();


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
		buttonNumber = 2;
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
			}
			break;
		case DOWN:
			if(!pressedDOWN)
			{
				pressedDOWN = true;
				cursorPos++;
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
				pauseFlag = false;
				break;
			case 1:
				System.out.println("Quitter le jeu");
				quitGameFlag = true;
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
		if(joueur.isAlive())//on ne met à jour que les monstres vivants 
		{

			//PIEGE
			if(joueur.contactCase(3))
			{
				joueur.setPV(joueur.getPV()-2);
			}

			
			//POTION HP 
			
			if(joueur.contactCase(8)) {
				int test=0;
				
				if (joueur.getPV()==Main.PVheros) {
					test=0;
				}
				else if (joueur.getPV()+soins>Main.PVheros) {
					joueur.setPV(Main.PVheros);
					test=1;
				}
				else  {
					joueur.setPV(joueur.getPV()+soins);
					test=1;
				}
				if (test==1) {
					int[][] tab=Carte.getCarte().donnees;
					int t=Carte.taillecase;
					int casex1=(int) joueur.getSeuilImg()[0]/t;
					int casex2=(int) joueur.getSeuilImg()[1]/t;
					int casey1=(int) joueur.getSeuilImg()[2]/t;
					int casey2=(int) joueur.getSeuilImg()[3]/t;
					if (tab[casex1][casey1]==8) {
						tab[casex1][casey1]=0;
					}
					else if (tab[casex2][casey1]==8) {
						tab[casex2][casey1]=0;
						}
					else if (tab[casex1][casey2]==8) {
						tab[casex1][casey2]=0;
						}
					else if (tab[casex2][casey2]==8) {
						tab[casex2][casey2]=0;
						}
					test=0;
				}
				
			}
			

			//SPEED BOOST
			if((joueur.contactCase(4)) &&(dureeitemspeed==0)) {
				vitesseinitiale=joueur.getVitesse();
				joueur.setVitesse(joueur.getVitesse()+5);
				dureeitemspeed=1;
			}
			if (dureeitemspeed>50) {
				dureeitemspeed=0;
				joueur.setVitesse(vitesseinitiale);
			}
			else if (dureeitemspeed>0) {
				dureeitemspeed++;
			}

			//INTERRUPTEUR PORTE
			if(joueur.contactCase(5)) {
				dureeouvertureporte=1;
				for (int i=0;i<memoire.donnees.length;i++) {
					for (int j=0;j<memoire.donnees[0].length;j++) {
						if (memoire.donnees[i][j]==6) {
							memoirecase = new int[2];
							memoirecase[0]= i;
							memoirecase[1]= j;
							memoirecases.add(memoirecase);
							memoire.donnees[i][j]=0;
						}
					}
				}
			}

			if (dureeouvertureporte>30) {
				boolean test=false;
				int t=Carte.taillecase;
				int casex1=(int) h.getSeuilImg()[0]/t;
				int casex2=(int) h.getSeuilImg()[1]/t;
				int casey1=(int) h.getSeuilImg()[2]/t;
				int casey2=(int) h.getSeuilImg()[3]/t;
				for (int j=0;j<memoirecases.size();j++) {
					try {
						if ((casex1==memoirecases.get(j)[0]) && (casey1==memoirecases.get(j)[1])) {
							test=true;
						}
						else if ((casex1==memoirecases.get(j)[0]) && (casey2==memoirecases.get(j)[1])){
							test=true;
						}
						else if ((casex2==memoirecases.get(j)[0]) && (casey1==memoirecases.get(j)[1])) {
							test=true;
						}
						else if ((casex2==memoirecases.get(j)[0]) && (casey2==memoirecases.get(j)[1])) {
							test=true;
						}
						else {
							test=false;
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("ERREUR DIRECTION MONSTRE");
					}
					if (!test) {
						
						memoire.donnees[memoirecases.get(j)[0]][memoirecases.get(j)[1]]=6;
					}
					if (memoirecases.isEmpty()) {
						dureeouvertureporte=0;
					}
				}

			}
			else if (dureeouvertureporte>0) {
				dureeouvertureporte++;
			}

			//PORTE QUI OUVRE QUAND TOUT LES MONSTRES SONT MORTS
			if (listeMonstre.isEmpty()) {
				for (int i=0;i<memoire.donnees.length;i++) {
					for (int j=0;j<memoire.donnees[0].length;j++) {
						if (memoire.donnees[i][j]==7) {
							memoire.donnees[i][j]=0;
						}
					}
				}
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
		if(joueur.contactCase(2))
		{	
			if(victoryFlag==true) {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				quitGameFlag = true;
			}
			victoryFlag = true;

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
