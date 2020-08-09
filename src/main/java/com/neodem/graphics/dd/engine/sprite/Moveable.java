package com.neodem.graphics.dd.engine.sprite;


public interface Moveable {
    void setPosition(int x, int y);

    void setVelocity(int x, int y);

    void updatePosition();
}
