package com.neodem.graphics.rays;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.neodem.graphics.rays.WorldMap.ElementType.HWALL;
import static com.neodem.graphics.rays.WorldMap.ElementType.VWALL;

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

    public class Intersection {
        ElementType elementType;

        // this is the intersection point on the element from the element origin location
        float hitPoint;
    }

    /**
     * return the first intersection with the world
     *
     * @param ray
     * @return
     */
    public Intersection rayIntersection(Ray ray) {
        List<FloatingPoint> hPoints = ray.intersectWorldHorizontal(mapHeight);
        // check against all HWALLS, remove all misses
        Collection<Point> hWallOrigins = data.get(HWALL);
        Iterator<FloatingPoint> iterator = hPoints.iterator();
        while (iterator.hasNext()) {
            FloatingPoint point = iterator.next();
            if (point.getY() < 0 || point.getY() > mapHeight || point.getX() < 0 || point.getX() > mapWidth) {
                iterator.remove();
                continue;
            }
            boolean hit = false;
            for (Point wallOrigin : hWallOrigins) {
                // do we connect with any H wall?
                if ((int) point.getY() == wallOrigin.y) {
                    if (point.getX() >= wallOrigin.x && point.getX() < (wallOrigin.x + 1)) {
                        hit = true;
                        break;
                    }
                }
            }

            if (!hit) iterator.remove();
        }

        List<FloatingPoint> vPoints = ray.intersectWorldVertical(mapWidth);
        // check against all VWALLS, remove all misses
        Collection<Point> vWallOrigins = data.get(VWALL);
        iterator = vPoints.iterator();
        while (iterator.hasNext()) {
            FloatingPoint point = iterator.next();
            if (point.getY() < 0 || point.getY() > mapHeight || point.getX() < 0 || point.getX() > mapWidth) {
                iterator.remove();
                continue;
            }
            boolean hit = false;
            for (Point wallOrigin : vWallOrigins) {
                // do we connect with any V wall?
                if ((int) point.getX() == wallOrigin.x) {
                    if (point.getY() >= wallOrigin.y && point.getY() < (wallOrigin.y + 1)) {
                        hit = true;
                        break;
                    }
                }
            }

            if (!hit) iterator.remove();
        }

        // compute distances for all remaining points
        // for the least distance we compute the hitPoint and return the Intersection

        return null;
    }

    private boolean inersectVWall(Point vWallOrigin, FloatingPoint floatingPoint) {


        return false;
    }

    private boolean intersectHWall(Point hWallOrigin, FloatingPoint floatingPoint) {
        return (int) floatingPoint.getY() == hWallOrigin.y;
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
