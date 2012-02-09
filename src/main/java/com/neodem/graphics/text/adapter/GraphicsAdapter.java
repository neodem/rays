package com.neodem.graphics.text.adapter;

import com.neodem.graphics.text.model.GraphicsObject;

public interface GraphicsAdapter<T> {
	public GraphicsObject convert(T o);
}
