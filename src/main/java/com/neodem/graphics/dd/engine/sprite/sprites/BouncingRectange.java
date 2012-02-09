package com.neodem.graphics.dd.engine.sprite.sprites;

import com.neodem.graphics.dd.engine.common.Color;
import com.neodem.graphics.dd.engine.sprite.Moveable;


public class BouncingRectange extends RectangleSprite implements Moveable {

	protected int maxW;
	protected int maxH;
	protected int vx;
	protected int vy;
	
	public BouncingRectange(int x, int y, int w, int h, Color c, int maxW, int maxH) {
		super(x, y, w, h, c);
		this.maxH = maxH;
		this.maxW = maxW;
	}

	public void setPosition(int x, int y) {
		locx = x;
		locy = y;
	}

	public void setVelocity(int x, int y) {
		vx = x;
		vy = y;
	}

	public void updatePosition() {
		locx += vx;
		locy += vy;
	}
	
	public void update() {
		if((locx + width > maxW) || locx<0) {
			vx = -vx;
		}
		
		if((locy + height > maxH) || locy<0) {
			vy = -vy;
		}
		
		updatePosition();
	}

}
