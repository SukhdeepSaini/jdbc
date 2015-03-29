package databases.jdbc.assignments;

import java.sql.Date;

public class Comment 
{
	//Foreign key constraint to User and Movie
	private String comment;
	private Date date;
	private String user;
	private String movie;
	private String commentId; //Primary Key Auto Increment
	
	public Comment() 
	{
		super();
	}

	public Comment(String comment, Date date, String user,String movie,String commentId) 
	{
		super();
		this.comment = comment;
		this.date = date;
		this.user = user;
		this.movie=movie;
		this.commentId = commentId;
	}

	public void setComment(String comment)
	{
		this.comment=comment;
	}
	
	public String getComment()
	{
		return this.comment;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public String getUser() 
	{
		return this.user;
	}
	
	public void setUser(String user) 
	{
		this.user = user;
	}
	
	public void setMovie(String movie) 
	{
		this.movie = movie;
	}
	
	public String getMovie() 
	{
		return this.movie;
	}
	
	public void setCommentId(String CommentId) 
	{
		this.commentId = CommentId;
	}
	
	public String getCommentId() 
	{
		return this.commentId;
	}
}
