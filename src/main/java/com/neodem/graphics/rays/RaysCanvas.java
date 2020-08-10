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
    private final int screenMid;

    private final Color bgColor;
    protected List<Paintable> rays;

    protected WorldMap worldMap;

    public RaysCanvas(int width, int height) {
        super(new Dimension(width, height));
        this.screenW = width;
        this.screenH = height;
        this.screenMid = screenH / 2;
        this.bgColor = Color.lightGray;
    }

    @Override
    public void init() {
        worldMap = new TestWorldMap();
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

        for (int locX = 0; locX < screenW; locX++) {
            rays.add(new Ray(random.nextInt(300), locX, screenMid, Color.white));
        }
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
        g.fillRect(0, 0, screenW, screenMid);
    }


}
