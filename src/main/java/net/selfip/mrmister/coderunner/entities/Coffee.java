package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.util.Images;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * a friendly entity, which gives +1 energy.
 *
 */
public class Coffee extends AbstractEntity {

	private static BufferedImage[] pics = null;

	private static final int ANIMATION_STEPS = 1;
	private static final int ANIMATION_TIMEOUT = 0;

	public Coffee(Point2D pos, Bounds gameBounds) {
		super(Coffee.pics, pos, ANIMATION_TIMEOUT, gameBounds);
	}

	/**
	 * check for collisions. If collides with the player, add one energy.
	 * @param e entity to check collision with.
	 * @return return if a collision is detected
	 */
	@Override
	public boolean collidedWith(AbstractEntity e) {
		if (this.intersects(e)) {
			if (e instanceof Player) {
				((Player) e).addEnergy();
			}
			return true;
		}

		return false;
	}

	/**
	 * register animation for Coffee entities.
	 */
	public static void init() {
		try {
			pics = Images.loadAnimation("coffee.png", ANIMATION_STEPS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
