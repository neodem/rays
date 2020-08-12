package com.neodem.graphics.rays;

import com.neodem.graphics.dd.engine.common.Color;
import com.neodem.graphics.dd.engine.core.ActiveCanvas;
import com.neodem.graphics.dd.engine.core.Paintable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    // in degrees
    private static final int VIEWPORT = 64;

    protected List<Paintable> rays;

    protected WorldMap worldMap;

    protected FloatingPoint playerLocation;
    protected float playerViewAngle;

    private final float angleTick;

    public RaysCanvas(int width, int height) {
        super(new Dimension(width, height));
        this.screenW = width;
        this.screenH = height;
        this.screenHMid = screenH / 2;
        this.screenWMid = screenW / 2;
        this.angleTick = VIEWPORT / screenW;
    }

    @Override
    public void init() {
        worldMap = new TestWorldMap();
        playerLocation = new FloatingPoint(7.5f, 13.5f);
        playerViewAngle = 0;
    }

    @Override
    public void update(long elapsedTime) {
        computeRays();
    }

    @Override
    public void draw(Graphics g) {
        drawBackground(g);
        drawRays(rays, g);
    }

    private void computeRays() {
        rays = new ArrayList<>();

        // left view
        for (int locX = 0; locX < screenWMid; locX++) {
            float angleOffset = playerViewAngle - (angleTick * (screenWMid - locX));
            int height = computeRay(angleOffset, playerLocation);
            rays.add(new PaintableRay(height, locX, screenHMid, Color.white));
        }

        // right view
        for (int locX = screenWMid; locX < screenW; locX++) {
            float angleOffset = playerViewAngle + (angleTick * locX);
            int height = computeRay(angleOffset, playerLocation);
            rays.add(new PaintableRay(height, locX, screenHMid, Color.white));
        }
    }

    private int computeRay(float angleOffset, FloatingPoint playerLocation) {
      //  Float distance = worldMap.intersectElement(playerLocX, playerLocY, angleOffset);

        // should relate the distance to the height.. with a close distance = max height and
        // a far distance being a small height
        int height = random.nextInt(300);


        return height;
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
