package com.neodem.graphics.rays;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/11/20
 */
public class Ray {

    // origin of the ray in the coordinate system of the World
    private final FloatingPoint rayOrigin;

    // angle is 0-359 with 0 being UP and 180 being DOWN
    private final float angle;

    private final Quadrant quadrant;

    public Ray(FloatingPoint origin, float angle) {
        if (origin == null) throw new IllegalArgumentException("origin may not be null");
        this.rayOrigin = origin;

        if (angle < 0) throw new IllegalArgumentException("angle needs to be 0-359");
        if (angle > 359) throw new IllegalArgumentException("angle needs to be 0-359");
        this.angle = angle;

        this.quadrant = AngleHelpers.determineQuadrant(angle);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "rayOrigin=" + rayOrigin +
                ", angle=" + angle +
                ", quadrant=" + quadrant +
                '}';
    }

    /**
     * compute all the points where this ray intersects the world map on its horizontal axis'
     *
     * @param numberToCompute the number of intersecting points to compute
     * @return the intersections with the 0 index being the origin of the ray
     */
    public List<FloatingPoint> intersectHorizontal(int numberToCompute) {
        if (numberToCompute <= 0) throw new IllegalArgumentException("numberToCompute needs to be greater than 0");

        List<FloatingPoint> points = new ArrayList<>();

        float dh = getDistanceToClosestHorizontal(rayOrigin.getY(), AngleHelpers.orientUpDown(quadrant));

        float localOriginX = 0;
        float localAngle = 0;
        if (quadrant == Quadrant.UP_LEFT) {
            localOriginX = 1 - dh;
            localAngle = 360 - angle;
        } else if (quadrant == Quadrant.UP_RIGHT) {
            localOriginX = 1 - dh;
            localAngle = angle;
        } else if (quadrant == Quadrant.DOWN_LEFT) {
            localOriginX = dh;
            localAngle = angle - 180;
        }


        List<Float> yValues = AngleHelpers.intersectVertical(localOriginX, localAngle, numberToCompute);

        if (quadrant == Quadrant.UP_LEFT) {
            points = makePointsFromOriginUpLeft(rayOrigin, yValues);
        } else if (quadrant == Quadrant.UP_RIGHT) {
            points = makePointsFromOriginUpRight(rayOrigin, yValues);
        } else if (quadrant == Quadrant.DOWN_LEFT) {
            points = makePointsFromOriginDownLeft(rayOrigin, yValues);
        }

        return points;
    }

    protected List<FloatingPoint> makePointsFromOriginDownLeft(FloatingPoint worldOrigin, List<Float> offsets) {
        if (offsets == null) return null;

        List<FloatingPoint> result = new ArrayList<>();

        int y = (int) worldOrigin.getY();
        float xBase = worldOrigin.getX();

        for (Float offset : offsets) {
            result.add(new FloatingPoint(xBase - offset, ++y));
        }

        return result;
    }

    protected List<FloatingPoint> makePointsFromOriginUpLeft(FloatingPoint worldOrigin, List<Float> offsets) {
        if (offsets == null) return null;

        List<FloatingPoint> result = new ArrayList<>();

        int y = (int) worldOrigin.getY();
        float xBase = worldOrigin.getX();

        for (Float offset : offsets) {
            result.add(new FloatingPoint(xBase - offset, y--));
        }

        return result;
    }

    protected List<FloatingPoint> makePointsFromOriginUpRight(FloatingPoint worldOrigin, List<Float> offsets) {
        if (offsets == null) return null;

        List<FloatingPoint> result = new ArrayList<>();

        int y = (int) worldOrigin.getY();
        float xBase = worldOrigin.getX();

        for (Float offset : offsets) {
            result.add(new FloatingPoint(xBase + offset, y--));
        }

        return result;
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
        return rayOrigin;
    }

    public float getAngle() {
        return angle;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }
}
