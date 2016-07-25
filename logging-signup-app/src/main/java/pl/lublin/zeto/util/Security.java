package pl.lublin.zeto.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This class is responsible for hashing passwords and generating salts.
 * <p>
 * 
 * @author Michal Rakowski
 *
 */
public class Security
{
	
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
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return "";
		
	}

}
