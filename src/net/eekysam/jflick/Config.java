package net.eekysam.jflick;

import java.util.Arrays;
import java.util.List;

public class Config
{
	public static boolean debug = false;

	public static char inc = '+';
	public static char dec = '-';
	public static char left = '<';
	public static char right = '>';
	public static char read = ',';
	public static char write = '.';
	public static char jump = '[';
	public static char back = ']';

	public static List<Character> valid = Arrays.asList(inc, dec, left, right, read, write, jump, back);

	public static boolean isValid(char c)
	{
		return valid.contains(c);
	}

	public static char enter = '\n';
}
