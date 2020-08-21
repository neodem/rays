package com.neodem.rays;

import com.neodem.rays.graphics.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/21/20
 */
public class WallImageTest {

    private WallImage wallImage;

    @Before
    public void setUp() throws Exception {
        wallImage = new WallImage(Utils.loadPngFromClasspath("hwall-0.png"));
    }

    @After
    public void tearDown() throws Exception {
        wallImage = null;
    }

    @Test
    public void getSliceShouldShrink() {
        Color[] slice = wallImage.getSlice(7);

        Color[] result = wallImage.getSlice(7, 2);
        assertThat(result[0]).isEqualTo(slice[0]);
        assertThat(result[1]).isEqualTo(slice[32]);

        result = wallImage.getSlice(7, 4);
        assertThat(result[0]).isEqualTo(slice[0]);
        assertThat(result[1]).isEqualTo(slice[16]);
        assertThat(result[2]).isEqualTo(slice[32]);
        assertThat(result[3]).isEqualTo(slice[48]);

        // will actually do 32
        result = wallImage.getSlice(7, 33);
        assertThat(result[0]).isEqualTo(slice[0]);
        assertThat(result[1]).isEqualTo(slice[2]);
        assertThat(result[31]).isEqualTo(slice[62]);

        result = wallImage.getSlice(7, 64);
        assertThat(result[0]).isEqualTo(slice[0]);
        assertThat(result[1]).isEqualTo(slice[1]);
        assertThat(result[31]).isEqualTo(slice[31]);
        assertThat(result[63]).isEqualTo(slice[63]);
    }

    @Test
    public void getSliceShouldEnlarge() {
        Color[] slice = wallImage.getSlice(7);

        Color[] result = wallImage.getSlice(7, 128);
        assertThat(result[0]).isEqualTo(slice[0]);
        assertThat(result[1]).isEqualTo(slice[0]);
        assertThat(result[126]).isEqualTo(slice[63]);
        assertThat(result[127]).isEqualTo(slice[63]);
    }
}
