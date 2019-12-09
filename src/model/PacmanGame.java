package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import engine.Cmd;
import engine.Game;
import jeu.Heros;
import jeu.Monstre;
import jeu.Personnage;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class PacmanGame implements Game {

	private boolean victoryFlag,gameOverFlag;
	private ArrayList<Monstre> listeMonstre;
	private Heros joueur;
	private PacmanPainter painter;
	
	
	/**
	 * constructeur avec fichier source pour le help
	 * 
	 */
	public PacmanGame(ArrayList<Monstre> listeMonstre, Heros joueur, PacmanPainter painter) {
		victoryFlag = false;
		gameOverFlag = false;
		this.listeMonstre = listeMonstre;
		this.joueur = joueur;
		this.painter = painter;
	}

	
	public void input(Cmd commande)
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
		case IDLE:
			Heros.Joueur.surPlace();
		default:
			break;
		}
	}
	
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
	
	/**
	 * faire evoluer le jeu suite a une commande
	 * 
	 * @param commande
	 */
	@Override
	public void evolve(Cmd commande) {

		//--------------------------
		//Mise à jour du joueur
		//--------------------------
		if(joueur.isAlive())//on ne met à jour que les monstres vivants 
		{
			input(commande);//gestion des commandes du joueurs
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
				m.deplacer();
			}
			else
			{
				//on efface les monstre morts de la liste
				//listeMonstre.remove(m);
			}
		}
		
		//victoire (tout les monstres sont morts)
		if(listeMonstre.size() == 0)
		{
			victoryFlag = true;
		}
		

		conditionsDeVictoireDefaite();
		
		
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {

		


		
		
		return false;
	}

}
