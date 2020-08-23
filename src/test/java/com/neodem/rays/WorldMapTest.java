package com.neodem.rays;

import org.assertj.core.data.Offset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
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
    public void testWorldMapShouldHaveTheRightHWalls() {
        Collection<Point> horizontalWalls = world.getElements(WorldMap.ElementType.HWALL);
        assertThat(horizontalWalls).hasSize(28);

        assertThat(horizontalWalls).contains(new Point(2, 9));
        assertThat(horizontalWalls).contains(new Point(2, 11));

        assertThat(horizontalWalls).contains(new Point(3, 9));
        assertThat(horizontalWalls).contains(new Point(3, 13));

        assertThat(horizontalWalls).contains(new Point(4, 9));
        assertThat(horizontalWalls).contains(new Point(4, 11));
        assertThat(horizontalWalls).contains(new Point(4, 12));
        assertThat(horizontalWalls).contains(new Point(4, 13));

        assertThat(horizontalWalls).contains(new Point(5, 12));
        assertThat(horizontalWalls).contains(new Point(5, 13));

        assertThat(horizontalWalls).contains(new Point(6, 10));
        assertThat(horizontalWalls).contains(new Point(6, 13));

        assertThat(horizontalWalls).contains(new Point(7, 7));
        assertThat(horizontalWalls).contains(new Point(7, 9));
        assertThat(horizontalWalls).contains(new Point(7, 10));
        assertThat(horizontalWalls).contains(new Point(7, 14));

        assertThat(horizontalWalls).contains(new Point(8, 7));
        assertThat(horizontalWalls).contains(new Point(8, 13));

        assertThat(horizontalWalls).contains(new Point(9, 7));
        assertThat(horizontalWalls).contains(new Point(9, 9));
        assertThat(horizontalWalls).contains(new Point(9, 10));
        assertThat(horizontalWalls).contains(new Point(9, 13));

        assertThat(horizontalWalls).contains(new Point(10, 10));
        assertThat(horizontalWalls).contains(new Point(10, 12));

        assertThat(horizontalWalls).contains(new Point(11, 10));
        assertThat(horizontalWalls).contains(new Point(11, 12));

        assertThat(horizontalWalls).contains(new Point(12, 10));
        assertThat(horizontalWalls).contains(new Point(12, 12));

    }

    @Test
    public void testWorldMapShouldHaveTheRightVWalls() {
        Collection<Point> verticalWalls = world.getElements(WorldMap.ElementType.VWALL);
        assertThat(verticalWalls).hasSize(21);
        assertThat(verticalWalls).contains(new Point(2, 9));
        assertThat(verticalWalls).contains(new Point(2, 10));
        assertThat(verticalWalls).contains(new Point(3, 11));
        assertThat(verticalWalls).contains(new Point(3, 12));
        assertThat(verticalWalls).contains(new Point(4, 11));
        assertThat(verticalWalls).contains(new Point(5, 9));
        assertThat(verticalWalls).contains(new Point(5, 10));
        assertThat(verticalWalls).contains(new Point(6, 10));
        assertThat(verticalWalls).contains(new Point(6, 11));
        assertThat(verticalWalls).contains(new Point(7, 7));
        assertThat(verticalWalls).contains(new Point(7, 8));
        assertThat(verticalWalls).contains(new Point(7, 13));
        assertThat(verticalWalls).contains(new Point(8, 9));
        assertThat(verticalWalls).contains(new Point(8, 13));
        assertThat(verticalWalls).contains(new Point(9, 9));
        assertThat(verticalWalls).contains(new Point(10, 7));
        assertThat(verticalWalls).contains(new Point(10, 8));
        assertThat(verticalWalls).contains(new Point(10, 11));
        assertThat(verticalWalls).contains(new Point(10, 12));
        assertThat(verticalWalls).contains(new Point(13, 10));
        assertThat(verticalWalls).contains(new Point(13, 11));
    }

    @Test
    public void addWallsShouldAddCorrectVWalls() {
        world.clearElements(WorldMap.ElementType.VWALL);
        world.addWalls(WorldMap.ElementType.VWALL, 12, 10, 2);
        Collection<Point> elements = world.getElements(WorldMap.ElementType.VWALL);
        assertThat(elements).hasSize(2);
        assertThat(elements).contains(new Point(12, 10));
        assertThat(elements).contains(new Point(12, 11));
    }

    @Test
    public void removeElementShouldRemoveElement() {
        world.removeElement(WorldMap.ElementType.HWALL, 7, 13);
        Collection<Point> elements = world.getElements(WorldMap.ElementType.HWALL);
        assertThat(elements).doesNotContain(new Point(7, 13));
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
