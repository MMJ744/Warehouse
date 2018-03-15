package src.com.rp25.tools;

import java.util.regex.Pattern;

public class StringMethods {

	public static String[] split(String str, String regex, int limit) {
		return Pattern.compile(regex).split(str, limit);
	}
}
