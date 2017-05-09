package net.selfip.mrmister.codeRunner.entities;

/**
 * Specify an entity which acts FPS independent.
 * @author mrm1st3r
 *
 */
public interface Movable {

	/**
	 * move object.
	 * @param delta last cycle time
	 */
	void move(long delta);
	
	/**
	 * do logical operations.
	 * @param delta  last cycle time
	 */
	void doLogic(long delta);
}