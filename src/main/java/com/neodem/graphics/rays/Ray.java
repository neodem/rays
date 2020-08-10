package com.neodem.graphics.rays;

import com.neodem.graphics.dd.engine.common.Color;
import com.neodem.graphics.dd.engine.core.Paintable;

import java.awt.Graphics;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class Ray implements Paintable {

    private final int height;
    private final int locX;
    private final int locY;
    private final Color color;

    public Ray(int height, int locX, int midY, Color color) {
        this.height = height;
        this.locX = locX;
        this.locY = midY - (height/2);
        this.color = color;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color.getAWTColor());
        g.fillRect(locX, locY, 1, height);
    }
}
