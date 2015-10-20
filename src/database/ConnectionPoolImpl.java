package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionPoolImpl implements IConnectionPool{
	
	private static ConnectionPoolImpl instance = null;
	
	
	
	@Override
	public Connection getConnection() throws SQLException {
		
		Connection connection = null;
		 String dbUrl = "jdbc:mysql://localhost/test";
	        String dbClass = "com.mysql.jdbc.Driver";
	        String username = "root";
	        String password = "password";
	        try {

	            Class.forName(dbClass);
	             connection = DriverManager.getConnection(dbUrl,
	                username, password);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
		
		return connection;
	}
	
	public static ConnectionPoolImpl getInstance() {
	    if(instance == null){
	        instance = new ConnectionPoolImpl();
	    }
	    
	    return instance;
	}

}