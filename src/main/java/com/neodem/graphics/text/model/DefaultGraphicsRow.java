package com.neodem.graphics.text.model;

import java.util.ArrayList;
import java.util.List;


public class DefaultGraphicsRow implements GraphicsRow {
	
	private List<GraphicsObject> objects;
	
	/**
	 * a GO can be made up of many lines. To help formatting, we keep track of the max 
	 * number of lines encountered
	 */
	private int maxLines = 0;
	
	public DefaultGraphicsRow() {
		objects = new ArrayList<GraphicsObject>();
	}
	
	public void add(GraphicsObject o) {
		if(o.isValid()) {
			objects.add(o);
			
			int height = o.getHeight();
			if(height > maxLines) {
				maxLines = height;
			}
		}
	}

	public boolean isValid() {
		return true;
	}

	public int getMaxLines() {
		return maxLines;
	}

	public List<GraphicsObject> getGraphicObjects() {
		return objects;
	}

}
