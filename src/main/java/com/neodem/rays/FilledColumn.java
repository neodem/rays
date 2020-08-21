package com.neodem.rays;

import com.neodem.rays.graphics.Color;
import com.neodem.rays.graphics.Paintable;

import java.awt.Graphics;

/**
 * a vertical line one pixel wide of varying height aligned on a midpoint
 * <p>
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class FilledColumn implements Paintable {

    private final float height;
    private final int locX;
    private final int locY;
    private final Color color;

    @Override
    public String toString() {
        return "VDrawLine{" +
                "height=" + height +
                ", locX=" + locX +
                ", locY=" + locY +
                ", color=" + color +
                '}';
    }

    /**
     * The line will be aligned at X,Y where X == locX
     * and Y = midY - ((int) height /2)
     *
     * @param height the height of the line to draw
     * @param locX   the X location
     * @param midY   the Y midpoint of the line location
     * @param color  the color to draw it
     */
    public FilledColumn(float height, int locX, int midY, Color color) {
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
