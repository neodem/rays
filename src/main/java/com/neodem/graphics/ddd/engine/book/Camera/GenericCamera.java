package com.neodem.graphics.ddd.engine.book.Camera;

import com.neodem.graphics.ddd.engine.book.Matrix.Matrix3d;
import com.neodem.graphics.ddd.engine.book.structures.Angle3d;
import com.neodem.graphics.ddd.engine.book.structures.ArrayOf2dPoints;
import com.neodem.graphics.ddd.engine.book.structures.ArrayOf3dPoints;
import com.neodem.graphics.ddd.engine.book.structures.Point3d;

public class GenericCamera {
	/** temp buffer for projection
	 * 
	 */
	protected static ArrayOf2dPoints buffer2D = new ArrayOf2dPoints(new int[100], new int[100], 100);

	/** temp buffer for WCS to VCS transform
	 * 
	 */
	protected static ArrayOf3dPoints buffer3D = new ArrayOf3dPoints(new double[100], new double[100], new double[100],
			100);
	
	/** screen distance
	 * 
	 */
	protected double screenDist;
	
	/** screen origin x
	 * 
	 */
	protected int x0;
	
	/** screen origin y
	 * 
	 */
	protected int y0;
	
	/** camera view angle
	 * 
	 */
	protected double viewAngle;
	
	/** used in the WCS to VCS transform
	 * 
	 */
	protected Matrix3d WCStoVCSmatrix;
	
	/**
	 * 
	 */
	protected boolean matrixIsDirty;
	
	/** camera position in the WCS
	 * 
	 */
	protected Point3d position;
	
	/** camera angle in the WCS
	 * 
	 */
	protected Angle3d angle;
	
	public GenericCamera(int width, int height, double viewAngle) {
		this.viewAngle = viewAngle;
		x0= width>>1;
		y0 = height >> 1;
		screenDist = (double)x0/(Math.tan(viewAngle/2));
		WCStoVCSmatrix = new Matrix3d();
		position = new Point3d();
		angle = new Angle3d();
		matrixIsDirty = true;
	}
	
	public void setOrientation(Point3d newPosition, Angle3d newAngle) {
		if(this.position.equals(newPosition) == false) {
			this.position.set(newPosition);
			matrixIsDirty = true;
		}
		
		if(this.angle.equals(newAngle) == false) {
			this.angle.set(newAngle);
			matrixIsDirty = true;
		}
	}
	
	public ArrayOf2dPoints project(ArrayOf3dPoints p3d) {
		// update the matrix
		updateMatrix();
		
		// transform the WCS vertices to VCS and store the results into a buffer
		WCStoVCSmatrix.transform(p3d, buffer3D);
		
		// project the VCS coordinates to SCS storing into a buffer
		for(int n=0;n<p3d.numberOfPoints;n++) {
			double z = buffer3D.z[n];
			buffer2D.x[n]=-(int)(screenDist * buffer3D.x[n]/z) + x0;
			buffer2D.y[n]=-(int)(screenDist * buffer3D.y[n]/z) + y0;		
		}
		
		// lend the buffer to the caller
		return buffer2D;
	}

	private void updateMatrix() {
		if(matrixIsDirty) {
			WCStoVCSmatrix.makeWCStoVCStransform(position, angle);
			matrixIsDirty = false;
		}
	}
	
}
