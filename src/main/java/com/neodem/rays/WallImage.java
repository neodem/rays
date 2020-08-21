package com.neodem.rays;

import com.neodem.rays.graphics.Color;
import com.neodem.rays.graphics.SimpleImage;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/21/20
 */
public class WallImage {

    private final SimpleImage image;

    public WallImage(SimpleImage image) {
        this.image = image;
    }

    /**
     * return a vertical slice of the image but with the slice sized up or down
     *
     * @param x      which slice (measured along the top axis)
     * @param height
     * @return
     */
    public Color[] getSlice(int x, int height) {
        Color[] slice = getSlice(x);
        if (height % 2 != 0) height = height - 1;
//        return getHardCoded(slice, even);
        return getAlg(slice, height);
    }

    protected Color[] getAlg(Color[] slice, int size) {

        Color[] result = new Color[size];

        float sourceCursor = 0;
        int destCursor = 0;
        float step = (float) image.getHeight() / size;

        while (size > 0) {
            int actualSourceCursor = (int) sourceCursor;
            result[destCursor] = slice[actualSourceCursor];
            destCursor++;
            size--;
            sourceCursor += step;
        }

        return result;
    }

    public Color[] getSlice(int x) {
        return image.getColorData()[x];
    }

    //////

    private Color[] getHardCoded(Color[] slice, int even) {
        switch (even) {
            case 2:
                return squashTo2(slice);
        }
        return null;
    }

    public static Color[] squashTo2(Color[] input) {
        return new Color[]{input[16], input[48]};
    }

    // etc. as needed
}
