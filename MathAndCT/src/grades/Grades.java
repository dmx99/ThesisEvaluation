package grades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import utilCommon.Layout;
import utilDatabase.GradeOperations;
import utilDatabaseUser.UserOperations;
import commonScreens.Home;

/**
 * This class contains the grades screen.
 * @author d.caballero and m.golajer
 */
public class Grades extends Layout {

	protected Image backB, prev, next;
	protected Sound click;
	protected int gameNumber;
	protected String gameName;
	protected int page, i;
	UserOperations userOps;
	GradeOperations gradeOps;
	List<GradeClass> gradeList;
	
	public Grades(int state) { 
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initLayout();
		
		backB = new Image("res/buttons/back.png");
		prev = new Image("res/buttons/back.png");
        next = new Image("res/buttons/forward.png");

        click = new Sound("res/sounds/click2.ogg");
        
		gameName = Home.gradeC.getGameName();
		gameNumber = Home.gradeC.getGameIDforGrade();

		page = 0;
		gradeList = new ArrayList<GradeClass>();
		userOps = new UserOperations();
		gradeOps = new GradeOperations();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gameName = Home.gradeC.getGameName();
		gameNumber = Home.gradeC.getGameIDforGrade();
		
		back.draw(0, 0, 850, 500);
		backB.draw(15, 400, 90, 100);
		tin.draw(690, 240, 140, 250);
		text.drawString(100, 30, "Scores for "+gameName, Color.cyan);
		
		try { /*This part displays all the grades per game */
    		gradeList = gradeOps.getGradeList(gameNumber);
    		
    		int x = 30, y = 140;
    		text2.drawString(50, 100, "DATE");
			text2.drawString(200, 100, "USER");
			text2.drawString(350, 100, "SCORE");
			text2.drawString(450, 100, "REMARKS");
    		
			for(i = 0 +(page*11); i < 11+(page*11) && i < gradeList.size(); i++) {
    			int userID = gradeList.get(i).getUserID();
    			String user = userOps.getUserByIDOrUsername(userID, null).getUsername();
    			x = 40;
    			text2.drawString(x, y, gradeList.get(i).getDateOfGrade());
    			x += 150;
    			text2.drawString(x, y, user);
    			x += 160;
    			text2.drawString(x, y, Integer.toString(gradeList.get(i).getNumberGrade()));
    			x += 100;
    			text2.drawString(x, y, gradeList.get(i).getGrade());
    			y += 20;
    		}
		
		} catch (SQLException e) {
			text2.drawString(30, 100, "There are no list of grades because");
			text2.drawString(30, 130, "no one hasn't played this game yet.");
			e.printStackTrace();
		}
		
		if(page > 0) {
    		prev.draw(630, 430, 80, 50);
    	}
    	if(i < gradeList.size()) {
    		next.draw(750, 430, 80, 50);
    	}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX(), ypos = Mouse.getY(); 
		
		if((xpos >15 && xpos <105) && (ypos <100)) { /*Checks if back to home is clicked*/
			if(input.isMousePressed(GameConstants.leftClick)){
				click.play();
				sbg.enterState(GameConstants.HOME);  
			}
		}
    	else if((xpos > 630 && xpos < 710) && (ypos > 20 && ypos < 70)) { /*Checks if prev arrow is clicked*/
    		if(input.isMousePressed(GameConstants.leftClick)) {
    			if(page > 0) {
    				click.play();
    				page = page - 1;
    			}
            }
    	}
    	else if((xpos > 750 && xpos < 830) && (ypos > 20 && ypos < 70)) { /*Checks if next arrow is clicked*/
    		if(input.isMousePressed(GameConstants.leftClick)) {
    			if(i < gradeList.size()) {
    				click.play();
    				page = page + 1;
    			}
            }
    	}
	}

	@Override
	public int getID() {
		return -3;
	}

}
