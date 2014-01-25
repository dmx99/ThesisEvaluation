package utilDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class creates the connection to the database.
 * @author d.caballero and m.golajer
 */
public class DBConnector {

	private static DBConnector instance = new DBConnector(); /*static reference to itself*/
	public static final String DRIVER_CLASS = "org.sqlite.JDBC";
	
	private DBConnector() {
		try {
			Class.forName(DRIVER_CLASS); /*Load MySQL Java driver*/
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	private Connection createConnection() {
		
	    Connection connection = null;
	    try {
	        connection = DriverManager.getConnection("jdbc:sqlite:test.db"); /*Establish Java MySQL connection*/
	    } catch (SQLException e) {
	        System.out.println("ERROR: Unable to Connect to Database.");
	    }
	    return connection;
	}  
	
	public static Connection getConnection() {
	    return instance.createConnection();
	}
}
