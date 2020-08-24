package com.neodem.rays.graphics;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/23/20
 */
public class SimpleCanvas extends ActiveCanvas {

    public SimpleCanvas(int width, int height) {
        super(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    protected void initCanvas() {
    }
}
