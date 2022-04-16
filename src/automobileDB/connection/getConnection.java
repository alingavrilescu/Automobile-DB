package automobileDB.connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class getConnection {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		getConnection pro = new getConnection();
		pro.createConnection();
	}

	public static Connection createConnection() throws ClassNotFoundException, SQLException
	{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodb", "root","eda5fe17");
			return con;
	}
	
	
	
}
