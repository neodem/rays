package com.neodem.rays;

import com.neodem.rays.graphics.SimpleImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/21/20
 */
public class Utils {
    public static SimpleImage loadPngFromClasspath(String filename) {
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
}
