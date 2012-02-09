package com.neodem.graphics.dd.engine.standalone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * stolen wotlas engine idea
 */
public abstract class GamePanel extends JPanel implements Runnable {

	/*------------------------------------------------------------------------------------*/

	/**
	 * Represents the visible part of the JPanel (it has the JPanel's size) and
	 * is expressed in the background's coordinate (we use the background as a
	 * reference here, because all Drawables should be expressed in background
	 * coordinates).
	 */
	protected Rectangle screen;

	/**
	 * Background's Dimension. The background can be any Drawable, we don't have
	 * to possess a handle it, we only need its dimension.
	 */
	protected Dimension background;

	/*------------------------------------------------------------------------------------*/

	/**
	 * Can we display our drawables ?
	 */
	protected boolean display;

	/**
	 * Lock for repaint...
	 */
	protected Object lockPaint = new Object();

	/**
	 * OffScreen image for the GraphicsDirector.
	 */
	protected Image backBufferImage;

	private Thread paintThread;

	private boolean isLocked;

	private boolean running;

	/*------------------------------------------------------------------------------------*/

	public GamePanel() {
		super(false); // we don't use the default JPanel double-buffering

		display = false;
		setBackground(Color.white);
		isLocked = false;
	}

	public void init(Dimension screen) {

		this.screen = new Rectangle(screen);
		setPreferredSize(screen);
		setMaximumSize(background);
		setMinimumSize(new Dimension(10, 10));
	
		display = true;

		init();
	}

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
								GamePanel.this.paint(GamePanel.this.getGraphics());

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

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * To avoid flickering.
	 */
	public void update(Graphics g) {
		paint(g);
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * To paint this JPanel.
	 * 
	 * @param gc
	 *            graphics object.
	 */
	public void paint(Graphics gc) {
		if (gc == null || screen == null || getHeight() <= 0 || getWidth() <= 0)
			return;

		// double-buffer init
		if (backBufferImage == null || getWidth() != backBufferImage.getWidth(this)
				|| getHeight() != backBufferImage.getHeight(this))
			backBufferImage = (Image) new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

		Graphics backBufferGraphics = backBufferImage.getGraphics();

		if (!display) {
			backBufferGraphics.setColor(Color.white);
			backBufferGraphics.fillRect(0, 0, getWidth(), getHeight());
			gc.drawImage(backBufferImage, 0, 0, this);
			return;
		}

		Graphics2D gc2D = (Graphics2D) backBufferGraphics;

		// Anti-aliasing init
		RenderingHints savedRenderHints = gc2D.getRenderingHints(); // save
		RenderingHints antiARenderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		antiARenderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		backBufferGraphics.setColor(Color.white);
		backBufferGraphics.fillRect(0, 0, getWidth(), getHeight());

		draw(gc2D);

		// Rendering Hints restore...
		gc2D.setRenderingHints(savedRenderHints);

		// double-buffer print
		gc.drawImage(backBufferImage, 0, 0, this);
	}

	public abstract void init();

	public abstract void update(long time);

	public abstract void draw(Graphics g);

	
	public void run() {
		try {
			init();
			initMainLoop();
			mainLoop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			exitGame();
		}
	}
	
	
	
	private void initMainLoop() {
		
	}

	protected void mainLoop() {
		Object lock = new Object();
		while (true) {
			update(0);
			if (screen == null)
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
	
	public void exitGame() {
		try {
			System.exit(0);
		} catch (java.security.AccessControlException e) {
		}
	}


	/**
	 * To get the screen rectangle. Any changes to the returned rectangle are
	 * affected to the original.
	 */
	public Rectangle getScreenRectangle() {
		return screen;
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * To get the background dimension.
	 */
	public Dimension getBackgroundDimension() {
		return background;
	}
//
//	public void startGame() {
//		mainLoop();
//	}
	
	public void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}
	private Thread animator;
	public void stopGame() {
		running = false;
	}
	
}
