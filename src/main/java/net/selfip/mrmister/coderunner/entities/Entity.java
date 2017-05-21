package net.selfip.mrmister.coderunner.entities;

import java.awt.image.BufferedImage;

/**
 * Base functionality for all entities.
 */
public interface Entity {

    int getXPosition();

    void setXPosition(int x);

    int getYPosition();

    /**
     * check whether a collision with another entity happened
     * and perform the relevant action.
     * @param e the other entity
     * @return whether the collision happened
     */
    boolean collidedWith(Entity e);

    BufferedImage currentImage();

    void doLogic(long delta);

    void move(long delta);

    int height();

    int width();
}
