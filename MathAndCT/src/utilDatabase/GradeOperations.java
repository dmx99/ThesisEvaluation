package utilDatabase;

import grades.GradeClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import commonScreens.Welcome;

/**
 * This class contains database operations for grades.
 * @author d.caballero and m.golajer
 */
public class GradeOperations {

	private Connection connection = null;
    private Statement statement = null;
    private ResultSet rs = null;
    
    private GradeClass grade = null;
    private String query;
    
    public List<GradeClass> getGradeList(int gID) throws SQLException {
    	List<GradeClass> list = new ArrayList<GradeClass>();
    	query = "SELECT * FROM grade where gameID = "+gID +" ORDER BY dateOfGrade DESC";
	    
    	 try {
 	        connection = DBConnector.getConnection();
 	        statement = connection.createStatement();
 	        rs = statement.executeQuery(query);
 	            
 	        while (rs.next()) {
 	        	grade = new GradeClass();
 	            grade.setGradeID(rs.getInt("gradeID"));
 	            grade.setGameID(rs.getInt("gameID"));
 	            grade.setUserID(rs.getInt("userID"));
 	            grade.setNumberGrade(rs.getInt("numberGrade"));
 	            grade.setGrade((rs.getString("grade")));
 	            grade.setDateOfGrade((rs.getString("dateOfGrade")));
 	 
 	            list.add(grade);
 	        }
 	     } finally {
 	    	 statement.close();
 	         connection.close();
 	     }
 	        
    	 return list;
    }
    
    public void addGrade(GradeClass newgrade) {
    	
    	try {
	            connection = DBConnector.getConnection();
	            statement = connection.createStatement(); 
	            
	            Date today = new Date();
	            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	            String currentDate = dateFormat.format(today); 
	            query = "INSERT INTO grade(gameID, userID, dateOfGrade, grade, numberGrade) VALUES('" 
	            						+ newgrade.getGameID() +"', '" + Welcome.currUser.getUserID()
	            						+"', '" + currentDate +"', '" +newgrade.getGrade()
	            						+"', '" + newgrade.getNumberGrade()  +"')";
            	
	            statement.executeUpdate(query);
	    
    	} catch (SQLException e) {
    		e.printStackTrace();
	    } 
    	finally {
    		try {
    			statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}   
	    }
    }
}
