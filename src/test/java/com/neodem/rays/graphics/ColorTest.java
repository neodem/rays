package com.neodem.rays.graphics;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/21/20
 */
public class ColorTest {

    @Test
    public void toFromRGB() {
        Color c = new Color(1244);
        int rgb = c.getRgb();
        assertThat(rgb).isEqualTo(1244);
    }
}
