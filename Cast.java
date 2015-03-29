package databases.jdbc.assignments;

public class Cast
{
	//Foreign Key References to actor and movie, add these fields here for the foreign key constraint
	private String characterName;
	private String actor;
	private String movie;
	private String castId; // Primary Key
	
	public Cast() 
	{
		super();
	}

	public Cast(String characterName, String actor, String movie,String castID) 
	{
		super();
		this.characterName = characterName;
		this.actor = actor;
		this.movie = movie;
		this.castId = castID;
	}

	public String getCharacterName() 
	{
		return this.characterName;
	}
	
	public void setCharacterName(String characterName)
	{
		this.characterName = characterName;
	}
	
	public String getActor() 
	{
		return this.actor;
	}
	
	public void setActor(String actor) 
	{
		this.actor = actor;
	}
	
	public String getMovie() 
	{
		return this.movie;
	}
	
	public void setMovie(String movie)
	{
		this.movie = movie;
	}
	
	public String getCastId() 
	{
		return this.castId;
	}
	
	public void setCastId(String castId)
	{
		this.castId = castId;
	}
}
