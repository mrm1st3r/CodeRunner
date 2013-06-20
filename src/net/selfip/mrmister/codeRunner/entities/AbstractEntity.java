package net.selfip.mrmister.codeRunner.entities;

import java.awt.geom.Point2D;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;

/**
 * Base of all player- and NPC-classes.
 * @author mrm1st3r
 *
 */
public abstract class AbstractEntity implements Runnable {

	public static final int SIGHT_OFFSET = 5;
	
	private RunnerPanel env;
	
	private Point2D coord;
	
	/**
	 * 
	 * @return the current position
	 */
	public Point2D getPosition() {
		return coord;
	}
	
	/**
	 * a collision with another entity happened.
	 * @param e collided entity
	 */
	public abstract void colliseWith(AbstractEntity e);
	
	/**
	 * whether or not the entity is out of the currently displayed area.
	 * @return true if not visible
	 */
	public boolean outOfSight() {
		
		return coord.getX() < (env.getProgress() + SIGHT_OFFSET);
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
