package databases.jdbc.assignments;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class ActorManager 
{
	//SQL Queries against Movie Table
	private static final String CreateActorStatement = "insert into actor (id,firstname,lastname,dateofbirth) values (?,?,?,?,?,?)";
	private static final String SelectAllActors = "select * from actor";
	private static final String ReadParticularActor = "select * from actor where id = ?";
	private static final String UpdateActor = "update actor set id = ?,firstname = ?,lastname = ?,dateOfBirth = ? where id = ?";
	private static final String DeleteActor = "delete from actor where id = ?";

	public void createActor(Actor newActor) throws NamingException 
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(CreateActorStatement);
		)
		{
			statement.setString(1, newActor.getId());
			statement.setString(2, newActor.getFirstName());
			statement.setString(3, newActor.getLastName());
			statement.setDate(4, newActor.getDateOfBirth());
			statement.execute();
		} 
		catch (SQLException e)
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		} 
	}

	public List<Actor> readAllActors() throws NamingException
	{
		List<Actor> actors = new ArrayList<Actor>();
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(SelectAllActors);	
				ResultSet result = statement.executeQuery();
			)
		{
			while (result.next())
			{
				String id = result.getString("id");
				String firstname = result.getString("firstname");
				String lastname = result.getString("lastname");
				Date dateofbirth = result.getDate("dateOfBirth");
				Actor newActor = new Actor(id, firstname, lastname, dateofbirth);
				actors.add(newActor);
			}
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
		return actors;
	}

	public Actor readActor(String actorId) throws NamingException, SQLException 
	{
		Actor newActor = null;
		ResultSet result = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadParticularActor);	
				
			)
		{
			statement.setString(1, actorId);
			result = statement.executeQuery();
			result.first(); // Move the cursor to the first row
			int row = result.getRow(); // check if the row is not zero i.e. a row is being returned
			if (row == 1)
			{
				String id = result.getString("id");
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				Date dateOfBirth = result.getDate("dateOfBirth");
				newActor = new Actor(id, firstName, lastName, dateOfBirth);
			}
			else
				System.err.println("No such Actor was found with ID = " + actorId);
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
		return newActor;

	}

	public void updateActor(String actorId, Actor actor) throws NamingException
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(UpdateActor);
		)
		{
			statement.setString(1, actor.getId());
			statement.setString(2, actor.getFirstName());
			statement.setString(3, actor.getLastName());
			statement.setDate(4, actor.getDateOfBirth());
			statement.setString(5, actorId);
			statement.execute();
		} 
		catch (SQLException e)
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
	}

	public void deleteActor(String actorId) throws NamingException
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(DeleteActor);
		)
		{
			statement.setString(1,actorId);
			statement.execute();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}

