package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;
import net.selfip.mrmister.codeRunner.util.Time;

/**
 * Base of all player- and NPC-classes.
 * @author mrm1st3r
 *
 */
public abstract class AbstractEntity extends Rectangle2D.Double {

	public static final int SIGHT_OFFSET = 5;
	
	static Logger log = Logger.getLogger("AbstractEntity");
	static final long serialVersionUID = 1L;

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

	public abstract int getRelHeight();
	
	public abstract void setRelHeight(int pos);
	
	/**
	 * move the entity on screen.
	 */
	public void move() {
		if (deltaX != 0) {
			x += deltaX * (1.0 * env.getDelta() / Time.NANOS_PER_SEC);
		}

		if (deltaY != 0) {
			y += deltaY * (1.0 * env.getDelta() / Time.NANOS_PER_SEC);
			if (getRelHeight() < 0) {
				setRelHeight(0);
			}
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
