package net.selfip.mrmister.codeRunner.entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;

/**
 * a friendly entity, which gives +1 energy.
 * @author mrm1st3r
 *
 */
public class Coffee extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private static BufferedImage[] pics = null;

	/**
	 * @param p belonging panel
	 * @param pos where the coffee should spawn
	 */
	public Coffee(Point2D pos, RunnerPanel p) {
		super(Coffee.pics, pos, 0, p);
	}
	
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
	 * @param p animation
	 */
	public static void setPics(BufferedImage[] p) {
		pics = p;
	}
}
