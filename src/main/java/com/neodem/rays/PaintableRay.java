package com.neodem.rays;

import com.neodem.rays.graphics.Color;
import com.neodem.rays.graphics.Paintable;

import java.awt.Graphics;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class PaintableRay implements Paintable {

    private final float height;
    private final int locX;
    private final int locY;
    private final Color color;

    public PaintableRay(float height, int locX, int midY, Color color) {
        this.height = height;
        this.locX = locX;
        this.locY = midY - ((int) height / 2);
        this.color = color;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color.getAWTColor());
        g.fillRect(locX, locY, 1, (int) height);
    }
}
