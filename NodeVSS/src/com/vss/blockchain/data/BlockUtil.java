package com.vss.blockchain.data;

import java.security.MessageDigest;

public class BlockUtil
{

	public static String computeHash(String str)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Applies sha256 to our input,
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++)
			{
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	// Returns difficulty string target, to compare to hash. eg difficulty of 5 will
	// return "00000"
	public static String getDificultyString(int difficulty)
	{
		return new String(new char[difficulty]).replace('\0', '0');
	}
}
