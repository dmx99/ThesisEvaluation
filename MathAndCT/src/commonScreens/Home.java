package commonScreens;

import grades.GradeClass;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import tutorials.TutorialClass;
import utilCommon.GameConstants;
import utilCommon.Layout;
import utilDatabase.GradeOperations;

/**
 * This class contains the home screen.
 * @author d.caballero and m.golajer
 */
public class Home extends Layout {

	protected Image game, game1, game3, game4, game2, close; 
	protected Image binaryNum, compEq, multNum, findPatterns;
	protected Image tutorial, grade, backHome, tutorialHover, gradeHover, backHomeHover;
    protected Sound click;
	protected String chooseGame;
	protected boolean clickedGrade, clickedTutorial, hoveredTutorial, hoveredGrade, hoveredBackHome;
	public static GradeClass gradeC = new GradeClass(); 
	public static TutorialClass tutC  = new TutorialClass();
	public static GradeOperations gradeOps  = new GradeOperations();
	
	public Home(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initLayout();
		
		game = new Image("res/images/logo2.png");
		close = new Image("res/buttons/close.png");
		chooseGame = "Choose Game :";

		click = new Sound("res/sounds/click2.ogg");
        
		game1 = new Image("res/images/game1.png");
		game2 = new Image("res/images/game2.png");
		game3 = new Image("res/images/game3.png");
		game4 = new Image("res/images/game4.png");
		
		binaryNum = new Image("res/buttons/binaryNumbersB.png");
		compEq = new Image("res/buttons/completeEquationB.png");
		multNum = new Image("res/buttons/multiplyingNumbersB.png");
		findPatterns = new Image("res/buttons/findingPatternsB.png");
		
		tutorial = new Image("res/buttons/viewTutorialsB.png");
		grade = new Image("res/buttons/viewGradesB.png");
		backHome = new Image("res/buttons/exitGameB.png");
		
		tutorialHover = new Image("res/buttons/viewTutorials.png");
		gradeHover = new Image("res/buttons/viewGrades.png");
		backHomeHover = new Image("res/buttons/exitGame.png");
		
		clickedGrade = false;
		clickedTutorial = false;
		hoveredTutorial = false;
		hoveredGrade = false;
		hoveredBackHome = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		back.draw(0,0,850,500);
		game.draw(250, 10, 380, 140);
		
		text.drawString(40, 130, chooseGame);
		
		text2.drawString(90, 170, GameConstants.BINGAME_NAME);
		text2.drawString(350, 170, GameConstants.COMPEQGAME_NAME);
		text2.drawString(70, 330, GameConstants.TABLEGAME_NAME);
		text2.drawString(360, 330, GameConstants.NXTNUMGAME_NAME);

		/*game screenshots*/
		game1.draw(100, 210, 140, 120);
		game2.draw(380, 210, 140, 120);
		game3.draw(100, 370, 140, 120);
		game4.draw(380, 370, 140, 120);
		
		
		if( hoveredBackHome == false) {
			backHome.draw(620, 350, 200,40);
		} else {
			backHomeHover.draw(620, 350, 200,40);
		}

		if( hoveredTutorial == false) {
			tutorial.draw(620, 400, 200,40);
		} else {
			tutorialHover.draw(620, 400, 200,40);
		}
		
		if( hoveredGrade == false) {
			grade.draw(620, 450, 200, 40);
		} else {
			gradeHover.draw(620, 450, 200, 40);
		}
		
		if(clickedTutorial == true) {
			gray.draw(0,0,850,500);
			pop.draw(230, 100, 400, 270);
			  
			text.drawString(320, 110, "View Tutorials For");
			binaryNum.draw(310, 150, 250, 40);
			compEq.draw(310, 200, 250, 40);
			multNum.draw(310, 250, 250, 40);
			findPatterns.draw(310, 300, 250, 40);
			 
			close.draw(590, 110, 30, 30);
		}		
		if(clickedGrade == true) {
			gray.draw(0,0,850,500);
			pop.draw(230, 100, 400, 270);
			
			text.drawString(320, 110, "View Grades For");
			binaryNum.draw(310, 150, 250, 40);
			compEq.draw(310, 200, 250, 40);
			multNum.draw(310, 250, 250, 40);
			findPatterns.draw(310, 300, 250, 40);
			 
			close.draw(590, 110, 30, 30);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int x = Mouse.getX(), y = Mouse.getY(); 
		 
		if(clickedTutorial == false && clickedGrade == false) {
			if((x>100 && x<240) && (y>170 && y<290)) {
				if(input.isMousePressed(GameConstants.leftClick)) {  
					click.play();
					tutC.setGameIDforTutorial(GameConstants.BINARYGAME_ID);
					tutC.setGameName(GameConstants.BINGAME_NAME);
					sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn); 
				}
			}
			else if((x>380 && x<540) && (y>170 && y<290) ) {
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					tutC.setGameIDforTutorial(GameConstants.COMPEQGAME_ID);
					tutC.setGameName(GameConstants.COMPEQGAME_NAME);
					sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn); 
				}
			}
			else if((x>100 && x<240) && (y>10 && y<130) ) {
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					tutC.setGameIDforTutorial(GameConstants.TABLEGAME_ID);
					tutC.setGameName(GameConstants.TABLEGAME_NAME);
					sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn);
				}
			}
			else if((x>380 && x<540) && (y>10 && y<130) ) {
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					tutC.setGameIDforTutorial(GameConstants.NXTNUMGAME_ID);
					tutC.setGameName(GameConstants.NXTNUMGAME_NAME);
					sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn);
				}
			}
			else if((x>620 && x<820) && (y>110 && y<150)) {
				hoveredBackHome = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					sbg.enterState(GameConstants.WELCOME, fadeOut, fadeIn);
				}
			}
			else if((x>620 && x<820) && (y>60 && y<100) ) { /*Checks if view tutorial is clicked*/
				hoveredTutorial = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedTutorial = true;
				}
			}
			else if((x>620 && x<820) && (y>10 && y<50) ) { /*Checks if view grade is clicked*/
				hoveredGrade = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedGrade = true;
				}
			}
			else {
				hoveredGrade =  false;
				hoveredTutorial = false;
				hoveredBackHome = false;
			}			
		}
		
		else { 
			if((x>590 && x<620) && (y>360 && y<390) ) { /*Checks if close button of view tutorial/grade is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedGrade = false;
					clickedTutorial = false;
				}
			}
			
			if(clickedGrade == true) { /*Popup for View Grades*/
				if((x>310 && x<560) && (y>310 && y<350) ) {
					if(input.isMousePressed(GameConstants.leftClick)) {
						click.play();
						gradeC.setGameIDforGrade(GameConstants.BINARYGAME_ID);
						gradeC.setGameName(GameConstants.BINGAME_NAME);
						sbg.enterState(GameConstants.GRADES, fadeOut, fadeIn);
						clickedGrade = false;
					}
				}
				else if((x>310 && x<560) && (y>260 && y<300) ) {
					if(input.isMousePressed(GameConstants.leftClick)) { 
						click.play();
						gradeC.setGameIDforGrade(GameConstants.COMPEQGAME_ID);
						gradeC.setGameName(GameConstants.COMPEQGAME_NAME);
						sbg.enterState(GameConstants.GRADES, fadeOut, fadeIn);
						clickedGrade = false;
					}
				}
				else if((x>310 && x<560) && (y>210 && y<250) ) {
					if(input.isMousePressed(GameConstants.leftClick)) { 
						click.play();
						gradeC.setGameIDforGrade(GameConstants.TABLEGAME_ID);
						gradeC.setGameName(GameConstants.TABLEGAME_NAME);
						sbg.enterState(GameConstants.GRADES, fadeOut, fadeIn);
						clickedGrade = false;
					}
				}
				else if((x>310 && x<560) && (y>160 && y<200) ) {
					if(input.isMousePressed(GameConstants.leftClick)) { 
						click.play();
						gradeC.setGameIDforGrade(GameConstants.NXTNUMGAME_ID);
						gradeC.setGameName(GameConstants.NXTNUMGAME_NAME);
						sbg.enterState(GameConstants.GRADES, fadeOut, fadeIn);
						clickedGrade = false;
					}
				}

			}
			
			if(clickedTutorial == true) { /*Popup for View Tutorial*/
				if((x>310 && x<560) && (y>310 && y<350) ) {
					if(input.isMousePressed(GameConstants.leftClick)) { 
						click.play();
						tutC.setGameIDforTutorial(GameConstants.BINARYGAME_ID);
						tutC.setGameName(GameConstants.BINGAME_NAME);
						sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn);
						clickedTutorial = false;
					}
				}
				else if((x>310 && x<560) && (y>260 && y<300) ) {
					if(input.isMousePressed(GameConstants.leftClick)) { 
						click.play();
						tutC.setGameIDforTutorial(GameConstants.COMPEQGAME_ID);
						tutC.setGameName(GameConstants.COMPEQGAME_NAME);
						sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn);
						clickedTutorial = false;
					}
				}
				else if((x>310 && x<560) && (y>210 && y<250) ) {
					if(input.isMousePressed(GameConstants.leftClick)) { 
						click.play();
						tutC.setGameIDforTutorial(GameConstants.TABLEGAME_ID);
						tutC.setGameName(GameConstants.TABLEGAME_NAME);
						sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn);
						clickedTutorial = false;
					}
				}
				else if((x>310 && x<560) && (y>160 && y<200) ) {
					if(input.isMousePressed(GameConstants.leftClick)) { 
						click.play();
						tutC.setGameIDforTutorial(GameConstants.NXTNUMGAME_ID);
						tutC.setGameName(GameConstants.NXTNUMGAME_NAME);
						sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn);
						clickedTutorial = false;
					}
				}
			}
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
