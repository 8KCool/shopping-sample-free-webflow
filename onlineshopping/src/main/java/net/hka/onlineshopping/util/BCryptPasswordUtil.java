package net.hka.onlineshopping.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Utility class can be used to generates an encoded BCrypt password 
 * 
 * @author Hany Kamal
 *
 */
public class BCryptPasswordUtil {
	
	public static String passwordEncoder(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		return hashedPassword;
	}
	
	public static void main(String[] args) {
		System.out.println(passwordEncoder("123"));
	}
}
