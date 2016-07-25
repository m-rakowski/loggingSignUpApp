package pl.lublin.zeto.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lublin.zeto.mapping.LogAndUser;
import pl.lublin.zeto.mapping.User;

/**
 * This class is responsible for operating on the database.
 * <p>
 * 
 * @author Michal Rakowski
 *
 */
public class DAO
{
	/**
	 * This is the logger for the application. The log is stored in /logs/log4j-log.log
	 */
	final static Logger logger = Logger.getLogger(DAO.class);
	
	/**
	 * Method inserts a user into table Users in the database.
	 * <p>
	 * 
	 * @param user To be inserted into table Users in the database.
	 */
	public static void insertDataIntoTableUsers(User user)
	{
		Connection c = null;
		Statement stmt = null;
		try
		{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/logging_app_db", "postgres", "");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			
			String sql;
			
			sql = "INSERT INTO users (id, name, email, passwordhash, salt, role) "
					+ "VALUES (DEFAULT, '"
					+user.getName()+"', '"
					+user.getEmail()+"', '"
					+user.getPasswordHash()+"', '"
					+user.getSalt()+"', '"
					+user.getRole()+"' );";
			
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}
	
	/**
	 * Method looks for the user in the database.
	 * 
	 * @param user
	 * @return Returns the found user or null if not found.
	 */
	public static User findUserFromDB(User user)
	{
		
		Connection c = null;
		Statement stmt = null;
		try
		{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/logging_app_db", "postgres", "");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
			while (rs.next())
			{				
				long id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");			
				String passwordHash = rs.getString("passwordHash");
				String role = rs.getString("role");
				logger.debug("in DAO in DB [id]: "+ id);
				logger.debug("in DAO in DB [name]: "+ name);
				logger.debug("in DAO in DB [email]: "+ email);
				logger.debug("in DAO in DB [passwordHash]: "+ passwordHash);
				logger.debug("in DAO in DB [role]: "+ role);
				logger.debug("got from form [name]: "+ user.getName());
				logger.debug("got from form [passwordHash]: "+ user.getPasswordHash());
				
				if(user.getName().equals(name) && user.getPasswordHash().equals(passwordHash))
				{
					logger.debug("name and login match");
					user.setEmail(email);
					user.setRole(role);
					user.setId(id);
					return user;
				}
					
				
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return null;
	}
	
	/**
	 * Method returns a list of all the logs from the database.
	 * @return
	 */
	public static List<LogAndUser> listLogsFromDB()
	{
		List<LogAndUser> list = new ArrayList<LogAndUser>(); 
		
		Connection c = null;
		Statement stmt = null;
		try
		{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/logging_app_db", "postgres", "");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT logs.id, logs.timestamp, users.name, users.id, users.email, users.role FROM logs, users WHERE users.id=logs.user_id order by timestamp desc;");
			while (rs.next())
			{
				String timestamp = rs.getString("timestamp");
				String name = rs.getString("name");
				String email = rs.getString("email");			
				String role = rs.getString("role");
				
				list.add(new LogAndUser(timestamp, name, email, role));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return list;
	}
	/**
	 * 
	 * @return
	 */
	public static List<User> select_from_table_users()
	{
		List<User> list = new ArrayList<User>(); 
		
		Connection c = null;
		Statement stmt = null;
		try
		{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/logging_app_db", "postgres", "");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
			while (rs.next())
			{				
				long id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");			
				String passwordHash = rs.getString("passwordHash");
				String role = rs.getString("role");
				
				list.add(new User(id, name, email, passwordHash, role));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return list;
	}
}