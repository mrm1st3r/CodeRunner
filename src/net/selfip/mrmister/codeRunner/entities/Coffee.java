package net.selfip.mrmister.codeRunner.entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.frame.RunnerPanel;

/**
 * a friendly entity, which gives +1 energy.
 * @author mrm1st3r
 *
 */
public class Coffee extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private static BufferedImage[] pics = null;
	
	private static final int ANIMATION_STEPS = 1;
	private static final int ANIMATION_TIMEOUT = 0;

	/**
	 * @param p belonging panel
	 * @param pos where the coffee should spawn
	 */
	public Coffee(Point2D pos, RunnerPanel p) {
		super(Coffee.pics, pos, ANIMATION_TIMEOUT, p);
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
		pics = CodeRunner.loadImages("coffee.png", ANIMATION_STEPS);
	}
}
