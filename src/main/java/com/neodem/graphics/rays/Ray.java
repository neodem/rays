package com.neodem.graphics.rays;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

        this.quadrant = Angles.determineQuadrant(angle);
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

        float yBase = rayOrigin.getY();
        float xBase = rayOrigin.getX();
        float localAngle;
        float localOriginX = 1 - getDistanceToClosestHorizontal(rayOrigin.getY(), Angles.orientUpDown(quadrant));

        Function<Float, Float> xTransform;
        Function<Integer, Integer> yTransform;

        if (quadrant == Quadrant.UP_LEFT) {
            localAngle = 360 - angle;
            xTransform = offset -> xBase - offset;
            yTransform = y -> y - 1;
            yBase++;
        } else if (quadrant == Quadrant.UP_RIGHT) {
            localAngle = angle;
            xTransform = offset -> xBase + offset;
            yTransform = y -> y - 1;
            yBase++;
        } else if (quadrant == Quadrant.DOWN_LEFT) {
            localAngle = angle - 180;
            xTransform = offset -> xBase - offset;
            yTransform = y -> y + 1;
        } else if (quadrant == Quadrant.DOWN_RIGHT) {
            localAngle = 180 - angle;
            xTransform = offset -> xBase + offset;
            yTransform = y -> y + 1;
        } else {

            // for a Quadrant of UP, DOWN, LEFT, RIGHT we can't compute anything so we return the empty List
            return points;
        }

        // compute all the offset values from the Y axis
        List<Float> yValues = Angles.intersectVertical(localOriginX, localAngle, numberToCompute);

        // transpose
        if (yValues != null && !yValues.isEmpty()) {
            for (Float offset : yValues) {
                float newX = xTransform.apply(offset);
                float newY = yTransform.apply((int) yBase);
                yBase = newY;
                points.add(new FloatingPoint(newX, newY));
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
        return rayOrigin;
    }

    public float getAngle() {
        return angle;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }
}
