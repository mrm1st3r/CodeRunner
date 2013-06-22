package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.frame.RunnerPanel;

/**
 * the player object.
 * @author mrm1st3r
 *
 */
public class Player extends Sprite {

	public static final int KEY_LEFT = KeyEvent.VK_LEFT;
	public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	public static final int KEY_JUMP = KeyEvent.VK_SPACE;
	
	private static final long serialVersionUID = 1L;
	private static final String PLAYER_SPRITE = "player.png";
	
	/**
	 * create a new player.
	 * @param p containing panel
	 */
	public Player(RunnerPanel p) {
		super(CodeRunner.loadImages(PLAYER_SPRITE, 1),
				new Point2D.Double(RunnerPanel.HEIGHT, RunnerPanel.WIDTH / 2),
				0, p);
				
		p.addKeyListener(new Keyboard());
	}
	

	
	@Override
	public void doLogic() {
		
	}

	public void jump() {
		// TODO Auto-generated method stub
		
	}

	class Keyboard extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			char key = Character.toLowerCase(e.getKeyChar());
			
			if (getEnv().isPaused()) {
				if (key == RunnerPanel.KEY_PAUSE) {
					getEnv().resume();
				}

				return;
			}

			switch (key) {
			case KEY_LEFT:
				deltaX = -1;
				break;
			case KEY_RIGHT:
				deltaX = 1;
				break;
			case KEY_JUMP:
				//jump();
				deltaY = -1;
			case RunnerPanel.KEY_PAUSE:
				getEnv().pause();
			default:
			}
		}
	}
}
