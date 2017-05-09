package net.selfip.mrmister.codeRunner.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;
import net.selfip.mrmister.codeRunner.util.DisplayWriter;

/**
 * a bed makes the player tired and maybe fall to sleep.
 * @author mrm1st3r
 *
 */
public class Bed extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private static final int HEIGHT = 25;
	private static final int WIDTH = 60;
	private static final int LEFT_POST_HEIGHT = 18;
	private static final int RIGHT_POST_HEIGHT = 25;
	private static final int POST_WIDTH = 2;
	private static final int FRAME_HEIGHT = 7;
	private static final int MATTRESS_HEIGHT = 2;

	private static final Color POST_COLOR = new Color(64, 64, 60);
	private static final Color FRAME_COLOR = new Color(132, 92, 48);

	/**
	 * @param pos spawn position
	 * @param p parent panel
	 */
	public Bed(Point2D pos, RunnerPanel p) {
		super(null, pos, 0, p);

		width = WIDTH;
		height = HEIGHT;
		setRelativeY(0);
	}

	@Override
	public void draw(Graphics g, DisplayWriter d) {
		// draw posts
		g.setColor(POST_COLOR);
		g.fillRect(getRelativeX(), (int) y + (HEIGHT - LEFT_POST_HEIGHT),
				POST_WIDTH, LEFT_POST_HEIGHT);
		g.fillRect(getRelativeX() + WIDTH - POST_WIDTH, (int) y,
				POST_WIDTH, RIGHT_POST_HEIGHT);

		// draw frame
		g.setColor(FRAME_COLOR);
		g.fillRect(getRelativeX() + POST_WIDTH,
				(int) y + (HEIGHT - 2 * FRAME_HEIGHT),
				WIDTH - 2 * POST_WIDTH, FRAME_HEIGHT);

		// draw mattress
		g.setColor(Color.WHITE);
		g.fillRect(getRelativeX() + POST_WIDTH,
				(int) y + HEIGHT - 2 * FRAME_HEIGHT - MATTRESS_HEIGHT,
				WIDTH - 2 * POST_WIDTH, MATTRESS_HEIGHT);
	}

	@Override
	public void doLogic(long delta) {

	}

	@Override
	public boolean collidedWith(AbstractEntity e) {
		if (this.intersects(e)) {
			if (e instanceof Player) {
				((Player) e).reduceEnergy();
			}
			return true;
		}
		return false;
	}
}
