package com.victor.utils;

import java.security.*;
import java.math.*;

public class Password {
	protected static String hash(String password) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes(), 0, password.length());
			
			return new BigInteger(1, md5.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
