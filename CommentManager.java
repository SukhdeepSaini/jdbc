package databases.jdbc.assignments;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class CommentManager
{

	//SQL Queries against Comment Table
	private static final String CreateCommentStatement = "insert into comment (comment,date,username,movieid) values (?,?,?,?)";
	private static final String SelectAllComments = "select * from comment";
	private static final String ReadCommentsForaUser = "select * from comment where user = ?";
	private static final String ReadCommentsForaMovie = "select * from comment where movie = ?";
	private static final String ReadCommentsForaCommentId =  "select * from comment where commentId = ?";
	private static final String UpdateComment = "update comment set comment = ? where commentid = ?";
	private static final String DeleteComment = "delete from comment where commentid = ?";

	public void createComment(Comment comment) throws NamingException 
	{
		try 
		(
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(CreateCommentStatement);
		)
		{
			//No Need to insert Comment ID as it is Auto Increment
			statement.setString(1, comment.getComment());
			statement.setDate(2, comment.getDate());
			statement.setString(3, comment.getUser());
			statement.setString(4, comment.getMovie());
			statement.execute();
		}
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
	}

	public List<Comment> readAllComments() throws NamingException
	{
		List<Comment> comments = new ArrayList<Comment>();
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(SelectAllComments);	
				ResultSet result = statement.executeQuery();
			)
		{
			String userName,comment,movieId,CommentId;
			Date date;
			
			while (result.next()) 
			{
				comment = result.getString("comment");
				date = result.getDate("date");
				userName = result.getString("username");
				movieId = result.getString("movieid");
				CommentId = result.getString("commentId");
				Comment newComment = new Comment(comment, date, userName, movieId,CommentId);
				comments.add(newComment);
			}
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		} 
		return comments;
	}

	public List<Comment> readAllCommentsForUsername(String userName) throws NamingException, SQLException 
	{
		List<Comment> comments = new ArrayList<Comment>();
		ResultSet result = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadCommentsForaUser);	
				
			)
		{
			String comment,movieId,CommentId;
			Date date;
			
			statement.setString(1, userName);
			result = statement.executeQuery();
			result.last();
			int row = result.getRow();
			if(row == 0)
				System.err.println("There are no comments with the user having username =" + userName);
			else
			{
				result.beforeFirst();
				while (result.next()) 
				{
					comment = result.getString("comment");
					date = result.getDate("date");
					movieId = result.getString("movieid");
					CommentId = result.getString("commentId");
					Comment newComment = new Comment(comment, date, userName, movieId,CommentId);
					comments.add(newComment);
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
		return comments;
	}

	public List<Comment> readAllCommentsForMovie(String movieId) throws NamingException, SQLException
	{
		List<Comment> comments = new ArrayList<Comment>();
		ResultSet result = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadCommentsForaMovie);	
				
			)
		{
			
			String comment,userName,CommentId;
			Date date;
			
			statement.setString(1, movieId);
			result = statement.executeQuery();
			result.last();
			int row = result.getRow();
			if(row == 0)
				System.err.println("There are no comments with the movie having movie ID =" + movieId);
			else
			{
				result.beforeFirst();
				while (result.next()) 
				{
					comment = result.getString("comment");
					date = result.getDate("date");
					userName = result.getString("userName");
					CommentId = result.getString("commentId");
					Comment newComment = new Comment(comment, date, userName, movieId,CommentId);
					comments.add(newComment);
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
		return comments;
	}

	public Comment readCommentForId(String commentId) throws NamingException, SQLException 
	{
		Comment comment = null;
		ResultSet result = null;
		try (
				//Implements AutoClosable Interface
				Connection conn = DbUtilities.getConnection();
				PreparedStatement statement = conn.prepareStatement(ReadCommentsForaCommentId);	
				
			)
		{
			
			String userName,commentDesc,movieId;
			Date date;
			
			statement.setString(1, commentId);
			result = statement.executeQuery();
			result.last();
			int row = result.getRow();
			if(row == 0)
				System.err.println("There are no comments with the ID =" + commentId);
			else
			{
				result.beforeFirst();
				while (result.next()) 
				{
					commentDesc = result.getString("comment");
					date = result.getDate("date");
					userName = result.getString("userName");
					movieId = result.getString("movieId");
				    comment = new Comment(commentDesc, date, userName, movieId,commentId);

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
		return comment;
	}

	public void updateComment(String commentId, String commentDesc) throws NamingException
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(UpdateComment);
		)
		{
			statement.setString(1, commentDesc);
			statement.setString(2, commentId);
			statement.execute();
		} 
		catch (SQLException e) 
		{
			//Logging of exceptions
			DbUtilities.ProcessException(e);
		}
	}

	public void deleteComment(String commentId) throws NamingException
	{
		try 
		(
			//Implements AutoClosable Interface
			Connection conn = DbUtilities.getConnection();
			PreparedStatement statement = conn.prepareStatement(DeleteComment);
		)
		{
			statement.setString(1, commentId);
			statement.execute();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
	}
}
