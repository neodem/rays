package com.neodem.graphics.rays;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/12/20
 */
public class Angles {

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
     * this will compute a number of tangents based on an angle and an originX value.
     * <p>
     * The coordinate system is as follows: standard X/Y graph with Y up and X to the right
     * The origin/base of the angle is oriented where Y=0 and X = originX
     *
     * @param originX         (must be greater or equal to 0)
     * @param angle           0-90 degrees
     * @param numberToCompute the number of tans to compute
     * @return
     */
    public static List<Float> intersectVertical(float originX, float angle, int numberToCompute) {
        if (numberToCompute <= 0) throw new IllegalArgumentException("numberToCompute needs to be gt 0");
        if (angle < 0) throw new IllegalArgumentException("angle should be positive");
        if (angle > 90) throw new IllegalArgumentException("angle may not be greater than 90");
        if (originX < 0) throw new IllegalArgumentException("originX may not be negative");

        List<Float> points = new ArrayList<>();

        double tan = Math.tan(Math.toRadians(angle));

        float y;
        // compute first point!
        if (originX > 0) {
            float x = (int) originX + 1;
            float dx = x - originX;
            y = (float) (dx * tan);
        } else {
            y = (float) tan;
        }

        points.add(y);

        // compute other points
        if (numberToCompute > 1) {
            float previousY = y;
            for (int i = 0; i < numberToCompute - 1; i++) {
                float newY = (float) (previousY + tan);
                points.add(newY);
                previousY = newY;
            }
        }

        return points;
    }
}
