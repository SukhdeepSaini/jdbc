package databases.jdbc.assignments;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class UserManager 
{
	
	//SQL Queries against Users Table
	private static final String CreateUserStatement = "insert into users (username,password,firstname,lastname,email,dateofbirth) values (?,?,?,?,?,?)";
	private static final String SelectAllUsers = "select * from users";
	private static final String ReadParticularUser = "select * from users where userName = ?";
	private static final String UpdateUser = "update user set firstName = ?,lastName = ?,password = ?,email = ?,dateofbirth = ? where username = ?";
	private static final String DeleteUser = "delete from user where username = ?";

	public void createUser(User newUser) throws SQLException, NamingException
	{
		try 
		(
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(CreateUserStatement);
		)
		{
		
			statement.setString(1, newUser.getUsername());
			statement.setString(2, newUser.getPassword());
			statement.setString(3, newUser.getFirstName());
			statement.setString(4, newUser.getLastName());
			statement.setString(5, newUser.getEmail());
			statement.setDate(6, newUser.getDateOfBirth());
			statement.execute();
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
	}

	public List<User> readAllUsers() throws NamingException
	{
		List<User> users = new ArrayList<User>();
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(SelectAllUsers);	
				ResultSet result = statement.executeQuery();
			)
		{

			String userName,password,firstName,lastName,email;
			Date dateOfBirth;

			while(result.next())
			{
				userName = result.getString("username");
				password = result.getString("password");
				firstName = result.getString("firstName");
				lastName = result.getString("lastName");
				email = result.getString("email");
				dateOfBirth = result.getDate("dateOfBirth");

				User newUser = new User(userName,password,firstName,lastName,email,dateOfBirth);
				users.add(newUser);
			}
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}

		return users;
	}
	
	public User readUser(String userName) throws SQLException, NamingException
	{
		User newUser = null;
		ResultSet result = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadParticularUser);	
				
			)
		{

			String password,firstName,lastName,email;
			Date dateOfBirth;
			statement.setString(1, userName);
			result = statement.executeQuery();
			result.first(); // Move the cursor to the first row
			int row = result.getRow(); // check if the row is not zero i.e. a row is being returned
			if(row == 1)
			{
				password = result.getString("password");
				firstName = result.getString("firstName");
				lastName = result.getString("lastName");
				email = result.getString("email");
				dateOfBirth = result.getDate("dateOfBirth");
				newUser = new User(userName,password,firstName,lastName,email,dateOfBirth);
			}
			else
				System.err.println("There is no user with Username = " + userName);

		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
		finally
		{
			if(result != null)
				result.close();
		}
		return newUser;
	}
	
	public void updateUser(String username, User user) throws NamingException 
	{
		
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(UpdateUser);
		)
		{
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getEmail());
			statement.setDate(5, user.getDateOfBirth());
			statement.setString(6, username);
			statement.execute();
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		} 
	}

	public void deleteUser(String username) throws NamingException 
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(DeleteUser);
		)
		{			
			statement.setString(1, username);
			statement.execute();
		}
		catch (SQLException e)
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
	}
}
