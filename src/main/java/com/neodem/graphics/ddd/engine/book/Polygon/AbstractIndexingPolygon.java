package com.neodem.graphics.ddd.engine.book.Polygon;

import java.awt.Graphics;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

import com.neodem.graphics.dd.engine.common.AbstractGameObject;

public abstract class AbstractIndexingPolygon extends AbstractGameObject {
	protected int[] indices;

	protected int numberOfIndices;

	protected static Polygon scratchPoly = new Polygon(new int[50], new int[50], 50);

	protected AbstractIndexingPolygon(int indices[], int n) {
		this.indices = indices;
		numberOfIndices = n;
	}

	public AbstractIndexingPolygon(InputStream is) throws IOException {
		createFromInputStream(is);
	}
	
	public abstract void paint(Graphics g, int x[], int y[]);

	public void createFromInputStream(InputStream is) throws IOException {
		Reader r = new BufferedReader(new InputStreamReader(is));
		StreamTokenizer stream = new StreamTokenizer(r);
		stream.commentChar('#');

		stream.nextToken();
		numberOfIndices = (int) stream.nval;

		indices = new int[numberOfIndices];

		for (int i = 0; i < numberOfIndices; i++) {
			stream.nextToken();
			indices[i] = (int) stream.nval;
		}
	}
	
	public String getStringRepresentation() {
		StringBuffer b = new StringBuffer();
		b.append(numberOfIndices);
		
		for(int n=0; n<numberOfIndices; n++) {
			b.append(" ");
			b.append(indices[n]);		
		}
		b.append("\n");
		
		return b.toString();
	}

	protected void copyIndexedPoints(int x[], int y[]) {
		for (int n = 0; n < numberOfIndices; n++) {
			int i = indices[n];
			scratchPoly.xpoints[n] = x[i];
			scratchPoly.ypoints[n] = y[i];
		}

		scratchPoly.npoints = numberOfIndices;
	}

	protected static int orientation() {
		int p1x = scratchPoly.xpoints[1];
		int p1y = scratchPoly.ypoints[1];

		int v1x = scratchPoly.xpoints[2] - p1x;
		int v1y = scratchPoly.ypoints[2] - p1y;

		int v2x = scratchPoly.xpoints[0] - p1x;
		int v2y = scratchPoly.ypoints[0] - p1y;

		return v1x * v2y - v2x * v1y;
	}
	
	public abstract AbstractIndexingPolygon makeClone();

}
