package net.selfip.mrmister.coderunner.game;

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
        offset += addition;
    }

    public int getOffset() {
        return offset;
    }
}
