package com.neodem.graphics.dd.engine.sprite.sprites;

import com.neodem.graphics.dd.engine.common.Color;

public class DancingRectangleSprite extends RectangleSprite {

	private static final byte DOWN = 0;
	private static final byte UP = 1;
	private static final byte RIGHT = 2;
	private static final byte LEFT = 3;

	private byte state;
	private int maxX;
	private int maxY;
	private int minX;
	private int minY;

	public DancingRectangleSprite(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
		maxX = locx + 13;
		minX = locx - 13;
		maxY = locy + 13;
		minY = locy - 13;
	}

	public void update() {
		switch (state) {
		case DOWN:
			locy += 2;
			if (locy >= maxY) {
				state = UP;
			}
			break;

		case UP:
			locy -= 2;
			if (locy <= minY) {
				state = RIGHT;
			}
			break;

		case RIGHT:
			locx += 2;
			if (locx >= maxX) {
				state = LEFT;
			}
			break;

		case LEFT:
			locx -= 2;
			if (locx <= minX) {
				state = DOWN;
			}
			break;
		}
	}
}
