package grades;

import utilCommon.GameConstants;
import utilDatabase.GradeOperations;
import commonScreens.Welcome;

/**
 * This class evaluates the grade.
 * @author d.caballero and m.golajer
 */
public class GradeEvaluator {

	protected int overallEasy = 0;
	protected int overallAve = 0;
	protected int overallHard = 0;
	protected int overallGrade = 0;
	protected int gameId, gameMaxTime;
	protected long overallTime;
	public static GradeDetails grades = new GradeDetails();
	
	public void computeGrade(GradeDetails gradeDetails) {
		
		gameId = gradeDetails.getGameID();
		
		overallEasy = gradeDetails.getEasyQuestions() * GameConstants.SCOREWEIGHT_EASY;
		
		overallAve = gradeDetails.getAveQuestions() * GameConstants.SCOREWEIGHT_AVE;
		
		overallHard = gradeDetails.getHardQuestions() * GameConstants.SCOREWEIGHT_HARD;
		
		overallGrade = overallEasy + overallAve + overallHard;
		
		gradeDetails.setGradeScore(overallGrade);
		
		if(overallGrade <= 12) {
			gradeDetails.setGrade(GameConstants.SCORE_P);
		}
		else if(overallGrade == 14 || overallGrade == 16) {
			gradeDetails.setGrade(GameConstants.SCORE_NI);
		}
		else if(overallGrade == 18 || overallGrade == 20) {
			gradeDetails.setGrade(GameConstants.SCORE_S);
		}
		else if(overallGrade == 22) {
			gradeDetails.setGrade(GameConstants.SCORE_VS);
		}
		else if(overallGrade == 24) {
			
			overallTime = gradeDetails.getOverAllTime();
			
			if(gameId == GameConstants.BINARYGAME_ID) {
				gameMaxTime = GameConstants.BINGAME_NORMAL_MAXTIME;
			}
			else if(gameId == GameConstants.COMPEQGAME_ID) {
				gameMaxTime = GameConstants.COMPEQGAME_NORMAL_MAXTIME;
			}
			else if(gameId == GameConstants.TABLEGAME_ID) {
				gameMaxTime = GameConstants.TABLEGAME_NORMAL_MAXTIME;
			}
			else if(gameId == GameConstants.NXTNUMGAME_ID) {
				gameMaxTime = GameConstants.NXTNUMGAME_NORMAL_MAXTIME; 
			}
			 
			if(overallTime < gameMaxTime) { /*overallGrade = 25*/
				gradeDetails.setGrade(GameConstants.SCORE_E);
				overallGrade += 1;
				gradeDetails.setGradeScore((overallGrade));
			} 
			else if(overallTime == gameMaxTime) {
				gradeDetails.setGrade(GameConstants.SCORE_E);
			}
			else { /*overallGrade == 23*/
				gradeDetails.setGrade(GameConstants.SCORE_VS);
				overallGrade -= 1;
				gradeDetails.setGradeScore((overallGrade));
			}
		} 
		grades = gradeDetails;
		GradeClass gradeClass = new GradeClass();
		gradeClass.setGameID(gameId);
		gradeClass.setGameIDforGrade(gameId);
		gradeClass.setUserID(Welcome.currUser.getUserID());
		gradeClass.setGrade(gradeDetails.getGrade());
		gradeClass.setNumberGrade(overallGrade);
		
		GradeOperations gradeOp = new GradeOperations();
		gradeOp.addGrade(gradeClass);
	}
}
