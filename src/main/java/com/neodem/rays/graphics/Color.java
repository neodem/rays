package com.neodem.rays.graphics;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Color implements Cloneable {

    private final int r;
    private final int g;
    private final int b;
    private final int rgb;

    protected java.awt.Color awtColor;

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

        this.rgb = (0x000000ff & ((char) b << 0)) |
                (0x0000ff00 & ((char) g << 8)) |
                (0x00ff0000 & ((char) r << 16));

        awtColor = new java.awt.Color(r, g, b);
    }

    public Color(int rgb) {
        int alpha = (rgb >> 24) & 0xFF;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = (rgb) & 0xFF;

        this.r = red;
        this.g = green;
        this.b = blue;

        this.rgb = rgb;

        awtColor = new java.awt.Color(r, g, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        return new EqualsBuilder()
                .append(r, color.r)
                .append(g, color.g)
                .append(b, color.b)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(r)
                .append(g)
                .append(b)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", rgb=" + rgb +
                '}';
    }

    public Color clone() {
        return new Color(r, g, b);
    }

    /**
     * @return Returns the baseColor.
     */
    public java.awt.Color getAWTColor() {
        return awtColor;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getRgb() {
        return rgb;
    }
}
