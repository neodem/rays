package com.neodem.graphics.dd.engine.common;

import java.io.IOException;
import java.io.InputStream;

public interface Savable {
	public void createFromInputStream(InputStream is) throws IOException;
	public String getStringRepresentation();
}
