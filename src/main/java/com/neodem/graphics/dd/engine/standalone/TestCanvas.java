package com.neodem.graphics.dd.engine.standalone;

import java.awt.Graphics;

import com.neodem.graphics.dd.engine.common.Color;
import com.neodem.graphics.dd.engine.sprite.sprites.BouncingRectange;
import com.neodem.graphics.dd.engine.sprite.sprites.DancingRectangleSprite;

@SuppressWarnings("serial")
public class TestCanvas extends GamePanel {

	private int screenW;
	private int screenH;
	private Color bgColor;
	
	public TestCanvas(int width, int height, Color bgColor) {
		super();
		screenW = width;
		screenH = height;
		this.bgColor = bgColor;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(bgColor.getAWTColor());
		g.fillRect(0, 0, screenW, screenH);
		sprite.paint(g);
		b.paint(g);
	}

	@Override
	public void update(long elapsedTime) {
		sprite.update();
		b.update();
	}

	@Override
	public void init() {
		sprite = new DancingRectangleSprite(20, 30, 50, 50, Color.red);
		sprite.setFill(true);
		b = new BouncingRectange(100,200,100,100,Color.blue,400, 200);
		b.setFill(true);
		b.setVelocity(3,4);
	}
	
	
	protected DancingRectangleSprite sprite;
	protected BouncingRectange b;

}
