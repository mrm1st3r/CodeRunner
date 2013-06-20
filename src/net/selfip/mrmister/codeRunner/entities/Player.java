package net.selfip.mrmister.codeRunner.entities;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;

/**
 * the player object.
 * @author mrm1st3r
 *
 */
public class Player extends AbstractEntity {

	/**
	 * create a new player.
	 * @param environment containing panel
	 */
	public Player(RunnerPanel environment) {
		setEnv(environment);
	}
	
	@Override
	public void run() {
		
	}
	
	/**
	 * a collision happened.
	 * @param e the collided entity
	 */
	public void colliseWith(AbstractEntity e) {
		
	}

}
