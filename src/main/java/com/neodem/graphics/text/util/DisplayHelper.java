/* file 	: 	DisplayHelper.java
 * created 	:	Feb 17, 2006
 * modified :	
 */

package com.neodem.graphics.text.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Vincent Fumo (neodem@gmail.com)
 * 
 */
public class DisplayHelper {

	public static char SPACE = ' ';

	/**
	 * 
	 */
	public DisplayHelper() {
		super();
	}

	/**
	 * either add spaces or trim the input string to fit into a new string of
	 * the given side. Right Justify
	 * 
	 * @param input
	 * @param width
	 * @return
	 */
	public static String fitIntoRight(String input, int width) {
		String trimmed = StringUtils.left(input, width);
		String padded = StringUtils.leftPad(trimmed, width);
		return padded;
	}

	/**
	 * either add spaces or trim the input string to fit into a new string of
	 * the given side. Left Justify
	 * 
	 * @param input
	 * @param width
	 * @return
	 */
	public static String fitIntoLeft(String input, int width) {
		String trimmed = StringUtils.left(input, width);
		String padded = StringUtils.rightPad(trimmed, width);
		return padded;
	}

	/**
	 * 
	 * @param text
	 *            the text to surrond
	 * @param pattern
	 *            the top and bottom border pattern to use
	 * @param side
	 *            the character to put on the left and right
	 * @param size
	 *            the overall length of the box
	 */
	public static String create3RowSurroundBox(String text, String pattern, char side, int size) {
		StringBuffer b = new StringBuffer(size * 3);
		b.append(repeatedPatternLine(pattern, size));
		b.append("\n");
		b.append(centerTextLine(text, side, size));
		b.append("\n");
		b.append(repeatedPatternLine(pattern, size));
		b.append("\n");
		return b.toString();
	}

	/**
	 * size < 3 will return an empty string text will be truncated to fit size
	 * 
	 * @param text
	 * @param side
	 * @param size
	 * @return
	 */
	public static String centerTextLine(String text, char side, int size) {
		if (size < 3)
			return "";

		// remove the side chars for our math
		int available = size - 2;

		if (text == null) {
			text = "";
		}

		int textSize = text.length();
		int spaceSize = 0;
		if (textSize > available) {
			text = trim(text, available);
		} else {
			spaceSize = available - textSize;
		}

		int leftSpaceSize = spaceSize / 2;
		int rightSpaceSize = spaceSize - leftSpaceSize;

		StringBuffer b = new StringBuffer(size);
		b.append(side);
		b.append(charLine(leftSpaceSize, SPACE));
		b.append(text);
		b.append(charLine(rightSpaceSize, SPACE));
		b.append(side);

		return b.toString();
	}

	/**
	 * make a line with the given pattern (repeated as necc.)
	 * 
	 * @param pattern
	 *            pattern to use
	 * @param size
	 *            size of line
	 * @return
	 */
	public static String repeatedPatternLine(String pattern, int size) {
		if (pattern == null) {
			return charLine(size, SPACE);
		}

		if (size <= 0)
			return "";

		int patternSize = pattern.length();

		if (patternSize > size) {
			return trim(pattern, size);
		}

		int numberToUse = size / patternSize + 1;

		StringBuffer b = new StringBuffer(numberToUse * patternSize);
		for (int i = 0; i < numberToUse; i++) {
			b.append(pattern);
		}
		String result = b.toString();

		return trim(result, size);
	}

	/**
	 * create a line of characters
	 * 
	 * @param rightSpaceSize
	 * @return
	 */
	public static String charLine(int size, char c) {
		if (size <= 0)
			return "";

		StringBuffer b = new StringBuffer(size);

		for (int i = 0; i < size; i++) {
			b.append(c);
		}

		return b.toString();
	}

	public static String trim(String str, int size) {
		if (str == "")
			return "";
		if (str == null)
			return null;
		if (size > str.length())
			return str;
		return str.substring(0, size);
	}

	public static String drawCollection(Collection<?> c) {
		return drawCollectionWithSkip(c, null);
	}

	/**
	 * will draw a collection but skip the given element
	 * @param fromPins
	 * @return
	 */
	public static String drawCollectionWithSkip(Collection<?> c, Object skip) {
		StringBuffer b = new StringBuffer();
		boolean first = true;
		for (Object o : c) {
			
			if(o.equals(skip)) {
				continue;
			}
			
			if (!first) {
				b.append(',');
			}
			b.append(o.toString());
			first = false;
		}

		return b.toString();
	}

}
