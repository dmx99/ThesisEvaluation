package utilCommon;

/**
 * This class contains all game global constants.
 * @author d.caballero and m.golajer
 */
public class GameConstants {

	/*Screen IDs and States*/
	public static final int BINARYGAME_ID = 1;
	public static final int COMPEQGAME_ID = 2;
	public static final int TABLEGAME_ID = 3;
	public static final int NXTNUMGAME_ID = 4;
	
	public static final int OVER = -6;
	public static final int SPLASH = -5;
	public static final int TUTORIALS = -4;
	public static final int GRADES = -3;
	public static final int WELCOME = -1;
	public static final int HOME = 0;
	
	/*General Game Details Constants*/
	public static final String GAMENAME = "Math&CT";
	
	public static final int EASY_LEVEL = 1;
	public static final int AVE_LEVEL = 2; 
	public static final int HARD_LEVEL = 3;
	
	public static final int QUESTIONS_PER_LEVEL = 4;
	public static final int QUESTIONS_MAX = 12;
	
	public static final String BINGAME_NAME = "Binary Numbers";
	public static final String COMPEQGAME_NAME = "Complete the Equation";
	public static final String TABLEGAME_NAME = "Multiplying Numbers";
	public static final String NXTNUMGAME_NAME = "Finding Patterns";
	
	public static final int leftClick = 0;
	
	/* Constants for Binary Game*/ 
	public static final int BIN_L1_MAXTIME = 20;
	public static final int BIN_L2_MAXTIME = 30;
	public static final int BIN_L3_MAXTIME = 45;
	public static final int BINGAME_NORMAL_MAXTIME = 380;
	
	/* Constants for Complete the Equation Game*/ 
	public static final int COMPEQ_L1_MAXTIME = 25; 
	public static final int COMPEQ_L2_MAXTIME = 30;
	public static final int COMPEQ_L3_MAXTIME = 40;
	public static final int COMPEQGAME_NORMAL_MAXTIME = 380;
	
	public static final int ADDITION = 1;
	public static final int SUBTRACTION = 2;
	public static final int MULTIPLICATION = 3;
	public static final int DIVISION = 4;
	
	/* Constants for Table Game*/ 
	public static final int TABLE_L1_MAXTIME = 20;
	public static final int TABLE_L2_MAXTIME = 25;
	public static final int TABLE_L3_MAXTIME = 30;
	public static final int TABLEGAME_NORMAL_MAXTIME= 300;
	
	/* Constants for Next Number Game*/ 
	public static final int NXTNUM_L1_MAXTIME = 20;
	public static final int NXTNUM_L2_MAXTIME = 30;
	public static final int NXTNUM_L3_MAXTIME = 40;
	public static final int NXTNUMGAME_NORMAL_MAXTIME = 360;
	
	/* Constants for Game Evaluation*/ 
	public static final int SCOREWEIGHT_EASY = 1;
	public static final int SCOREWEIGHT_AVE = 2;
	public static final int SCOREWEIGHT_HARD = 3;
	
	public static final String SCORE_E = "Excellent";
	public static final String SCORE_VS = "Very Satisfactory";
	public static final String SCORE_S = "Satisfactory";
	public static final String SCORE_NI = "Needs Improvement";
	public static final String SCORE_P = "Poor";
}
