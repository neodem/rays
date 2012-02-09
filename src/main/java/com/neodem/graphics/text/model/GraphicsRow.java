package com.neodem.graphics.text.model;

import java.util.List;

public interface GraphicsRow {

	public void add(GraphicsObject o);

	public boolean isValid();
	
	public int getMaxLines();

	public List<GraphicsObject> getGraphicObjects();
}
