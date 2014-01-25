package compEqGame;

import grades.GradeClass;

import java.util.Random;

import org.newdawn.slick.*;

import utilCommon.GameConstants;
import utilCommon.Layout;
import utilCommon.MyMathParser;

/**
 * This abstract class contains all objects unique only to the complete equation game layout.
 * @author d.caballero and m.golajer
 */
public abstract class CompleteEquation extends Layout {

	protected Image backB, okB, okHover, deleteB, deleteHover;
	protected Image numBlocks[], equals, plus, minus, slash, mult; 
	protected String lackingString, wrongAnswerString;
	protected AngelCodeFont opsNNums;
	protected boolean lacking, wrongAnswer;
	protected boolean hoveredOK, hoveredDelete;
	
	protected MyMathParser m;
	protected Random rand = new Random(); 
	protected GradeClass newGrade = new GradeClass();
	
	public void initCompleteEquation() throws SlickException {
		initLayout();
		
		opsNNums = new AngelCodeFont("res/fonts/operatorsAndNumbers.fnt", "res/fonts/operatorsAndNumbers.png");
		
		equals = new Image("res/operators/equals.png");
		plus = new Image("res/operators/plus.png");
		minus = new Image("res/operators/minus.png");
		slash = new Image("res/operators/divide.png");
		mult =  new Image("res/operators/multiply.png");
		backB = new Image("res/buttons/back.png");
		deleteB =  new Image("res/buttons/deleteB.png"); 
		deleteHover =  new Image("res/buttons/delete.png");
		dialog = new Image("res/images/ddialog.png");
		numBlocks = new Image[10];
		
		for(int i = 2; i < 9; i++) {
			numBlocks[i] = new Image("res/numbers/"+i+".png");
		}
		
		lackingString = "Your number of operands or operators is lacking.";
		wrongAnswerString = "Your equation is wrong."; 
		
		submit = new Image("res/buttons/submitB.png");
		submitHover = new Image("res/buttons/submit.png");
		okB = new Image("res/buttons/okB.png");
		okHover = new Image("res/buttons/ok.png");
		
		newGrade.setGameIDforGrade(GameConstants.COMPEQGAME_ID);
	}
}
