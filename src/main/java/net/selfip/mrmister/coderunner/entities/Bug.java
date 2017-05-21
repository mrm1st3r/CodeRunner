package net.selfip.mrmister.coderunner.entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.util.Images;

/**
 * a software-bug, which depresses the player.
 */
class Bug extends AbstractEntity {

	private static final int ANIMATION_TIMEOUT = 200;
	private static final int ANIMATION_STEPS = 4;

	private static final int JUMP_HEIGHT = 120;
	private static final int JUMP_SPEED = 100;

	Bug(Point2D pos, BufferedImage[] bugAnimation, Bounds gameBounds) {
		super(bugAnimation, pos, ANIMATION_TIMEOUT, gameBounds);
	}

	@Override
	public boolean collidedWith(Entity e) {
		if (this.intersects((AbstractEntity) e)) {
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

	static BufferedImage[] init() throws Exception {
		return Images.loadAnimation("bug.png", ANIMATION_STEPS);
	}
}
