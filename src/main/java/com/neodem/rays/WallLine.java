package com.neodem.rays;

import com.neodem.rays.graphics.Paintable;

import java.awt.Graphics;
import java.awt.image.Raster;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/20/20
 */
public class WallLine implements Paintable {

    private final Raster raster;
    private final int locX;
    private final int locY;

    public WallLine(Raster wallRaster, int locX, int midY) {
        this.locX = locX;
        this.locY = midY - (wallRaster.getHeight() / 2);
        this.raster = wallRaster;
    }

    @Override
    public void paint(Graphics g) {
//        g.drawBytes();
//
//        g.setColor(color.getAWTColor());
//        g.fillRect(locX, locY, 1, (int) height);
    }
}
