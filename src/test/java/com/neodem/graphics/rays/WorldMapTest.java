package com.neodem.graphics.rays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/14/20
 */
public class WorldMapTest {
    private WorldMap world;

    @Before
    public void setUp() throws Exception {
        world = new TestWorldMap();
    }

    @After
    public void tearDown() throws Exception {
        world = null;
    }

    @Test
    public void rayIntersect() {
        Ray ray = new Ray(7.5f, 13.8f, 360-22);
        WorldMap.Intersection intersection = world.rayIntersection(ray);
    }
}
