package binaryGame;

import grades.GradeDetails;
import grades.GradeEvaluator;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import commonScreens.Home;

/**
 * This class contains the whole logic for the binary game.
 * @author d.caballero and m.golajer
 */
public class BinaryGame extends Binary {

	protected int levelHolder = GameConstants.EASY_LEVEL;
	protected int questions = 1;
	protected int easyQuestions = 4, aveQuestions = 4, hardQuestions = 4;
	protected long startTime=0, stopTime=0, elapsedTime=0, newElapsedTime=0, newStartTime=0; 
	protected int overallDelta = 0;
	protected long lastRecordTime=0, lastRecordTimeInSystem=0, computedDelta=0, pausedTime=0, overallPause=0;
	
	protected int missedLevel = 0;
	protected int questionsCounter = 4, qHolder;
	protected boolean firstEnter = true, startOfTime = true, isPaused = false;
	
	protected Image plus, minus, bar; 
	protected Sound blockClick, click, fail;
	
	protected int level1Num;
	protected int num1, num2, sum;
	protected int numHolder, bin[], bin1[], bin2[], binSum[], i;
	protected int lev1counter[], lev2counter[], lev3counter[], sumValue;
	protected boolean lev1BlockIsOne[], lev1BlockIsZero[], lev2BlockIsOne[], lev2BlockIsZero[], lev3BlockIsOne[], lev3BlockIsZero[]; 
	
	public BinaryGame(int state) { 
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initBinary();
		
		plus = new Image("res/operators/plus.png");
		minus = new Image("res/operators/minus.png");
		bar = new Image("res/operators/bar.png");
		
		blockClick = new Sound("res/sounds/click1.ogg");
		click = new Sound("res/sounds/click2.ogg");
		fail = new Sound("res/sounds/fail.ogg");
		
		this.reinit();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		back.draw(0,0,850,500);
		title.drawString(250, 35, GameConstants.BINGAME_NAME);
		
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
			submit.draw(320, 420, 200, 40);
		} else {
			submitHover.draw(320, 420, 200, 40);
		}
		if(hoveredMenu == false) {
			menuB.draw(15, 430, 150, 40);
		} else {
			menuHover.draw(15, 430, 150, 40);
		}
		help.draw(170, 430, 40 , 40);
		
		/*These parts determine the level and what to render per level */
		if(levelHolder == GameConstants.EASY_LEVEL) {
			int x = 170, y = 160;

			for (int j = 0, binValue = 8; j < 4; j++, binValue = binValue / 2) {	
	 	    	number.drawString(x+40, y-25, ""+binValue);
	 	    	
	 	    	if(lev1BlockIsOne[j] == true) {
	 	    		one.draw(x, y, 90, 80);
	 	    	} else {
	 	    		zero.draw(x, y, 90, 80);
	 	    	}
	 	    	x+=100;
		    }
		    equals.draw(x, y, 80, 70);
		    x+=100;
			
		    bigNum.drawString(x, y, " "+level1Num);
		}
		
		if(levelHolder == GameConstants.AVE_LEVEL) {
			int x = 140, y = 150;
			for (int j = 0, binValue = 16; j < 5; j++, binValue = binValue / 2) {	
	 	    	number.drawString(x+40, y-25, ""+binValue);
	 	    	
	 	    	if(lev2BlockIsOne[j] == true) {
	 	    		one.draw(x, y, 90, 80);
	 	    	} else {
	 	    		zero.draw(x, y, 90, 80);
	 	    	}
	 	    	x+=100;
		    }
		    equals.draw(x, y, 80, 70);
		    x+=90;
		    bigNum.drawString(x, y, " "+num1);
		    y+=100;
		    x = 140;
			for (int j = 5, binValue = 16; j < 10; j++, binValue = binValue / 2) {	
	 	    	number.drawString(x+40, y-25, ""+binValue);
	 	    	
	 	    	if(lev2BlockIsOne[j] == true) {
	 	    		one.draw(x, y, 90, 80);
	 	    	} else {
	 	    		zero.draw(x, y, 90, 80);
	 	    	}
	 	    	x+=100;
		    }
			equals.draw(x, y, 80, 70);
		    x+=90;
		    bigNum.drawString(x, y, " "+num2);
		}
		
		if(levelHolder == GameConstants.HARD_LEVEL) {
			int x = 140, y = 110;

			for (int j = 5, binValue = 16; j > 0; j--, binValue = binValue / 2) {
				number.drawString(x+40, y-25, ""+binValue);	
		        if(bin1[j] == 0) {
		        	zero.draw(x, y, 80, 70);
		        }
		        if(bin1[j] == 1) {
		        	one.draw(x, y, 80, 70);
		        }
		        	x+=100;
			}
		         
		    equals.draw(x, y, 70, 60);
		    x+=85;
		    if(num1<10) {
		    	bigNum.drawString(x+25, y, ""+num1);
		    } else {
		    	bigNum.drawString(x, y, ""+num1);
		    }
		     
		    y+=100;
		    x = 40;
		    plus.draw(x, y, 70, 60);
		         
		    x = 140;
	        for (int j = 5; j > 0; j--) {
	        	if(bin2[j] == 0) {
		        	zero.draw(x, y, 80, 70);
		        }
		        if(bin2[j] == 1) {
		        	one.draw(x, y, 80, 70);
		        }
		        x+=100;
		    }
		    equals.draw(x, y, 70, 60);
		    x += 85;
		    if(num1<10) {
		    	bigNum.drawString(x+25, y, ""+num2);
		    } else {
		    	bigNum.drawString(x, y, ""+num2);
		    }
		    
		    bar.draw(40, y+75, 750, 20);
		    
		    x = 40;
		    y += 120;
		    
		    for (int j = 0, binValue = 32; j < 6; j++, binValue = binValue / 2) {
				number.drawString(x+40, y-25, ""+binValue);	
				
				if(lev3BlockIsOne[j] == true) {
	 	    		one.draw(x, y, 80, 70);
	 	    	} else {
	 	    		zero.draw(x, y, 80, 70);
	 	    	}
		        x+=100;
		    }
	        equals.draw(x, y, 70, 60);
	 	    
	 	    bigNum.drawString(725, 320, Integer.toString(sumValue));
		}
		
 	    if(wrongAnswer == true) {
	    	pop.draw(200, 300, 480, 100);
	    	text2.drawString(230, 310,wrongString);
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
 	    
 	    if(hoveredHelp == true) {
 	    	ldialog.draw(110, 320, 290, 110);
 	    	text2.drawString(180, 335, "Add top values", Color.black);
 	    	text2.drawString(170, 360, "of all 1 blocks.", Color.black);
 	    }
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int x  = Mouse.getX(), y  = Mouse.getY(); 
		overallDelta +=delta;
		
		if(wrongAnswer == false && clickedMenu == false) {
			if((x >15 && x <165) && (y >30 && y <70)) { /*Checks if menu is clicked*/
				hoveredMenu = true;
				if(input.isMousePressed(GameConstants.leftClick))  {
					click.play();
					clickedMenu = true;
					this.pause();
				}
			}
			else if((x >170 && x <210) && (y >30 && y <70)) {
				hoveredHelp = true;
			}
			else {
				hoveredSubmit = false;
				hoveredMenu = false;
				hoveredHelp = false;
				clickedMenu = false;
			}
		}
		
		/*These parts determine the level and what to update per level */
		if(wrongAnswer == false && clickedMenu == false) {
			
			if(levelHolder == GameConstants.EASY_LEVEL) {
				if((x >170 && x <260) && (y >260 && y <340)) { /*Checks if lev1 block1 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {  
						blockClick.play();
						if((lev1counter[0]%2) == 0) {
							lev1BlockIsOne[0] = true;
							lev1BlockIsZero[0] = false;
						} else {
							lev1BlockIsOne[0] = false;
							lev1BlockIsZero[0] = true;
						}
						lev1counter[0]++;
					}
				}
				else if((x >270 && x <360) && (y >260 && y <340)) { /*Checks if lev1 block2 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {
						blockClick.play();
						if((lev1counter[1]%2) == 0) {
							lev1BlockIsOne[1] = true;
							lev1BlockIsZero[1] = false;
						} else {
							lev1BlockIsOne[1] = false;
							lev1BlockIsZero[1] = true;
						}
						lev1counter[1]++;
					}
				}
				else if((x >370 && x <460) && (y >260 && y <340)) { /*Checks if lev1 block3 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {   
						blockClick.play();
						if((lev1counter[2]%2) == 0) {
							lev1BlockIsOne[2] = true;
							lev1BlockIsZero[2] = false;
						} else {
							lev1BlockIsOne[2] = false;
							lev1BlockIsZero[2] = true;
						}
						lev1counter[2]++;
					}
				}
				else if((x >470 && x <560) && (y >260 && y <340)) { /*Checks if lev1 block4 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {  
						blockClick.play();
						if((lev1counter[3]%2) == 0) {
							lev1BlockIsOne[3] = true;
							lev1BlockIsZero[3] = false;
						} else {
							lev1BlockIsOne[3] = false;
							lev1BlockIsZero[3] = true;
						}
						lev1counter[3]++;
					}
				}
				if((x >320 && x <520) && (y >40 && y <80)) { /*Checks if lev1 submit is clicked*/
					hoveredSubmit = true;
					if(input.isMousePressed(GameConstants.leftClick)) {  
						click.play();
						int match = 0, j, oneOrTwo;
						
						/*Start checking for answer at index 1*/
						for(i = 4, j = 0; i > 0; i--, j++) {
							oneOrTwo= (lev1counter[j]%2 == 0)?0:1;
									
							if( bin[i] == oneOrTwo) {
								match++;
							}
						}	
						
						if(match != 4) {
							fail.play();
							wrongAnswer = true;
						} else {
							if(questionsCounter > 1) {
								questionsCounter--;
								this.questions++;
								this.reinit();
							} else {
								this.GameLoop(gc, sbg); 
							} 
						}
					}
				} 				
			}
					
			else if(levelHolder == GameConstants.AVE_LEVEL) {
				if((x >140 && x <230) && (y >270 && y <350)) { /*Checks if lev2 block1 is clicked*/			
					if(input.isMousePressed(GameConstants.leftClick)) {   	
						blockClick.play();
						if((lev2counter[0]%2) == 0) {
							lev2BlockIsOne[0] = true;
							lev2BlockIsZero[0] = false;
						} else {
							lev2BlockIsOne[0] = false;
							lev2BlockIsZero[0] = true;
						}
						lev2counter[0]++;
					}
				}
				else if((x >240 && x <330) && (y >270 && y <350)) { /*Checks if lev2 block2 is clicked*/	
					if(input.isMousePressed(GameConstants.leftClick)) {  
						blockClick.play();
						if((lev2counter[1]%2) == 0) {
							lev2BlockIsOne[1] = true;
							lev2BlockIsZero[1] = false;
						} else {
							lev2BlockIsOne[1] = false;
							lev2BlockIsZero[1] = true;
						}
						lev2counter[1]++;
					}
				}
				else if((x >340 && x <430) && (y >270 && y <350)) { /*Checks if lev2 block3 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {   
						blockClick.play();
						if((lev2counter[2]%2) == 0) {
							lev2BlockIsOne[2] = true;
							lev2BlockIsZero[2] = false;
						} else {
							lev2BlockIsOne[2] = false;
							lev2BlockIsZero[2] = true;
						}
						lev2counter[2]++;
					}
				}
				else if((x >440 && x <530) && (y >270 && y <350)) { /*Checks if lev2 block4 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) { 
						blockClick.play();
						if((lev2counter[3]%2) == 0) {
							lev2BlockIsOne[3] = true;
							lev2BlockIsZero[3] = false;
						} else {
							lev2BlockIsOne[3] = false;
							lev2BlockIsZero[3] = true;
						}
						lev2counter[3]++;
					}
				}
				else if((x >540 && x <630) && (y >270 && y <350)) { /*Checks if lev2 block5 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {   
						blockClick.play();
						if((lev2counter[4]%2) == 0) {
							lev2BlockIsOne[4] = true;
							lev2BlockIsZero[4] = false;
						} else {
							lev2BlockIsOne[4] = false;
							lev2BlockIsZero[4] = true;
						}
						lev2counter[4]++;
					}
				}
						
				/*Second question for level 2 */
				else if((x >140 && x <230) && (y >170 && y <250)) { /*Checks if lev2 block6 is clicked*/		
					if(input.isMousePressed(GameConstants.leftClick)) {  
						blockClick.play();
						if((lev2counter[5]%2) == 0) {
							lev2BlockIsOne[5] = true;
							lev2BlockIsZero[5] = false;
						} else {
							lev2BlockIsOne[5] = false;
							lev2BlockIsZero[5] = true;
						}
						lev2counter[5]++;
					}
				}
				else if((x >240 && x <330) && (y >170 && y <250)) { /*Checks if lev2 block7 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) { 
						blockClick.play();
						if((lev2counter[6]%2) == 0) {
							lev2BlockIsOne[6] = true;
							lev2BlockIsZero[6] = false;
						} else {
							lev2BlockIsOne[6] = false;
							lev2BlockIsZero[6] = true;
						}
						lev2counter[6]++;	 
					}
				}
				else if((x >340 && x <430) && (y >170 && y <250)) { /*Checks if lev2 block8 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {  
						blockClick.play();
						if((lev2counter[7]%2) == 0) {
							lev2BlockIsOne[7] = true;
							lev2BlockIsZero[7] = false;
						} else {
							lev2BlockIsOne[7] = false;
							lev2BlockIsZero[7] = true;
						}
						lev2counter[7]++;
					}
				}
				else if((x >440 && x <530) && (y >170 && y <250)) { /*Checks if lev2 block9 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {   
						blockClick.play();
						if((lev2counter[8]%2) == 0) {
							lev2BlockIsOne[8] = true;
							lev2BlockIsZero[8] = false;
						} else {
							lev2BlockIsOne[8] = false;
							lev2BlockIsZero[8] = true;
						}
						lev2counter[8]++;
					}
				}
				else if((x >540 && x <630) && (y >170 && y <250)) { //*Checks if lev2 block10 is clicked*/	
					if(input.isMousePressed(GameConstants.leftClick)) {  
						blockClick.play();
						if((lev2counter[9]%2) == 0) {
							lev2BlockIsOne[9] = true;
							lev2BlockIsZero[9] = false;
						} else {
							lev2BlockIsOne[9] = false;
							lev2BlockIsZero[9] = true;
						}
						lev2counter[9]++;
					}
				}
				if((x >320 && x <520) && (y >40 && y <80)) { /*Checks if lev2 submit is clicked*/
					hoveredSubmit = true;
					if(input.isMousePressed(GameConstants.leftClick)) {  
						click.play();
						int match = 0, j, oneOrTwo;
								
						/*Start checking at index 5 -  second to the last index- 
						 * since binary in the array is backwards*/ 
						for(i = 5, j = 0; i > 0; i--, j++) {
							oneOrTwo= (lev2counter[j]%2 == 0)?0:1;
	
							if( bin1[i] == oneOrTwo) {
								match++;
							}
						}	 
						
						for(i = 5, j = 5; i > 0; i--, j++) {
							oneOrTwo= (lev2counter[j]%2 == 0)?0:1;
									
							if( bin2[i] == oneOrTwo) {
								match++;
							}
						}	
							
						if(match != 10) {
							fail.play();
							wrongAnswer = true;
						} else { 
							if(questionsCounter > 1) {
								questionsCounter--;
								this.questions++;
								this.reinit();
							} else {
								this.GameLoop(gc, sbg); 
							} 
						}
					}
				}
			}
					
			else if(levelHolder == GameConstants.HARD_LEVEL) {
				if((x >40 && x <120) && (y >100 && y <170)) { /*Checks if lev3 block1 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {  
						blockClick.play();
						if((lev3counter[0]%2) == 0) {
							sumValue += 32;
							lev3BlockIsOne[0] = true;
							lev3BlockIsZero[0] = false;
						} else {
							sumValue -= 32;
							lev3BlockIsOne[0] = false;
							lev3BlockIsZero[0] = true;
						}
						lev3counter[0]++;
					}
				}
				else if((x >140 && x <220) && (y >100 && y <170)) { /*Checks if lev3 block2 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {   
						blockClick.play();
						if((lev3counter[1]%2) == 0) {
							sumValue += 16;
							lev3BlockIsOne[1] = true;
							lev3BlockIsZero[1] = false;
						} else {
							sumValue -= 16;
							lev3BlockIsOne[1] = false;
							lev3BlockIsZero[1] = true;
						}
						lev3counter[1]++;
					}
				}
				else if((x >240 && x <320) && (y >100 && y <170)) { /*Checks if lev3 block3 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {   
						blockClick.play();
						if((lev3counter[2]%2) == 0)	{
							sumValue += 8;
							lev3BlockIsOne[2] = true;
							lev3BlockIsZero[2] = false;
						} else {
							sumValue -= 8;
							lev3BlockIsOne[2] = false;
							lev3BlockIsZero[2] = true;
						}
						lev3counter[2]++;
					}
				}
				else if((x >340 && x <420) && (y >100 && y <170)) { /*Checks if lev3 block4 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {   
						blockClick.play();
						if((lev3counter[3]%2) == 0) {
							sumValue += 4;
							lev3BlockIsOne[3] = true;
							lev3BlockIsZero[3] = false;
						} else {
							sumValue -= 4;
							lev3BlockIsOne[3] = false;
							lev3BlockIsZero[3] = true;
						}
						lev3counter[3]++;
					}
				}
				else if((x >440 && x <520) && (y >100 && y <170)) { /*Checks if lev3 block5 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) {  
						blockClick.play();
						if((lev3counter[4]%2) == 0) {
							sumValue += 2;
							lev3BlockIsOne[4] = true;
							lev3BlockIsZero[4] = false;
						} else {
							sumValue -= 2;
							lev3BlockIsOne[4] = false;
							lev3BlockIsZero[4] = true;
						}
						lev3counter[4]++;
					}
				}
				else if((x >540 && x <620) && (y >100 && y <170)) { /*Checks if lev3 block6 is clicked*/
					if(input.isMousePressed(GameConstants.leftClick)) { 
						blockClick.play();
						if((lev3counter[5]%2) == 0) {
							sumValue += 1;
							lev3BlockIsOne[5] = true;
							lev3BlockIsZero[5] = false;
						} else {
							sumValue -= 1;
							lev3BlockIsOne[5] = false;
							lev3BlockIsZero[5] = true;
						}
						lev3counter[5]++;
					}
				}
				if((x >320 && x <520) && (y >40 && y <80)) { /*Checks if lev3 submit is clicked*/
					hoveredSubmit = true;
					if(input.isMousePressed(GameConstants.leftClick)) {  
						click.play();
						int match = 0, j, oneOrTwo;
								
						for(i = 6, j = 0; i > 0; i--, j++) {
							oneOrTwo= (lev3counter[j]%2 == 0)?0:1;
									
							if( binSum[i] == oneOrTwo) {
								match++;
							}
						}	
									
						if(match != 6) {
							fail.play();
							wrongAnswer = true;
						} else {
							if(questionsCounter > 1) {
								questionsCounter--;
								this.questions++;
								this.reinit();
							} else {
								this.GameLoop(gc, sbg);
							} 
						}
					}
				}
			}
		}
		else if(clickedMenu == true) {
			if((x >350 && x <500) && (y >310 && y <350)) { /*Checks if back to home is clicked*/
				hoveredBackHome = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedMenu = false;
					sbg.enterState(GameConstants.HOME, fadeOut, fadeIn);
				}
				
			}
			else if((x >350 && x <500) && (y >250 && y <290)) { /*Checks if resume is clicked*/
				hoveredResume = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					clickedMenu = false;
					this.resume();
				}
			}
			else if((x >350 && x <500) && (y >190 && y <230)) { /*Checks if tutorial is clicked*/
				hoveredTutorial = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					Home.tutC.setGameIDforTutorial(GameConstants.BINARYGAME_ID);
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
			if((x >390 && x <510) && (y >120 && y <150)) { /*Checks if Ok in wrong answer popup is clicked*/
				hoveredOK = true;
				if(input.isMousePressed(GameConstants.leftClick)) {
					click.play();
					wrongAnswer = false;
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
					if(lastRecordTime > (GameConstants.BIN_L1_MAXTIME * GameConstants.QUESTIONS_PER_LEVEL)) {
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
					if(lastRecordTime > (GameConstants.BIN_L1_MAXTIME * qHolder)) {  /*By this time, there are no more hard questions*/
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
					if(lastRecordTime > (GameConstants.BIN_L1_MAXTIME* qHolder)) {
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
					if(lastRecordTime > (GameConstants.BIN_L1_MAXTIME * qHolder)) { /*By this time, there are no more average questions*/
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
					if(lastRecordTime > (GameConstants.BIN_L2_MAXTIME * GameConstants.QUESTIONS_PER_LEVEL)) {
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
					if(lastRecordTime > (GameConstants.BIN_L2_MAXTIME * qHolder)) {
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
					if(lastRecordTime > (GameConstants.BIN_L2_MAXTIME* qHolder)) { /*By this time, there are no more hard questions*/
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
			gradeDetails.setGameID(GameConstants.BINARYGAME_ID);
				
			GradeEvaluator evaluator = new GradeEvaluator();
			evaluator.computeGrade(gradeDetails);
				
			sbg.enterState(GameConstants.OVER, fadeOut, fadeIn);	
		}
	}
	
	public void reinit(){
		level1Num = 1 + rand.nextInt(15); /*highest value is 15*/
		num1 = 3 + rand.nextInt(13); /*highest value is 15*/
		num2 = 5 + rand.nextInt(16); /*highest value is 20*/
		sum = num1 + num2;
		
		sumValue = 0;
		wrongAnswer = false;
		hoveredOK = false;
		hoveredHelp = false;
		
		/*initializing value of blocks to 0 */
		lev1BlockIsOne = new boolean[4];
		lev1BlockIsZero = new boolean[4];
		lev2BlockIsOne = new boolean[10];
		lev2BlockIsZero = new boolean[10];
		lev3BlockIsOne = new boolean[6];
		lev3BlockIsZero = new boolean[6];
		
		for(int i = 0; i < 4; i++) {
			lev1BlockIsOne[i] = false;
			lev1BlockIsZero[i] = true;
		}
		
		for(int i = 0; i < 9; i++) {
			lev2BlockIsOne[i] = false;
			lev2BlockIsZero[i] = true;
		}
		
		for(int i = 0; i < 6; i++) {
			lev3BlockIsOne[i] = false;
			lev3BlockIsZero[i] = true;
		}
		
		/*This part converts the number to binary - order is reversed, 1st bit =0 1 2 4 8 16*/ 
		bin = new int[5];
		bin1 = new int[6];
		bin2 = new int[6];
		binSum = new int[7];
		
		i = 0;	numHolder = level1Num; /*For first level*/
		while (numHolder != 0) {
	    	i++;
	        bin[i] = numHolder % 2;
	        numHolder = numHolder / 2;
	    }
		i=0;	numHolder = num1; /*For second and third level num1*/
		while (numHolder != 0) {
		   	i++;
		   	bin1[i] = numHolder % 2;
		   	numHolder = numHolder / 2;
		}
		i=0;	numHolder = num2; /*For second and third level num2*/
		while (numHolder != 0) {
		   	i++;
		   	bin2[i] = numHolder % 2;
		   	numHolder = numHolder / 2;
		}
		i = 0;	numHolder= sum;		
		while (numHolder != 0) {
			i++;
			binSum[i] = numHolder % 2;
			numHolder = numHolder / 2;
	    }
		
		/*Initializing counters to 0*/
		lev1counter = new int[4];	
		for(int c=0; c<lev1counter.length ; c++)
		{	lev1counter[c] = 0;	}
		lev2counter = new int[10];
		for(int c=0; c<lev2counter.length ; c++)
		{	lev2counter[c] = 0;	}
		lev3counter = new int[6];
		for(int c=0; c<lev3counter.length ; c++)
		{	lev3counter[c] = 0;	}
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
		newStartTime += (time-lastSysTime);
	}

	@Override
	public int getID() {
		return 1;
	}

}
