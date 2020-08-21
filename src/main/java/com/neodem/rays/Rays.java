package com.neodem.rays;

import com.neodem.rays.graphics.ActiveCanvas;
import com.neodem.rays.graphics.AppFrame;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class Rays {
    private static final int APP_WIDTH = 640;
    private static final int APP_HEIGHT = 480;
    private static final String APP_NAME = "Rays";

    private Rays() {
        WallImage[] images = new WallImage[4];
        images[0] = new WallImage(Utils.loadPngFromClasspath("vwall-0.png"));
        images[1] = new WallImage(Utils.loadPngFromClasspath("vwall-1.png"));
        images[2] = new WallImage(Utils.loadPngFromClasspath("hwall-0.png"));
        images[3] = new WallImage(Utils.loadPngFromClasspath("hwall-1.png"));

        ActiveCanvas panel = new RaysCanvas(APP_WIDTH, APP_HEIGHT, images);
        //      ActiveCanvas panel = new TestCanvas(APP_WIDTH, APP_HEIGHT, images);
        AppFrame appFrame = new AppFrame(APP_NAME, panel);
        appFrame.start();
    }


    public static void main(String argv[]) {
        new Rays();
    }
}
