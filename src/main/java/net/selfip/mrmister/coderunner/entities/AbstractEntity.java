package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.util.Time;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * base class for non player entities.
 */
abstract class AbstractEntity extends Rectangle2D.Double implements Entity {

	int deltaX = 0;
	int deltaY = 0;
	int currentImage = 0;

	private final BufferedImage[] images;
	private final long delay;

	private long animationDuration = 0;

	AbstractEntity(BufferedImage[] i, Point2D pos, long d) {
		images = i;
		this.delay = d;
		height = images[0].getHeight();
		width = images[0].getWidth();
		x = pos.getX();
		y = pos.getY();
	}

	@Override
	public int getXPosition() {
		return (int) x;
	}

    @Override
    public void setXPosition(int x) {
        this.x = x;
    }

    @Override
	public void doLogic(long delta) {
		animationDuration += delta / Time.NANOS_PER_MILLI;
		if (animationDuration > delay) {
			animationDuration = 0;
			nextAnimationImage();
		}
	}

	private void nextAnimationImage() {
		if (++currentImage >= images.length) {
            currentImage = 0;
        }
	}

	@Override
	public void move(long delta) {
		double deltaFactor = ((double) delta) / Time.NANOS_PER_SEC;
		x += deltaX * deltaFactor;
		y += deltaY * deltaFactor;
	}

	@Override
	public BufferedImage currentImage() {
		return images[currentImage];
	}

	@Override
	public int getYPosition() {
		return (int) y;
	}

	@Override
	public int height() {
		return (int) height;
	}

	@Override
	public int width() {
		return (int) width;
	}
}
