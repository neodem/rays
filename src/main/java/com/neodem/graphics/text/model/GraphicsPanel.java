package com.neodem.graphics.text.model;

public interface GraphicsPanel {

	void addRow(GraphicsRow row);
	
	String asString();

	void setVSpacing(int i);
	void setHSpacing(int i);

}
