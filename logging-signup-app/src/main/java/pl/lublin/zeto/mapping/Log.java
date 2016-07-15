package pl.lublin.zeto.mapping;

public class Log
{
	protected long id;
	protected String timestamp;
	protected long userId;
	
	public Log() { }
	public Log(String timestamp, long userId)
	{
	
		this.timestamp = timestamp;
		this.userId = userId;
	}
	
	public long getId()	{ return id; }
	public void setId(long id) { this.id = id; }
	
	public String getTimestamp() { return timestamp; }
	public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
	
	public long getUserId()	{ return userId; }
	public void setUserId(long userId) { this.userId = userId; }
}
