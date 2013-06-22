package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;

/**
 * Base of all player- and NPC-classes.
 * @author mrm1st3r
 *
 */
public abstract class AbstractEntity extends Rectangle2D.Double {

	public static final int SIGHT_OFFSET = 5;

	static final long serialVersionUID = 0x1;
	
	protected int deltaX = 0;
	protected int deltaY = 0;

	private RunnerPanel env;
	
	/**
	 * draw the entity to the screen.
	 * @param g graphics to draw with
	 */
	public abstract void draw(Graphics g);
	
	/**
	 * logical calculations.
	 */
	public abstract void doLogic();
	
	/**
	 * move the entity on screen.
	 */
	public void move() {
		if (deltaX != 0) {
			x += deltaX * (env.getDelta()/1e9);
		}

		if (deltaY != 0) {
			y += deltaY * (env.getDelta()/1e9);
		}
	}

	/**
	 * whether or not the entity is out of the currently displayed area.
	 * @return true if not visible
	 */
	public boolean outOfSight() {
		
		return x < (env.getProgress() + SIGHT_OFFSET);
	}
	
	/**
	 * get the environment panel.
	 * @return current environment
	 */
	protected RunnerPanel getEnv() {
		return env;
	}
	
	/**
	 * set a new environment panel.
	 * @param rp new panel
	 */
	protected void setEnv(RunnerPanel rp) {
		env = rp;
	}

}
