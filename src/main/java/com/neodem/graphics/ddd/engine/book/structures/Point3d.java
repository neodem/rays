package com.neodem.graphics.ddd.engine.book.structures;

public class Point3d {

	public double x;
	public double y;
	public double z;

	public Point3d(double x0, double y0, double z0) {
		x = x0;
		y = y0;
		z = z0;
	}

	public Point3d() {
		x = y = z = 0;
	}

	public Point3d(Point3d p1, Point3d p2) {
		x = p2.x - p1.x;
		y = p2.y - p1.y;
		z = p2.z - p1.z;
	}

	public Point3d(Point3d p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}

	public void vectorProduct(Point3d p1, Point3d p2) {
		x = p1.y * p2.z - p1.z * p2.y;
		y = p1.z * p2.x - p1.x * p2.z;
		z = p1.x * p2.y - p1.y * p2.x;
	}

	public void normalize(double length) {
		double t = length / Math.sqrt(x * x + y * y + z * z);
		x = t * x;
		y = t * y;
		z = t * z;
	}

	public void plus(Point3d p) {
		x += p.x;
		y += p.y;
		z += p.z;
	}

	double dotProduct(Point3d p) {
		return p.x * x + p.y * y + p.z * z;
	}

	public boolean equals(Point3d p) {
		return (p.x == x) && (p.y == y) && (p.z == z);
	}

	public void set(Point3d p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}

	public void times(double s) {
		x *= s;
		y *= s;
		z *= s;
	}

	public void negate() {
		x = -x;
		y = -y;
		z = -z;
	}

	public String toString() {
		return new String("(" + x + ", " + y + ", " + z + ")");
	}

}
