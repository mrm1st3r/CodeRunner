package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.frame.RunnerPanel;
import net.selfip.mrmister.codeRunner.util.Time;

/**
 * the player object.
 * @author mrm1st3r
 *
 */
public class Player extends Sprite {

	public static final int KEY_LEFT = KeyEvent.VK_LEFT;
	public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	public static final int KEY_JUMP = KeyEvent.VK_SPACE;

	public static final int MOVE_SPEED = 100;
	private static final int JUMP_TIME = 700;
	
	private static final int JUMP_SPEED = 100;

	private static final long serialVersionUID = 1L;
	private static final String PLAYER_SPRITE = "player.png";

	private long jump = 0;

	/**
	 * create a new player.
	 * @param p containing panel
	 */
	public Player(RunnerPanel p) {
		super(CodeRunner.loadImages(PLAYER_SPRITE, 1),
				new Point2D.Double(0, 0),
				0, p);

		x = (p.getWidth() - pics[0].getWidth()) / 2;
		y = p.getHeight() - pics[0].getHeight();
	}

	@Override
	public void doLogic() {
		
	}

	@Override
	public void move() {
		if (jump != 0) {
			log.info("jumping!");
			int calcX = (int) (System.nanoTime() - jump) / Time.NANOS_PER_MILLI;
			if (calcX >= JUMP_TIME) {
				log.info("jumping time is over!" + calcX);
				endJump();			
			}
			
			if (deltaY == JUMP_SPEED && getRelHeight() <= 0) {
				deltaY = 0;
				jump = 0;
				setRelHeight(0);
			}
		}

		super.move();
	}

	private void startJump() {
		if (jump == 0 && onGround()) {
			log.info("jumping");
			jump = System.nanoTime();
			deltaY = -1 * JUMP_SPEED;
		}
	}

	private void endJump() {
		deltaY = JUMP_SPEED;
	}

	@Override
	public void draw(Graphics g) {
		g.drawString("pos: " + x + " / " + getRelHeight(), 10, 35);
		g.drawString("speed: " + deltaX + " / " + deltaY, 10, 50);
		
		super.draw(g);
	}

	/**
	 * register the player input.
	 * @param p related frame
	 */
	public void registerKeyHandler(JFrame p) {
		p.addKeyListener(new Keyboard());
	}

	class Keyboard extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			char key = Character.toLowerCase((char) e.getKeyCode());

			if (getEnv().isPaused()) {
				if (key == RunnerPanel.KEY_PAUSE) {
					getEnv().resume();
				}

				return;
			}

			switch (key) {
			case KEY_LEFT:
				log.info("moving left");
				deltaX = -1 * MOVE_SPEED;
				break;
			case KEY_RIGHT:
				log.info("moving right");
				deltaX = MOVE_SPEED;
				break;
			case KEY_JUMP:
				startJump();
				break;
			case RunnerPanel.KEY_PAUSE:
				getEnv().pause();
				break;
			default:
				log.info("Key event! (" + (int) key + ")");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			char key = Character.toLowerCase((char) e.getKeyCode());
			
			switch (key) {
			case KEY_LEFT:
				deltaX = 0;
				break;
			case KEY_RIGHT:
				deltaX = 0;
				break;
			case KEY_JUMP:
				endJump();
				break;
			default:
			}
		}
	}
}
