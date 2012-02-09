package com.neodem.graphics.dd.engine.applet;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class StandaloneApplet extends Applet {

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

	static public void main(String argv[]) {
		final Applet applet = new StandaloneApplet();
		System.runFinalizersOnExit(true);
		Frame frame = new Frame("MyApplet");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				applet.stop();
				applet.destroy();
				System.exit(0);
			}
		});
		frame.add("Center", applet);
		applet.setStub(new Stub(argv, applet));
		frame.show();
		applet.init();
		applet.start();
		frame.pack();
	}
}
