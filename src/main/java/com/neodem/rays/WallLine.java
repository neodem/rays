package com.neodem.rays;

import com.neodem.rays.graphics.Color;
import com.neodem.rays.graphics.Paintable;

import java.awt.Graphics;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/20/20
 */
public class WallLine implements Paintable {

    private final Color[] pixels;
    private final int locX;
    private final int locY;

    public WallLine(Color[] pixels, int locX, int midY) {
        this.locX = locX;
        this.locY = midY - (pixels.length / 2);
        this.pixels = pixels;
    }

    @Override
    public void paint(Graphics g) {
        // I'll bet this is super slow.. but it will do for now
        for (int y = 0; y < 64; y++) {
            g.setColor(pixels[y].getAWTColor());
            g.drawRect(locX, y + locY, 1, 1);
        }
    }
}
