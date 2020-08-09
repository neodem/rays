package com.neodem.graphics.dd.engine.core;

import javax.swing.*;
import java.awt.*;

/**
 * A main frame for our graphic environment. It will start an app window (JFrame) and fill it with an ActiveCanvas
 * implementation. It will then call start() on the canvas.
 * <p>
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class AppFrame extends JFrame {

    private final ActiveCanvas canvas;

    public AppFrame(String appName, ActiveCanvas canvas) {
        super(appName);
        Dimension canvasSize = canvas.getCanvasSize();
        this.canvas = canvas;

        super.setSize(canvasSize.width, canvasSize.height);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.add("Center", canvas);
        super.setVisible(true);
    }

    public void start() {
        canvas.start();
    }
}
