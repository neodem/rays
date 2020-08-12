package com.neodem.graphics.rays;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/11/20
 */
public class Ray {

    // origin of the ray in the coordinate system of the World
    private final FloatingPoint origin;

    // the angle of the ray. 0 is directly up.
    private final float angle;

    public Ray(FloatingPoint origin, float angle) {
        if (origin == null) throw new IllegalArgumentException("origin may not be null");
        this.origin = origin;

        if(angle < 0) throw new IllegalArgumentException("angle needs to be 0-359");
        if(angle > 359) throw new IllegalArgumentException("angle needs to be 0-359");
        this.angle = angle;
    }

    /**
     * give the point where this ray intersects the world map on the horizontal
     *
     * @param index (0 based) : which horizontal to check
     * @return the intersection
     */
    public FloatingPoint intersectHorizontal(int index) {

        float d = getDistanceToHorizontal(index);

        float localAngle = angle;
        if(angle > 180) localAngle = localAngle - 360;
        double tan = Math.tan(Math.toRadians(localAngle));


        return null;
    }

    public float getDistanceToHorizontal(int index) {
        boolean up = (angle > 270) || (angle < 90);
        float distanceToHorizontal = 0;
        if(up) {
            int y = (int) origin.getY() - index;
            distanceToHorizontal = origin.getY() - y;
        } else {
            int y = (int) origin.getY() + index;
            distanceToHorizontal = y - origin.getY();
        }
        return distanceToHorizontal;
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
