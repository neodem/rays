package com.neodem.graphics.text.model;

public interface GraphicsObject {

	boolean isValid();

	/**
	 * 
	 * @return number of lines high
	 */
	int getHeight();
	
	int getWidth();

	/**
	 * get one of the lines
	 * 
	 * @param lineIndex
	 * @return
	 */
	String getLine(int lineIndex);
	
	String getAsString();
}
