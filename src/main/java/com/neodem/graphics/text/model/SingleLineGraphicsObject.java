package com.neodem.graphics.text.model;

public abstract class SingleLineGraphicsObject implements GraphicsObject {

	private String line = "";
	
	protected void setLine(String line) {
		this.line = line;
	}

	public int getHeight() {
		return 1;
	}

	public String getLine(int lineIndex) {
		return line;
	}
	
	@Override
	public String toString() {
		return getAsString();
	}

	public String getAsString() {
		return line;
	}
	
	public int getWidth() {
		return line.length();
	}

}
