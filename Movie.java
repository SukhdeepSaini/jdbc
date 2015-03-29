package databases.jdbc.assignments;

import java.sql.Date;

public class Movie 
{
	private String id;
	private String title;
	private String posterImage;
	private Date releaseDate;
	
	public Movie()
	{
		super();
	}
	
	public Movie(String id, String title, String posterImage, Date releaseDate) 
	{
		super();
		this.id = id;
		this.title = title;
		this.posterImage = posterImage;
		this.releaseDate = releaseDate;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getId()
	{
		return this.id;
	}

	public void setTitle(String title)
	{
		this.title=title;
	}
	
	public String getTitle()
	{
		return this.title;
	}

	public void setPosterImage(String posterimage)
	{
		this.posterImage = posterimage;
	}
	
	public String getPosterImage()
	{
		return this.posterImage;
	}
	
	public Date getReleaseDate() 
	{
		return this.releaseDate;
	}

	public void setReleaseDate(Date releaseDate)
	{
		this.releaseDate = releaseDate;
	}

}
