package com.neodem.graphics.rays;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

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

        Point remove = new Point(x,y);

        Collection<Point> points = data.get(type);
        if(points != null) {
            Iterator<Point> iterator = points.iterator();
            while(iterator.hasNext()) {
                Point next = iterator.next();
                if(next.equals(remove)) iterator.remove();
                break;
            }
        }
    }
}
