package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

	/**
	 * constructeur avec fichier source pour le help
	 * 
	 */
	public PacmanGame() {
	}

	/**
	 * faire evoluer le jeu suite a une commande
	 * 
	 * @param commande
	 */
	@Override
	public void evolve(Cmd commande) {
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
		for(Monstre m:Personnage.listeMonstre) {
			m.deplacer();
		}
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		// le jeu n'est jamais fini
		return false;
	}

}
