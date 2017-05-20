package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.util.Images;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * a friendly entity, which gives +1 energy.
 *
 */
class Coffee extends AbstractEntity {

	private static BufferedImage[] pics = null;

	private static final int ANIMATION_STEPS = 1;
	private static final int ANIMATION_TIMEOUT = 0;

	Coffee(Point2D pos, Bounds gameBounds) {
		super(Coffee.pics, pos, ANIMATION_TIMEOUT, gameBounds);
	}

	@Override
	public boolean collidedWith(Entity e) {
		if (this.intersects((AbstractEntity) e)) {
			if (e instanceof Player) {
				((Player) e).addEnergy();
			}
			return true;
		}

		return false;
	}

	static void init() {
		try {
			pics = Images.loadAnimation("coffee.png", ANIMATION_STEPS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
