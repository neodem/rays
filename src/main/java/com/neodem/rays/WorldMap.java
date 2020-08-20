package com.neodem.rays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static final Logger logger = LoggerFactory.getLogger(WorldMap.class);

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

        // distance from the interesection to the ray origin
        float distance;

        // the actual intersection point that connected with the element
        FloatingPoint intersection;

        // the wall origin, in X,Y as integers
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

        intersections.stream()
                .forEach(i -> computeDistance(i, ray));

        return intersections;
    }

    /**
     * given a ray and the world, we determine if it interects and if so, we return the
     * intersection that we should paint.
     *
     * @param ray
     * @return
     */
    public WorldMap.Intersection findIntersectionToPaint(Ray ray) {
        Collection<WorldMap.Intersection> intersections = rayIntersections(ray);
        WorldMap.Intersection intersectionToPaint;
        if (intersections.isEmpty()) {
            // this ray hits the edge of the world
            // TODO fix this by making HWALL and VWALL around our world, even though right now
            // we have a TestWorldMap that is self contained so this should never happen
            throw new RuntimeException("World Edge Encountered");
        } else {
            intersections = intersections.stream()
                    .sorted()
                    .collect(Collectors.toList());

            intersectionToPaint = intersections.iterator().next();
        }
        return intersectionToPaint;
    }

    private void computeDistance(Intersection i, Ray ray) {
        // compute the distance to the hitPoint from the playerLocation
        // TODO consider the fisheye effect

        // temp.
        float x1 = i.intersection.getX();
        float y1 = i.intersection.getY();

        float x2 = ray.getOrigin().getX();
        float y2 = ray.getOrigin().getY();

        float xs = (x1 - x2) * (x1 - x2);
        float ys = (y1 - y2) * (y1 - y2);
        float m = xs + ys;

        i.distance = (float) Math.sqrt(m);
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
