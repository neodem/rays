package com.neodem.graphics.dd.engine.sprite.sprites;

import java.awt.Graphics;

import com.neodem.graphics.dd.engine.common.Color;




public class RectangleSprite extends Sprite2D {
	protected int width;
	protected int height;
	
	public RectangleSprite(int x, int y, int w, int h, Color c) {
		super(x,y,c, false);
		width = w;
		height = h;
		restore();
	}

	public void paint(Graphics g) {
		if(visible) {
			g.setColor(color.getAWTColor());
			if(fill) {
				g.fillRect(locx, locy, width, height);
			}
			else {
				g.drawRect(locx, locy, width, height);
			}
		}
	}

	public void update() {
	}

}
