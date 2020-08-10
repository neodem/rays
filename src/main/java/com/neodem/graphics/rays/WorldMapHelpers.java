package com.neodem.graphics.rays;

import static com.neodem.graphics.rays.WorldMap.ElementType.HWALL;
import static com.neodem.graphics.rays.WorldMap.ElementType.VWALL;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/10/20
 */
public class WorldMapHelpers {
    public static void addHWalls(WorldMap worldMap, int xStart, int yStart, int num) {
        for (int i = 0; i < num; i++) {
            worldMap.addElement(HWALL, xStart + i, yStart);
        }
    }

    public static void addVWalls(WorldMap worldMap, int xStart, int yStart, int num) {
        for (int i = 0; i < num; i++) {
            worldMap.addElement(VWALL, xStart, yStart + i);
        }
    }

    public static void addWalls(WorldMap.ElementType type, WorldMap worldMap, int xStart, int yStart, int num) {
        if (type == HWALL) addHWalls(worldMap, xStart, yStart, num);
        if (type == VWALL) addVWalls(worldMap, xStart, yStart, num);
    }
}
