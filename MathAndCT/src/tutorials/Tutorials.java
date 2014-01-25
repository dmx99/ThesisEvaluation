package tutorials;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import utilCommon.Layout;
import commonScreens.Home;

/**
 * This class contains the tutorials screen.
 * @author d.caballero and m.golajer
 */
public class Tutorials extends Layout {

	protected Image skip, skipHover, prev, prevHover, next, nextHover, play, playHover, back, backHover; 
	protected Image imageTutorialBinary[], imageTutorialCompEq[], imageTutorialTable[], imageTutorialNxtNum[];
	protected Sound click;
	protected int i, gameNumber, page, maxPage, maxPageHolder; 
	protected String gameName;
	protected boolean hoveredSkip, hoveredPlay, hoveredBack, hoveredPrev, hoveredNext;
	
	public Tutorials(int state) { 
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initLayout();
		
		skip = new Image("res/buttons/skipB.png");
		skipHover = new Image("res/buttons/skip.png");
		
		prev = new Image("res/buttons/back.png");
		prevHover = new Image("res/buttons/backB.png");
		
		next = new Image("res/buttons/forward.png");
		nextHover = new Image("res/buttons/forwardB.png");
		
		play = new Image("res/buttons/playB.png"); 
		playHover = new Image("res/buttons/play.png"); 
		
		back = new Image("res/buttons/backHomeB.png"); 
		backHover = new Image("res/buttons/backHome.png");

        click = new Sound("res/sounds/click2.ogg");
        
		maxPage = 10;
		imageTutorialBinary = new Image[maxPage];
		for (i = 0; i < maxPage; i++) {
			imageTutorialBinary[i] = new Image("res/images/bin/bin"+i+".png");
		}
		
		maxPage = 10;	
		imageTutorialCompEq = new Image[maxPage];
		for (i = 0; i < maxPage; i++) {
			imageTutorialCompEq[i]	= new Image("res/images/compEq/eq"+i+".png");
		}
			
		maxPage = 10;
		imageTutorialTable = new Image[maxPage];
		for (i = 0; i < maxPage; i++) {
			imageTutorialTable[i]	= new Image("res/images/table/tab"+i+".png");
		}		

		maxPage = 11;
		imageTutorialNxtNum = new Image[maxPage];
		for (i = 0; i < maxPage; i++) {
			imageTutorialNxtNum[i] = new Image("res/images/fin/fin"+i+".png");
		}

		hoveredSkip = false;
		hoveredPlay = false;
		hoveredBack = false;
		hoveredPrev = false;
		hoveredNext = false;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		page = 0;
		gameName = Home.tutC.getGameName();
		gameNumber = Home.tutC.getGameIDforTutorial();
		
		maxPageHolder = (gameNumber == 4)? 11 : 10;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		text.drawString(100, 10, "Tutorial for "+gameName);
		
		if(page < maxPageHolder-1) {
			if(hoveredSkip == false) {
				skip.draw(350, 445, 150, 40); 
			} else {
				skipHover.draw(350, 445, 150, 40); 
			}
		}

		/*This part chooses which images to render depending on chosen tutorial. */
		switch (gameNumber) {
			case GameConstants.BINARYGAME_ID:
				imageTutorialBinary[page].draw(50,50,750,380);
				break;
	
			case GameConstants.COMPEQGAME_ID:
				imageTutorialCompEq[page].draw(50,50,750,380);
				break;
			case GameConstants.TABLEGAME_ID:
				imageTutorialTable[page].draw(50,50,750,380);
				break;
			case GameConstants.NXTNUMGAME_ID:
				imageTutorialNxtNum[page].draw(50,50,750,380);
				break;
			default:
				break;
		}
		
		
		if(page > 0) {
			if(hoveredPrev == false) {
				prev.draw(20, 440, 100, 50);
			} else {
				prevHover.draw(20, 440, 100, 50);
			}
		} 
		if(page < maxPageHolder-1) {
			if(hoveredNext == false) {
				next.draw(730, 440, 100, 50);
			} else {
				nextHover.draw(730, 440, 100, 50);
			}
			
		} else {
			
			if(hoveredPlay == false) {
				play.draw(350, 445, 150, 40);
			} else {
				playHover.draw(350, 445, 150, 40);
			}
			
			if(hoveredBack == false) {
				back.draw(680, 445, 150, 40);
			} else {
				backHover.draw(680, 445, 150, 40);
			}
			
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX(), ypos = Mouse.getY(); 

		if(page < maxPageHolder-1) {
			if((xpos > 350 && xpos < 500) && (ypos > 15 && ypos < 55)) { /*Checks if skip is clicked*/
				hoveredSkip = true;
				if(input.isMousePressed(GameConstants.leftClick)) {  
					click.play();
					sbg.enterState(gameNumber, fadeOut, fadeIn);  
				}
			}
			else if((xpos > 20 && xpos < 120) && (ypos > 10 && ypos < 60)) { /*Checks if prev arrow is clicked*/
				hoveredPrev = true;
				if(input.isMousePressed(GameConstants.leftClick)) { 
					if(page > 0) {
						click.play();
						page--;
					}
				}
			}
			else if((xpos > 730 && xpos < 830) && (ypos >10 && ypos < 60)) { /*Checks if next arrow is clicked*/
				hoveredNext = true;
				if(input.isMousePressed(GameConstants.leftClick)) {  
					if(page < maxPageHolder-1) {	
						click.play();
						page++;
					}
				}
			}
			else {
				hoveredSkip = false;
				hoveredPrev = false;
				hoveredNext = false;
			}
		}
		
		
		if(page >= maxPageHolder-1) {  
			if((xpos > 350 && xpos < 500) && (ypos >15 && ypos < 55)) { /*Checks if play game is clicked*/
				hoveredPlay = true;
				if(input.isMousePressed(GameConstants.leftClick)) {  
					click.play();
					sbg.enterState(gameNumber, fadeOut, fadeIn);
				}
			}
			else if((xpos > 680 && xpos < 830) && (ypos > 15 && ypos < 55)) { /*Checks if back to home is clicked*/
				hoveredBack = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					sbg.enterState(GameConstants.HOME, fadeOut, fadeIn);
				}
			}
			else {
				hoveredPlay = false;
				hoveredBack = false;
			}
		}	
		
	}

	@Override
	public int getID() {
		return -4;
	}
	
}
