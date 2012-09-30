package com.banka.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
	
	public static String hashPassword(String password) {
		String hashword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);

		} catch (NoSuchAlgorithmException nsae) {
			System.out.println("*******hashleme sýrasýnda hata");
		}
		return hashword;
	}
	
	private boolean checkPassword(String par_sPass){
		String hashedPass = hashPassword(par_sPass);
		
		if( hashedPass.equals("") ){
			return true;
		}
		else{
			return false;
		}
			
	}
	
}
