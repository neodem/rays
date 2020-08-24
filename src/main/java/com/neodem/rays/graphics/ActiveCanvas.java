package com.neodem.rays.graphics;

import javax.swing.*;
import java.awt.Dimension;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/23/20
 */
public abstract class ActiveCanvas extends JPanel {
    private final Dimension canvasSize;

    public ActiveCanvas(Dimension canvasSize) {
        super(false);
        this.canvasSize = canvasSize;
    }

    public Dimension getCanvasSize() {
        return canvasSize;
    }

     public final void start() {
        initCanvas();
     }

    protected abstract void initCanvas();
}
