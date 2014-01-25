package nextNumGame;

import grades.GradeClass;

import java.util.Random;

import org.newdawn.slick.*;

import utilCommon.GameConstants;
import utilCommon.Layout;

/**
 * This abstract class contains all objects unique only to the next number game layout.
 * @author d.caballero and m.golajer
 */
public abstract class NextNumber extends Layout {

	protected Image backB, okB, okHover, deleteB, deleteHover, help, ldialog; 
	protected String lessString, greaterString, blankString;
	protected Image numBlocks[];
	protected AngelCodeFont opsNNums;
	protected boolean answerIsLess, answerIsGreater, blank;
	protected boolean hoveredOK, hoveredDelete, hoveredHelp;
	
	protected Random rand = new Random(); 
	protected GradeClass newGrade = new GradeClass();
	
	public void initNextNumber() throws SlickException {
		initLayout();
		
		opsNNums = new AngelCodeFont("res/fonts/operatorsAndNumbers.fnt", "res/fonts/operatorsAndNumbers.png");
		backB = new Image("res/buttons/back.png");
		help = new Image("res/buttons/help.png");
		ldialog = new Image("res/images/ldialogf.png");
		
		numBlocks = new Image[10];
		
		for(int i = 0; i < 10; i++) {
			numBlocks[i] = new Image("res/numbers/"+i+".png");
		}
		
		lessString = "Your answer is less than the correct answer.";
		greaterString = "Your answer is greater than the correct answer.";
		blankString = "You did not input any number.";
		
		submit = new Image("res/buttons/submitB.png");
		submitHover = new Image("res/buttons/submit.png");
		okB = new Image("res/buttons/okB.png");
		okHover = new Image("res/buttons/ok.png");
		deleteB = new Image("res/buttons/deleteB.png");
		deleteHover = new Image("res/buttons/delete.png");
		
		newGrade.setGameIDforGrade(GameConstants.NXTNUMGAME_ID);
	}
}
