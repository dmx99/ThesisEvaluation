package grades;

/**
 * This class contains details to be used to compute the grade.
 * @author d.caballero and m.golajer
 */
public class GradeDetails {

	private int easyQuestions=0;
	private int aveQuestions=0;
	private int hardQuestions=0;
	private int missedLevel=0;
	private long overAllTime=0;
	private String grade = "";
	private int gradeScore=0;
	private int gameID;
	
	public int getEasyQuestions() {
		return easyQuestions;
	}
	public void setEasyQuestions(int easyQuestions) {
		this.easyQuestions = easyQuestions;
	}
	public int getAveQuestions() {
		return aveQuestions;
	}
	public void setAveQuestions(int aveQuestions) {
		this.aveQuestions = aveQuestions;
	}
	public int getHardQuestions() {
		return hardQuestions;
	}
	public void setHardQuestions(int hardQuestions) {
		this.hardQuestions = hardQuestions;
	}
	public int getMissedLevel() {
		return missedLevel;
	}
	public void setMissedLevel(int missedLevel) {
		this.missedLevel = missedLevel;
	}
	public long getOverAllTime() {
		return overAllTime;
	}
	public void setOverAllTime(long overAllTime) {
		this.overAllTime = overAllTime;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getGradeScore() {
		return gradeScore;
	}
	public void setGradeScore(int gradeScore) {
		this.gradeScore = gradeScore;
	}
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
}
