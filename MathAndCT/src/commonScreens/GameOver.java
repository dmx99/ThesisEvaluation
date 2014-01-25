package commonScreens;

import grades.GradeEvaluator;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import utilCommon.Layout;

/**
 * This class contains the game over screen.
 * @author d.caballero and m.golajer
 */
public class GameOver extends Layout {

	protected Image okB, okHover, gameOver, dialog;
    protected Sound click;
	protected AngelCodeFont eveDialog, grade;
	protected boolean hoveredOK;
	
	public GameOver(int state){
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initLayout();
		
		okB = new Image("res/buttons/okB.png");
		okHover = new Image("res/buttons/ok.png");
		gameOver = new Image("res/images/gameOver/overRR.png");
		dialog = new Image("res/images/udialog.png");
		eveDialog = new AngelCodeFont("res/fonts/gameOver1.fnt", "res/fonts/gameOver1.png");
		grade = new AngelCodeFont("res/fonts/gameOver2.fnt", "res/fonts/gameOver2.png");
        
		click = new Sound("res/sounds/click2.ogg");

		hoveredOK = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		back.draw(0,0,850,500);
		
		if(hoveredOK == false) {
    		okB.draw(365, 420, 120, 30);
		} else {
    		okHover.draw(365, 420, 120, 30);
    	}
		eve.draw(700, 210, 110, 220);
		dialog.draw(440, 140, 290, 110);
		gameOver.draw(250, 5, 350, 150);
		
		text.drawString(80,160, "Easy Questions");
		text.drawString(360,160, Integer.toString(GradeEvaluator.grades.getEasyQuestions()));
		text.drawString(80,200, "Average Questions");
		text.drawString(360,200, Integer.toString(GradeEvaluator.grades.getAveQuestions()));
		text.drawString(80,240, "Hard Questions");
		text.drawString(360,240, Integer.toString(GradeEvaluator.grades.getHardQuestions()));
		text.drawString(80,280, "Missed Levels");
		text.drawString(360,280, Integer.toString(GradeEvaluator.grades.getMissedLevel()));
		text.drawString(80,320, "Overall Time");
		text.drawString(360,320, Integer.toString((int)GradeEvaluator.grades.getOverAllTime())); 
		g.setColor(Color.white);
	    g.setLineWidth(5);
		g.drawLine(80, 360, 420, 360); /*x & y of start point, x & y of end point*/ 
		text.drawString(80,370, "GRADE");
		text.drawString(360,370, Integer.toString(GradeEvaluator.grades.getGradeScore()));
		grade.drawString(420,360, GradeEvaluator.grades.getGrade(), Color.cyan);
		
		if(GradeEvaluator.grades.getGradeScore() > 18) {
			eveDialog.drawString(460,170, "Congratulations!", Color.pink);
		} else {
			eveDialog.drawString(460,170, "Better luck next time.", Color.pink);
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int x  = Mouse.getX(), y  = Mouse.getY(); 

		if((x >365 && x <485) && (y >50 && y <80)) { 
			hoveredOK = true;
			if(input.isMousePressed(GameConstants.leftClick))  {
				click.play();
				sbg.enterState(GameConstants.HOME, fadeOut, fadeIn);
			}
		} else {
			hoveredOK = false;
		}
	}

	@Override
	public int getID() {
		return -6;
	}

}
