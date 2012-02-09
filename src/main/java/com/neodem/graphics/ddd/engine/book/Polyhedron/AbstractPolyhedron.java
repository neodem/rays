package com.neodem.graphics.ddd.engine.book.Polyhedron;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

import com.neodem.graphics.dd.engine.common.AbstractGameObject;
import com.neodem.graphics.ddd.engine.book.Polygon.AbstractIndexingPolygon;
import com.neodem.graphics.ddd.engine.book.Polygon.FilledPolygon;
import com.neodem.graphics.ddd.engine.book.structures.ArrayOf2dPoints;
import com.neodem.graphics.ddd.engine.book.structures.ArrayOf3dPoints;

public abstract class AbstractPolyhedron extends AbstractGameObject {

	protected ArrayOf3dPoints verticies;
	protected AbstractIndexingPolygon[] polygons;
	protected int numberOfPolygons;
	
	protected AbstractPolyhedron(ArrayOf3dPoints points, AbstractIndexingPolygon[] polygons, int numberOfPolygons) {
		this.verticies = points;
		this.polygons = polygons;
		this.numberOfPolygons = numberOfPolygons;
	}
	
	protected AbstractPolyhedron(InputStream is) throws IOException {
		createFromInputStream(is);
	}
	
	public abstract void paint(Graphics g, ArrayOf2dPoints point2d);
	
	public String getStringRepresentation() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(verticies.getStringRepresentation());
		buffer.append(numberOfPolygons);
		buffer.append("\n");
		for(int n=0;n<numberOfPolygons;n++) {
			buffer.append(polygons[n].getStringRepresentation());
		}
		buffer.append(" ");
		return buffer.toString();
	}
	
	public void createFromInputStream(InputStream is) throws IOException {
		Reader reader = new BufferedReader(new InputStreamReader(is));
		StreamTokenizer stream = new StreamTokenizer(reader);
		stream.commentChar('#');

		verticies = new ArrayOf3dPoints(is);
		stream.nextToken();
		numberOfPolygons = (int)stream.nval;
		polygons = new AbstractIndexingPolygon[numberOfPolygons];
		
		for(int n=0;n<numberOfPolygons;n++) {
			polygons[n] = new FilledPolygon(is);
		}
	}
	
	public ArrayOf3dPoints getVertices() {
		return verticies;
	}
}
