package com.neodem.rays;

import org.assertj.core.data.Offset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

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
        Ray ray = new Ray(7.5f, 13.8f, 360 - 22);
        Collection<WorldMap.Intersection> intersections = world.rayIntersections(ray);
        assertThat(intersections).hasSize(2);

        Iterator<WorldMap.Intersection> i = intersections.iterator();
        WorldMap.Intersection i0 = i.next();
        assertThat(i0.elementType).isEqualTo(WorldMap.ElementType.HWALL);
        assertThat(i0.hitPoint).isCloseTo(0.1767788f, Offset.offset(.00001f));
        WorldMap.Intersection i1 = i.next();
        assertThat(i1.elementType).isEqualTo(WorldMap.ElementType.VWALL);
        assertThat(i1.hitPoint).isCloseTo(0.08736992f, Offset.offset(.00001f));
    }

    @Test
    public void rayIntersectShouldHandleUp() {
        Ray ray = new Ray(7.5f, 13.8f, 0);
        Collection<WorldMap.Intersection> intersections = world.rayIntersections(ray);
        assertThat(intersections).hasSize(4);

        Iterator<WorldMap.Intersection> i = intersections.iterator();
        WorldMap.Intersection i0 = i.next();
        assertThat(i0.elementType).isEqualTo(WorldMap.ElementType.HWALL);
        assertThat(i0.hitPoint).isCloseTo(0.1767788f, Offset.offset(.00001f));
        WorldMap.Intersection i1 = i.next();
        assertThat(i1.elementType).isEqualTo(WorldMap.ElementType.VWALL);
        assertThat(i1.hitPoint).isCloseTo(0.08736992f, Offset.offset(.00001f));
    }

    @Test
    public void intersectToPaint() {
        WorldMap.Intersection intersectionToPaint = world.findIntersectionToPaint(new Ray(7.5f, 13.8f, 302.4375f));
        assertThat(intersectionToPaint.elementType).isEqualTo(WorldMap.ElementType.VWALL);
        assertThat(intersectionToPaint.hitPoint).isCloseTo(.48223114f, Offset.offset(.00001f));
    }
}
