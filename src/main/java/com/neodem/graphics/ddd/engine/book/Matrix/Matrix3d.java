package com.neodem.graphics.ddd.engine.book.Matrix;

import com.neodem.graphics.ddd.engine.book.structures.Angle3d;
import com.neodem.graphics.ddd.engine.book.structures.Point3d;

public class Matrix3d extends Generic3dMatrix {

	/**
	 * construct the matrix
	 */
	public Matrix3d() {
		super();
	}

	/**
	 * let matrix contain the MCS to WCS transform
	 */
	public void makeMCStoWCStransform(Point3d pos, Angle3d agl, Point3d scale) {
		makeIdentity();
		concatS(scale.x, scale.y, scale.z);
		concatRx(agl.x);
		concatRy(agl.y);
		concatRz(agl.z);
		concatT(pos.x, pos.y, pos.z);
	}

	/**
	 * let matrix contain the MCS to WCS transform, without scaling
	 */
	public void makeMCStoWCStransform(Point3d pos, Angle3d agl) {
		makeIdentity();
		concatRx(agl.x);
		concatRy(agl.y);
		concatRz(agl.z);
		concatT(pos.x, pos.y, pos.z);
	}

	/**
	 * let matrix contain the WCS to MCS transform
	 */
	public void makeWCStoVCStransform(Point3d pos, Angle3d agl) {
		makeIdentity();
		concatT(-pos.x, -pos.y, -pos.z);
		concatRz(-agl.z);
		concatRy(-agl.y);
		concatRx(-agl.x);
	}

	public void makeLookAtPointTransform(Point3d p0, Point3d p1) {
		Point3d vecZaxis = new Point3d(p0, p1);
		vecZaxis.normalize(1);
		Point3d vecXaxis = new Point3d();
		vecXaxis.vectorProduct(new Point3d(0, 1, 0), p1);
		vecXaxis.normalize(1);

		Point3d vecYaxis = new Point3d();
		vecYaxis.vectorProduct(vecZaxis, vecXaxis);

		xo = -p0.x;
		yo = -p0.y;
		zo = -p0.z;
		xx = vecXaxis.x;
		xy = vecXaxis.y;
		xz = vecXaxis.z;
		yx = vecYaxis.x;
		yy = vecYaxis.y;
		yz = vecYaxis.z;
		zx = vecZaxis.x;
		zy = vecZaxis.y;
		zz = vecZaxis.z;
	}
}
