package net.selfip.mrmister.coderunner.entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.util.Images;

/**
 * a software-bug, which depresses the player.
 */
public class Bug extends AbstractEntity {

	private static BufferedImage[] pics;
	private static final int ANIMATION_TIMEOUT = 200;
	private static final int ANIMATION_STEPS = 4;

	private static final int JUMP_HEIGHT = 120;
	private static final int JUMP_SPEED = 100;

	/**
	 * @param pos spawn position
	 */
	public Bug(Point2D pos, Bounds gameBounds) {
		super(pics, pos, ANIMATION_TIMEOUT, gameBounds);
	}

	@Override
	public boolean collidedWith(AbstractEntity e) {
		if (this.intersects(e)) {
			if (e instanceof Player) {
				((Player) e).depress();
			}

			return true;
		}

		return false;
	}

	@Override
	public void move(long delta) {
		if (getRelativeY() >= JUMP_HEIGHT) {
			deltaY = JUMP_SPEED;
		}
		if (getRelativeY() <= 0) {
			deltaY = -JUMP_SPEED;
		}

		super.move(delta);
	}

	/**
	 * load animation for Bug entities.
	 */
	public static void init() {
		try {
			pics = Images.loadAnimation("bug.png", ANIMATION_STEPS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
