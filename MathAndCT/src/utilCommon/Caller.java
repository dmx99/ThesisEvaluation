package utilCommon;

import grades.Grades;
import nextNumGame.NextNumberGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import tableGame.TableGame;
import tutorials.Tutorials;
import commonScreens.*;
import compEqGame.CompleteEquationGame;
import binaryGame.BinaryGame;

/**
 * This class is where all game states are initialized and contains the main function.
 * @author d.caballero and m.golajer
 */
public class Caller extends StateBasedGame {

	public Caller(String gamename) {
		super(gamename);
		
		this.addState(new SplashScreen(GameConstants.SPLASH));
 		this.addState(new Welcome(GameConstants.WELCOME)); 
 		this.addState(new Home(GameConstants.HOME));
 		this.addState(new Grades(GameConstants.GRADES)); 
 		this.addState(new Tutorials(GameConstants.TUTORIALS));
 		this.addState(new GameOver(GameConstants.OVER));
 		
 		this.addState(new BinaryGame(GameConstants.BINARYGAME_ID));
 		this.addState(new CompleteEquationGame(GameConstants.COMPEQGAME_ID));
 		this.addState(new TableGame(GameConstants.TABLEGAME_ID));
 		this.addState(new NextNumberGame(GameConstants.NXTNUMGAME_ID));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		this.getState(GameConstants.SPLASH).init(gc, this);
		this.getState(GameConstants.WELCOME).init(gc, this);
		this.getState(GameConstants.HOME).init(gc, this);
		this.getState(GameConstants.GRADES).init(gc, this);
		this.getState(GameConstants.TUTORIALS).init(gc, this);
		this.getState(GameConstants.OVER).init(gc, this);
		
		this.getState(GameConstants.BINARYGAME_ID).init(gc, this);
		this.getState(GameConstants.COMPEQGAME_ID).init(gc, this);
		this.getState(GameConstants.TABLEGAME_ID).init(gc, this);
		this.getState(GameConstants.NXTNUMGAME_ID).init(gc, this);
		
		/*First state to enter */
		this.enterState(GameConstants.SPLASH); 
	}
	
	public static void main(String[] args) {
        
		AppGameContainer appgc;
		
		try{
 			appgc = new AppGameContainer(new Caller(GameConstants.GAMENAME));
 			appgc.setShowFPS(false); 
			appgc.setDisplayMode(850, 500, false); /*Not full screen */
			appgc.start(); /*This method creates the window*/
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

}
