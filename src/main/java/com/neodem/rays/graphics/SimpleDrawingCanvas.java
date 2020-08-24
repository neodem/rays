package com.neodem.rays.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/23/20
 */
public class SimpleDrawingCanvas extends ActiveCanvas {

    private List<List<Point>> curves = new ArrayList<>();

    public SimpleDrawingCanvas(int width, int height) {
        super(new Dimension(width, height));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (var curve: curves) {
            var previousPoint = curve.get(0);
            for (var point: curve) {
                g.drawLine(previousPoint.x, previousPoint.y, point.x, point.y);
                previousPoint = point;
            }
        }
    }

    protected void initCanvas() {
        // Register event listeners on construction of the panel.
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                var newCurve = new ArrayList<Point>();
                newCurve.add(new Point(e.getX(), e.getY()));
                curves.add(newCurve);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                curves.get(curves.size() - 1).add(new Point(e.getX(), e.getY()));
                repaint(0, 0, getWidth(), getHeight());
            }
        });
    }
}
