package com.neodem.rays.graphics;

import java.awt.image.BufferedImage;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/20/20
 */
public class SimpleImage {

    private int[][] rawData;
    private Color[][] colorData;
    private final BufferedImage image;

    private final int width;
    private final int height;

    public SimpleImage(BufferedImage image) {
        this.image = image;

        width = image.getWidth();
        height = image.getHeight();
        rawData = new int[height][width];
        colorData = new Color[height][width];

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                int rgb = image.getRGB(x, y);
                rawData[x][y] = rgb;
                colorData[x][y] = new Color(rgb);
            }
        }
    }

    public int[][] getRawData() {
        return rawData;
    }

    public Color[][] getColorData() {
        return colorData;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
