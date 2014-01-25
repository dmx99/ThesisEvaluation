package compEqGame;

import grades.GradeDetails;
import grades.GradeEvaluator;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import utilCommon.MyMathParser;
import commonScreens.Home;

/**
 * This class contains the whole logic for the complete equation game.
 * @author d.caballero and m.golajer
 */
public class CompleteEquationGame extends CompleteEquation {

	protected int levelHolder = GameConstants.EASY_LEVEL;
	protected int questions = 1;
	protected int easyQuestions = 4, aveQuestions = 4, hardQuestions = 4;
	protected long startTime=0, stopTime=0, elapsedTime=0, newElapsedTime=0, newStartTime=0; 
	protected int overallDelta = 0;
	protected long lastRecordTime=0, lastRecordTimeInSystem=0, computedDelta=0, pausedTime=0, overallPause=0;
	
	protected int missedLevel = 0;
	protected int questionsCounter = 4, qHolder;
	protected boolean firstEnter = true, startOfTime = true, isPaused = false;
	
	protected String lackingString2, lackingString3;
	protected StringBuilder stringAnswer;
	protected int usersAns, correctAns, stringLength;
	
	protected int  sum, difference, lev1Op;
	protected int sumOp1, sumOp2, sumOp3, diffOp1, diffOp2, diffOp3;
	
	protected int  product, quotient, lev2Op;
	protected int prodOp1, prodOp2, dividend, number;
	public static boolean divOp = false;

	protected int equation;
	protected int operand1, operand2, operand3;

	protected Sound blockClick, click, fail;
	
	public CompleteEquationGame(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initCompleteEquation();
		this.reinit();
		
		blockClick = new Sound("res/sounds/click1.ogg");
		click = new Sound("res/sounds/click2.ogg");
		fail = new Sound("res/sounds/fail.ogg");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		back.draw(0,0,850,500);
		title.drawString(160, 35, GameConstants.COMPEQGAME_NAME);
		
		/*These parts implement the timer function */
		if(startOfTime) {
			startTime = (Sys.getTime() / Sys.getTimerResolution()); 
			lastRecordTime = startTime;
			startOfTime = false;
		}
			
		if(!isPaused) {
			newElapsedTime = ((Sys.getTime() / Sys.getTimerResolution())  - startTime) - newStartTime;
		 	text2.drawString(630, 120, "Elapsed Time: " + newElapsedTime );
		} else {
			text2.drawString(630, 120, "Elapsed Time: " + newElapsedTime );
		}
		
		if(hoveredSubmit == false) {
			submit.draw(320, 295, 200, 40);
		} else {
			submitHover.draw(320, 295, 200, 40);
		}
		if(hoveredMenu == false) {
			menuB.draw(15, 430, 150, 40);
		} else {
			menuHover.draw(15, 430, 150, 40);
		}
				
		tin.draw(700, 320, 110, 160);
		text2.drawString(350, 410, lackingString2);
		text2.drawString(350, 430, lackingString3);
		dialog.draw(300, 360, 400, 120);
		
		numBlocks[2].draw(15,100,54,50);
		numBlocks[3].draw(75,100,54,50);
		numBlocks[4].draw(135,100,54,50);
		numBlocks[5].draw(15,160,54,50);
		numBlocks[6].draw(75,160,54,50);
		numBlocks[7].draw(135,160,54,50);
		numBlocks[8].draw(15,220,54,50);
		
		/*These parts determine the level and what to render per level */
		if(levelHolder == GameConstants.EASY_LEVEL) {
			if(lev1Op == GameConstants.ADDITION) {
				plus.draw(75, 220, 54, 50);
			} else {
				minus.draw(75, 220, 54, 50);
			}
		}
		
		if(levelHolder == GameConstants.AVE_LEVEL) {
			if(lev2Op == GameConstants.MULTIPLICATION) {
				mult.draw(75, 220, 54, 50);
			} else {
				slash.draw(75, 220, 54, 50);
			}
		}
		
		if(levelHolder == GameConstants.HARD_LEVEL) {
			mult.draw(75, 220, 54, 50);
			minus.draw(135, 220, 54, 50);
		}
		
		
		if(hoveredDelete == false) {
			deleteB.draw(25, 280, 150, 40);
		} else {
			deleteHover.draw(25, 280, 150, 40);
		}
		
		opsNNums.drawString(250, 150, stringAnswer.toString());
		
		text.drawString(405, 180, " is equal to " + correctAns);
		
		if(lacking == true) {
	    	pop.draw(160, 300, 570, 105);
	    	text2.drawString(180, 310,lackingString);
	    	
	    	if(hoveredOK == false) {
	    		okB.draw(390, 350, 120, 30);
			} else {
	    		okHover.draw(390, 350, 120, 30);
	    	}
	    }
		
		if(wrongAnswer == true) {
	    	pop.draw(260, 300, 340, 100);
	    	text2.drawString(305, 310,wrongAnswerString);
	    	if(hoveredOK == false) {	
	    		okB.draw(390, 350, 120, 30);
			} else {
	    		okHover.draw(390, 350, 120, 30);
	    	}
	    }
		
		if(clickedMenu == true) {
	    	gray.draw(0,0,850,500);
	    	pop.draw(300, 100, 250, 300);
	    	
	    	if(hoveredBackHome == false) {
				backHomeB.draw(350, 150, 150, 40);
			} else {
				backHomeHover.draw(350, 150, 150, 40);
			}
			if(hoveredResume == false) {
				resumeB.draw(350, 210, 150, 40);
			} else {
				resumeHover.draw(350, 210, 150, 40);
			}
			if(hoveredTutorial == false) {
				tutorialB.draw(350, 270, 150, 40);
			} else {
				tutorialHover.draw(350, 270, 150, 40);
			}
	    }
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int x1  = Mouse.getX(), y1  = Mouse.getY(); 
		overallDelta +=delta;
		
		if(lacking == false && wrongAnswer == false && clickedMenu == false) {
			if((x1 >15 && x1 <165) && (y1 >30 && y1 <70)) { /*Checks if menu is clicked*/
				hoveredMenu = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedMenu = true;
					this.pause();
				}
			}
			
			else if((x1 > 15 && x1 < 69) && (y1 > 350 && y1 < 400)) { /*Checks if block2 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {   
					blockClick.play();
					if(stringAnswer.length() < stringLength) {
						stringAnswer.append("2");
					}
				}
					
			}
			else if((x1 > 75 && x1 < 129) && (y1 > 350 && y1 < 400)) { /*Checks if block3 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {  
					blockClick.play();
					if(stringAnswer.length() < stringLength) {
						stringAnswer.append("3");
					}
				}	
			}
			else if((x1 > 135 && x1 < 189) && (y1 > 350 && y1 < 400)) { /*Checks if block4 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(stringAnswer.length() < stringLength) {
						stringAnswer.append("4");
					}
				}
			}
			else if((x1 > 15 && x1 < 69) && (y1 > 290 && y1 < 340)) { /*Checks if block5 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(stringAnswer.length() < stringLength) {
						stringAnswer.append("5");
					}
				}
			}
			else if((x1 > 75 && x1 < 129) && (y1 > 290 && y1 < 340)) { /*Checks if block6 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(stringAnswer.length() < stringLength) {
						stringAnswer.append("6");
					}
				}
			}
			else if((x1 > 135 && x1 < 189) && (y1 > 290 && y1 < 340)) { /*Checks if block7 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(stringAnswer.length() < stringLength) {
						stringAnswer.append("7");
					}
				}
			}
			else if((x1 > 15 && x1 < 69) && (y1 > 230 && y1 < 280)) { /*Checks if block8 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(stringAnswer.length() < stringLength) {
						stringAnswer.append("8");
					}
				}		
			}
			else if((x1 > 75 && x1 < 129) && (y1 > 230 && y1 < 280)) { /*Checks if first operator is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(stringAnswer.length() < stringLength) {
						if(levelHolder == GameConstants.EASY_LEVEL) {
							if(lev1Op == GameConstants.ADDITION) {
								stringAnswer.append("+");
							} else {
								stringAnswer.append("-");
							}
						}
						else if(levelHolder == GameConstants.AVE_LEVEL) {
							if(lev2Op == GameConstants.MULTIPLICATION) {
								stringAnswer.append("*");
							} else {
								stringAnswer.append("/");
							}
						}
						else if(levelHolder == GameConstants.HARD_LEVEL){
							stringAnswer.append("*");
						}
					}
				}		
			}
			else if((x1 > 135 && x1 < 189) && (y1 > 230 && y1 < 280)) { /*Checks if second operator is clicked*/
				if(levelHolder == GameConstants.HARD_LEVEL) {	
					if(input.isMousePressed(GameConstants.leftClick)) {
						blockClick.play();
						if(stringAnswer.length() < stringLength) {
							stringAnswer.append("-");
						}
					}		
				}
			}
			else if((x1 > 25 && x1 < 175) && (y1 > 180 && y1 < 220)) { /*Checks if delete is clicked*/
				hoveredDelete = true;
				if(input.isMousePressed(GameConstants.leftClick)) { 
					click.play();
					if(stringAnswer.length() > 1 ) {
						stringAnswer.deleteCharAt(stringAnswer.length()-1);
					}
				}
			}
			else if((x1 >320 && x1 <520) && (y1 >165 && y1 <205)) { /*Checks if submit is clicked*/
				hoveredSubmit = true;
				if(input.isMousePressed(GameConstants.leftClick)) {  
					click.play();
					if(stringAnswer.length() < stringLength) {
						fail.play();
						lacking = true;
					} else {
						/*This part calls the mathparser to evaluate the given answer */
						m = new MyMathParser(stringAnswer.toString(),levelHolder);
						usersAns = m.evaluateString(stringAnswer.toString());
						
						if(usersAns == correctAns) {
							if(questionsCounter > 1) {
								questionsCounter--;
								this.questions++;
								this.reinit();
							} else {
								this.GameLoop(gc, sbg);
							}
						} else {
							fail.play();
							wrongAnswer = true;
						}
					}
				}
			}
			else {
				hoveredSubmit = false;
				hoveredDelete = false;
				hoveredMenu = false; 
				clickedMenu = false;
			}
		}
		else if(clickedMenu == true) {
			if((x1 >350 && x1 <500) && (y1 >310 && y1 <350)) { /*Checks if back to home is clicked*/
				hoveredBackHome = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedMenu = false;
					sbg.enterState(GameConstants.HOME, fadeOut, fadeIn);
				}
			}
			else if((x1 >350 && x1 <500) && (y1 >250 && y1 <290)) { /*Checks if resume is clicked*/
				hoveredResume = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedMenu = false;
					this.resume();
				}
			}
			else if((x1 >350 && x1 <500) && (y1 >190 && y1 <230)) { /*Checks if tutorial is clicked*/
				hoveredTutorial = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					Home.tutC.setGameIDforTutorial(GameConstants.COMPEQGAME_ID);
					clickedMenu = false;
					sbg.enterState(GameConstants.TUTORIALS, fadeOut, fadeIn);
				}
			}
			else {
				hoveredBackHome = false;
				hoveredTutorial = false;
				hoveredResume = false;
			}
		}
		else { 
			if((x1 >390 && x1 <510) && (y1 >120 && y1 <150)) { /*Checks if Ok in wrong answer popup is clicked*/
				hoveredOK = true;
				if(input.isMousePressed(GameConstants.leftClick)) { 
					click.play();
					wrongAnswer = false;
					lacking = false;
				}
			} else {
				hoveredOK = false;
			}
		}
	}
	
	/*This method controls the gameloop */
	public void GameLoop(GameContainer gc, StateBasedGame sbg) throws SlickException {
		long newTime = (Sys.getTime() / Sys.getTimerResolution());
		pausedTime = overallPause - pausedTime; /*Computes pauses in seconds per level*/
		
		if(firstEnter) { /*Computation is different for first since last record time is equal start time*/
			elapsedTime = (newTime - lastRecordTime);
			computedDelta = (overallDelta/1000);
			elapsedTime -= (elapsedTime-(computedDelta-overallPause));
			firstEnter = false;
		} else {
			elapsedTime = (newTime - lastRecordTimeInSystem) - pausedTime;
			computedDelta = (((overallDelta/1000)-overallPause)-lastRecordTime);
			elapsedTime = computedDelta - (computedDelta - elapsedTime);
		}
		lastRecordTime = elapsedTime;
		lastRecordTimeInSystem = newTime; 
			
		if(questions < GameConstants.QUESTIONS_MAX) {
			if(levelHolder == GameConstants.EASY_LEVEL) {
				if(easyQuestions == 4) { 
					if(lastRecordTime > (GameConstants.COMPEQ_L1_MAXTIME * GameConstants.QUESTIONS_PER_LEVEL)) {
						questionsCounter = 2;
						easyQuestions += 2;
						hardQuestions -= 2;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.AVE_LEVEL;
						questionsCounter = 4;
					}
				}
				else if(easyQuestions == 6) { /*By this time, there are no more hard questions*/
					if(lastRecordTime > (GameConstants.COMPEQ_L1_MAXTIME * qHolder)) {
						questionsCounter = 2;
						easyQuestions += 2;
						hardQuestions -= 2;
						missedLevel = 1;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.AVE_LEVEL;
						questionsCounter = 4;
					}
				}
				else if(easyQuestions == 8) { 
					if(lastRecordTime > (GameConstants.COMPEQ_L1_MAXTIME * qHolder)) {
						questionsCounter = 2;
						easyQuestions += 2;
						aveQuestions -= 2;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.AVE_LEVEL;
						questionsCounter = 4;
					}
				}
				else if(easyQuestions == 10) { /*By this time, there are no more average questions*/
					if(lastRecordTime > (GameConstants.COMPEQ_L1_MAXTIME * qHolder)) {
						questionsCounter = 2;
						easyQuestions += 2;
						aveQuestions -= 2;
						missedLevel = 2;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.AVE_LEVEL;
						questionsCounter = 2;
					}
				}
				this.questions++;
				this.reinit();
			}
				
			else if(levelHolder == GameConstants.AVE_LEVEL) {
				if(aveQuestions == 4 && easyQuestions == 4) {
					if(lastRecordTime > (GameConstants.COMPEQ_L2_MAXTIME * GameConstants.QUESTIONS_PER_LEVEL)) {
						questionsCounter = 2;
						aveQuestions += 2;
						hardQuestions -= 2;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.HARD_LEVEL;
						questionsCounter = 4;
					}
				}
				else if(aveQuestions == 6 && easyQuestions == 4) { /*By this time, there are no more hard questions*/
					if(lastRecordTime > (GameConstants.COMPEQ_L2_MAXTIME * qHolder)) {
						questionsCounter = 2;
						aveQuestions += 2;
						hardQuestions -= 2;
						missedLevel = 1;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.HARD_LEVEL;
						questionsCounter = 2;
					}
				}
				else if(aveQuestions == 4 && easyQuestions == 6) { /*By this time, there are no more hard questions*/
					if(lastRecordTime > (GameConstants.COMPEQ_L2_MAXTIME * qHolder)) {
						questionsCounter = 2;
						aveQuestions += 2;
						hardQuestions -= 2;
						missedLevel = 1;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.HARD_LEVEL;
						questionsCounter = 2;
					}
				}
				this.questions++;
				this.reinit();
			}
		} else { /*grade details are determined here - 12th question */
			stopTime = (Sys.getTime() / Sys.getTimerResolution());
			elapsedTime = (stopTime-startTime); 
			elapsedTime -= (elapsedTime-((overallDelta/1000)-overallPause));
				
			GradeDetails gradeDetails = new GradeDetails();
			gradeDetails.setEasyQuestions(easyQuestions);
			gradeDetails.setAveQuestions(aveQuestions);
			gradeDetails.setHardQuestions(hardQuestions);
			gradeDetails.setMissedLevel(missedLevel);
			gradeDetails.setOverAllTime(elapsedTime);
			gradeDetails.setGameID(GameConstants.COMPEQGAME_ID);
				
			GradeEvaluator evaluator = new GradeEvaluator();
			evaluator.computeGrade(gradeDetails);
				
			sbg.enterState(GameConstants.OVER, fadeOut, fadeIn);
		}
	}
	
	public void reinit(){
		sumOp1 = 2 + rand.nextInt(7); /*highest value is 8*/
		sumOp2 = 2 + rand.nextInt(6);
		sumOp3 = 2 + rand.nextInt(5);
		diffOp1 = 7 + rand.nextInt(3); /*values, 7-9, 3-5,1-2*/
		diffOp2 = 3 + rand.nextInt(3);
		diffOp3 = 1 + rand.nextInt(2);
		
		lev1Op = 1 + rand.nextInt(2); /*determines operation to be used in level 1 - either 1-addition or 2-subtraction*/
		
		prodOp1 = 2 + rand.nextInt(5); /*highest value is 6, 5*/
		prodOp2 = 2 + rand.nextInt(5);
		number  = 10 + rand.nextInt(16); /*value from 10-25*/
		
		while(number == 11 || number == 13 || number == 17 || number ==  19 || number == 22 || number == 23) {
			number  = 10 + rand.nextInt(16);
		}
		
		dividend = 2 + rand.nextInt(7); /*value of 2-6*/
		while(number % dividend != 0) {
			dividend = 2 + rand.nextInt(7); 
		}
			
		lev2Op = 3+ rand.nextInt(2); /*determines operation to be used in level 2 - either 3-multiplication or 4-division*/
		divOp = (lev2Op == GameConstants.DIVISION) ? true: false;
		
		operand1 = 2 + rand.nextInt(4); /*range is 2-5*/ 
		operand2 = 4 + rand.nextInt(5); /*range is 4-8*/
		operand3 = 2 + rand.nextInt(6); /*range is 2-7*/
		
		/*These parts determine the level and what to init per level */
		if(levelHolder == GameConstants.EASY_LEVEL) {
			stringLength = 5;
			if(lev1Op == GameConstants.ADDITION) {
				sum = sumOp1 + sumOp2 + sumOp3;
				correctAns = sum;
				stringAnswer = new StringBuilder(""+ sumOp1);
			} else {
				difference = diffOp1 - diffOp2 - diffOp3;
				correctAns = difference;
				stringAnswer = new StringBuilder(""+ diffOp1);
			}
			lackingString2 = "There should be 3 operands";
			lackingString3 = "   and 2 operators.";
		}
		else if(levelHolder == GameConstants.AVE_LEVEL) {
			stringLength = 3;
			if(lev2Op == GameConstants.MULTIPLICATION) {
				product = prodOp1 * prodOp2;
				correctAns = product;
				stringAnswer = new StringBuilder(""+ prodOp1);
			} else {
				stringLength = 4;
				quotient = number / dividend;
				correctAns = quotient;
				stringAnswer = new StringBuilder(""+number);
			}
			lackingString2 = "There should be 2 operands";
			lackingString3 = "   and 1 operator.";
		}
		else if(levelHolder == GameConstants.HARD_LEVEL) {
			stringLength = 5;
			equation = operand1 * operand2 - operand3;
			correctAns = equation;
			stringAnswer = new StringBuilder(""+operand1);
			lackingString2 = "There should be 3 operands";
			lackingString3 = "   and 2 operators.";
		}
		
		hoveredOK = false;
		hoveredDelete = false;
		lacking = false;
		wrongAnswer = false;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {	 
		levelHolder = GameConstants.EASY_LEVEL;
		questions = 1;
		easyQuestions = 4;
		aveQuestions = 4; 
		hardQuestions = 4;
		missedLevel = 0;
		questionsCounter = 4;
		firstEnter = true; 
		startOfTime = true;
		isPaused = false;
		startTime = 0; stopTime =0; elapsedTime = 0; newElapsedTime = 0; newStartTime = 0; 
		overallDelta = 0; 
		lastRecordTime = 0; lastRecordTimeInSystem = 0; computedDelta = 0; pausedTime = 0; overallPause=0;
		
		this.reinit();
	}

	public void pause() {
		isPaused = true;
	}
	
	public void resume() {
		isPaused = false;
		long lastSysTime = startTime + newElapsedTime + newStartTime;
		long time = Sys.getTime() / Sys.getTimerResolution();
		overallPause += (time-lastSysTime);
		newStartTime += (time - lastSysTime);
	}

	@Override
	public int getID() {
		return 2;
	}

}
