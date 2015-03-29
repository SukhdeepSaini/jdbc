package databases.jdbc.assignments;

import java.sql.Date;

public class Actor 
{
	private String id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;

	public Actor() 
	{
		super();
	}

	public Actor(String id, String firstName, String lastName, Date dateOfBirth) 
	{
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setFirstName(String firstname) 
	{
		this.firstName = firstname;
	}

	public String getFirstName() 
	{
		return this.firstName;
	}

	public void setDateOfBirth(Date dateOfBirth) 
	{
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfBirth() 
	{
		return this.dateOfBirth;
	}

	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	public String getLastName() 
	{
		return lastName;
	}


}
