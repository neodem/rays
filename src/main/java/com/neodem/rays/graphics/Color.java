package com.neodem.rays.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

public class Color {

    public int r;
    public int g;
    public int b;

    protected java.awt.Color baseColor;

    public static final Color black = new Color(0, 0, 0);
    public static final Color yellow = new Color(255, 255, 0);
    public static final Color red = new Color(255, 0, 0);
    public static final Color green = new Color(0, 255, 0);
    public static final Color blue = new Color(0, 0, 255);
    public static final Color white = new Color(255, 255, 255);
    public static final Color lightGray = new Color(100, 100, 100);
    public static final Color magenta = new Color(190, 0, 190);
    public static final Color lightGreen = new Color(100, 255, 100);
    public static final Color orange = new Color(255, 100, 0);
    public static final Color lightBlue = new Color(100, 100, 255);

    public Color(int r, int g, int b) {
        if (r > 255) r = 255;
        if (g > 255) g = 255;
        if (b > 255) b = 255;

        if (r < 0) r = 0;
        if (g < 0) g = 0;
        if (b < 0) b = 0;

        this.r = r;
        this.g = g;
        this.b = b;

        baseColor = new java.awt.Color(r, g, b);
    }

    public Color(InputStream is) throws IOException {
        createFromInputStream(is);
    }

    public void createFromInputStream(InputStream is) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(is));
        StreamTokenizer stream = new StreamTokenizer(reader);
        stream.commentChar('#');

        stream.nextToken();
        r = (int) stream.nval;
        stream.nextToken();
        g = (int) stream.nval;
        stream.nextToken();
        b = (int) stream.nval;
    }

    public String getStringRepresentation() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        buffer.append(r);
        buffer.append(" ");
        buffer.append(g);
        buffer.append(" ");
        buffer.append(b);
        buffer.append(" ");
        return buffer.toString();
    }

    public Color makeClone() {
        return new Color(r, g, b);
    }

    /**
     * @return Returns the baseColor.
     */
    public java.awt.Color getAWTColor() {
        return baseColor;
    }

}
