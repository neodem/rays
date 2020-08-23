package com.neodem.rays;

import com.neodem.rays.graphics.Color;
import com.neodem.rays.graphics.Paintable;

import java.awt.Graphics;

/**
 * A paintable wall col. This will correct/clip if the pixels to draw gets past yMax
 * <p>
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/20/20
 */
public class WallColumn implements Paintable {

    private final Color[] pixels;
    private final int startX;
    private final int startY;
    private final int stopY;
    private final int midCorrect;

    public WallColumn(Color[] pixels, int startX, int midY, int yMax) {
        this.startX = startX;
        int pixelsMid = (pixels.length / 2);
        if (pixels.length > yMax) {
            this.startY = 0;
            this.stopY = yMax;
            this.midCorrect = pixelsMid - midY;
        } else {
            this.startY = midY - pixelsMid;
            this.stopY = pixels.length;
            this.midCorrect = 0;
        }

        this.pixels = pixels;
    }

    @Override
    public String toString() {
        return "WallColumn{" +
                "pixels=" + pixels.length +
                ", locX=" + startX +
                ", locY=" + startY +
                '}';
    }

    @Override
    public void paint(Graphics g) {
        for (int yOffset = 0; yOffset < stopY; yOffset++) {
            g.setColor(pixels[yOffset + midCorrect].getAWTColor());

            int actualY = startY + yOffset;

            // a point is a very short line
            g.drawLine(startX, actualY, startX, actualY);
        }
    }
}
