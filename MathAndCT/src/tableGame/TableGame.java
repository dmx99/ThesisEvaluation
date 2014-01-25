package tableGame;

import grades.GradeDetails;
import grades.GradeEvaluator;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import commonScreens.Home;

/**
 * This class contains the whole logic for the table game.
 * @author d.caballero and m.golajer
 */
public class TableGame extends Table {

	protected int levelHolder = GameConstants.EASY_LEVEL;
	protected int questions = 1;
	protected int easyQuestions = 4, aveQuestions = 4, hardQuestions = 4;
	protected long startTime=0, stopTime=0, elapsedTime=0, newElapsedTime=0, newStartTime=0; 
	protected int overallDelta = 0;
	protected long lastRecordTime=0, lastRecordTimeInSystem=0, computedDelta=0, pausedTime=0, overallPause=0;
	
	protected int missedLevel = 0;
	protected int questionsCounter = 4, qHolder;
	protected boolean firstEnter = true, startOfTime = true, isPaused = false;
	
	protected int lev1Height, lev1Width, lev2Height, lev2Width;
	protected int height3, width3, groups;
	protected int ans, ans2, ans3, answerHolder;
	protected int lev1HeightHolder, lev1WidthHolder, lev2HeightHolder, lev2WidthHolder, lev3HeightHolder, lev3WidthHolder;
	protected int x, y, imageNumber;

	protected Sound blockClick, click, fail;
	
	protected StringBuilder usersAns;
	
	public TableGame(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initTable();
		this.reinit();
		
		blockClick = new Sound("res/sounds/click1.ogg");
		click = new Sound("res/sounds/click2.ogg");
		fail = new Sound("res/sounds/fail.ogg");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		back.draw(0,0,850,500);
		title.drawString(190, 35, GameConstants.TABLEGAME_NAME);
		
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
	 	
		if(hoveredSubmit == false) {
			submit.draw(320, 400, 200, 40);	
		} else {
			submitHover.draw(320, 400, 200, 40);
		}
		if(hoveredMenu == false) {
			menuB.draw(15, 430, 150, 40);
		} else {
			menuHover.draw(15, 430, 150, 40);
		}
		help.draw(170, 430, 40 , 40);
		
		/*These parts determine the level and what to render per level */
		if(levelHolder == GameConstants.EASY_LEVEL) {		
			answerHolder = ans;
			x = 50;
			y = 100;
			lev1HeightHolder = lev1Height;
			
			for (; lev1HeightHolder > 0 ; lev1HeightHolder--) {	
				for (lev1WidthHolder = lev1Width, x = 50; lev1WidthHolder > 0; lev1WidthHolder--) {
					pic.draw(x, y, 30, 60);
					x = x + 40;
				}
				y = y + 70;
		    }
		}
		
		if (levelHolder == GameConstants.AVE_LEVEL) {
			answerHolder = ans2;
			x = 50;
			y = 100;
			lev2HeightHolder = lev2Height;
			
			for (; lev2HeightHolder > 0 ; lev2HeightHolder--) {
				for (lev2WidthHolder = lev2Width, x = 50; lev2WidthHolder > 0; lev2WidthHolder--) {
					pic.draw(x, y, 30, 60);
					x = x + 40;
				}
				y = y + 70;
		    }
		}
		
		if (levelHolder == GameConstants.HARD_LEVEL) { 
			
			int x[] ={50,185,320,455}, ctr, currentX;
			answerHolder = ans3;

			for ( ctr = 0; ctr < groups ; ctr++) {
				y = 100;
				
				for (lev3HeightHolder = height3; lev3HeightHolder > 0 ; lev3HeightHolder--) {
					for (lev3WidthHolder = width3, currentX = x[ctr]; lev3WidthHolder > 0; lev3WidthHolder--) {
						pic.draw(currentX, y, 25, 55);
						currentX = currentX + 35;
					}
					y = y + 65;
			    }
			}
		}
		
		numbers[0].draw(650,80,54,50); 
		numbers[1].draw(710,80,54,50);
		numbers[2].draw(770,80,54,50);
		numbers[3].draw(650,140,54,50);
		numbers[4].draw(710,140,54,50);
		numbers[5].draw(770,140,54,50);
		numbers[6].draw(650,200,54,50);
		numbers[7].draw(710,200,54,50);
		numbers[8].draw(770,200,54,50);
		numbers[9].draw(650,260,54,50);
		
		if(hoveredDelete == false) {
			deleteB.draw(710,270,100,40);
		} else {
			deleteHover.draw(710,270,100,40);
		}
		
		text2.drawString(650, 320, "Answer: " + usersAns );
		
		if(answerIsLess == true) {
			pop.draw(180, 300, 530, 100);
		    text2.drawString(200, 310,lessString);
		    if(hoveredOK == false) {
		    	okB.draw(430, 350, 120, 30);
		    } else {
		    	okHover.draw(430, 350, 120, 30);
		    }
		}
		
		if(answerIsGreater == true) {
			pop.draw(150, 300, 560, 100);
			text2.drawString(170, 310,greaterString);
		    if(hoveredOK == false) {
		    	okB.draw(430, 350, 120, 30);
		    } else {
		    	okHover.draw(430, 350, 120, 30);
		    }	
		}
		
		if(blank == true) {
			pop.draw(260, 300, 380, 100);
			text2.drawString(290, 310,blankString);
			if(hoveredOK == false) {
		    	okB.draw(430, 350, 120, 30);
		    } else {
		    	okHover.draw(430, 350, 120, 30);
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
 	    
 	    if(hoveredHelp == true) {
 	    	ldialog.draw(110, 320, 290, 110);
 	    	text2.drawString(140, 335, "Count number of rows,", Color.black);
 	    	text2.drawString(150, 360, "columns or groups.", Color.black);
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
			 
			else if((x1 >650 && x1 <704) && (y1 >370 && y1 <420)) { /*Checks if block0 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("0");
					}
				}
			}
			else if((x1 >710 && x1 <764) && (y1 >370 && y1 <420)) { /*Checks if block1 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("1");
					}
				}
			}
			else if((x1 >770 && x1 <824) && (y1 >370 && y1 <420)) { /*Checks if block2 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("2");
					}
				}
			}
			else if((x1 >650 && x1 <704) && (y1 >310 && y1 <360)) { /*Checks if block3 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("3");
					}
				}
			}
			else if((x1 >710 && x1 <764) && (y1 >310 && y1 <360)) { /*Checks if block4 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("4");
					}
				}
			}
			else if((x1 >770 && x1 <824) && (y1 >310 && y1 <360)) { /*Checks if block5 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("5");
					}
				}
			}
			else if((x1 >650 && x1 <704) && (y1 >250 && y1 <300)) { /*Checks if block6 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) {
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("6");
					}
				}
			}
			else if((x1 >710 && x1 <764) && (y1 >250 && y1 <300)) { /*Checks if block7 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("7");
					}
				}
			}
			else if((x1 >770 && x1 <824) && (y1 >250 && y1 <300)) { /*Checks if block8 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("8");
					}
				}
			}
			else if((x1 >650 && x1 <704) && (y1 >190 && y1 <240)) { /*Checks if block9 is clicked*/
				if(input.isMousePressed(GameConstants.leftClick)) { 
					blockClick.play();
					if(usersAns.length() < 3) {
						usersAns.append("9");
					}
				}
			}
			
			else if((x1  >710 && x1  <810) && (y1  >190 && y1  <230)) { /*Checks if delete is clicked*/
				hoveredDelete = true;
				if(input.isMousePressed(GameConstants.leftClick)) { 
					click.play();
					if(usersAns.length() > 0) {
						usersAns.deleteCharAt(usersAns.length()-1);
					}
				}
			}
			
			else if((x1 >320 && x1 <520) && (y1 >60 && y1 <100)) { /*Checks if submit is clicked*/
				hoveredSubmit = true;
				if(input.isMousePressed(GameConstants.leftClick)) {  
					click.play();
					if(usersAns.length() == 0) {
						fail.play();
						blank = true;
					} else {
						blank = false;
						if(usersAns.toString().trim().equals(Integer.toString(answerHolder))) {
							if(questionsCounter > 1) {
								questionsCounter--;
								this.questions++;
								this.reinit();
							} else {
								this.GameLoop(gc, sbg);
							}
						}
						else if(Integer.parseInt(usersAns.toString().trim()) < answerHolder) {
							fail.play();
							answerIsLess = true;
						} 
						else {
							fail.play();
							answerIsGreater = true;
						}
					}
				}
			}
			else {
				hoveredDelete = false;
				hoveredSubmit = false;
				hoveredHelp = false;
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
			else if((x1 >350 && x1 <500) && (y1 >190 && y1 <230)) { /*Checks if view tutorial is clicked*/
				hoveredTutorial = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedMenu = false;
					Home.tutC.setGameIDforTutorial(GameConstants.TABLEGAME_ID);
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
			if((x1  >430 && x1  <550) && (y1  >120 && y1  <150)) { /*Checks if Ok in wrong answer popup is clicked*/
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
					if(lastRecordTime > (GameConstants.TABLE_L1_MAXTIME * GameConstants.QUESTIONS_PER_LEVEL)) {
						questionsCounter = 2;
						easyQuestions += 2;
						hardQuestions -= 2;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.AVE_LEVEL;
						questionsCounter = 4;						}
					}
				else if(easyQuestions == 6) { 
					if(lastRecordTime > (GameConstants.TABLE_L1_MAXTIME * qHolder)) { /*By this time, there are no more hard questions*/
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
					if(lastRecordTime > (GameConstants.TABLE_L1_MAXTIME * qHolder)) {
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
					if(lastRecordTime > (GameConstants.TABLE_L1_MAXTIME * qHolder)) { /*By this time, there are no more average questions*/
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
					if(lastRecordTime > (GameConstants.TABLE_L2_MAXTIME * GameConstants.QUESTIONS_PER_LEVEL)) {
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
					if(lastRecordTime > (GameConstants.TABLE_L2_MAXTIME * qHolder)) { /*By this time, there are no more hard questions*/
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
					if(lastRecordTime > (GameConstants.TABLE_L2_MAXTIME * qHolder)) { /*By this time, there are no more hard questions*/
						questionsCounter = 2;
						aveQuestions += 2;
						hardQuestions -= 2;
						missedLevel = 1;
						qHolder = questionsCounter;
					} else {
						levelHolder = GameConstants.HARD_LEVEL;
						questionsCounter = 2;
					};
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
			gradeDetails.setGameID(GameConstants.TABLEGAME_ID);
				
			GradeEvaluator evaluator = new GradeEvaluator();
			evaluator.computeGrade(gradeDetails);
				
			sbg.enterState(GameConstants.OVER, fadeOut, fadeIn);
		}
	}
	
	public void reinit() throws SlickException{
		lev1Height = 2 + rand.nextInt(3); /*highest value is 4*/
		lev1Width = 4 + rand.nextInt(3); /*highest value is 6*/
		lev2Height = 2 + rand.nextInt(3); /*highest value is 4*/
		lev2Width = 5 + rand.nextInt(3); /*highest value is 7*/
		height3 = 2 + rand.nextInt(2);  /*value is 2-3*/ 
		width3 = 2 + rand.nextInt(2); /*value is 2-3*/
		groups = 2 + rand.nextInt(3); /*value is 2-4*/
		ans = lev1Height * lev1Width;
		ans2 = lev2Height * lev2Width;
		ans3 = (height3 * width3) * groups;
		
		imageNumber = rand.nextInt(8); //values are 0-7
		
		switch (imageNumber) {
		case 0:
			pic = new Image("res/images/tableGame/cane.png");
			break;
		case 1:
			pic = new Image("res/images/tableGame/cottoncandy.png");		
			break;
		case 2:
			pic = new Image("res/images/tableGame/hotdog.png");
			break;
		case 3:
			pic = new Image("res/images/tableGame/icecream.png");
			break;
		case 4:
			pic = new Image("res/images/tableGame/icedrop.png");
			break;
		case 5:
			pic = new Image("res/images/tableGame/lollipop.png");
			break;
		case 6:
			pic = new Image("res/images/tableGame/flower.png");
			break;
		case 7:
			pic = new Image("res/images/tableGame/pencil.png");
			break;
		default:
	
		}
		
		usersAns = new StringBuilder("");

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
		
		usersAns.setLength(0);
		
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
		return 3;
	}

}
