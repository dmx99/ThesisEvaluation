package utilDatabase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This class creates the tables for the database.
 * @author d.caballero and m.golajer
 */
public class TableCreator {

	private Connection connection;
	private Statement statement;
	private String query;
	
	public void createAndInsert() {
		try {
		      connection = DBConnector.getConnection();
		      statement = connection.createStatement();
		      
//		      statement.executeUpdate("drop table if exists grade;");
//		      statement.executeUpdate("drop table if exists user;");
		      
		      query = "CREATE TABLE IF NOT EXISTS grade (" +
		              "gradeID INTEGER PRIMARY KEY NOT NULL, "+
		              "gameID INTEGER NOT NULL, "+
		              "userID INTEGER NOT NULL, "+
		              "dateOfGrade varchar(10) NOT NULL, "+
		              "grade varchar(20) NOT NULL, "+
		              "numberGrade INTEGER NOT NULL); ";
		      
		    
		      statement.executeUpdate(query);
		      
		      query = "CREATE TABLE IF NOT EXISTS user("+
		              "userID INTEGER PRIMARY KEY NOT NULL, "+
		              "username varchar(20) NOT NULL, "+ 
		              "lname varchar(20) NOT NULL, "+
		              "fname varchar(20) NOT NULL, "+
		              "dateJoined varchar(10) NOT NULL); ";
		  
		      statement.executeUpdate(query);
		      
		      //Print all tables created
		      DatabaseMetaData md = connection.getMetaData();
		      ResultSet rs = md.getTables(null, null, "%", null);
		      while (rs.next()) {
		        
		      }
		      rs = null;
		      
		      query = "SELECT * FROM user WHERE username = 'sample'";
		      rs = statement.executeQuery(query);
		      if(!rs.next()) { 
			      query = "INSERT INTO user (userID, username, lname, fname, dateJoined) "+
			              "VALUES (1, 'sample', 'first', 'last', '12/18/2013');";
			      statement.executeUpdate(query);
		      }
		      statement.close();
		      connection.close();
		
		} catch ( Exception e ) { 
		}
	}
}
