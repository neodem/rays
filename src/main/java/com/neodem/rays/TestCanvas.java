package com.neodem.rays;

import com.neodem.rays.graphics.ActiveCanvas;
import com.neodem.rays.graphics.Color;
import com.neodem.rays.graphics.Paintable;
import com.neodem.rays.graphics.SimpleImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/20/20
 */
public class TestCanvas extends ActiveCanvas {

    private static final Logger logger = LoggerFactory.getLogger(RaysCanvas.class);

    private final int screenW;
    private final int screenH;
    private final int screenHMid;

    // the wall images
    private final SimpleImage[] images;

    public TestCanvas(int width, int height, SimpleImage[] images) {
        super(new Dimension(width, height));
        this.screenW = width;
        this.screenH = height;
        this.screenHMid = screenH / 2;

        this.images = images;
    }



    @Override
    public void init() {

    }

    @Override
    public void update(long elapsedTime, String kp, String kr) {

    }

    @Override
    public void draw(Graphics g) {
        drawBackground(g);
        drawImage(g);
    }

    private void drawImage(Graphics g) {
        SimpleImage image = images[2];

        // this will draw the image (as is)
        g.drawImage(image.getImage(), 320-32, 240-32, null);

        // this will draw the image, pixel by pixel
        Color[][] pixels = image.getColorData();
        for(int x=0; x<64; x++) {
            for (int y = 0; y < 64; y++) {
                g.setColor(pixels[x][y].getAWTColor());
                g.drawRect(x, y, 1, 1);
            }
        }

        // this will draw the image, using my wall line technique
        for(int x=0; x<32; x++) {
            WallColumn line = new WallColumn(pixels[x], x+100, 100, 500);
            line.paint(g);
        }
    }


    private Paintable makePaintableLine(WorldMap.ElementType elementType, float hitPoint, int projectionHeight, int locX) {

//        BufferedImage wall;
//        if (elementType == WorldMap.ElementType.VWALL) {
//            wall = images[0];
//        } else {
//            wall = images[2];
//        }
//
//        Raster wallRaster = wall.getData(new Rectangle(locX, 0, 1, 64));
//        wallRaster = resizeWallRaster(wallRaster, projectionHeight);
//
//        WallLine wallLine = new WallLine(wallRaster, locX, screenHMid);

        FilledColumn wallLine = new FilledColumn(projectionHeight, locX, screenHMid, Color.white);

        return wallLine;
    }

    // resize a raster
    private Raster resizeWallRaster(Raster wallRaster, int projectionHeight) {
        DataBuffer fromDataBuffer = wallRaster.getDataBuffer();
        return wallRaster;
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