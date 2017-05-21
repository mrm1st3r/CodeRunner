package net.selfip.mrmister.coderunner.game;

import net.selfip.mrmister.coderunner.entities.Entity;

/**
 * The games current viewport bounds.
 */
public class Bounds {

    private final int width;
    private final int height;
    private int offset;

    public Bounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    void addToOffset(int addition) {
        if (addition <= 0) {
            return;
        }
        offset += addition;
    }

    public int getOffset() {
        return offset;
    }

    void reset() {
        offset = 0;
    }

    int rightHorizon() {
        return offset + width;
    }

    int leftHorizon() {
        return offset;
    }

    boolean hasPassed(Entity entity) {
        return entity.getXPosition() < leftHorizon();
    }
}
