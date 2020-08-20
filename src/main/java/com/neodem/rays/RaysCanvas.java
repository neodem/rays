package com.neodem.rays;

import com.neodem.rays.graphics.ActiveCanvas;
import com.neodem.rays.graphics.Color;
import com.neodem.rays.graphics.Paintable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class RaysCanvas extends ActiveCanvas {

    private static final Logger logger = LoggerFactory.getLogger(RaysCanvas.class);

    private final static Random random = new Random(System.currentTimeMillis());

    private final int screenW;
    private final int screenH;
    private final int screenHMid;
    private final int screenWMid;

    // field of view (in degrees)
    private static final int VIEWPORT = 120;

    protected List<Paintable> rays = new ArrayList<>();

    protected WorldMap worldMap;

    protected FloatingPoint playerLocation;

    // the direction the player is facing (0-359), 0 is up, 180 is down
    protected float playerViewAngle;

    // when computing rays, this is the change of angle needed for each ray
    private final float angleTick;

    private final RayComputer rayComputer;

    private boolean viewChanged;

    // keypress states
    boolean key_w = false;
    boolean key_a = false;
    boolean key_s = false;
    boolean key_d = false;

    public RaysCanvas(int width, int height) {
        super(new Dimension(width, height));
        this.screenW = width;
        this.screenH = height;
        this.screenHMid = screenH / 2;
        this.screenWMid = screenW / 2;
        this.angleTick = VIEWPORT / (float) screenW;

        worldMap = new TestWorldMap();

        playerLocation = new FloatingPoint(7.5f, 13.8f);
        playerViewAngle = 0;

        rayComputer = new RayComputer(screenW, VIEWPORT);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String keyPressed = KeyEvent.getKeyText(e.getKeyCode());
        logger.debug("Key Press: {}", keyPressed);
        if ("W".equals(keyPressed)) {
            key_w = true;
        } else if ("A".equals(keyPressed)) {
            key_a = true;
        } else if ("S".equals(keyPressed)) {
            key_s = true;
        } else if ("D".equals(keyPressed)) {
            key_d = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String keyPressed = KeyEvent.getKeyText(e.getKeyCode());
        logger.debug("Key Release: {}", keyPressed);
        if ("W".equals(keyPressed)) {
            key_w = false;
        } else if ("A".equals(keyPressed)) {
            key_a = false;
        } else if ("S".equals(keyPressed)) {
            key_s = false;
        } else if ("D".equals(keyPressed)) {
            key_d = false;
        }
    }

    @Override
    public void init() {
        updateRays();
        viewChanged = false;
    }

    @Override
    public void update(long elapsedTime, String kp, String kr) {
        handleKeys();

        if (viewChanged) {
            updateRays();
            viewChanged = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        drawBackground(g);
        drawRays(rays, g);
    }

    private void handleKeys() {
        if (key_w) {
            movePlayer(-.005f, 0);
        }

        if (key_s) {
            movePlayer(.005f, 0);
        }

        if (key_a) {
            turnPlayer(-.001f);
        }

        if (key_d) {
            turnPlayer(.001f);
        }
    }

    private void turnPlayer(float angle) {
        playerViewAngle += angle;
        viewChanged = true;
        logger.info("new player angle : {}", playerViewAngle);
    }

    private void movePlayer(float x, int y) {
        playerLocation = playerLocation.addX(x);
        playerLocation = playerLocation.addY(y);
        viewChanged = true;
        logger.info("new player location : {}", playerLocation);
    }

    private void updateRays() {
        long start = System.currentTimeMillis();
        Collection<RayComputer.WorldRay> actualRays = rayComputer.computeRays(playerLocation, playerViewAngle);

        rays.clear();
        for (RayComputer.WorldRay worldRay : actualRays) {
            WorldMap.Intersection intersectionToPaint = worldMap.findIntersectionToPaint(worldRay.ray);
            float projectionHeight = computeHeight(intersectionToPaint.distance);
            PaintableRay paintableRay = new PaintableRay(projectionHeight, worldRay.locX, screenHMid, Color.white);
            rays.add(paintableRay);
        }
        logger.info("computed Rays : {}", (System.currentTimeMillis() - start));
    }

    /**
     * given a distance from the player, we need to draw a certain height.
     *
     * @param distance
     * @return
     */
    private float computeHeight(float distance) {
        // TODO placeholder here.
        return 100 / distance;
    }

    private void drawRays(List<Paintable> rays, Graphics g) {
        for (Paintable r : rays) {
            r.paint(g);
        }
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
