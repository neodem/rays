package com.neodem.graphics.rays;

import com.neodem.graphics.dd.engine.core.ActiveCanvas;
import com.neodem.graphics.dd.engine.core.AppFrame;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class Rays {
    private static final int APP_WIDTH = 640;
    private static final int APP_HEIGHT = 480;
    private static final String APP_NAME = "Rays";

    private Rays() {
        ActiveCanvas panel = new RaysCanvas(APP_WIDTH, APP_HEIGHT);
        AppFrame appFrame = new AppFrame(APP_NAME, panel);
        appFrame.start();
    }

    public static void main(String argv[]) {
        new Rays();
    }
}
