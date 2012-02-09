package com.neodem.graphics.dd.engine.applet;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class NoFlickerApplet extends Applet {

	private static final long serialVersionUID = 1L;

	private Image offScreenImage;

	private Graphics offScreenGraphics;

	private Dimension offScreenSize;

	public final void update(Graphics g) {
		Dimension d = getSize();
		if ((offScreenImage == null) || (d.width != offScreenSize.width) || (d.height != offScreenSize.height)) {
			offScreenImage = createImage(d.width, d.height);
			offScreenSize = d;
			offScreenGraphics = offScreenImage.getGraphics();
			offScreenGraphics.clearRect(0, 0, offScreenSize.width, offScreenSize.height);
		}
		paint(offScreenGraphics);
		g.drawImage(offScreenImage, 0, 0, null);
	}
}