package binaryGame;

import grades.GradeClass;

import java.util.Random;

import org.newdawn.slick.*;

import tutorials.TutorialClass;
import utilCommon.GameConstants;
import utilCommon.Layout;

/**
 * This abstract class contains all objects unique only to the binary game layout.
 * @author d.caballero and m.golajer
 */
public abstract class Binary extends Layout {

	protected Image zero, one, equals, backB, okB, okHover, help, ldialog; 
	protected String wrongString;
	protected int  binValue;
	protected boolean wrongAnswer;
	protected boolean hoveredOK, hoveredHelp;
	
	protected GradeClass newGrade = new GradeClass();
	protected TutorialClass viewTutorial = new TutorialClass();
	protected Random rand = new Random(); 
	
	public void initBinary() throws SlickException {
		initLayout();
		
		equals = new Image("res/operators/equals.png");
		backB = new Image("res/buttons/back.png");
		submit = new Image("res/buttons/submitB.png");
		submitHover = new Image("res/buttons/submit.png");
		okB = new Image("res/buttons/okB.png");
		okHover = new Image("res/buttons/ok.png");
		wrongString = "Your answer is wrong, please try again.";
		zero = new Image("res/numbers/0.png");
		one = new Image("res/numbers/1.png");
		help = new Image("res/buttons/help.png");
		ldialog = new Image("res/images/ldialogf.png");

		newGrade.setGameIDforGrade(GameConstants.BINARYGAME_ID);
	}
}
