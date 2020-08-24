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
import java.util.List;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class RaysCanvas extends ActiveCanvas {

    private static final Logger logger = LoggerFactory.getLogger(RaysCanvas.class);

    private final int screenW;
    private final int screenH;
    private final int screenHMid;

    // field of view (in degrees)
    private static final int VIEWPORT = 124;

    // the walls
    protected List<Paintable> columns = new ArrayList<>();

    protected WorldMap worldMap;

    protected FloatingPoint playerLocation;

    // the direction the player is facing (0-359), 0 is up, 180 is down
    protected float playerViewAngle;

    private final RayComputer rayComputer;

    private boolean viewChanged;

    // the wall images
    private final WallImage[] wallImages;

    // keypress states
    private boolean key_w = false;
    private boolean key_a = false;
    private boolean key_s = false;
    private boolean key_d = false;
    private boolean key_r = false;

    private final boolean debug;

    public RaysCanvas(int width, int height, WallImage[] wallImages, boolean debug) {
        super(new Dimension(width, height));
        this.screenW = width;
        this.screenH = height;
        this.screenHMid = screenH / 2;

        this.wallImages = wallImages;

        worldMap = new TestWorldMap();

        playerLocation = new FloatingPoint(7.5f, 13.8f);
        playerViewAngle = 0;

        rayComputer = new RayComputer(screenW, VIEWPORT);

        this.debug = debug;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String keyPressed = KeyEvent.getKeyText(e.getKeyCode());
        //    logger.debug("Key Press: {}", keyPressed);
        if ("W".equals(keyPressed)) {
            key_w = true;
        } else if ("A".equals(keyPressed)) {
            key_a = true;
        } else if ("S".equals(keyPressed)) {
            key_s = true;
        } else if ("D".equals(keyPressed)) {
            key_d = true;
        } else if ("R".equals(keyPressed)) {
            key_r = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String keyPressed = KeyEvent.getKeyText(e.getKeyCode());
        //   logger.debug("Key Release: {}", keyPressed);
        if ("W".equals(keyPressed)) {
            key_w = false;
        } else if ("A".equals(keyPressed)) {
            key_a = false;
        } else if ("S".equals(keyPressed)) {
            key_s = false;
        } else if ("D".equals(keyPressed)) {
            key_d = false;
        } else if ("R".equals(keyPressed)) {
            key_r = false;
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

        // draw the walls
        for (Paintable c : columns) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("drawing: " + r);
//            }
            c.paint(g);
        }

        if (debug) {
            drawGrid(g);
        }
    }

    private void drawGrid(Graphics g) {
        int step = 100;
        int x = 0;
        g.setColor(Color.red.getAWTColor());
        while (x < screenW) {
            g.drawLine(x, 0, x, screenH);
            x = x + step;
        }
    }

    private void handleKeys() {
        if (key_w) {
            movePlayer(.05f, playerViewAngle);
        }

        if (key_s) {
            movePlayer(-.05f, playerViewAngle);
        }

        if (key_a) {
            turnPlayer(-10f);
        }

        if (key_d) {
            turnPlayer(10f);
        }

        // reset location
        if (key_r) {
            playerLocation = new FloatingPoint(7.5f, 13.8f);
            playerViewAngle = 0;
            viewChanged = true;
            logPlayer();
        }
    }

    private void logPlayer() {
        logger.debug("new player location, angle: {}, location: {}", playerViewAngle, playerLocation);
    }

    private void turnPlayer(float angle) {
        playerViewAngle += angle;
        playerViewAngle = Angles.correctAngle(playerViewAngle);
        viewChanged = true;
        logPlayer();
    }

    /**
     * transpose the player in world space
     *
     * @param amount
     * @param angle
     */
    private void movePlayer(float amount, float angle) {
        float y = Angles.worldY(angle, amount);
        float x = Angles.worldX(angle, amount);

        playerLocation = playerLocation.addY(y);
        playerLocation = playerLocation.addX(x);

        if (playerLocation.getX() < 0) playerLocation = new FloatingPoint(0, playerLocation.getY());
        if (playerLocation.getX() > screenW) playerLocation = new FloatingPoint(screenW, playerLocation.getY());
        if (playerLocation.getY() < 0) playerLocation = new FloatingPoint(playerLocation.getX(), 0);
        if (playerLocation.getY() > screenH) playerLocation = new FloatingPoint(playerLocation.getX(), screenH);

        viewChanged = true;
        logPlayer();
    }

    private void updateRays() {
        long start = System.currentTimeMillis();

        if (logger.isDebugEnabled()) {
            logger.debug("player: {}, angle: {}", playerLocation, playerViewAngle);
        }

        // compute the rays for the players view. These will be in order from 0-ScreenW
        List<Ray> rays = rayComputer.computeRays(playerLocation, playerViewAngle);

        columns.clear();
        int i = 0;
        for (Ray r : rays) {
            WorldMap.Intersection intersectionToPaint = worldMap.findIntersectionToPaint(r);
            int projectionHeight = computeHeight(intersectionToPaint.distance);
            Paintable paintable = makeWallColumn(intersectionToPaint.elementType, intersectionToPaint.hitPoint, projectionHeight, i);
            columns.add(paintable);
            if (logger.isDebugEnabled())
                logger.debug("{} {},{},height:{},{}", i, r, intersectionToPaint, projectionHeight, paintable);
            i++;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("updateRays() : {}", (System.currentTimeMillis() - start));
        }
    }

    private Paintable makeWallColumn(WorldMap.ElementType elementType, float hitPoint, int projectionHeight, int locX) {
        WallImage wall;
        if (elementType == WorldMap.ElementType.VWALL) {
            wall = wallImages[0];
        } else {
            wall = wallImages[2];
        }

        int sliceNumber = (int) (64 * hitPoint);
        Color[] slice = wall.getSlice(sliceNumber, projectionHeight);

        return new WallColumn(slice, locX, screenHMid, screenH);
    }

    /**
     * given a distance from the player, we need to draw a certain height.
     *
     * @param distance
     * @return
     */
    private int computeHeight(float distance) {
        return (int) (240 / distance);
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
