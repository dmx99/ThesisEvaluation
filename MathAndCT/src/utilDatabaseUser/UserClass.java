package utilDatabaseUser;

/**
 * This class contains details to be used to save user to the DB.
 * @author d.caballero and m.golajer
 */
public class UserClass {

	private int userID;
	private int currUserID;
    private String username; 
    private String lname;
    private String fname;
    private String dateJoined;
	
    public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getCurrUserID() {
		return currUserID;
	}
	public void setCurrUserID(int currUserID) {
		this.currUserID = currUserID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	} 
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(String dateJoined) {
		this.dateJoined = dateJoined;
	}
}
