package com.neodem.graphics.ddd.engine.book.Polyhedron;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import com.neodem.graphics.ddd.engine.book.Polygon.AbstractIndexingPolygon;
import com.neodem.graphics.ddd.engine.book.structures.ArrayOf2dPoints;
import com.neodem.graphics.ddd.engine.book.structures.ArrayOf3dPoints;

public class ConvexPolyhedron extends AbstractPolyhedron {

	public ConvexPolyhedron(ArrayOf3dPoints points, AbstractIndexingPolygon[] polygons, int numberOfPolygons) {
		super(points, polygons, numberOfPolygons);
	}
	
	public ConvexPolyhedron(InputStream is) throws IOException {
		super(is);
	}

	public void paint(Graphics g, ArrayOf2dPoints point2d) {
		for(int n=0;n<numberOfPolygons;n++) {
			polygons[n].paint(g, point2d.x, point2d.y);
		}
	}

}
