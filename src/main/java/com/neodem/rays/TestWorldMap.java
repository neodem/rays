package com.neodem.rays;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/10/20
 */
public class TestWorldMap extends WorldMap {
    public TestWorldMap() {
        super(16, 16);

        // room 1
        addWalls(ElementType.HWALL, 7, 7, 3);
        addWalls(ElementType.HWALL, 7, 9, 3);
        addWalls(ElementType.VWALL, 7, 7, 2);
        addWalls(ElementType.VWALL, 10, 7, 2);
        removeElement(ElementType.HWALL, 8, 9);

        // room 2
        addWalls(ElementType.HWALL,  2, 9, 3);
        addWalls(ElementType.HWALL,  2, 11, 3);
        addWalls(ElementType.VWALL,  2, 9, 2);
        addWalls(ElementType.VWALL,  5, 9, 2);
        removeElement(ElementType.HWALL, 3, 11);

        // room 3
        addWalls(ElementType.HWALL,  10, 10, 3);
        addWalls(ElementType.HWALL,  10, 12, 3);
        addWalls(ElementType.VWALL,  10, 10, 2);
        addWalls(ElementType.VWALL,  13, 10, 2);
        removeElement(ElementType.VWALL, 10, 10);

        // room 4
        addWalls(ElementType.HWALL,  6, 10, 4);
        addWalls(ElementType.HWALL,  6, 13, 4);
        addWalls(ElementType.VWALL,  6, 10, 3);
        addWalls(ElementType.VWALL,  10, 10, 3);
        removeElement(ElementType.VWALL, 10, 10);
        removeElement(ElementType.VWALL, 6, 12);
        removeElement(ElementType.HWALL, 7, 13);
        removeElement(ElementType.HWALL, 8, 10);

        // start room
        addElement(ElementType.VWALL, 7, 13);
        addElement(ElementType.VWALL, 8, 13);
        addElement(ElementType.HWALL, 7, 14);

        // hall 1
        addElement(ElementType.VWALL, 8, 9);
        addElement(ElementType.VWALL, 9, 9);

        // hall 2
        addWalls(ElementType.VWALL,  3, 11, 2);
        addElement(ElementType.VWALL, 4, 11);

        // hall 3
        addWalls(ElementType.HWALL,  4, 12, 2);
        addWalls(ElementType.HWALL,  3, 13, 4);
    }
}
