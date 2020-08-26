package com.neodem.rays.maths;

import com.neodem.rays.FloatingPoint;
import com.neodem.rays.Orientation;
import com.neodem.rays.Quadrant;
import com.neodem.rays.Ray;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Ray math
 *
 * <p>
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/19/20
 */
public class Rays {

    /**
     * Will compute a Collection of Rays for a given view width and angle and
     * return them with their world X
     *
     * @param numberToCompute number of rays to compute
     * @param viewAngle       the view angle (in degrees)
     * @param playerLocation  the location of the player in the world
     * @param playerDirection the direction the player is facing (0-359), 0 is up, 180 is down
     * @return a List of Ray objects representing the field of view for the player at this location
     */
    public static List<Ray> computeRays(int numberToCompute, float viewAngle, FloatingPoint playerLocation, float playerDirection) {
        int halfWayPoint = numberToCompute / 2;
        float angleTick = viewAngle / (float) numberToCompute;

        List<Ray> rays = new ArrayList<>(numberToCompute);

        // left view
        for (int locX = 0; locX < halfWayPoint; locX++) {

            // rayAngle is the angle from the center to the left
            // number of angleTicks == 1/2 of the screen width - locX * one angle tick
            float rayAngle = angleTick * (halfWayPoint - locX);

            // offset is the actual angle in world degrees.
            float angleOffset = Angles.convertLeftAngle(rayAngle, playerDirection);

            // add it to the collection
            rays.add(new Ray(playerLocation, angleOffset, playerDirection));
        }

        // right view
        for (int locX = halfWayPoint; locX < numberToCompute; locX++) {

            // rayAngle is the angle from the center to the right
            // number of angleTicks == screen width - locX * one angle tick
            float rayAngle = (locX - halfWayPoint) * angleTick;

            // offset is the actual angle in world degrees.
            float angleOffset = Angles.convertRightAngle(rayAngle, playerDirection);

            // add it to the collection
            rays.add(new Ray(playerLocation, angleOffset, playerDirection));
        }

        return rays;
    }

    /**
     * compute all the points where this ray intersects the world map on its horizontal axis' (axi?)
     *
     * @param ray
     * @param numberToCompute the number of intersecting points to compute
     * @return the intersections found
     */
    public static List<FloatingPoint> intersectWorldHorizontal(Ray ray, int numberToCompute) {
        if (numberToCompute <= 0) throw new IllegalArgumentException("numberToCompute needs to be greater than 0");

        List<FloatingPoint> points = new ArrayList<>(numberToCompute);

        Quadrant rayQuadrant = ray.getQuadrant();
        float rayAngle = ray.getAngle();
        FloatingPoint rayOrigin = ray.getOrigin();

        if (rayQuadrant == Quadrant.RIGHT || rayQuadrant == Quadrant.LEFT) {
            // for a Quadrant of LEFT, RIGHT we can't compute anything so we return the empty List
            return points;
        }

        float yBase = rayOrigin.getY();
        float xBase = rayOrigin.getX();
        float localAngle;

        Function<Float, Float> xTransform;
        Function<Integer, Integer> yTransform;

        if (rayQuadrant == Quadrant.UP) {
            localAngle = 0;
            xTransform = offset -> xBase - offset;
            yTransform = y -> y - 1;
            yBase++;
        } else if (rayQuadrant == Quadrant.DOWN) {
            localAngle = 0;
            xTransform = offset -> xBase - offset;
            yTransform = y -> y + 1;
        } else if (rayQuadrant == Quadrant.UP_LEFT) {
            localAngle = 360 - rayAngle;
            xTransform = offset -> xBase - offset;
            yTransform = y -> y - 1;
            yBase++;
        } else if (rayQuadrant == Quadrant.UP_RIGHT) {
            localAngle = rayAngle;
            xTransform = offset -> xBase + offset;
            yTransform = y -> y - 1;
            yBase++;
        } else if (rayQuadrant == Quadrant.DOWN_LEFT) {
            localAngle = rayAngle - 180;
            xTransform = offset -> xBase - offset;
            yTransform = y -> y + 1;
        } else {
            localAngle = 180 - rayAngle;
            xTransform = offset -> xBase + offset;
            yTransform = y -> y + 1;
        }

        // compute all the offset values from the Y axis
        float localOriginX = 1 - getDistanceToClosestHorizontal(yBase, Angles.orientUpDown(rayQuadrant));
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
     * compute all the points where this ray intersects the world map on its vertical axis' (axi?)
     *
     * @param numberToCompute the number of intersecting points to compute
     * @return the intersections found
     */
    public static List<FloatingPoint> intersectWorldVertical(Ray ray, int numberToCompute) {
        if (numberToCompute <= 0) throw new IllegalArgumentException("numberToCompute needs to be greater than 0");

        List<FloatingPoint> points = new ArrayList<>(numberToCompute);

        Quadrant rayQuadrant = ray.getQuadrant();
        float rayAngle = ray.getAngle();
        FloatingPoint rayOrigin = ray.getOrigin();

        if (rayQuadrant == Quadrant.UP || rayQuadrant == Quadrant.DOWN) {
            // for a Quadrant of UP, DOWN we can't compute anything so we return the empty List
            return points;
        }

        float yBase = rayOrigin.getY();
        float xBase = rayOrigin.getX();
        float localAngle;

        Function<Integer, Integer> xTransform;
        Function<Float, Float> yTransform;

        if (rayQuadrant == Quadrant.UP_LEFT) {
            localAngle = 360 - rayAngle;
            yTransform = offset -> yBase - offset;
            xTransform = x -> x - 1;
            xBase++;
        } else if (rayQuadrant == Quadrant.LEFT) {
            localAngle = 90;
            yTransform = offset -> yBase - offset;
            xTransform = x -> x - 1;
            xBase++;
        } else if (rayQuadrant == Quadrant.DOWN_LEFT) {
            localAngle = rayAngle - 180;
            yTransform = offset -> yBase + offset;
            xTransform = x -> x - 1;
            xBase++;
        } else if (rayQuadrant == Quadrant.UP_RIGHT) {
            localAngle = rayAngle;
            yTransform = offset -> yBase - offset;
            xTransform = x -> x + 1;
        } else if (rayQuadrant == Quadrant.RIGHT) {
            localAngle = 90;
            yTransform = offset -> yBase + offset;
            xTransform = x -> x + 1;
        } else { // DOWN_RIGHT
            localAngle = 180 - rayAngle;
            yTransform = offset -> yBase + offset;
            xTransform = x -> x + 1;
        }

        // compute all the offset values from the Y axis
        float localOriginY = 1 - getDistanceToClosestVertical(xBase, Angles.orientRightLeft(rayQuadrant));
        List<Float> yValues = Angles.intersectHorizontal(localOriginY, localAngle, numberToCompute);

        // transpose
        if (yValues != null && !yValues.isEmpty()) {
            for (Float offset : yValues) {
                float newX = xTransform.apply((int) xBase);
                float newY = yTransform.apply(offset);
                xBase = newX;
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
    public static float getDistanceToClosestHorizontal(float y, Orientation orientation) {
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
    public static float getDistanceToClosestVertical(float x, Orientation orientation) {
        if (orientation == Orientation.NA) return Float.POSITIVE_INFINITY;
        if (orientation == Orientation.RIGHT) {
            return (int) x - x + 1;
        } else {
            return x - (int) x;
        }
    }
}
