package com.first.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEnconder = new BCryptPasswordEncoder();
		System.out.println(passwordEnconder.encode("abcd"));		
	}

}
