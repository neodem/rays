package com.neodem.graphics.dd.engine.sprite.sprites;

import com.neodem.graphics.dd.engine.common.Color;

/** 
 * a 2d Sprite
 * 
 * @author Vince
 *
 */
public abstract class Sprite2D extends Sprite {
	
	protected int locx;
	protected int locy;
	
	protected Color color;
	protected boolean fill;
	
	public Sprite2D() {
		super();
	}
	
	public Sprite2D(int locx, int locy, Color color, boolean fill) {
		this(locx, locy, color, fill, false, false);
	}
	
	public Sprite2D(int locx, int locy, Color color, boolean fill, boolean visible, boolean active) {
		super(visible, active);
		this.locx = locx;
		this.locy = locy;
		setColor(color);
		setFill(fill);
	}
	
	/**
	 * @return Returns the color.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @param color The color to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * @return Returns the fill.
	 */
	public boolean isFill() {
		return fill;
	}
	
	/**
	 * @param fill The fill to set.
	 */
	public void setFill(boolean fill) {
		this.fill = fill;
	}

}
