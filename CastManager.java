package databases.jdbc.assignments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class CastManager 
{
	//SQL Queries against Cast Table
	private static final String CreateCastStatement = "insert into cast (charactername,actor,movie) values (?,?,?)";
	private static final String SelectAllCast = "select * from cast";
	private static final String ReadCastForanActor = "select * from cast where actor = ?";
	private static final String ReadCastForaMovie = "select * from cast where movie = ?";
	private static final String ReadCastForaCastId =  "select * from cast where castid = ?";
	private static final String UpdateCast = "update cast set charactername = ?,actor = ?,movie = ? where castid = ? ";
	private static final String DeleteCast = "delete from cast where castid = ?";

	void createCast(Cast newCast) throws NamingException
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(CreateCastStatement);
		)
		{
			//No need to insert for Cast Id as it is auto increment in table
			statement.setString(1, newCast.getCharacterName());
			statement.setString(2, newCast.getActor());
			statement.setString(3, newCast.getMovie());
			statement.execute();
		} 
		catch (SQLException e)
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		} 
	}

	public List<Cast> readAllCast() throws NamingException 
	{
		List<Cast> allCasts = new ArrayList<Cast>();
		Cast cast = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(SelectAllCast);	
				ResultSet result = statement.executeQuery();
			)
		{
			while (result.next())
			{
				String characterName = result.getString("charactername");
				String actor = result.getString("actor");
				String movie = result.getString("movie");
				String castId = result.getString("castId");
				cast = new Cast(characterName, actor, movie,castId);
				allCasts.add(cast);
			}
		}
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		} 
		
		return allCasts;
	}

	public List<Cast> readAllCastForActor(String actorId) throws NamingException, SQLException {
		List<Cast> allCasts = new ArrayList<Cast>();
		ResultSet result = null;
		Cast cast = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadCastForanActor);	
				
			)
		{
			statement.setString(1, actorId);
			result = statement.executeQuery();
			result.last();
			int row = result.getRow();
			if(row == 0)
				System.err.println("There are no cast with the actor having actor ID =" + actorId);
			else
			{
				result.beforeFirst();
				while (result.next()) {
					String characterName = result.getString("charactername");
					String movie = result.getString("movie");
					String castId = result.getString("castId");
					cast = new Cast(characterName, actorId, movie,castId);
					allCasts.add(cast);
				}
			}
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
		return allCasts;
	}

	public List<Cast> readAllCastForMovie(String movieId) throws NamingException, SQLException {
		List<Cast> allCasts = new ArrayList<Cast>();
		ResultSet result = null;
		Cast cast = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadCastForaMovie);	
				
			)
		{
			statement.setString(1, movieId);
			result = statement.executeQuery();
			result.last();
			int row = result.getRow();
			if(row == 0)
				System.err.println("There are no cast with the movie having movie ID =" + movieId);
			else
			{
				result.beforeFirst();
				while (result.next()) {
					String characterName = result.getString("charactername");
					String actorId = result.getString("actor");
					String castId = result.getString("castId");
					cast = new Cast(characterName, actorId, movieId,castId);
					allCasts.add(cast);
				}
			}
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
		return allCasts;
	}

	public Cast readCastForId(String castId) throws SQLException, NamingException 
	{
		ResultSet result = null;
		Cast cast = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadCastForaCastId);	
				
			)
		{
			statement.setString(1, castId);
			result = statement.executeQuery();
			result.last();
			int row = result.getRow();
			if(row == 0)
				System.err.println("There are no cast with the Cast ID =" + castId);
			else
			{
				result.beforeFirst();
				while (result.next()) {
					String characterName = result.getString("charactername");
					String actorId = result.getString("actor");
					String movie = result.getString("movie");
					cast = new Cast(characterName, actorId, movie,castId);
				}
			}
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
		return cast;
	}

	public void updateCast(String castId, Cast newCast) throws NamingException {
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(UpdateCast);
		)
		{
			statement.setString(1, newCast.getCharacterName());
			statement.setString(2, newCast.getActor());
			statement.setString(3, newCast.getMovie());
			statement.setString(4, castId);
			statement.execute();
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
	}

	public void deleteCast(String castId) throws NamingException {
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(DeleteCast);
		)
		{
			statement.setString(1,castId);
			statement.execute();
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
	}

}
