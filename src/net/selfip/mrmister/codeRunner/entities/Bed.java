package net.selfip.mrmister.codeRunner.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;
import net.selfip.mrmister.codeRunner.util.DisplayWriter;

/**
 * 
 * @author mrm1st3r
 *
 */
public class Bed extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private static final int HEIGHT = 30;
	private static final int WIDTH = 50;
	
	private static final Color BROWN = new Color(111, 74, 50);
	
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
		g.setColor(Color.BLACK);
		g.drawRect(getRelativeX(), (int) y, (int) width, (int) height);
		g.setColor(BROWN);
		g.fillRect(getRelativeX() + 1, (int) y + 1,
				(int) width - 1, (int) height / 2);
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
