package pl.lublin.zeto.mapping;

public class User
{
	private long id;
	private String name;
	private String email;
	private String passwordHash;
	private String passwordHash2;
	private String salt;
	private String role;
	
	public User()
	{
		
	}
	public User(long id, String name, String email, String passwordHash, String role)
	{
		this.id = id;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;		
	}
	
	public long getId()	{ return id; }
	public void setId(long id) { this.id = id; }
	
	public String getName()	{ return name; }
	public void setName(String name) { this.name = name; }
	
	public String getEmail()	{ return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getPasswordHash()	{ return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
	
	public String getPasswordHash2()	{ return passwordHash2; }
	public void setPasswordHash2(String passwordHash2) { this.passwordHash2 = passwordHash2; }
	
	public String getSalt()	{ return salt; }
	public void setSalt(String salt) { this.salt = salt; }
	
	public String getRole()	{ return role; }
	public void setRole(String role) { this.role = role; }
}
