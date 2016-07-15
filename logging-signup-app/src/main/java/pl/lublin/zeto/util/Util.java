package pl.lublin.zeto.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.cache.interceptor.KeyGenerator;

import pl.lublin.zeto.mapping.Log;
import pl.lublin.zeto.mapping.LogAndUser;
import pl.lublin.zeto.mapping.User;
import pl.lublin.zeto.web.controller.HelloController;

public class Util
{
	final static Logger logger = Logger.getLogger(Util.class);
		
	static void create_table()
	{
		Connection c = null;
		Statement stmt = null;
		try
		{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/logging_app_db", "postgres", "");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE COMPANY " + "(ID SERIAL PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, " + " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");

	}

	static void insert_data_into_table()
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
					+ "VALUES (DEFAULT, 'johhny', 'johnny@gmail.com', '9f9e0bc87093d0b803db2097dfd8f5e6f0167a5d63d2098bd8d5b503ba9792c3', 'ccb711f092ac8ef1805b5045fab7e8a6189cb97ad04565e21b5fbcfc9e542e42', 'user' );";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO users (id, name, email, passwordhash, salt, role) "
					+ "VALUES (DEFAULT, 'lucy', 'lucy@gmail.com', '3cd761153c52755de39c8c0c182009ae0e88b09b95ade17f82e0eb63e3d57ef7', 'ccb711f092ac8ef1805b5045fab7e8a6189cb97ad04565e21b5fbcfc9e542e42', 'user' );";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO users (id, name, email, passwordhash, salt, role) "
					+ "VALUES (DEFAULT, 'tommy', 'tommy@gmail.com', '3cd761153c52755de39c8c0c182009ae0e88b09b95ade17f82e0eb63e3d57ef7', 'ccb711f092ac8ef1805b5045fab7e8a6189cb97ad04565e21b5fbcfc9e542e42', 'user' );";
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

	public static String hashingFunction(String password)
	{
		MessageDigest md;
		try
		{
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return String.format("%064x", new java.math.BigInteger(1, digest));
		}		
		catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}

	public static byte[] generateSalt() throws NoSuchAlgorithmException
	{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[256];
		sr.nextBytes(salt);
		for (int i = 0; i < 256; i++)
		{
			System.out.print(salt[i] & 0x00FF);
			System.out.print(" ");
		}
		return salt;
	}

	public static void insert_data_into_table_Users(User user)
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
				logger.debug("in Util in DB [id]: "+ id);
				logger.debug("in Util in DB [name]: "+ name);
				logger.debug("in Util in DB [email]: "+ email);
				logger.debug("in Util in DB [passwordHash]: "+ passwordHash);
				logger.debug("in Util in DB [role]: "+ role);
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
	public static void select_from_table()
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
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String address = rs.getString("address");
				float salary = rs.getFloat("salary");
				logger.debug("ID = " + id);
				System.out.println("NAME = " + name);
				System.out.println("AGE = " + age);
				System.out.println("ADDRESS = " + address);
				System.out.println("SALARY = " + salary);
				System.out.println();
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
	}
}