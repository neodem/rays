package com.neodem.rays;

import org.assertj.core.data.Offset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/19/20
 */
public class RayComputerTest {
    private RayComputer rayComputer;

    @Before
    public void setUp() throws Exception {
        rayComputer = new RayComputer(640, 120);
    }

    @After
    public void tearDown() throws Exception {
        rayComputer = null;
    }

    @Test
    public void computeRaysShouldMakeANiceSpread() {
        List<RayComputer.WorldRay> rays = rayComputer.computeRays(new FloatingPoint(7.5f, 13.8f), 0);
        assertThat(rays).isNotNull().hasSize(640);

        assertThat(rays.get(0).ray.getAngle()).isCloseTo(300f, Offset.offset(.00001f));
        assertThat(rays.get(0).ray.getQuadrant()).isEqualTo(Quadrant.UP_LEFT);
        assertThat(rays.get(1).ray.getAngle()).isCloseTo(300.1875f, Offset.offset(.00001f));
        assertThat(rays.get(1).ray.getQuadrant()).isEqualTo(Quadrant.UP_LEFT);
        assertThat(rays.get(319).ray.getAngle()).isCloseTo(359.8125f, Offset.offset(.00001f));
        assertThat(rays.get(319).ray.getQuadrant()).isEqualTo(Quadrant.UP_LEFT);
        assertThat(rays.get(320).ray.getAngle()).isCloseTo(0f, Offset.offset(.00001f));
        assertThat(rays.get(320).ray.getQuadrant()).isEqualTo(Quadrant.UP);
        assertThat(rays.get(321).ray.getAngle()).isCloseTo(0.1875f, Offset.offset(.00001f));
        assertThat(rays.get(321).ray.getQuadrant()).isEqualTo(Quadrant.UP_RIGHT);
        assertThat(rays.get(639).ray.getAngle()).isCloseTo(59.8125f, Offset.offset(.00001f));
        assertThat(rays.get(639).ray.getQuadrant()).isEqualTo(Quadrant.UP_RIGHT);

    }
}
