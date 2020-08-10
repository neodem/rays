package com.neodem.graphics.rays;

import static com.neodem.graphics.rays.WorldMap.ElementType.HWALL;
import static com.neodem.graphics.rays.WorldMap.ElementType.VWALL;
import static com.neodem.graphics.rays.WorldMapHelpers.addWalls;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/10/20
 */
public class TestWorldMap extends WorldMap {
    public TestWorldMap() {
        super(16, 16);

        // room 1
        addWalls(HWALL, this, 7, 7, 3);
        addWalls(HWALL, this, 7, 9, 3);
        addWalls(VWALL, this, 7, 7, 2);
        addWalls(VWALL, this, 10, 7, 2);
        this.removeElement(HWALL, 9, 8);

        // room 2
        addWalls(HWALL, this, 2, 9, 3);
        addWalls(HWALL, this, 2, 11, 3);
        addWalls(VWALL, this, 2, 9, 2);
        addWalls(VWALL, this, 5, 9, 2);
        this.removeElement(HWALL, 3, 11);

        // room 3
        addWalls(HWALL, this, 10, 10, 3);
        addWalls(HWALL, this, 10, 12, 3);
        addWalls(VWALL, this, 10, 10, 2);
        addWalls(VWALL, this, 12, 10, 2);
        this.removeElement(VWALL, 10, 10);

        // room 4
        addWalls(HWALL, this, 6, 10, 4);
        addWalls(HWALL, this, 6, 13, 4);
        addWalls(VWALL, this, 6, 10, 3);
        addWalls(VWALL, this, 10, 10, 3);
        this.removeElement(VWALL, 10, 10);
        this.removeElement(VWALL, 6, 12);
        this.removeElement(HWALL, 7, 13);

        // hall 1
        this.addElement(VWALL, 8, 9);
        this.addElement(VWALL, 9, 9);

        // hall 2
        addWalls(VWALL, this, 3, 11, 2);
        this.addElement(VWALL, 4, 11);

        // hall 3
        addWalls(HWALL, this, 4, 12, 2);
        addWalls(VWALL, this, 3, 13, 4);

        // start room
        this.addElement(VWALL, 7, 13);
        this.addElement(VWALL, 8, 13);
        this.addElement(HWALL, 7, 14);

    }
}
