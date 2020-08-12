package com.neodem.graphics.rays;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/11/20
 */
public class FloatingPoint {

    private final float x;
    private final float y;

    public FloatingPoint() {
        this.x = 0f;
        this.y = 0f;
    }

    public FloatingPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
