package com.neodem.rays;

import com.neodem.rays.maths.Angles;

/**
 * Math stuff for a Ray that we are going to project from a point on the WorldMap. Everything
 * here operates on the assumption that the world map has 0,0 in the top left and Y is Down
 * and X is Right
 * <p>
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/11/20
 */
public class Ray {

    // origin of the ray in the coordinate system of the World
    private final FloatingPoint rayOrigin;

    private final float viewAngle;

    // angle is 0-359 with 0 being UP and 180 being DOWN
    private final float angle;

    // the direction of the ray
    private final Quadrant quadrant;

    /**
     * @param x
     * @param y
     * @param angle     the ray Angle
     * @param viewAngle the view Angle
     */
    public Ray(float x, float y, float angle, float viewAngle) {
        this(new FloatingPoint(x, y), angle, viewAngle);
    }

    public Ray(FloatingPoint origin, float angle, float viewAngle) {
        if (origin == null) throw new IllegalArgumentException("origin may not be null");
        this.rayOrigin = origin;

        if (angle < 0) throw new IllegalArgumentException("angle needs to be 0-359, was " + angle);
        if (!(angle < 360)) throw new IllegalArgumentException("angle needs to be 0-359, was " + angle);
        this.angle = angle;

        this.viewAngle = viewAngle;

        this.quadrant = Angles.determineQuadrant(angle);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "rayOrigin=" + rayOrigin +
                ", rayAngle=" + angle +
                ", viewAngle=" + viewAngle +
                ", quadrant=" + quadrant +
                '}';
    }

    public FloatingPoint getOrigin() {
        return rayOrigin;
    }

    public float getAngle() {
        return angle;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public float getViewAngle() {
        return viewAngle;
    }
}
