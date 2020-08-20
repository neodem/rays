package com.neodem.rays;

import com.neodem.rays.graphics.ActiveCanvas;
import com.neodem.rays.graphics.AppFrame;
import com.neodem.rays.graphics.SimpleImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class Rays {
    private static final int APP_WIDTH = 640;
    private static final int APP_HEIGHT = 480;
    private static final String APP_NAME = "Rays";

    private Rays() {
        SimpleImage[] images = new SimpleImage[4];
        images[0] = loadPNG("vwall-0.png");
        images[1] = loadPNG("vwall-1.png");
        images[2] = loadPNG("hwall-0.png");
        images[3] = loadPNG("hwall-1.png");

        ActiveCanvas panel = new RaysCanvas(APP_WIDTH, APP_HEIGHT, images);
  //      ActiveCanvas panel = new TestCanvas(APP_WIDTH, APP_HEIGHT, images);
        AppFrame appFrame = new AppFrame(APP_NAME, panel);
        appFrame.start();
    }

    private SimpleImage loadPNG(String filename) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
            BufferedImage image = ImageIO.read(path.toFile());
            return new SimpleImage(image);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Issue loading image : " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Issue loading image : " + e.getMessage(), e);
        }
    }

    public static void main(String argv[]) {
        new Rays();
    }
}
