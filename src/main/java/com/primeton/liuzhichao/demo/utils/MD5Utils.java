package com.primeton.liuzhichao.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.security.crypto.codec.Hex;

public class MD5Utils {
	
	private static final String SALT = "1234567812345678";
	
	public static String md5(String rawPassword) {
		
		rawPassword = md5Hex(rawPassword+SALT);
		char[] cs = new char[48];
		for(int i=0; i< 48; i+= 3) {
			cs[i] = rawPassword.charAt(i / 3 * 2); 
			char c = SALT.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = rawPassword.charAt(i / 3 * 2 + 1);
		}
		//System.out.println("------cs-------:"+String.valueOf(cs));
		return String.valueOf(cs);

	}
	
	private static String md5Hex(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(str.getBytes());
			return new String(new Hex().encode(digest));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
		
	}



}
