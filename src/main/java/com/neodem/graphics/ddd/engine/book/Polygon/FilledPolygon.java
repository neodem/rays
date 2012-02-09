package com.neodem.graphics.ddd.engine.book.Polygon;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import com.neodem.graphics.dd.engine.common.Color;

public class FilledPolygon extends AbstractIndexingPolygon {

	protected Color color;

	public FilledPolygon(int indices[], int numberOfIndices, Color color) {
		super(indices, numberOfIndices);
		this.color = color;
	}
	
	public FilledPolygon(InputStream is) throws IOException {
		super(is);
	}
	
	
	public void paint(Graphics g, int[] x, int[] y) {
		copyIndexedPoints(x,y);
		render(g);
	}

	protected void render(Graphics g) {
		if(orientation() > 0) {
			g.setColor(color.getAWTColor());
			g.fillPolygon(scratchPoly);
		}
	}

	public AbstractIndexingPolygon makeClone() {
		int i[];
		System.arraycopy(indices, 0, i= new int[numberOfIndices],0, numberOfIndices);
		return new FilledPolygon(i, numberOfIndices, color.makeClone());
	}
	
	public void createFromInputStream(InputStream is) throws IOException {
		super.createFromInputStream(is);
		color = new Color(is);
	}
	
	public String getStringRepresentation() {
		String str = super.getStringRepresentation();
		StringBuffer buffer = new StringBuffer(str);
		buffer.append(" ");
		buffer.append(color.getStringRepresentation());
		buffer.append("\n");
		
		return buffer.toString();
	}
	
}
