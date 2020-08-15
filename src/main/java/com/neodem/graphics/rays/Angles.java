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
     * <p>
     * The tangents are computed on all of the X values that are integers greater than `originX`
     *
     * @param originX         (must be greater or equal to 0)
     * @param angle           (0-90) degrees
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

    /**
     * this will compute a number of tangents based on an angle and an originX value.
     * <p>
     * The coordinate system is as follows: standard X/Y graph with Y up and X to the right
     * The origin/base of the angle is oriented where Y=0 and X = originX
     * <p>
     * The tangents are computed on all of the Y values that are integers greater than `originY`
     *
     * @param originY         (must be greater or equal to 0)
     * @param angle           (0-90) degrees
     * @param numberToCompute the number of tans to compute
     * @return
     */
    public static List<Float> intersectHorizontal(float originY, float angle, int numberToCompute) {
        if (numberToCompute <= 0) throw new IllegalArgumentException("numberToCompute needs to be gt 0");
        if (angle < 0) throw new IllegalArgumentException("angle should be positive");
        if (angle > 90) throw new IllegalArgumentException("angle may not be greater than 90");
        if (originY < 0) throw new IllegalArgumentException("originY may not be negative");

        List<Float> points = new ArrayList<>();

        double tan = Math.tan(Math.toRadians(90 - angle));

        float x;
        // compute first point!
        if (originY > 0) {
            float y = (int) originY + 1;
            float dy = y - originY;
            x = (float) (dy * tan);
        } else {
            x = (float) tan;
        }

        points.add(x);

        // compute other points
        if (numberToCompute > 1) {
            float previousX = x;
            for (int i = 0; i < numberToCompute - 1; i++) {
                float newX = (float) (previousX + tan);
                points.add(newX);
                previousX = newX;
            }
        }

        return points;
    }

    /**
     * convert from an absolute angle (like 22 degrees) to a world angle relative to the playerViewAngle to the "left" or counter-clockwise
     * eg. if the player is facing 10 and we want to transpose 22 to the left we'd want 348
     *
     * @param rayAngle        the angle we need to convert
     * @param playerViewAngle the players direction [0-360) 0 == up
     * @return
     */
    public static float convertLeftAngle(float rayAngle, float playerViewAngle) {
        if (playerViewAngle < 0)
            throw new IllegalArgumentException("playerViewAngle should be greater than or equal to 0");
        if (!(playerViewAngle < 360)) throw new IllegalArgumentException("playerViewAngle must be less than 360");

        // go "left" on the circle
        float absAngle = playerViewAngle - rayAngle;
        if (absAngle > 0) return absAngle;
        if (absAngle == 0) return 0;
        return correctAngle(absAngle);
    }

    /**
     * convert from an absolute angle (like 22 degrees) to a world angle relative to the playerViewAngle to the "right" or clockwise
     * eg. if the player is facing 340 and we want to transpose 22 to the right we'd want 2
     *
     * @param rayAngle        the angle we need to convert
     * @param playerViewAngle the players direction [0-360) 0 == up
     * @return
     */
    public static float convertRightAngle(float rayAngle, float playerViewAngle) {
        if (playerViewAngle < 0)
            throw new IllegalArgumentException("playerViewAngle should be greater than or equal to 0");
        if (!(playerViewAngle < 360)) throw new IllegalArgumentException("playerViewAngle must be less than 360");

        // go "right" on the circle
        float absAngle = playerViewAngle + rayAngle;
        if (absAngle == 0) return 0;
        if (absAngle < 360) return absAngle;
        return correctAngle(absAngle);
    }

    /**
     * correct the angle to be from [0-360)
     *
     * @param angle
     * @return
     */
    public static float correctAngle(float angle) {
        if (angle < 0) return correctAngle(360 + angle);
        if (angle == 0) return 0;
        if (angle < 360) return angle;
        return correctAngle(angle - 360);
    }
}
