package com.neodem.graphics.rays;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/12/20
 */
public class AngleHelpers {

    public static Quadrant determineQuadrant(float angle) {
        if (angle < 0) throw new IllegalArgumentException("angle needs to be 0-359");
        if (angle > 359) throw new IllegalArgumentException("angle needs to be 0-359");

        if (angle == 0) return Quadrant.UP;
        if (angle == 90) return Quadrant.RIGHT;
        if (angle == 180) return Quadrant.DOWN;
        if (angle == 270) return Quadrant.LEFT;

        if (angle > 0 && angle < 90) return Quadrant.UP_RIGHT;
        if (angle > 90 && angle < 180) return Quadrant.DOWN_RIGHT;
        if (angle > 180 && angle < 270) return Quadrant.DOWN_LEFT;
        if (angle > 270 && angle < 360) return Quadrant.UP_LEFT;

        return null;
    }

    public static Orientation orientUpDown(Quadrant q) {
        if (q == Quadrant.DOWN) return Orientation.DOWN;
        if (q == Quadrant.UP) return Orientation.UP;
        if (q == Quadrant.LEFT || q == Quadrant.RIGHT) return Orientation.NA;

        if (q == Quadrant.UP_RIGHT || q == Quadrant.UP_LEFT) return Orientation.UP;
        if (q == Quadrant.DOWN_LEFT || q == Quadrant.DOWN_RIGHT) return Orientation.DOWN;

        return null;
    }

    public static Orientation orientRightLeft(Quadrant q) {
        if (q == Quadrant.RIGHT) return Orientation.RIGHT;
        if (q == Quadrant.LEFT) return Orientation.LEFT;
        if (q == Quadrant.UP || q == Quadrant.DOWN) return Orientation.NA;

        if (q == Quadrant.UP_RIGHT || q == Quadrant.DOWN_RIGHT) return Orientation.RIGHT;
        if (q == Quadrant.UP_LEFT || q == Quadrant.DOWN_LEFT) return Orientation.LEFT;

        return null;
    }

    /**
     * will compute the points based on a traditional X/Y graph with the origin at the bottom left and
     * Y going up and X going Right. Angles are in degrees going up to 90 with 0 being aligned with the X axis
     * and 90 being aligned with the Y axis
     * <p>
     * Horizontal in this case means the first (and subsequent) whole X value axises
     *
     * @param size   the number of points to compute (>0)
     * @param angle  from 0-90
     * @param origin the starting point ( must be at 0,0 or greater)
     * @return a List with the origin first and up to max points after
     */
    public static List<FloatingPoint> intersectHorizontal(FloatingPoint origin, float angle, int size) {
        if (size <= 0) throw new IllegalArgumentException("size needs to be gt 0");
        if (angle < 0) throw new IllegalArgumentException("angle should be positive");
        if (angle > 90) throw new IllegalArgumentException("angle may not be greater than 90");
        if (origin == null) throw new IllegalArgumentException("origin may not be null");

        List<FloatingPoint> points = new ArrayList<>();
        points.add(origin);

        float oX = origin.getX();
        float oY = origin.getY();

        if (oX < 0) throw new IllegalArgumentException("originX may not be negative");
        if (oY < 0) throw new IllegalArgumentException("originY may not be negative");

        // compute first point!
        float x = (int) oX + 1;
        float dx = x - oX;
        double tan = Math.tan(Math.toRadians(angle));
        float y = (float) (dx * tan) + oY;

        FloatingPoint firstPoint = new FloatingPoint(x, y);
        points.add(firstPoint);

        // compute other points
        if (size > 1) {
            FloatingPoint previousPoint = firstPoint;
            for (int i = 0; i < size - 1; i++) {
                float newX = previousPoint.getX() + 1;
                float newY = (float) (previousPoint.getY() + tan);
                previousPoint = new FloatingPoint(newX, newY);
                points.add(previousPoint);
            }
        }

        return points;
    }

    /**
     * will compute the points based on a traditional X/Y graph with the origin at the bottom left and
     * Y going up and X going Right. Angles are in degrees going up to 90 with 0 being aligned with the X axis
     * and 90 being aligned with the Y axis
     * <p>
     * Vertical in this case means the first (and subsequent) whole Y value axises
     *
     * @param size   the number of points to compute (>0)
     * @param angle  from 0-90
     * @param origin the starting point ( must be at 0,0 or greater)
     * @return a List with the origin first and up to max points after
     */
    public static List<FloatingPoint> intersectVertical(FloatingPoint origin, float angle, int size) {
        if (size <= 0) throw new IllegalArgumentException("size needs to be gt 0");
        if (angle < 0) throw new IllegalArgumentException("angle should be positive");
        if (angle > 90) throw new IllegalArgumentException("angle may not be greater than 90");
        if (origin == null) throw new IllegalArgumentException("origin may not be null");

        List<FloatingPoint> points = new ArrayList<>();
        points.add(origin);

        float oX = origin.getX();
        float oY = origin.getY();

        if (oX < 0) throw new IllegalArgumentException("originX may not be negative");
        if (oY < 0) throw new IllegalArgumentException("originY may not be negative");

        // compute first point!
        float y = (int) oY + 1;
        float dy = y - oY;
        double tan = Math.tan(Math.toRadians(90 - angle));
        float x = (float) (dy * tan) + oX;

        FloatingPoint firstPoint = new FloatingPoint(x, y);
        points.add(firstPoint);

        // compute other points
        if (size > 1) {
            FloatingPoint previousPoint = firstPoint;
            for (int i = 0; i < size - 1; i++) {
                float newY = previousPoint.getY() + 1;
                float newX = (float) (previousPoint.getX() + tan);
                previousPoint = new FloatingPoint(newX, newY);
                points.add(previousPoint);
            }
        }

        return points;
    }

}
