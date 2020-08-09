package com.neodem.graphics.rays;

import com.neodem.graphics.dd.engine.common.Color;
import com.neodem.graphics.dd.engine.core.ActiveCanvas;
import com.neodem.graphics.dd.engine.sprite.sprites.BouncingRectange;
import com.neodem.graphics.dd.engine.sprite.sprites.DancingRectangleSprite;

import java.awt.*;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/9/20
 */
public class RaysCanvas extends ActiveCanvas {


    private final int screenW;
    private final int screenH;
    private final Color bgColor;
    protected DancingRectangleSprite sprite;
    protected BouncingRectange b;

    public RaysCanvas(int width, int height) {
        super(new Dimension(width, height));
        this.screenW = width;
        this.screenH = height;
        this.bgColor = Color.lightGray;
    }

    @Override
    public void init() {
        sprite = new DancingRectangleSprite(20, 30, 50, 50, com.neodem.graphics.dd.engine.common.Color.red);
        sprite.setFill(true);
        b = new BouncingRectange(100, 200, 100, 100, Color.blue, 400, 200);
        b.setFill(true);
        b.setVelocity(3, 4);
    }

    @Override
    public void update(long elapsedTime) {
        sprite.update();
        b.update();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(bgColor.getAWTColor());
        g.fillRect(0, 0, screenW, screenH);
        sprite.paint(g);
        b.paint(g);
    }


}
