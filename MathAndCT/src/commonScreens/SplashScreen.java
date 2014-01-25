package commonScreens;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import utilCommon.GameConstants;
import utilCommon.Layout;

/**
 * This class contains the splash screen.
 * @author d.caballero and m.golajer
 */
public class SplashScreen extends Layout{

	private int elapsedTime;
	private Animation bar;
	private Image name, splash;
	private final int delay = 10000;
	
	public SplashScreen(int state){
	
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initLayout();
		
		splash = new Image("res/splash/splash1.png");
		
		Image barArray[] = {new Image("res/splash/bar0.png"), new Image("res/splash/bar1.png"), 
                new Image("res/splash/bar2.png"), new Image("res/splash/bar3.png"), 
                new Image("res/splash/bar4.png"), new Image("res/splash/bar5.png"), 
                new Image("res/splash/bar6.png"), new Image("res/splash/bar7.png"), 
                new Image("res/splash/bar8.png"), new Image("res/splash/bar9.png")};
		
        int duration[] = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
        name = new Image("res/splash/image.png");
        bar = new Animation(barArray, duration, true);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		splash.draw(0,0,850,500);
		bar.draw(300, 280, 230, 20);
		text.drawString(360, 300, "Loading...");
		name.draw(630,460, 220, 40);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		elapsedTime += delta;
		
		if(elapsedTime >= delay) {
			sbg.enterState(GameConstants.WELCOME);
		}
	}

	@Override
	public int getID() {
		return -5;
	}

}
