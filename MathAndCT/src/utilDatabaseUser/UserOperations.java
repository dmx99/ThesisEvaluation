package utilDatabaseUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utilDatabase.DBConnector;

/**
 * This class contains database operations for user.
 * @author d.caballero and m.golajer
 */
public class UserOperations {

	private Connection connection;
    private Statement statement;
    private ResultSet rs = null;
    private UserClass user = null;
    private String query; 
    public static boolean  userExists = true; 
    
    public UserClass getUserByIDOrUsername(int userID, String username) throws SQLException {
		
    	if(username != null)  {
    		 query = "SELECT * FROM user WHERE username = '" + username +"';";
    	}
    	else if(userID >= 0) {
    		 query = "SELECT * FROM user WHERE userID = " + userID +";";
    	}
    	
    	try {
            connection = DBConnector.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
 
            if (rs.next()) {
            	user = new UserClass();
            	user.setUserID(rs.getInt("userID"));
            	user.setUsername(rs.getString("username")); 
            	user.setFname(rs.getString("fname"));
            	user.setLname((rs.getString("lname")));
            	user.setDateJoined((rs.getString("dateJoined")));
           }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            statement.close();
            connection.close();
        }
        return user;
    }
    
    public int getLatestUser() throws SQLException {
    	int id = -1;
 
    	query = "select userID from user order by userID desc;";  
    	
    	try {
    		connection = DBConnector.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query); 
            
            if(!rs.next()) {
         	   System.out.println("RS is  empty, query executed Unsuccessfully");
            } else {
        	   	id = rs.getInt("userID");
            }
    	} 
        finally  {
             statement.close();
             connection.close();
        }
         
    	 return id;
    }
    
    public List<UserClass> getUserList() throws SQLException {
    	List<UserClass> list = new ArrayList<UserClass>();
    	
    	query = "SELECT * FROM user;";
    	
    	try {
    		connection = DBConnector.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            
            while (rs.next()) {
                user = new UserClass();
                user.setUserID(rs.getInt("userID"));
            	user.setUsername(rs.getString("username")); 
            	user.setLname(rs.getString("lname"));
            	user.setFname((rs.getString("fname")));
            	user.setDateJoined((rs.getString("dateJoined")));
 
                list.add(user);
            }
        } 
        finally  {
            statement.close();
            connection.close();
        }
        
    	return list;
    }
    
    public String addUser(UserClass newUser) throws SQLException {
    	query = "SELECT * FROM user WHERE username = '" + newUser.getUsername() +"';";
        String returnString = null;
        
        try {
        	connection = DBConnector.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
 
            if(!rs.next()) { 
            	userExists = false;
            	
            	Date today = new Date();
	            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	            String currentDate = dateFormat.format(today);
            	
	            query = "INSERT INTO user VALUES(null, '" 
            						+ newUser.getUsername() +"'" 
            						+", '" +newUser.getFname() +"', '" +newUser.getLname() 
            						+"', '" +currentDate +"');";
	            
	            statement.executeUpdate(query);
            	
	            returnString = "User was SUCCESSFULLY added!";
            } else {
            	returnString = "Username ALREADY Exists!";
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
             statement.close();
             connection.close();
        }
            
        return returnString;
    }
    
    public void deleteUserByID(int userID) throws SQLException {
    	query = "DELETE FROM user WHERE userID = " +userID +";"; 
    	
    	try {
        	connection = DBConnector.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            
            query = "DELETE FROM grade WHERE userID = " +userID +";";
            statement.executeUpdate(query);
            
    	} catch (SQLException e) {
            e.printStackTrace();
        } finally {
             statement.close();
             connection.close();
        }
    	
    }
    
}
