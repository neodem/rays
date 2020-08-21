package com.neodem.rays.graphics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * An Active Canvas is a live running graphical canvas that is meant to be dropped into
 * an AppFrame. Once inited and started, it will loop and call update() on children
 * <p>
 * <p>
 * stolen wotlas engine idea
 */
public abstract class ActiveCanvas extends JPanel implements Runnable, KeyListener {

    private static final Logger logger = LoggerFactory.getLogger(ActiveCanvas.class);

    private boolean isLocked;

    /**
     * Represents the visible part of the JPanel (it has the JPanel's size) and
     * is expressed in the background's coordinate (we use the background as a
     * reference here, because all Drawables should be expressed in background
     * coordinates).
     */
    protected final Rectangle canvas;

    protected final Dimension canvasSize;

    // the main thread of the canvas.
    private Thread canvasThread;

    private boolean running;

    public ActiveCanvas(Dimension canvasSize) {
        super(false); // we don't use the default JPanel double-buffering
        setBackground(Color.white);
        isLocked = false;
        this.canvasSize = canvasSize;
        this.canvas = new Rectangle(canvasSize);

        setPreferredSize(canvasSize);
        setMaximumSize(canvasSize);
        setMinimumSize(new Dimension(10, 10));

        addKeyListener(this);
        setFocusable(true);
    }

    private String keyPressed = null;
    private String keyReleased = null;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void start() {
        if (canvasThread == null || !running) {
            canvasThread = new Thread(this);
            canvasThread.start();
        }
    }

    public final void stop() {
        running = false;
    }

    /**
     * implementations can set up here. This is called just before the main graphics loop starts
     */
    public abstract void init();

    /**
     * called in the mainLoop just before we repaint
     *
     * @param time
     */
    public abstract void update(long time, String keyPressed, String keyReleased);

    public abstract void draw(Graphics g);

    // called when canvasThread runs
    public void run() {
        init();

        Object lock = new Object();
        while (true) {

            update(0, keyPressed, keyReleased);

            if (canvas == null)
                return;

            repaint();

            synchronized (lock) {
                try {
                    lock.wait(20);
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Lock for repaint...
     */
    protected Object lockPaint = new Object();

    private Thread paintThread;

    /**
     * Our customized repaint method
     */
    public void repaint() {
        if (lockPaint == null)
            return; // to prevent repaint() calls during the constructor call.

        paint(getGraphics());

        synchronized (lockPaint) {

            if (paintThread == null) {
                isLocked = false;

                paintThread = new Thread() {
                    public void run() {
                        while (true)
                            try {
                                ActiveCanvas.this.paint(ActiveCanvas.this.getGraphics());

                                synchronized (lockPaint) {
                                    isLocked = true;
                                    lockPaint.notifyAll();
                                    lockPaint.wait(400);
                                    isLocked = false;
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in repaint() : " + e);
                                isLocked = false;
                            }
                    }
                };

                paintThread.start();
            } else {
                try {
                    if (!isLocked)
                        lockPaint.wait(400);
                } catch (Exception e) {
                }

                lockPaint.notify();
            }
        }
    }


    /*------------------------------------------------------------------------------------*/


    /**
     * OffScreen image for the GraphicsDirector.
     */
    protected Image backBufferImage;


    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    /**
     * To paint this JPanel.
     *
     * @param gc graphics object.
     */
    public void paint(Graphics gc) {
        if (gc == null || canvas == null || getHeight() <= 0 || getWidth() <= 0)
            return;

        // double-buffer init
        if (backBufferImage == null || getWidth() != backBufferImage.getWidth(this) || getHeight() != backBufferImage.getHeight(this))
            backBufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics backBufferGraphics = backBufferImage.getGraphics();

        Graphics2D gc2D = (Graphics2D) backBufferGraphics;

        // Anti-aliasing init
        RenderingHints savedRenderHints = gc2D.getRenderingHints(); // save
        RenderingHints antiARenderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        antiARenderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        backBufferGraphics.setColor(Color.white);
        backBufferGraphics.fillRect(0, 0, getWidth(), getHeight());

        long start = System.currentTimeMillis();
        draw(gc2D);
        long time = System.currentTimeMillis() - start;
        if (logger.isTraceEnabled()) {
            logger.trace("drawTime: " + time);
        }

        // Rendering Hints restore...
        gc2D.setRenderingHints(savedRenderHints);

        // double-buffer print
        gc.drawImage(backBufferImage, 0, 0, this);
    }

    public Dimension getCanvasSize() {
        return canvasSize;
    }

}
