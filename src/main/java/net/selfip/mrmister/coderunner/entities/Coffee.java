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

	private static final int ANIMATION_STEPS = 1;
	private static final int ANIMATION_TIMEOUT = 0;

	Coffee(Point2D pos, BufferedImage[] coffeeAnimation, Bounds gameBounds) {
		super(coffeeAnimation, pos, ANIMATION_TIMEOUT, gameBounds);
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

	static BufferedImage[] init() throws Exception {
		return Images.loadAnimation("coffee.png", ANIMATION_STEPS);
	}
}
