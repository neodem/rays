package com.neodem.graphics.text.model;

import org.apache.commons.lang3.StringUtils;

public class ConsoleTools {

	public static String spaces(int num) {
		return chars(num, ' ');
	}

	public static String chars(int num, char c) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < num; i++) {
			b.append(c);
		}
		return b.toString();
	}

	public static char newLine() {
		return '\n';
	}

	public static String newLines(int num) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < num; i++) {
			b.append(newLine());
		}
		return b.toString();
	}

	/**
	 * return a string filled with c but with target centered in it
	 * 
	 * @param target
	 * @param size (must be above 4 else we return "(<size> spaces)")
	 * @param c
	 * @return
	 */
	public static String centerInto(String target, int size, char c) {
		if(size < 4) {
			return spaces(size);
		}
		
		String abbreviated = StringUtils.abbreviate(StringUtils.defaultString(target), size);
		int tSize = abbreviated.length();
		
		int fillerSize = size - tSize;
		int fillerFront = fillerSize/2;
		
		StringBuffer b = new StringBuffer();
		b.append(chars(fillerFront, c));
		b.append(abbreviated);
		b.append(chars(fillerSize-fillerFront, c));
		
		return b.toString();
	}

}
