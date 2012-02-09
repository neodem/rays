package com.neodem.graphics.ddd.engine.book.structures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.neodem.graphics.dd.engine.common.AbstractGameObject;

public class ArrayOf3dPoints extends AbstractGameObject {
	public double x[];
	public double y[];
	public double z[];
	public int numberOfPoints;
	
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	public ArrayOf3dPoints(double x[], double y[], double z[], int numberOfPoints) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.numberOfPoints = numberOfPoints;
	}
	
	public ArrayOf3dPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
		x = new double[numberOfPoints];
		y = new double[numberOfPoints];
		z = new double[numberOfPoints];
	}
	
	public ArrayOf3dPoints(InputStream is) throws IOException {
		createFromInputStream(is);
	}
	
	public String getStringRepresentation() {
		StringBuffer b = new StringBuffer();
		b.append(" ");
		b.append(numberOfPoints);
		b.append("\n");
		
		for(int n=0; n<numberOfPoints; n++) {
			b.append(x[n]);
			b.append(" ");
			b.append(y[n]);
			b.append(" ");	
			b.append(z[n]);
			b.append("\n");				
		}
		
		return b.toString();	
	}
	
	public ArrayOf3dPoints makeClone() {
		double xNew[];
		double yNew[];
		double zNew[];
		System.arraycopy(x,0,xNew = new double[numberOfPoints],0, numberOfPoints);
		System.arraycopy(y,0,yNew = new double[numberOfPoints],0, numberOfPoints);
		System.arraycopy(z,0,zNew = new double[numberOfPoints],0, numberOfPoints);
		
		return new ArrayOf3dPoints(xNew, yNew, zNew, numberOfPoints);
	}

	public void createFromInputStream(InputStream is) throws IOException {
		Reader r = new BufferedReader(new InputStreamReader(is));
		StreamTokenizer stream = new StreamTokenizer(r);
		stream.commentChar('#');

		stream.nextToken();
		numberOfPoints = (int) stream.nval;
		
		x = new double[numberOfPoints];
		y = new double[numberOfPoints];
		z = new double[numberOfPoints];

		for (int n = 0; n < numberOfPoints; n++) {
			stream.nextToken();
			x[n] = (double) stream.nval;
			stream.nextToken();
			y[n] = (double) stream.nval;
			stream.nextToken();
			z[n] = (double) stream.nval;
		}	
	}
}
