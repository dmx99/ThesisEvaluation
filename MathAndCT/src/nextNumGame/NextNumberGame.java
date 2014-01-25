package nextNumGame;

import grades.GradeDetails;
import grades.GradeEvaluator;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import commonScreens.Home;

/**
 * This class contains the whole logic for the nextnumber game.
 * @author d.caballero and m.golajer
 */
public class NextNumberGame extends NextNumber {

	protected int levelHolder = GameConstants.EASY_LEVEL;
	protected int questions = 1;
	protected int easyQuestions = 4, aveQuestions = 4, hardQuestions = 4;
	protected long startTime=0, stopTime=0, elapsedTime=0, newElapsedTime=0, newStartTime=0; 
	protected int overallDelta = 0;
	protected long lastRecordTime=0, lastRecordTimeInSystem=0, computedDelta=0, pausedTime=0, overallPause=0;
	
	protected int missedLevel = 0;
	protected int questionsCounter = 4, qHolder;
	protected boolean firstEnter = true, startOfTime = true, isPaused = false;
	
	protected int addend, subtrahend, multiplier, answerHolder; 
	protected int number1, number2;
	protected int numbers3[]= {1,2,3,4,5}; 
	protected int lastSum, lastDiff, lastProd;
	
	protected Sound blockClick, click, fail;
	
	protected StringBuilder userAns;
	
	public NextNumberGame(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initNextNumber();
		this.reinit();
		
		blockClick = new Sound("res/sounds/click1.ogg");
		click = new Sound("res/sounds/click2.ogg");
		fail = new Sound("res/sounds/fail.ogg");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		back.draw(0,0,850,500);
		title.drawString(240, 35, GameConstants.NXTNUMGAME_NAME);
		
		/*These parts implement the timer function */
		if(startOfTime) {
			startTime = (Sys.getTime() / Sys.getTimerResolution()); 
			lastRecordTime = startTime;
			startOfTime = false;
		}
			
		if(!isPaused) {
			newElapsedTime = ((Sys.getTime() / Sys.getTimerResolution())  - startTime) - newStartTime;
		 	text2.drawString(610, 420, "Elapsed Time: " + newElapsedTime );
		} else {
			text2.drawString(610, 420, "Elapsed Time: " + newElapsedTime );
		}
	 	
		if(hoveredSubmit == false){
			submit.draw(320, 295, 200, 40);
		} else {
			submitHover.draw(320, 295, 200, 40);
		}
		if(hoveredMenu == false){
			menuB.draw(15, 430, 150, 40);
		} else {
			menuHover.draw(15, 430, 150, 40);
		}
		help.draw(170, 430, 40 , 40);
		 
		/*These parts determine the level and what to render per level */
		if(levelHolder == GameConstants.EASY_LEVEL) {
			answerHolder = lastProd;
			for(int i = 0, x = 100, y = 100; i < numbers3.length; i++, x+= 100)
			{
				bigNum.drawString(x, y, "" + (numbers3[i] * multiplier));
			}
		}
		
		if (levelHolder == GameConstants.AVE_LEVEL) {
			answerHolder = lastDiff;
			for(int i = 0, x = 100, y = 100, diff = number2; i < 5; i++, x+= 100)
			{
				bigNum.drawString(x, y, "" + diff);
				diff -= subtrahend;
			}
		}
		if (levelHolder == GameConstants.HARD_LEVEL) {
			answerHolder = lastSum;
			for(int i = 0, x = 100, y = 100, sum = number1; i < 5; i++, x+= 100)
			{
				bigNum.drawString(x, y, "" + sum);
				sum += addend;
			}
		}
		
		g.setColor(Color.white);
	    g.setLineWidth(5);
		g.drawLine(580, 170, 660, 170);  /*x & y of start point, x & y of end point*/
		opsNNums.drawString(580, 100, userAns.toString());
	    
	    int x = 680, y = 130;
	    for(int i = 0; i < 5 ; i++) {
			numBlocks[i].draw(x,y,60, 50);
			y += 60;
		}
	    x = 750; 	y = 50;
	    for(int i = 5; i < 10 ; i++) {
			numBlocks[i].draw(x,y,60, 50);
			y += 60;
		}
	    
	    if(hoveredDelete == false) {
	    	deleteB.draw(750,350,60, 50);
	    } else {
	    	deleteHover.draw(750,350,60, 50);
	    }
	    
	    if(answerIsLess == true) {
	    	pop.draw(170, 300, 530, 100);
		   	text2.drawString(200, 310,lessString);
		   	if(hoveredOK == false) {
		   		okB.draw(390, 350, 120, 30);
		   	} else {
		   		okHover.draw(390, 350, 120, 30);
		   	}
		}
	    
	    if(answerIsGreater == true) {
			pop.draw(160, 300, 560, 100);
		   	text2.drawString(190, 310,greaterString);
		   	if(hoveredOK == false) {
		   		okB.draw(390, 350, 120, 30);
		   	} else {
		   		okHover.draw(390, 350, 120, 30);
		   	}
		}
	    
	    if(blank == true) {
			pop.draw(250, 300, 380, 100);
		   	text2.drawString(280, 310,blankString);
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
			if(hoveredTutorial == false)
			{
				tutorialB.draw(350, 270, 150, 40);
			} else {
				tutorialHover.draw(350, 270, 150, 40);
			}
	    }
 	    
 	    if(hoveredHelp == true) {
 	    	ldialog.draw(110, 320, 290, 110);
 	    	text2.drawString(180, 335, "Find pattern in ", Color.black);
 	    	text2.drawString(133, 355, "between to get answer.", Color.black);
 	    }
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int x1  = Mouse.getX(), y1  = Mouse.getY(); 
		overallDelta +=delta;
		
		if(answerIsLess == false && answerIsGreater == false && blank == false && clickedMenu == false) {
			if((x1 >15 && x1 <165) && (y1 >30 && y1 <70)) { /*Checks if menu is clicked*/
				hoveredMenu = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedMenu = true;
					this.pause();
				}
			}
			else if((x1 >170 && x1 <210) && (y1 >30 && y1 <70)) {
				hoveredHelp = true;
			}
			
			else if((x1 >680 && x1 <740) && (y1 >320 && y1 <370)) { /*Checks if block0 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {  
					blockClick.play();
					if(userAns.length() < 2) {
						userAns.append("0");
					}
				}
			}
			else if((x1 >680 && x1 <740) && (y1 >260 && y1 <310)) { /*Checks if block1 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(userAns.length() < 2) {
						userAns.append("1");
					}
				}
			}
			else if((x1 >680 && x1 <740) && (y1 >200 && y1 <250)) { /*Checks if block2 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {  
					blockClick.play();
					if(userAns.length() < 2) {
						userAns.append("2");
					}
				}
			}
			else if((x1 >680 && x1 <740) && (y1 >140 && y1 <190)) { /*Checks if block3 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {  
					blockClick.play();
					if(userAns.length() < 2) {
						userAns.append("3");
					}
				}
			}
			else if((x1 >680 && x1 <740) && (y1 >80 && y1 <130)) { /*Checks if block4 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(userAns.length() < 2) {
						userAns.append("4");
					}
				}
			}
			else if((x1 >750 && x1 <810) && (y1 >400 && y1 <450)) { /*Checks if block5 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {
					blockClick.play();
					if(userAns.length() < 3) {
						userAns.append("5");
					}
				}
			}
			else if((x1 >750 && x1 <810) && (y1 >340 && y1 <390)) { /*Checks if block6 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {  
					blockClick.play();
					if(userAns.length() < 3) {
						userAns.append("6");
					}
				}
			}
			else if((x1 >750 && x1 <810) && (y1 >280 && y1 <330)) { /*Checks if block7 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(userAns.length() < 3) {
						userAns.append("7");
					}
				}
			}
			else if((x1 >750 && x1 <810) && (y1 >220 && y1 <270)) { /*Checks if block8 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {  
					blockClick.play();
					if(userAns.length() < 2) {
						userAns.append("8");
					}
				}
			}
			else if((x1 >750 && x1 <810) && (y1 >160 && y1 <210)) { /*Checks if block9 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(userAns.length() < 2) {
						userAns.append("9");
					}
				}
			}
			else if((x1 >750 && x1 <810) && (y1 >100 && y1 <150)) { /*Checks if delete is clicked*/
				hoveredDelete = true;
				if(input.isMousePressed(GameConstants.leftClick)) { 
					click.play();
					if(userAns.length() > 0) {
						userAns.deleteCharAt(userAns.length()-1);
					}
				}
			}
			else if((x1 >320 && x1 <520) && (y1 >165 && y1 <205)) { /*Checks if submit is clicked*/
				hoveredSubmit = true;
				if(input.isMousePressed(GameConstants.leftClick)) {  
					click.play();
					if(userAns.length() == 0) {
						fail.play();
						blank = true;
					}
					else if(userAns.toString().trim().equals(Integer.toString(answerHolder))) {
						if(questionsCounter > 1) {
							questionsCounter--;
							this.questions++;
							this.reinit();
						} else {
							this.GameLoop(gc, sbg);
						}
					}
					else if(Integer.parseInt(userAns.toString().trim()) < answerHolder) {
						fail.play();
						answerIsLess = true;
					}
					else {
						fail.play();
						answerIsGreater = true;
					}
				}
			}
			else {
				hoveredSubmit = false;
				hoveredDelete = false;
				hoveredMenu = false; 
				hoveredHelp = false;
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
			else if((x1 >350 && x1 <500) && (y1 >190 && y1 <230)) { /*Checks if view tutorial is clicked*/
				hoveredTutorial = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedMenu = false;
					Home.tutC.setGameIDforTutorial(GameConstants.NXTNUMGAME_ID);
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
					answerIsLess = false;
					answerIsGreater = false;
					blank = false;
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
					if(lastRecordTime > (GameConstants.NXTNUM_L1_MAXTIME * GameConstants.QUESTIONS_PER_LEVEL)) {
						questionsCounter = 2;
						easyQuestions += 2;
						hardQuestions -= 2;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.AVE_LEVEL;
						questionsCounter = 4;
					}
				}
				else if(easyQuestions == 6) { 
					if(lastRecordTime > (GameConstants.NXTNUM_L1_MAXTIME * qHolder)) { /*By this time, there are no more hard questions*/
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
					if(lastRecordTime > (GameConstants.NXTNUM_L1_MAXTIME * qHolder)) {
						questionsCounter = 2;
						easyQuestions += 2;
						aveQuestions -= 2;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.AVE_LEVEL;
						questionsCounter = 4;
					}
				}
				else if(easyQuestions == 10) { 
					if(lastRecordTime > (GameConstants.NXTNUM_L1_MAXTIME * qHolder)) { /*By this time, there are no more average questions*/
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
					if(lastRecordTime > (GameConstants.NXTNUM_L2_MAXTIME * GameConstants.QUESTIONS_PER_LEVEL)) {
						questionsCounter = 2;
						aveQuestions += 2;
						hardQuestions -= 2;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.HARD_LEVEL;
						questionsCounter = 4;
					}
				}
				else if(aveQuestions == 6 && easyQuestions == 4) { 
					if(lastRecordTime > (GameConstants.NXTNUM_L2_MAXTIME * qHolder)) { /*By this time, there are no more hard questions*/
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
				else if(aveQuestions == 4 && easyQuestions == 6) {
					if(lastRecordTime > (GameConstants.NXTNUM_L2_MAXTIME * qHolder)) { /*By this time, there are no more hard questions*/
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
			gradeDetails.setGameID(GameConstants.NXTNUMGAME_ID);
				
			GradeEvaluator evaluator = new GradeEvaluator();
			evaluator.computeGrade(gradeDetails);
				
			sbg.enterState(GameConstants.OVER, fadeOut, fadeIn);
		}
	}
	
	public void reinit(){
		 addend = 2 + rand.nextInt(5); /*value is 2-6*/
		 number1 = 4 + rand.nextInt(11); /*value from 5-15*/
		 lastSum = (addend * 5) + number1;
			
		 subtrahend = 2 + rand.nextInt(4); /*value is 2-5*/
		 number2 = 25 + rand.nextInt(6); /*value from 25-30*/ 
		 lastDiff = number2 - (subtrahend * 5);
	
		 multiplier = 2 + rand.nextInt(9); /*value is 2 - 10*/
		 lastProd = 6 * multiplier;
		 
		 userAns = new StringBuilder("");
		 
		 answerIsLess = false;
		 answerIsGreater = false;
		 blank = false;
		 hoveredOK = false;
		 hoveredDelete = false;
		 hoveredHelp = false;
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
		
		userAns.setLength(0);
		
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
		return 4;
	}

}
