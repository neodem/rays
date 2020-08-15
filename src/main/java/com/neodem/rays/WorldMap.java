package com.neodem.rays;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static com.neodem.rays.WorldMap.ElementType.HWALL;
import static com.neodem.rays.WorldMap.ElementType.VWALL;

/**
 * a WorldMap uses X,Y notation with it's top left being 0,0 and X going right and Y going down
 * (bottom right is mapWidth, mapHeight)
 * <p>
 * it has many element types:
 * 1) Walls
 * <p>
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/10/20
 */

public class WorldMap {

    public enum ElementType {
        VWALL, HWALL
    }

    private final int mapWidth;
    private final int mapHeight;

    private final Map<ElementType, Collection<Point>> data = new HashMap<>();

    public WorldMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public class Intersection implements Comparable<Intersection> {
        ElementType elementType;

        // this is the intersection point on the element from the element origin location
        float hitPoint;

        // distance from ray origin
        float distance;

        FloatingPoint intersection;

        Point wallOrigin;

        @Override
        public int compareTo(Intersection o) {
            return (int) (distance - o.distance);
        }
    }

    /**
     * return the intersections with elements in the WorldMap
     *
     * @param ray
     * @return
     */
    public Collection<Intersection> rayIntersections(Ray ray) {

        // collect all possible intersections
        Collection<FloatingPoint> possiblePoints = ray.intersectWorldHorizontal(mapHeight);
        possiblePoints.addAll(ray.intersectWorldVertical(mapWidth));

        Collection<Point> hWallOrigins = data.get(HWALL);
        Collection<Point> vWallOrigins = data.get(VWALL);

        Collection<Intersection> intersections = new HashSet<>();
        possiblePoints.stream()
                .filter(this::outOfBounds)
                .forEach(p -> {
                    for (Point wallOrigin : hWallOrigins) {
                        // do we connect with any H wall?
                        if (p.isYRelativelyEqualTo(wallOrigin.y)) {
                            if (p.isXWithinRange(wallOrigin.x, wallOrigin.x + 1)) {
                                Intersection i = new Intersection();
                                i.elementType = HWALL;
                                i.hitPoint = p.getXAbsolute();
                                i.intersection = p;
                                i.wallOrigin = wallOrigin;
                                intersections.add(i);
                            }
                        }
                    }
                    for (Point wallOrigin : vWallOrigins) {
                        // do we connect with any V wall?
                        if (p.isXRelativelyEqualTo(wallOrigin.x)) {
                            if (p.isYWithinRange(wallOrigin.y, wallOrigin.y + 1)) {
                                Intersection i = new Intersection();
                                i.elementType = VWALL;
                                i.intersection = p;
                                i.hitPoint = p.getYAbsolute();
                                i.wallOrigin = wallOrigin;
                                intersections.add(i);
                            }
                        }
                    }
                });

        return intersections;
    }

    private boolean outOfBounds(FloatingPoint point) {
        return (point.getY() > 0 && point.getY() <= mapHeight && point.getX() > 0 && point.getX() <= mapWidth);
    }

    /// creating Elements /adding to/removing from map

    public void addElement(ElementType type, int x, int y) {
        if (x < 0) throw new IllegalArgumentException("x (" + x + ") may not be less than zero");
        if (y < 0) throw new IllegalArgumentException("y (" + y + ") may not be less than zero");
        if (x > mapWidth)
            throw new IllegalArgumentException("x (" + x + ") may not be greater than mapWidth:" + mapWidth);
        if (y > mapHeight)
            throw new IllegalArgumentException("y (" + y + ") may not be greater than mapHeight:" + mapHeight);

        Collection<Point> points = data.get(type);
        if (points == null) points = new HashSet<>();
        points.add(new Point(x, y));

        data.put(type, points);
    }

    public void removeElement(ElementType type, int x, int y) {
        if (x < 0) throw new IllegalArgumentException("x (" + x + ") may not be less than zero");
        if (y < 0) throw new IllegalArgumentException("y (" + y + ") may not be less than zero");
        if (x > mapWidth)
            throw new IllegalArgumentException("x (" + x + ") may not be greater than mapWidth:" + mapWidth);
        if (y > mapHeight)
            throw new IllegalArgumentException("y (" + y + ") may not be greater than mapHeight:" + mapHeight);

        Point remove = new Point(x, y);

        Collection<Point> points = data.get(type);
        if (points != null) {
            Iterator<Point> iterator = points.iterator();
            while (iterator.hasNext()) {
                Point next = iterator.next();
                if (next.equals(remove)) iterator.remove();
                break;
            }
        }
    }

    public void addHWalls(int xStart, int yStart, int num) {
        for (int i = 0; i < num; i++) {
            addElement(HWALL, xStart + i, yStart);
        }
    }

    public void addVWalls(int xStart, int yStart, int num) {
        for (int i = 0; i < num; i++) {
            addElement(VWALL, xStart, yStart + i);
        }
    }

    public void addWalls(WorldMap.ElementType type, int xStart, int yStart, int num) {
        if (type == HWALL) addHWalls(xStart, yStart, num);
        if (type == VWALL) addVWalls(xStart, yStart, num);
    }
}
