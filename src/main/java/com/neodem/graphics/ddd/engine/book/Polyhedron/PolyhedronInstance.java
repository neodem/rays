package com.neodem.graphics.ddd.engine.book.Polyhedron;

import java.awt.Graphics;

import com.neodem.graphics.ddd.engine.book.Camera.GenericCamera;
import com.neodem.graphics.ddd.engine.book.Matrix.Matrix3d;
import com.neodem.graphics.ddd.engine.book.structures.Angle3d;
import com.neodem.graphics.ddd.engine.book.structures.ArrayOf3dPoints;
import com.neodem.graphics.ddd.engine.book.structures.Point3d;

public class PolyhedronInstance {

	protected ArrayOf3dPoints transformedVertices;
	protected Matrix3d transformMatrix;
	protected AbstractPolyhedron polyhedron;
	protected Point3d position;
	protected Angle3d angle;
	protected boolean positionIsDirty;
	protected boolean angleIsDirty;

	public PolyhedronInstance(AbstractPolyhedron poly) {
		polyhedron = poly;
		
		try {
			transformedVertices = (ArrayOf3dPoints)polyhedron.getVertices().makeClone();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		position = new Point3d();
		angle = new Angle3d();
		transformMatrix = new Matrix3d();
	}
	
	public void setOrientation(Point3d newPosition, Angle3d newAngle) {
		if(position.equals(newPosition)) {
			position.set(newPosition);
			positionIsDirty = true;
		}
		
		if(angle.equals(newAngle) == false) {
			angle.set(newAngle);
			angleIsDirty = true;
		}
	}
	
	public void paint(Graphics g, GenericCamera camera) {
		if(positionIsDirty || angleIsDirty) {
			transformMatrix.makeMCStoWCStransform(position, angle);
			transformMatrix.transform(polyhedron.getVertices(), transformedVertices);
			positionIsDirty = false;
			angleIsDirty = false;
		}
		
		polyhedron.paint(g,camera.project(transformedVertices));	
	}
}
