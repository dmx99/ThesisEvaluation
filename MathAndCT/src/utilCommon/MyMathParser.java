package utilCommon;

import compEqGame.CompleteEquationGame;

/**
 * This class evaluates a user's given answer from the Complete the Equation game.
 * @author d.caballero and m.golajer
 */
public class MyMathParser {

	private int operands[];
	private int levelHolder;
	private String invalidOps[] = {"++", "+++", "++++", "++++", "--", "---", "----", "-----", "//", "///", "**", "***", "-*","*-"};
	
	public MyMathParser(String equation, int level) {
		levelHolder = level;
		 
		if(levelHolder == 2) {
			operands = new int[2]; 	
		} else {
			operands = new int[3];
		}
	}
	
	public int evaluateString(String equation) {
		int result = 0;
		char oper1;

		for(int i = 0; i < invalidOps.length; i++ ) {
			if(equation.contains(invalidOps[i]))
				return -1;
		}
		
		/*Check for improper operator places*/ 
		if(levelHolder == 1) { 
			if((equation.charAt(1) != '+' && equation.charAt(3) != '+')
				&& (equation.charAt(1) != '-' && equation.charAt(3) != '-')) {
				
				return -1;
			} 
		}
		
		else if(levelHolder == 2) { 
			if(CompleteEquationGame.divOp) {
				if(equation.charAt(2) != '/')
					return -1;
			} else {
 				if(equation.charAt(1) != '*')
					return -1;
				
			} 

		}
		
		else if(levelHolder == 3) {
			if(equation.charAt(1) == '*' && equation.charAt(3) == '*') {
				return -1;
			}
			
			if(equation.charAt(1) == '-' && equation.charAt(3) == '-') {
				return -1;
			}
		}
		/*Parse the equation*/
		if(levelHolder == 2) {  
			if(CompleteEquationGame.divOp) {
				operands[0] = Integer.parseInt(equation.substring(0,2)); /*Get 1st 2-digit operand*/ 
				operands[1] = Character.getNumericValue(equation.charAt(3)); 
				
				result = operands[0] / operands[1];
			} else {
				operands[0] = Character.getNumericValue(equation.charAt(0));
				operands[1] = Character.getNumericValue(equation.charAt(2));
				
				result = operands[0] * operands[1];
			}
		
		}
		
		else { /*For 2 operators*/
			operands[0] = Character.getNumericValue(equation.charAt(0));
			operands[1] = Character.getNumericValue(equation.charAt(2));
			operands[2] = Character.getNumericValue(equation.charAt(4));
			
			oper1 = equation.charAt(1);
		
			if(equation.contains("-") && equation.contains("*")) {
				if(oper1 == '*') {
					result = operands[0] * operands[1] - operands[2];
				} else {
					result = operands[0] - operands[1] * operands[2];
				}
			}
			else { 
				switch(oper1) {
					case '+':
						result = operands[0] + operands[1] + operands[2];
						break;
					case '-':
						result = operands[0] - operands[1] - operands[2];
						break; 
				}
			}
		}
		
		return result;
	}
}
