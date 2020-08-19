package com.neodem.rays;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/11/20
 */
public class FloatingPoint {

    final double THRESHOLD = .0001;

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

    public FloatingPoint addX(float x) {
        return new FloatingPoint(this.x + x, this.y);
    }

    public FloatingPoint addY(float y) {
        return new FloatingPoint(this.x, this.y + y);
    }

    public boolean isYRelativelyEqualTo(float compare) {
        return Math.abs(y - compare) < THRESHOLD;
    }

    public boolean isXRelativelyEqualTo(float compare) {
        return Math.abs(x - compare) < THRESHOLD;
    }

    /**
     * range [low,high)
     *
     * @param low
     * @param high
     * @return
     */
    public boolean isYWithinRange(int low, int high) {
        return y >= low && y < high;
    }

    /**
     * range [low,high)
     *
     * @param low
     * @param high
     * @return
     */
    public boolean isXWithinRange(int low, int high) {
        return x >= low && x < high;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getXAbsolute() {
        return x - (int) x;
    }

    public float getYAbsolute() {
        return y - (int) y;
    }
}
