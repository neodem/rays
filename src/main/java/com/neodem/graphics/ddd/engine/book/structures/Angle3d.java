package com.neodem.graphics.ddd.engine.book.structures;

public class Angle3d {

	public double x;
	public double y;
	public double z;

	public Angle3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Angle3d() {
		x = y = z = 0;
	}

	public Angle3d(Angle3d a) {
		x = a.x;
		y = a.y;
		z = a.z;
	}

	public boolean equals(Angle3d a) {
		return (a.x == x) && (a.y == y) && (a.z == z);
	}

	public void set(Angle3d a) {
		x = a.x;
		y = a.y;
		z = a.z;
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
