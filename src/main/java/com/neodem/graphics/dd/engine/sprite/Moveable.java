package com.neodem.graphics.dd.engine.sprite;


public interface Moveable {
	public void setPosition(int x, int y);
	public void setVelocity(int x, int y);
	public void updatePosition();
}
