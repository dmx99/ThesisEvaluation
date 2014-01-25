package tableGame;

import grades.GradeClass;

import java.util.Random;

import org.newdawn.slick.*;

import utilCommon.GameConstants;
import utilCommon.Layout;

/**
 * This abstract class contains all objects unique only to the table game layout.
 * @author d.caballero and m.golajer
 */
public abstract class Table extends Layout {

	protected Image pic, backB, numbers[], deleteB, deleteHover, okB, okHover, help, ldialog; 
	protected String lessString, greaterString, blankString;
	
	protected boolean answerIsLess, answerIsGreater, blank;
	protected boolean hoveredOK, hoveredDelete, hoveredHelp;
	
	protected Random rand = new Random(); 
	protected GradeClass newGrade = new GradeClass();
	
	public void initTable() throws SlickException {
		initLayout();
		
		backB = new Image("res/buttons/back.png");
		help = new Image("res/buttons/help.png");
		ldialog = new Image("res/images/ldialogf.png");
		
		numbers = new Image[10];
		
		for(int i = 0; i < 10; i++) {
			numbers[i] = new Image("res/numbers/"+i+".png");
		}
		
		lessString = "Your answer is less than the correct number.";
		greaterString = "Your answer is greater than the correct number.";
		blankString = "You did not input any answer.";
		
		deleteB = new Image("res/buttons/deleteB.png"); 
		deleteHover = new Image("res/buttons/delete.png");
		
		submit = new Image("res/buttons/submitB.png");
		submitHover = new Image("res/buttons/submit.png");
		okB = new Image("res/buttons/okB.png");
		okHover = new Image("res/buttons/ok.png");
		
		newGrade.setGameIDforGrade(GameConstants.TABLEGAME_ID);
	}
}
