package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private Connection connection;
	private final String URL = "jdbc:mysql://localhost:3306/controle_estoque";
	private final String USER = "root";
	private final String PASSWORD = "root";
	
	public Connection openConnection()
	{
		try
		{
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public void closeConnection()
	{
		try
		{
			this.connection.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
