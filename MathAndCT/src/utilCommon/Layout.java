package utilCommon;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * This abstract class contains all objects for overall game layout.
 * @author d.caballero and m.golajer
 */
public abstract class Layout extends BasicGameState {

	protected Image back, tin, eve, dialog, pop, gray;
	protected Image submit, menuB, backHomeB, resumeB, tutorialB;
	protected Image submitHover, menuHover, pauseHover, backHomeHover, resumeHover, tutorialHover;
	protected AngelCodeFont title, text, text2, number, bigNum;
	protected boolean hoveredSubmit, hoveredMenu, hoveredBackHome, hoveredResume, hoveredTutorial, clickedMenu;
	protected FadeOutTransition fadeOut = new FadeOutTransition(Color.black, 800);
	protected FadeInTransition fadeIn = new FadeInTransition(Color.white, 200);
	
	public void initLayout() throws SlickException {
		
		back = new Image("res/images/board.PNG");
		
		title = new AngelCodeFont("res/fonts/title.fnt", "res/fonts/title.png");
		text = new AngelCodeFont("res/fonts/bigText.fnt", "res/fonts/bigText.png");
		text2 = new AngelCodeFont("res/fonts/smallText.fnt", "res/fonts/smallText.png");
		number = new AngelCodeFont("res/fonts/number.fnt", "res/fonts/number.png");
		bigNum= new AngelCodeFont("res/fonts/bigNum.fnt", "res/fonts/bigNum.png");
		
		tin = new Image("res/images/tin.png");
		eve = new Image("res/images/eve.png");
		pop = new Image("res/images/wood1.png");
		gray = new Image("res/images/gray.png");
		
		menuB = new Image("res/buttons/menuB.png");
		backHomeB = new Image("res/buttons/backHomeB.png"); 
		resumeB = new Image("res/buttons/resumeB.png"); 
		tutorialB = new Image("res/buttons/viewTutorialsB.png");
		
		menuHover = new Image("res/buttons/menu.png");
		backHomeHover = new Image("res/buttons/backHome.png"); 
		resumeHover = new Image("res/buttons/resume.png"); 
		tutorialHover = new Image("res/buttons/viewTutorials.png");
		
		hoveredMenu = false; 
		hoveredSubmit = false;
		hoveredBackHome = false;
		hoveredResume = false;
		hoveredTutorial = false;
		clickedMenu = false;
	}
}
