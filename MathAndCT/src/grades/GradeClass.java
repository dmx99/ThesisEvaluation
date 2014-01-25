package grades;

/**
 * This class contains details to be used to save grade to the DB.
 * @author d.caballero and m.golajer
 */
public class GradeClass {

	private int gradeID;
	private int gameID;
	private int userID;
	private int numberGrade;
	private int gameIDforGrade;
    private String grade, dateOfGrade;
    private String gameName;
	
    public int getGradeID() {
		return gradeID;
	}
	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getNumberGrade() {
		return numberGrade;
	}
	public void setNumberGrade(int numberGrade) {
		this.numberGrade = numberGrade;
	}
	public int getGameIDforGrade() {
		return gameIDforGrade;
	}
	public void setGameIDforGrade(int gameIDforGrade) {
		this.gameIDforGrade = gameIDforGrade;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getDateOfGrade() {
		return dateOfGrade;
	}
	public void setDateOfGrade(String dateOfGrade) {
		this.dateOfGrade = dateOfGrade;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

}
