package com.neodem.graphics.dd.engine.core;


import java.awt.Graphics;

/**
 * basic sprite class
 *
 * @author Vince
 */
public abstract class Sprite implements GraphicalObject {

    protected boolean visible;

    protected boolean active;

    public Sprite() {
        this(false, false);
    }

    public Sprite(boolean visible, boolean active) {
        this.visible = visible;
        this.active = active;
    }

    public abstract void paint(Graphics g);

    public abstract void update();

    /**
     * @return Returns the active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active The active to set.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return Returns the visible.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible The visible to set.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void suspend() {
        setVisible(false);
        setActive(false);
    }

    public void restore() {
        setVisible(true);
        setActive(true);
    }
}
