package com.connectgas.app.utils;

import java.util.Random;

public class PasswordUtil {

	@SuppressWarnings("unused")
	public static String generateRandomPassword() {
		int length = 8;
		String symbol = "&*_!@%";
		String cap_letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String small_letter = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		String finalString = small_letter + numbers;

		Random random = new Random();

		char[] password = new char[length];

		for (int i = 0; i < length; i++) {
			password[i] = finalString.charAt(random.nextInt(finalString.length()));

		}
		return new String(password);
	}

}
