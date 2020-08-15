package com.neodem.graphics.rays;

import com.neodem.graphics.rays.graphics.Color;
import com.neodem.graphics.rays.graphics.ActiveCanvas;
import com.neodem.graphics.rays.graphics.Paintable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class RaysCanvas extends ActiveCanvas {

    private final static Random random = new Random(System.currentTimeMillis());

    private final int screenW;
    private final int screenH;
    private final int screenHMid;
    private final int screenWMid;

    // field of view (in degrees)
    private static final int VIEWPORT = 120;

    protected List<Paintable> rays;

    protected WorldMap worldMap;

    protected FloatingPoint playerLocation;

    // the direction the player is facing (0-359), 0 is up, 180 is down
    protected float playerViewAngle;

    // when computing rays, this is the change of angle needed for each ray
    private final float angleTick;

    public RaysCanvas(int width, int height) {
        super(new Dimension(width, height));
        this.screenW = width;
        this.screenH = height;
        this.screenHMid = screenH / 2;
        this.screenWMid = screenW / 2;
        this.angleTick = VIEWPORT / (float) screenW;
    }

    @Override
    public void init() {
        worldMap = new TestWorldMap();
        playerLocation = new FloatingPoint(7.5f, 13.8f);
        playerViewAngle = 0;
    }

    @Override
    public void update(long elapsedTime) {
        makeRays();
    }

    @Override
    public void draw(Graphics g) {
        drawBackground(g);
        drawRays(rays, g);
    }

    private void makeRays() {
        rays = new ArrayList<>();

        // left view
        for (int locX = 0; locX < screenWMid; locX++) {
            float rayAngle = angleTick * (screenWMid - locX);
            float angleOffset = Angles.convertLeftAngle(rayAngle, playerViewAngle);
            PaintableRay ray = computeRay(angleOffset, playerLocation);
            rays.add(ray);
        }

        // right view
        for (int locX = screenWMid; locX < screenW; locX++) {
            float rayAngle = playerViewAngle + (angleTick * locX);
            float angleOffset = Angles.convertRightAngle(rayAngle, playerViewAngle);
            PaintableRay ray = computeRay(angleOffset, playerLocation);
            rays.add(ray);
        }
    }

    private PaintableRay computeRay(float angleOffset, FloatingPoint playerLocation) {
        PaintableRay paintableRay = null;

        Ray ray = new Ray(playerLocation, angleOffset);
        Collection<WorldMap.Intersection> intersections = worldMap.rayIntersections(ray);
        WorldMap.Intersection intersectionToPaint;
        if (intersections.isEmpty()) {
            // this ray hits the edge of the world
            // TODO fix this by making HWALL and VWALL around our world, even though right now
            // we have a TestWorldMap that is self contained so this should never happen
            throw new RuntimeException("World Edge Encountered");
        } else {
            intersections = intersections.stream()
                    .peek(this::addDistance)
                    .sorted()
                    .collect(Collectors.toList());

            intersectionToPaint = intersections.iterator().next();
        }

        return makePaintableRay(intersectionToPaint);
    }

    private PaintableRay makePaintableRay(WorldMap.Intersection intersection) {
        // its here we convert the intersection into a ray. We would compute the height, the location and the
        // bitmap to paint (based on the element type, HWALL, VWALL, etc)

        addDistance(intersection);

        return null;
    }

    private void addDistance(WorldMap.Intersection intersection) {
        // compute the distance to the hitPoint from the playerLocation
        // consider the fisheye effect
    }

    private void drawRays(List<Paintable> rays, Graphics g) {
        rays.stream().forEach(r -> r.paint(g));
    }

    private void drawBackground(Graphics g) {
        // floor
        g.setColor(Color.lightGray.getAWTColor());
        g.fillRect(0, 0, screenW, screenH);

        // roof
        g.setColor(Color.lightBlue.getAWTColor());
        g.fillRect(0, 0, screenW, screenHMid);
    }


}
