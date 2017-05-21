package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.util.DisplayWriter;

import java.awt.*;

/**
 * Base functionality for all entities.
 */
public interface Entity {

    int getXPosition();

    /**
     * check whether a collision with another entity happened
     * and perform the relevant action.
     * @param e the other entity
     * @return whether the collision happened
     */
    boolean collidedWith(Entity e);

    void draw(Graphics g, DisplayWriter d);

    void doLogic(long delta);

    void move(long delta);

    /**
     * whether or not the entity is out of the currently displayed area.
     * @return true if not visible
     */
    boolean outOfSight();
}
