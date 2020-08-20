package com.neodem.rays;

import java.util.ArrayList;
import java.util.List;

/**
 * Will compute a Collection of Rays for a given view width and angle and
 * return them with their world X
 * <p>
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/19/20
 */
public class RayComputer {

    private final int screenW;
    private final int screenWMid;

    // field of view (in degrees)
    private final int viewport;

    // when computing rays, this is the change of angle needed for each ray
    private final float angleTick;

    public RayComputer(int viewPixels, int viewAngle) {
        this.screenW = viewPixels;
        this.viewport = viewAngle;
        this.screenWMid = screenW / 2;
        this.angleTick = viewport / (float) screenW;
    }

    /**
     * @param playerLocation  the location of the player in the world
     * @param playerViewAngle the direction the player is facing (0-359), 0 is up, 180 is down
     * @return a List of Ray objects representing the field of view for the player at this location
     */
    public List<Ray> computeRays(FloatingPoint playerLocation, float playerViewAngle) {
        List<Ray> rays = new ArrayList<>();

        // left view
        for (int locX = 0; locX < screenWMid; locX++) {

            // rayAngle is the angle from the center to the left
            // number of angleTicks == 1/2 of the screen width - locX * one angle tick
            float rayAngle = angleTick * (screenWMid - locX);

            // offset is the actual angle in world degrees.
            float angleOffset = Angles.convertLeftAngle(rayAngle, playerViewAngle);

            // add it to the collection
            rays.add(new Ray(playerLocation, angleOffset));
        }

        // right view
        for (int locX = screenWMid; locX < screenW; locX++) {

            // rayAngle is the angle from the center to the right
            // number of angleTicks == screen width - locX * one angle tick
            float rayAngle = (locX - screenWMid) * angleTick;

            // offset is the actual angle in world degrees.
            float angleOffset = Angles.convertRightAngle(rayAngle, playerViewAngle);

            // add it to the collection
            rays.add(new Ray(playerLocation, angleOffset));
        }

        return rays;
    }
}
