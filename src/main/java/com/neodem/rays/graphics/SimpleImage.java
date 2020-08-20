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

    public SimpleImage(BufferedImage image) {
        this.image = image;

        int width = image.getWidth();
        int height = image.getHeight();
        rawData = new int[height][width];
        colorData = new Color[height][width];

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                int rgb = image.getRGB(x, y);
                rawData[x][y] = rgb;
                colorData[x][y] = convertRGB(rgb);
            }
        }
    }

    private Color convertRGB(int rgb) {
        int alpha = (rgb >> 24) & 0xFF;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = (rgb) & 0xFF;

        return new Color(red, green, blue);
    }

    public int[][] getRawData() {
        return rawData;
    }

    public Color[] getSlice(int x) {
        return colorData[x];
    }

    public Color[][] getColorData() {
        return colorData;
    }

    public BufferedImage getImage() {
        return image;
    }
}
