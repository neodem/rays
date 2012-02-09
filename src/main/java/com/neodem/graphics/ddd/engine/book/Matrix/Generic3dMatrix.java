package com.neodem.graphics.ddd.engine.book.Matrix;

import com.neodem.graphics.ddd.engine.book.structures.ArrayOf3dPoints;

public class Generic3dMatrix {

	protected double xx, xy, xz, xo;
	protected double yx, yy, yz, yo;
	protected double zx, zy, zz, zo;

	public Generic3dMatrix() {
		makeIdentity();
	}

	public void makeIdentity() {
		xx = 1;
		xy = 0;
		xz = 0;
		xo = 0;

		yx = 0;
		yy = 1;
		yz = 0;
		yo = 0;

		zx = 0;
		zy = 0;
		zz = 1;
		zo = 0;
	}

	/**
	 * does a "smart" multiply about the z-axis
	 * 
	 * @param az
	 */
	public void concatRz(double az) {
		double ct = Math.cos(az);
		double st = Math.sin(az);

		double newYX = (yx * ct + xx * st);
		double newYY = (yy * ct + xy * st);
		double newYZ = (yz * ct + xz * st);
		double newYO = (yo * ct + xo * st);

		double newXX = (xx * ct - yx * st);
		double newXY = (xy * ct - yy * st);
		double newXZ = (xz * ct - yz * st);
		double newXO = (xo * ct - yo * st);

		xx = newXX;
		xy = newXY;
		xz = newXZ;
		xo = newXO;

		yx = newYX;
		yy = newYY;
		yz = newYZ;
		yo = newYO;
	}

	/**
	 * does a "smart" multiply about the y-axis
	 * 
	 * @param ay
	 */
	public void concatRy(double ay) {
		double ct = Math.cos(ay);
		double st = Math.sin(ay);

		double newXX = (xx * ct + zx * st);
		double newXY = (xy * ct + zy * st);
		double newXZ = (xz * ct + zz * st);
		double newXO = (xo * ct + zo * st);

		double newZX = (zx * ct - xx * st);
		double newZY = (zy * ct - xy * st);
		double newZZ = (zz * ct - xz * st);
		double newZO = (zo * ct - xo * st);

		xx = newXX;
		xy = newXY;
		xz = newXZ;
		xo = newXO;

		zx = newZX;
		zy = newZY;
		zz = newZZ;
		zo = newZO;
	}

	/**
	 * does a "smart" multiply about the x-axis
	 * 
	 * @param az
	 */
	public void concatRx(double ax) {
		double ct = Math.cos(ax);
		double st = Math.sin(ax);

		double newYX = (yx * ct + zx * st);
		double newYY = (yy * ct + zy * st);
		double newYZ = (yz * ct + zz * st);
		double newYO = (yo * ct + zo * st);

		double newZX = (zx * ct - yx * st);
		double newZY = (zy * ct - yy * st);
		double newZZ = (zz * ct - yz * st);
		double newZO = (zo * ct - yo * st);

		zx = newZX;
		zy = newZY;
		zz = newZZ;
		zo = newZO;

		yx = newYX;
		yy = newYY;
		yz = newYZ;
		yo = newYO;
	}

	/**
	 * does a "smart" multiply for a translation
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void concatT(double x, double y, double z) {
		xo += x;
		yo += y;
		zo += z;
	}
	
	/** does a "smart" multiply for scaling
	 * 
	 * @param sx
	 * @param sy
	 * @param sz
	 */
	public void concatS(double sx, double sy, double sz) {
		xx *= sx;
		xy *= sx;
		xz *= sx;
		xo *= sx;
		
		yx *= sy;
		yy *= sy;
		yz *= sy;
		yo *= sy;
		
		zx *= sz;
		zy *= sz;
		zz *= sz;
		zo *= sz;	
	}
	
	/** multiplies the vector ps and stores the result into pd
	 * 
	 * @param ps
	 * @param pd
	 */
	public void transform(ArrayOf3dPoints ps, ArrayOf3dPoints pd) {
		for(int i=0; i<ps.numberOfPoints; i++) {
			double x = ps.x[i];
			double y = ps.y[i];
			double z = ps.z[i];
			
			pd.x[i] = x*xx + y*xy + z*xz + xo;
			pd.y[i] = x*yx + y*yy + z*yz + yo;
			pd.z[i] = x*zx + y*zy + z*zz + zo;
		}
	}

}
