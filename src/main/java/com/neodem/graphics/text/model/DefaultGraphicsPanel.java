package com.neodem.graphics.text.model;

import java.util.ArrayList;
import java.util.List;


public class DefaultGraphicsPanel implements GraphicsPanel {
	
	public static final int DEFAULT_OBJECT_H_SPACING = 3;
	public static final int DEFAULT_OBJECT_V_SPACING = 2;
	
	private List<GraphicsRow> rows;
	
	/**
	 * spacing between objects
	 */
	private int hSpacing = DEFAULT_OBJECT_H_SPACING;
	private int vSpacing = DEFAULT_OBJECT_V_SPACING;
	
	public DefaultGraphicsPanel() {
		rows = new ArrayList<GraphicsRow>();
	}

	public void addRow(GraphicsRow row) {
		if(row.isValid()) {
			rows.add(row);
		}
	}

	public String asString() {
		StringBuffer b = new StringBuffer();
		
		// draw each row
		for(GraphicsRow row : rows) {
			List<GraphicsObject> objects = row.getGraphicObjects();
			int height = row.getMaxLines();
			for(int lineIndex=0; lineIndex < height; lineIndex++) {
				for(GraphicsObject object : objects) {
					b.append(object.getLine(lineIndex));
					b.append(ConsoleTools.spaces(hSpacing));
				}
				b.append(ConsoleTools.newLine());
			}
			b.append(ConsoleTools.newLines(vSpacing));
		}
		
		return b.toString();
	}

	/**
	 * @return the hSpacing
	 */
	public int getHSpacing() {
		return hSpacing;
	}

	/**
	 * @param hSpacing the hSpacing to set
	 */
	public void setHSpacing(int hSpacing) {
		this.hSpacing = hSpacing;
	}

	/**
	 * @return the vSpacing
	 */
	public int getVSpacing() {
		return vSpacing;
	}

	/**
	 * @param vSpacing the vSpacing to set
	 */
	public void setVSpacing(int vSpacing) {
		this.vSpacing = vSpacing;
	}

}
