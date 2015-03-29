package databases.jdbc.assignments;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DbUtilities
{	
	public static Connection getConnection() throws SQLException, NamingException
	{
		
		InitialContext myContext = new InitialContext();
		DataSource dataSource = (DataSource) myContext.lookup("java:comp/env/jdbc/");
		
		try
		{
			return dataSource.getConnection();
		}
		catch (SQLException e)
		{
			ProcessException(e);
		}		
		return null;
	}
	
	public static void ProcessException(SQLException e)
	{
		System.err.println("Error Occured is " + e.getMessage());
		System.err.println("Error Code " + e.getErrorCode());
		System.err.println("SQL State " + e.getSQLState());
	}
}
