package com.neodem.rays;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/12/20
 */
public class RayTest {



    @Test
    public void constructorSetsCorrectQuadrant() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 338, 0);
        assertThat(ray.getQuadrant()).isEqualTo(Quadrant.UP_LEFT);
    }

}
