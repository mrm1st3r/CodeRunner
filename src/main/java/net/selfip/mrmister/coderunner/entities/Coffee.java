package net.selfip.mrmister.coderunner.entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import net.selfip.mrmister.coderunner.game.GameLoop;
import net.selfip.mrmister.coderunner.util.Images;
import net.selfip.mrmister.coderunner.frame.RunnerPanel;

/**
 * a friendly entity, which gives +1 energy.
 *
 */
public class Coffee extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private static BufferedImage[] pics = null;
	
	private static final int ANIMATION_STEPS = 1;
	private static final int ANIMATION_TIMEOUT = 0;

	/**
	 * @param pos where the coffee should spawn
	 * @param game
	 * @param p belonging panel
	 */
	public Coffee(Point2D pos, GameLoop game, RunnerPanel p) {
		super(Coffee.pics, pos, ANIMATION_TIMEOUT, game, p);
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
