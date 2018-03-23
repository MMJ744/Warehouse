package com.rp25.tools;

import java.util.regex.Pattern;

public class HelperMethods {

	public static String[] split(String str, String regex, int limit) {
		return Pattern.compile(regex).split(str, limit);
	}

}
