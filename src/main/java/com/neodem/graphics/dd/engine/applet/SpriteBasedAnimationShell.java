package com.neodem.graphics.dd.engine.applet;

import java.awt.Graphics;
import java.awt.Image;

import com.neodem.graphics.dd.engine.common.Color;

public abstract class SpriteBasedAnimationShell extends NoFlickerApplet implements Runnable {

	private static final long serialVersionUID = 1L;

	private int refreshRate;

	private int screenW;

	private int screenH;

	private Color bgColor;

	private Thread thread;

	private Image image;

	private Graphics offscreenBuffer;

	public SpriteBasedAnimationShell(int refreshRate, int screenW, int screenH, Color bgColor) {
		this.refreshRate = refreshRate;
		this.screenH = screenH;
		this.screenW = screenW;
		this.bgColor = bgColor;
	}

	public void init() {
		setBackground(bgColor.getAWTColor());
		initSprites();
		image = createImage(screenW, screenH);
		offscreenBuffer = image.getGraphics();
	}

	protected abstract void initSprites();

	protected abstract void updateSprites();

	protected abstract void paintSprites(Graphics g);

	public void run() {
		Thread thisThread = Thread.currentThread();
		while (thread == thisThread) {
			repaint();
			updateSprites();

			try {
				Thread.sleep(refreshRate);
			} catch (InterruptedException e) {
			}
		}
	}

	public void start() {
		thread = new Thread(this);
		if (thread != null) {
			thread.start();
		}
	}

	public void stop() {
		thread = null;
	}

	public void paint(Graphics g) {
		offscreenBuffer.setColor(bgColor.getAWTColor());
		offscreenBuffer.fillRect(0, 0, screenW, screenH);
		paintSprites(offscreenBuffer);
		g.drawImage(image, 0, 0, this);
	}
}
