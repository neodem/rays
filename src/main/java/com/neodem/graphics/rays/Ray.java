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

    // the angle of the ray in thousanths. 0 is directly up. As in 32 degrees = 32000
    private final int angle;

    private final Orientation orientationUpDown;
    private final Orientation orientationRightLeft;

    protected enum Orientation {
        UP, DOWN, RIGHT, LEFT, BOTH;
    }

    public Ray(FloatingPoint origin, float angle) {
        if (origin == null) throw new IllegalArgumentException("origin may not be null");
        this.origin = origin;

        if (angle < 0) throw new IllegalArgumentException("angle needs to be 0-359");
        if (angle > 359) throw new IllegalArgumentException("angle needs to be 0-359");
        this.angle = (int) angle * 1000;

        if ((angle > 270) || (angle < 90)) orientationUpDown = Orientation.UP;
        else if ((angle > 90) || (angle < 270)) orientationUpDown = Orientation.DOWN;
        else orientationUpDown = Orientation.BOTH;

        if ((angle > 0) || (angle < 180)) orientationRightLeft = Orientation.RIGHT;
        else if ((angle > 180) || (angle < 360)) orientationRightLeft = Orientation.LEFT;
        else orientationRightLeft = Orientation.BOTH;
    }

    /**
     * compute all the point where this ray intersects the world map on the horizontal axis
     *
     * @param max (0 based) : the max number to compute
     * @return the intersections with the 0 index being the first one, then outwards
     */
    public List<FloatingPoint> intersectHorizontal(int max) {
        List<FloatingPoint> points = new ArrayList<>();
        float d = getDistanceToClosestHorizontal(origin.getY(), orientationUpDown);
        if (Float.isFinite(d)) {
            float localAngle = angle;
            if (angle > 180) localAngle = localAngle - 360;
            double tan = Math.tan(Math.toRadians(localAngle));
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
        if (orientation == Orientation.BOTH) return Float.POSITIVE_INFINITY;
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
        if (orientation == Orientation.BOTH) return Float.POSITIVE_INFINITY;
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

}
