package com.neodem.graphics.rays;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/11/20
 */
public class Ray {

    // origin of the ray in the coordinate system of the World
    private final FloatingPoint origin;

    private final float angle;

    private final Quadrant quadrant;

    public Ray(FloatingPoint origin, float angle) {
        if (origin == null) throw new IllegalArgumentException("origin may not be null");
        this.origin = origin;

        if (angle < 0) throw new IllegalArgumentException("angle needs to be 0-359");
        if (angle > 359) throw new IllegalArgumentException("angle needs to be 0-359");
        this.angle = angle;

        this.quadrant = AngleHelpers.determineQuadrant(angle);
    }

    /**
     * compute all the point where this ray intersects the world map on the horizontal axis
     *
     * @param max (0 based) : the max number to compute
     * @return the intersections with the 0 index being the first one, then outwards
     */
    public List<FloatingPoint> intersectHorizontal(int max) {

        List<FloatingPoint> points = new ArrayList<>();
        if (max < 0) throw new IllegalArgumentException("max needs to be positive");

        if (max > 0) {

            float dh = getDistanceToClosestHorizontal(origin.getY(), AngleHelpers.orientUpDown(quadrant));
            float dv = getDistanceToClosestVertical(origin.getX(), AngleHelpers.orientRightLeft(quadrant));

            if (quadrant == Quadrant.UP_LEFT) {
                // compute first point!

                // closest horizontal is up
                int y = (int) origin.getY();

                // closest vertical is to the left
                int cv = (int) origin.getX();

                // angle off the axis
                float localAngle = 360 - angle;

                double tan = Math.tan(Math.toRadians(localAngle));

                float x = (float) (tan * dh) + cv;

                FloatingPoint firstPoint = new FloatingPoint(x, y);
                points.add(firstPoint);

                FloatingPoint previousPoint = firstPoint;
                if (max > 1) {
                    for (int i = 0; i < max; i++) {
                        float newX = (float) (previousPoint.getX() - tan);
                        float newY = previousPoint.getY() - 1;
                        previousPoint = new FloatingPoint(newX, newY);
                        points.add(previousPoint);
                    }
                }
            }
        }

        return points;
    }

    /**
     * compute the distance to the closest horizontal
     *
     * @param y           the y value of the origin
     * @param orientation orientation of computation
     * @return
     */
    public float getDistanceToClosestHorizontal(float y, Orientation orientation) {
        if (orientation == Orientation.NA) return Float.POSITIVE_INFINITY;
        if (orientation == Orientation.UP) {
            return y - (int) y;
        } else {
            return (int) y - y + 1;
        }
    }

    /**
     * compute the distance to the closest vertical
     *
     * @param x           the x value of the origin
     * @param orientation orientation of computation
     * @return
     */
    public float getDistanceToClosestVertical(float x, Orientation orientation) {
        if (orientation == Orientation.NA) return Float.POSITIVE_INFINITY;
        if (orientation == Orientation.RIGHT) {
            return (int) x - x + 1;
        } else {
            return x - (int) x;
        }
    }

    /**
     * give the point where this ray intersects the world map on the vertical
     *
     * @param index (0 based) : which vertical to check
     * @return the intersection
     */
    public FloatingPoint intersectVertical(int index) {
        return null;
    }

    public FloatingPoint getOrigin() {
        return origin;
    }

    public float getAngle() {
        return angle;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }
}
