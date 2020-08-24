package com.neodem.rays.graphics;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/23/20
 */
public class TestSimpleCanvas {
    private static final int APP_WIDTH = 640;
    private static final int APP_HEIGHT = 480;
    private static final String APP_NAME = "Rays";

    public static void main(String[] args) {
        ActiveCanvas panel = new SimpleCanvas(APP_WIDTH, APP_HEIGHT);
        AppFrame appFrame = new AppFrame(APP_NAME, panel);
        appFrame.start();
    }
}
