package databases.jdbc.assignments;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class MovieManager 
{
	//SQL Queries against Movie Table
	private static final String CreateMovieStatement = "insert into movie (id,title,posterimage,releaseDate) values (?,?,?,?)";
	private static final String SelectAllMovies = "select * from movie";
	private static final String ReadParticularMovie = "select * from movie where id = ?";
	private static final String UpdateMovie = "update movie set title = ?,posterimage = ?,releasedate = ? where id = ?";
	private static final String DeleteMovie = "delete from movie where id = ?";
	
	public void createMovie(Movie newMovie) throws NamingException 
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(CreateMovieStatement);
		)
		{
			statement.setString(1, newMovie.getId());
			statement.setString(2, newMovie.getTitle());
			statement.setString(3, newMovie.getPosterImage());
			statement.setDate(4, newMovie.getReleaseDate());
			statement.execute();
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		} 
	}

	public List<Movie> readAllMovies() throws NamingException 
	{
		List<Movie> movies = new ArrayList<Movie>();
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(SelectAllMovies);	
				ResultSet result = statement.executeQuery();
			)
		{
			while (result.next()) 
			{
				String id = result.getString("id");
				String title = result.getString("title");
				String posterImage = result.getString("posterImage");
				Date releaseDate = result.getDate("releaseDate");
				Movie movie = new Movie(id, title, posterImage, releaseDate);
				movies.add(movie);
			}
		}
		catch (SQLException e)
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		} 
		return movies;
	}

	public Movie readMovie(String movieId) throws NamingException, SQLException {

		Movie newMovie = null;
		ResultSet result = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadParticularMovie);	
				
			)
		{
			statement.setString(1, movieId);
			result = statement.executeQuery();
			result.first(); // Move the cursor to the first row
			int row = result.getRow(); // check if the row is not zero i.e. a row is being returned
			if (row == 1)
			{

				String id = result.getString("id");
				String title = result.getString("title");
				String posterimage = result.getString("posterimage");
				Date releasedate = result.getDate("releasedate");
				newMovie = new Movie(id, title, posterimage, releasedate);
			}
			else
				System.err.println("No Movie Found with ID = " + movieId);

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
		return newMovie;

	}

	public void updateMovie(String movieId, Movie movie) throws NamingException {
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(UpdateMovie);
		)
		{
			statement.setString(1, movie.getTitle());
			statement.setString(2, movie.getPosterImage());
			statement.setDate(3, movie.getReleaseDate());
			statement.setString(4, movieId);
			statement.execute();
		} 
		catch (SQLException e) 
		{
			DbUtilities.ProcessException(e);
		}
	}

	public void deleteMovie(String movieId) throws NamingException
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(DeleteMovie);
		)
		{
			statement.setString(1, movieId);
			statement.execute();

		} catch (SQLException e) 
		{
			e.printStackTrace();
		} 
	}
}
