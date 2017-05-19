package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.util.DisplayWriter;
import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.util.Time;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * base class for non player entities.
 */
public abstract class AbstractEntity extends Rectangle2D.Double {

	private static final int SIGHT_OFFSET = 5;

	int deltaX = 0;
	int deltaY = 0;
	int currPic = 0;

	private final Bounds gameBounds;
	private final BufferedImage[] pics;
	private final long delay;

	private long anim = 0;

	AbstractEntity(BufferedImage[] i, Point2D pos, long d, Bounds gameBounds) {

		pics = i;
		this.gameBounds = gameBounds;
		this.delay = d;
		if (i != null) {
			height = pics[0].getHeight();
			width = pics[0].getWidth();
		}
		x = pos.getX();
		setRelativeY((int) pos.getY());
	}

	/**
	 * check whether a collision with another entity happened
	 * and perform the relevant action.
	 * @param e the other entity
	 * @return whether the collision happened
	 */
	public abstract boolean collidedWith(AbstractEntity e);

	/**
	 * @return y-axis position relative to the ground
	 */
	int getRelativeY() {
		return (int) (gameBounds.getHeight() - y - height);
	}

	/**
	 * set a new y-axis position relative to the ground.
	 * @param pos new position
	 */
	void setRelativeY(int pos) {
		y = (gameBounds.getHeight() - pos - height);
	}

	/**
	 * @return x-axis position relative to the left display border
	 */
	int getRelativeX() {
		return (int) (x - gameBounds.getOffset());
	}

	/**
	 * set a new x-axis position relative to the left display border.
	 */
	void resetRelativeX() {
		x = gameBounds.getOffset();
	}

	/**
	 * @return whether the entity is touching the ground
	 */
	boolean onGround() {
		return getRelativeY() == 0;
	}

	public void draw(Graphics g, DisplayWriter d) {
		g.drawImage(pics[currPic], getRelativeX(), (int) y, null);
	}

	public void doLogic(long delta) {
		anim += delta / Time.NANOS_PER_MILLI;

		// show next animation image
		if (anim > delay) {
			anim = 0;
			currPic++;
			if (currPic >= pics.length) {
				currPic = 0;
			}
		}
	}

	public void move(long delta) {
		if (deltaX != 0) {
			x += deltaX * (1.0 * delta / Time.NANOS_PER_SEC);
		}

		if (deltaY != 0) {
			y += deltaY * (1.0 * delta / Time.NANOS_PER_SEC);
		}
	}

	/**
	 * whether or not the entity is out of the currently displayed area.
	 * @return true if not visible
	 */
	public boolean outOfSight() {
		return getRelativeX() + AbstractEntity.SIGHT_OFFSET + width < 0;
	}

}
