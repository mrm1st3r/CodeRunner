package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.util.Images;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * a bed makes the player tired and maybe fall to sleep.
 *
 */
class Bed extends AbstractEntity {

	private static final int ANIMATION_STEPS = 1;

	Bed(Point2D pos, BufferedImage[] bedAnimation, Bounds gameBounds) {
		super(bedAnimation, pos, 0, gameBounds);
	}

	@Override
	public boolean collidedWith(Entity e) {
		if (this.intersects((AbstractEntity) e)) {
			if (e instanceof Player) {
				((Player) e).reduceEnergy();
			}
			return true;
		}
		return false;
	}

	static BufferedImage[] init() throws Exception {
		return Images.loadAnimation("bed.png", ANIMATION_STEPS);
	}
}
