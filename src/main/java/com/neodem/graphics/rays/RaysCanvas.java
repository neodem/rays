package com.neodem.graphics.rays;

import com.neodem.graphics.dd.engine.common.Color;
import com.neodem.graphics.dd.engine.core.ActiveCanvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class RaysCanvas extends ActiveCanvas {


    private final int screenW;
    private final int screenH;
    private final Color bgColor;
    protected List<Ray> rays;

    public RaysCanvas(int width, int height) {
        super(new Dimension(width, height));
        this.screenW = width;
        this.screenH = height;
        this.bgColor = Color.lightGray;
    }

    @Override
    public void init() {

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
    }

    private void drawRays(List<Ray> rays, Graphics g) {
        rays.stream().forEach(r -> draw(g));
    }

    private void drawBackground(Graphics g) {
        g.setColor(bgColor.getAWTColor());
        g.fillRect(0, 0, screenW, screenH);
    }


}
