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
	private static BufferedImage[] pics;

	Bed(Point2D pos, Bounds gameBounds) {
		super(Bed.pics, pos, 0, gameBounds);
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

	static void init() {
		try {
			pics = Images.loadAnimation("bed.png", ANIMATION_STEPS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
