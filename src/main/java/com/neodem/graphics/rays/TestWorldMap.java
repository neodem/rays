package com.neodem.graphics.rays;

import static com.neodem.graphics.rays.WorldMap.ElementType.HWALL;
import static com.neodem.graphics.rays.WorldMap.ElementType.VWALL;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/10/20
 */
public class TestWorldMap extends WorldMap {
    public TestWorldMap() {
        super(16, 16);

        // room 1
        addWalls(HWALL, 7, 7, 3);
        addWalls(HWALL, 7, 9, 3);
        addWalls(VWALL, 7, 7, 2);
        addWalls(VWALL, 10, 7, 2);
        removeElement(HWALL, 9, 8);

        // room 2
        addWalls(HWALL,  2, 9, 3);
        addWalls(HWALL,  2, 11, 3);
        addWalls(VWALL,  2, 9, 2);
        addWalls(VWALL,  5, 9, 2);
        removeElement(HWALL, 3, 11);

        // room 3
        addWalls(HWALL,  10, 10, 3);
        addWalls(HWALL,  10, 12, 3);
        addWalls(VWALL,  10, 10, 2);
        addWalls(VWALL,  12, 10, 2);
        removeElement(VWALL, 10, 10);

        // room 4
        addWalls(HWALL,  6, 10, 4);
        addWalls(HWALL,  6, 13, 4);
        addWalls(VWALL,  6, 10, 3);
        addWalls(VWALL,  10, 10, 3);
        removeElement(VWALL, 10, 10);
        removeElement(VWALL, 6, 12);
        removeElement(HWALL, 7, 13);

        // hall 1
        addElement(VWALL, 8, 9);
        addElement(VWALL, 9, 9);

        // hall 2
        addWalls(VWALL,  3, 11, 2);
        addElement(VWALL, 4, 11);

        // hall 3
        addWalls(HWALL,  4, 12, 2);
        addWalls(VWALL,  3, 13, 4);

        // start room
        addElement(VWALL, 7, 13);
        addElement(VWALL, 8, 13);
        addElement(HWALL, 7, 14);

    }
}
