package commonScreens;

import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import utilCommon.Layout;
import utilDatabase.TableCreator;
import utilDatabaseUser.UserClass;
import utilDatabaseUser.UserOperations;

/**
 * This class contains the welcome screen.
 * @author d.caballero and m.golajer
 */
public class Welcome extends Layout {

	protected String userString, addUserResult, delUserResult, sureToDelete;
    protected Image game, saveB, addUserB, closeB, chooseUserB, exitB, delete, deleteB, okHover, okB, cancelHover, cancelB;
    protected Image saveHover, addUserHover, chooseUserHover, exitHover, deleteHover;
    protected Image prev, next;
    protected Music bgm;
    protected Sound click;
    protected boolean hoveredSubmit, hoveredSave, hoveredAdd1, hoveredAdd2, hoveredChoose, hoveredExit, hoveredDelete, hoveredOk, hoveredCancel;
    protected boolean chooseUser, blankInput, addUser, delUser, delUser2;  
    
    protected TextField user, first, last, chosenID; 
    protected int userIDToDelete, page, i;
    
    Font h1;
    TrueTypeFont ttf1;
    UnicodeFont font;
    
    public static UserOperations userOps = new UserOperations();
    public static UserClass currUser = new UserClass();
	protected List<UserClass> userList;
    protected TableCreator t = new TableCreator();
    
    public Welcome(int state) { 
        
    }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initLayout();
        
        t.createAndInsert();
        game = new Image("res/images/logo2.png");
        dialog = new Image("res/images/ldialog.png");
        back = new Image("res/images/board0.png");
        prev = new Image("res/buttons/back.png");
        next = new Image("res/buttons/forward.png");
        
        bgm = new Music("res/sounds/bgm1.ogg");
        bgm.setVolume(0.3f);
        bgm.loop();
        
        click = new Sound("res/sounds/click2.ogg");
        
        int id = -1;
		try { /*This part gets the latest user as default chosen user */
			id = userOps.getLatestUser(); 
			
			if(id == -1) {
				System.out.println("getLatestUser is not executing properly!!");
			}
			else {
				currUser = userOps.getUserByIDOrUsername(id, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		sureToDelete = "";
		userString = currUser.getUsername();
		font = getNewFont("Comic Sans MS" , 18);
        
        saveB = new Image("res/buttons/saveB.png");
        addUserB = new Image("res/buttons/addUserB.png"); 
        chooseUserB =  new Image("res/buttons/chooseUserB.png");
        submit =  new Image("res/buttons/enterB.png");
        closeB =  new Image("res/buttons/close.png");
        exitB = new Image("res/buttons/exitB.png");
        deleteB = new Image("res/buttons/deleteB.png");
        okB = new Image("res/buttons/okB.png");
        cancelB = new Image("res/buttons/cancelB.png");
        
        saveHover = new Image("res/buttons/save.png");
        addUserHover = new Image("res/buttons/addUser.png"); 
        chooseUserHover =  new Image("res/buttons/chooseUser.png");
        submitHover =  new Image("res/buttons/enter.png");
        exitHover = new Image("res/buttons/exit.png");
        deleteHover = new Image("res/buttons/delete.png");
        okHover = new Image("res/buttons/ok.png");
        cancelHover = new Image("res/buttons/cancel.png");
        
        chooseUser = false;
        addUser = false;
        hoveredSubmit = false;
        hoveredExit = false;
        hoveredDelete = false;
        hoveredOk = false; 
        hoveredCancel = false;
        delUser = false;
        delUser2 = false;
        addUserResult = "";
        delUserResult = "";
        
        userIDToDelete = -1;
        page = 0;
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		back.draw(0,0,850,500);
        game.draw(210, 50, 400, 140);
        dialog.draw(210, 170, 390, 150);
        text.drawString(350, 180, "Welcome ");
        
        g.setColor(Color.black);
        g.fillRect(310, 220, 200, 40);
        userString = currUser.getUsername();
        text.drawString(340, 225, userString);
        
        if(hoveredChoose == false) {
        	chooseUserB.draw(650, 100, 150, 30);
        } else {
        	chooseUserHover.draw(650, 100, 150, 30);
        }
        if(hoveredAdd1 == false) {
        	addUserB.draw(650, 140, 150, 30);
        } else {
        	addUserHover.draw(650, 140, 150, 30);
        }
        if(hoveredSubmit == false) {	
        	submit.draw(320, 350, 200, 40);
        } else {
        	submitHover.draw(320, 350, 200, 40);
        }
        if(hoveredExit == false) {	
        	exitB.draw(320, 400, 200, 40);
        } else {
        	exitHover.draw(320, 400, 200, 40);
        }

        if(chooseUser == true) {
        	gray.draw(0,0,850,500);
        	pop.draw(100, 50, 650, 420);
        	closeB.draw(690, 75, 40, 40);
        	
        	g.setColor(Color.white);
        	chosenID.render(gc, g);
        	text.drawString(380, 80, "Users");
        	text2.drawString(180, 330, "Enter Chosen UserID Number:");
        	text2.drawString(110, 370, "*Click on text area and press Enter on keyboard to type.");
        	        	
        	userList = new ArrayList<UserClass>();
        	try { /*This part displays all users */
        		userList = userOps.getUserList();
        		
        		int x = 220, y = 120;
        		text2.drawString(170, y, "ID");
        		text2.drawString(240, y, "USERNAME");
        		text2.drawString(390, y, "FIRST NAME");
        		text2.drawString(540, y, "LAST NAME");
        		y = 160;
        		
        		for(i = 0 +(page*6); i < 6+(page*6) && i < userList.size(); i++) {
        			x = 180;
        			text2.drawString(x, y, ""+userList.get(i).getUserID());
        			x = 240;
        			text2.drawString(x, y, userList.get(i).getUsername());
        			x += 150;
        			text2.drawString(x, y, userList.get(i).getFname());
        			x += 150;
        			text2.drawString(x, y, userList.get(i).getLname());
        			y += 20;
        		}
        		
			} catch (SQLException e) {
                text2.drawString(220, 160, "There are no users yet.");
				e.printStackTrace();
			}
        	
        	if(hoveredAdd2 == false) {
        		addUserB.draw(180, 410, 150, 30);
        	} else {
        		addUserHover.draw(180, 410, 150, 30);
        	}
        	
        	if(hoveredSubmit == false) {	
        		submit.draw(350, 410, 150, 30);
	        } else {
	        	submitHover.draw(350, 410, 150, 30);
	        }
        	
        	if(hoveredDelete == false) {	
        		deleteB.draw(520, 410, 150, 30);
	        } else {
	        	deleteHover.draw(520, 410, 150, 30);
	        }
        	
        	if(page > 0) {
        		prev.draw(170, 300, 80, 30);
        	}
        	if(i < userList.size()) {
        		next.draw(580, 300, 80, 30);
        	}
        }
        
        g.setFont(font);
        
        if(addUser == true) {
        	gray.draw(0,0,850,500);
        	pop.draw(250, 50, 415, 420);
        	text.drawString(350, 100, "Add a New User");
        	closeB.draw(605, 80, 40, 40);
        	g.setColor(Color.white);
        	user.render(gc, g); 
        	first.render(gc, g);
        	last.render(gc, g);
        	
        	if(hoveredSave == false) {
        		saveB.draw(380 , 310 , 150, 30);
            } else {
        		saveHover.draw(380 , 310 , 150, 30);
        	}
        	text2.drawString(280, 360, addUserResult);
        	text2.drawString(280, 400, "*Click on text area and press");
        	text2.drawString(280, 420, " Enter on the keyboard to type.");
        }
        
        if(delUser == true) {
        	String chosenIDString = chosenID.getText().toString().trim();
			try {
			    userIDToDelete = Integer.parseInt(chosenIDString);
			    delUser = false;
			    } catch (NumberFormatException nfe) {
			    	userIDToDelete = -1;
			    	delUserResult = "userID is a number";
			    }      	
        }
        
        if(delUser2 == true) { /*Popup for confirm to delete */
        	pop.draw(225, 150, 400, 180);
        	
        	text2.drawString(280, 170, "Are you sure you want to ");
           	text2.drawString(310, 200, " delete this user? ");
           	
           	if(hoveredOk == false) {
           		okB.draw(260 , 260 , 150, 30);
            } else {
            	okHover.draw(260 , 260 , 150, 30);
            }
            if(hoveredCancel == false) {
            	cancelB.draw(440 , 260 , 150, 30);
            } else {
            	cancelHover.draw(440 , 260 , 150, 30);
            }
        }
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
        int xpos = Mouse.getX(), ypos = Mouse.getY(); 

        font.loadGlyphs();
        
        if(chooseUser == false && addUser == false && delUser2 == false) {
	        if((xpos > 320 && xpos< 520) && (ypos > 110 && ypos < 150)) { 
	        	hoveredSubmit = true;
	        	if(input.isMousePressed(GameConstants.leftClick)) {  
	        		click.play();
	                sbg.enterState(GameConstants.HOME, fadeOut, fadeIn); 
	            }
	        }
	        else if((xpos > 320 && xpos< 520) && (ypos > 60 && ypos < 100)) {
	        	hoveredExit = true;
	        	if(input.isMousePressed(GameConstants.leftClick)) {
	        		click.play();
	                gc.exit();
	            }
	        }
	        else if((xpos > 650 && xpos< 800) && (ypos > 370 && ypos < 400)) {
	        	hoveredChoose = true;
	            if(input.isMousePressed(GameConstants.leftClick)) {
	            	click.play();
	            	chooseUser = true;
	            }
	        }
	        else if((xpos > 650 && xpos< 800) && (ypos > 330 && ypos < 360)) { //if add new user is clicked
	        	hoveredAdd1 = true;
	            if(input.isMousePressed(GameConstants.leftClick)) {
	            	click.play();
	            	addUser = true;
	            }
	        }
	        else  {
	        	hoveredSubmit = false;
	        	hoveredChoose = false;
	        	hoveredAdd1 = false;
	        	hoveredExit = false;
	        }
        }
        
        else if(chooseUser == true && delUser2 == false){ 
        	if(chooseUser == true) {
        		if((xpos > 180 && xpos < 330) && (ypos > 60 && ypos < 90)) { /*Checks if add user is clicked*/
            		hoveredAdd2 = true;
            		if(input.isMousePressed(GameConstants.leftClick)) {
            			click.play();
            			addUser = true;
            			chooseUser = false;
    	            }
            	}
        		else if ((xpos > 350 && xpos< 500) && (ypos > 60 && ypos < 90)) { /*Checks if enter is clicked*/
    	        	hoveredSubmit = true;
    	        	if(input.isMousePressed(GameConstants.leftClick)) {
    	        		click.play();
            			String chosenIDString = chosenID.getText().toString().trim();
                		try { /*This part sets chosen user for the whole game */
                		    Integer.parseInt(chosenIDString);
                		    try {
        						currUser = userOps.getUserByIDOrUsername(Integer.parseInt(chosenIDString), null);
        					} catch (SQLException e) {
        						e.printStackTrace();
        					}	
                		} catch (NumberFormatException nfe) {
                			try {
        						currUser = userOps.getUserByIDOrUsername(-1, chosenIDString);
        					} catch (SQLException e) {
        						e.printStackTrace();
        					}   
                		}
                		userString = currUser.getUsername();
                		chooseUser = false;		
    	            }
    	        }
        		else if((xpos > 520 && xpos < 670) && (ypos > 60 && ypos < 90)) { /*Checks if delete with input id is clicked*/
             		hoveredDelete = true;
        			if(input.isMousePressed(GameConstants.leftClick)) {
        				click.play();
             			delUser = true;
             			if(userIDToDelete != -1) {
             				delUser2 = true;
             			}
     	            }
             	}
        		else if((xpos > 690 && xpos < 730) && (ypos > 385 && ypos < 425)) { /*Checks if close button is clicked*/ 
            		if(input.isMousePressed(GameConstants.leftClick)) {
            			click.play();
            			chooseUser = false;
            			addUser = false;
    	            }
            	}
        		else {
        			hoveredAdd2 = false;
        			hoveredDelete = false;
        			hoveredSubmit = false;
        		}
        		if(page > 0) {
        			if((xpos > 170 && xpos < 250) && (ypos > 170 && ypos < 200)) { /*Checks if prev arrow is clicked*/
                		if(input.isMousePressed(GameConstants.leftClick)) {
                			if(page > 0) {
                				click.play();
                				page = page - 1;
                			}
        	            }
                	}
            	}
            	if(i < userList.size()) {
            		if((xpos > 580 && xpos < 660) && (ypos > 170 && ypos < 200)) { /*Checks if next arrow is clicked*/
                		if(input.isMousePressed(GameConstants.leftClick)) {
                			if(i < userList.size()) {
                				click.play();
                				page = page + 1;
                			}
        	            }
                	}
            	}
        	} 
        }
        else if(addUser == true) {
        	if((xpos > 380 && xpos < 530) && (ypos > 160 && ypos < 190)) { /*Checks if save is clicked*/
        		hoveredSave = true;
        		if(input.isMousePressed(GameConstants.leftClick)) { 
        			click.play();
        			if(user.getText().toString().isEmpty() || 
        					first.getText().toString().isEmpty() ||
        					last.getText().toString().isEmpty() )
        			{
        				addUserResult = "Don't leave any textbox blank.";
        			} else {            				
        				UserClass newUser = new UserClass ();
            			newUser.setUsername(user.getText()); 
            			newUser.setFname(first.getText());
            			newUser.setLname(last.getText());
            			
        				try { /*This part adds user details entered */
    						addUserResult = userOps.addUser(newUser);
    					} catch (SQLException e) {
    						e.printStackTrace();
    					}
            			
            			if(UserOperations.userExists == false) {
            				try {
    							currUser = userOps.getUserByIDOrUsername(-1, newUser.getUsername());
    						} catch (SQLException e) {
    							e.printStackTrace();
    						}
            			}
        			}
        			
        			
        		}
        	}
    		else if((xpos > 605 && xpos < 645) && (ypos > 380 && ypos < 420)) { /*Checks if close button in is clicked*/
        		if(input.isMousePressed(GameConstants.leftClick)) {
        			click.play();
        			chooseUser = false;
        			addUser = false;
        			addUserResult = ""; 
	            }
        	}
        	else {
           		hoveredSave = false;
        	}
        }
        else if(chooseUser == true && delUser2 == true) {
        	if((xpos > 260 && xpos < 410) && (ypos > 210 && ypos < 240)) { /*Checks if Ok in delete popup is clicked*/
    			hoveredOk = true; 
    			if(input.isMousePressed(GameConstants.leftClick)) {
    				click.play();
    				try { /*This part delete user chosen */
						userOps.deleteUserByID(userIDToDelete);
					} catch (SQLException e) {
						e.printStackTrace();
					}
    				delUser2 = false;
    				
    				int id = -1;
    				try {
    					id = userOps.getLatestUser(); 
    					
    					if(id == -1) {
    						System.out.println("getLatestUser is not executing properly!!");
    					}
    					else {
    						currUser = userOps.getUserByIDOrUsername(id, null);
    					}
    				} catch (SQLException e) {
    					e.printStackTrace();
    				} 
    			}
    		}
    		else if((xpos > 440 && xpos < 590) && (ypos > 210 && ypos < 240)) { /*Checks if cancel in delete popup is clicked*/
    			hoveredCancel = true;
    			if(input.isMousePressed(GameConstants.leftClick)) {
    				click.play();
        			delUser2 = false;
        			chosenID.setText("userID");
    			}
    		}
    		else {
    			hoveredCancel = false;
    			hoveredOk = false;
    		}
        }
	}

	@Override
	public int getID() {
		return -1;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		user = new TextField(gc, font, 350 , 150 , 200 , 35, new ComponentListener() {
        	public void componentActivated(AbstractComponent source) {
        		user.setFocus(true);
            }
        });
        user.setText("username");
        user.setBorderColor(null);
        user.setMaxLength(10); 
        
        first = new TextField(gc, font, 350 , 200 , 200 , 35, new ComponentListener() {
        	public void componentActivated(AbstractComponent source) {
        		first.setFocus(true);
            }
        });
        first.setText("first name");
        first.setBorderColor(null);
        
        last = new TextField(gc, font, 350 , 250 , 200 , 35, new ComponentListener() {
        	public void componentActivated(AbstractComponent source) {
        		last.setFocus(true);
            }
        });
        last.setText("last name");
        last.setBorderColor(null);
        
        chosenID = new TextField(gc, font, 510 , 330 , 80 , 35, new ComponentListener() {
        	public void componentActivated(AbstractComponent source) {
        		chosenID.setFocus(true);
            }
        });
        chosenID.setText("userID");
        chosenID.setBorderColor(null);
        chosenID.setMaxLength(8);
  	}
	
	@SuppressWarnings("unchecked")
	public UnicodeFont getNewFont(String fontName , int fontSize)
    {
        font = new UnicodeFont(new Font(fontName , Font.ITALIC + Font.BOLD, fontSize));
        font.addGlyphs("@");
        font.getEffects().add(new ColorEffect(java.awt.Color.white));
        
        return (font);
    }

}
