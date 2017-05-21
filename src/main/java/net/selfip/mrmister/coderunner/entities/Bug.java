package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.util.Images;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * a software-bug, which depresses the player.
 */
class Bug extends AbstractEntity {

	private static final int ANIMATION_TIMEOUT = 200;
	private static final int ANIMATION_STEPS = 4;

	private static final int JUMP_HEIGHT = 120;
	private static final int JUMP_SPEED = 100;

	Bug(Point2D pos, BufferedImage[] bugAnimation) {
		super(bugAnimation, pos, ANIMATION_TIMEOUT);
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
		if ((int) y >= JUMP_HEIGHT) {
			deltaY = -JUMP_SPEED;
		}
		if ((int) y <= 0) {
			deltaY = JUMP_SPEED;
		}
		super.move(delta);
	}

	static BufferedImage[] init() throws Exception {
		return Images.loadAnimation("bug.png", ANIMATION_STEPS);
	}
}
