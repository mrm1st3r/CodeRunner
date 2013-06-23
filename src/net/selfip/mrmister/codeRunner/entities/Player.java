package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.frame.MainFrame;
import net.selfip.mrmister.codeRunner.frame.RunnerPanel;
import net.selfip.mrmister.codeRunner.util.DisplayWriter;
import net.selfip.mrmister.codeRunner.util.Time;

/**
 * the player object.
 * @author mrm1st3r
 *
 */
public class Player extends AbstractEntity {

	public static final int KEY_LEFT = KeyEvent.VK_LEFT;
	public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	public static final int KEY_JUMP = KeyEvent.VK_SPACE;

	public static final int MOVE_SPEED = 180;
	public static final int MIN_SIGHT = 250;
	public static final int COFFEE_SHOCK = 10;

	private static final int JUMP_TIME = 500;
	private static final int JUMP_SPEED = 200;

	private static final long serialVersionUID = 1L;
	private static final String PLAYER_SPRITE = "player.png";
	private static final int ANIMATION_TIMEOUT = 300;
	private static final int ENERGY_POS_X = 720;

	private long jump = 0;
	private int energy = 0;

	/**
	 * create a new player.
	 * @param p containing panel
	 */
	public Player(RunnerPanel p) {
		super(CodeRunner.loadImages(PLAYER_SPRITE, 2),
				new Point2D.Double(0, 0),
				ANIMATION_TIMEOUT, p);

		x = (p.getWidth() - pics[0].getWidth()) / 2;
	}

	@Override
	public void doLogic(long delta) {
		if (deltaX != 0) {
			super.doLogic(delta);
		} else {
			currPic = 0;
		}
	}

	/**
	 * add one energy (e.g. after drinking a coffee).
	 */
	public void addEnergy() {
		energy++;
		
		if (energy >= COFFEE_SHOCK) {
			getEnv().stop("You died of a Coffeine shock! Score: " + (int) x);
		}
	}

	/**
	 * reduce energy (e.g. after laying into a bed). 
	 */
	public void reduceEnergy() {
		energy--;
		
		if (energy < 0) {
			getEnv().stop("You fell asleep! Score: " + (int) x);
		}
	}

	/**
	 * depress the player.
	 */
	public void depress() {
		getEnv().stop("You got a depression and can't continue! Score: "
	+ (int) x);
	}

	/**
	 * @return the current amount of energy
	 */
	public int getEnergy() {
		return energy;
	}

	@Override
	public void move(long delta) {
		calculateJump();

		super.move(delta);

		// Level weiterscrollen
		if (getRelativeX() >= (MainFrame.WIDTH - MIN_SIGHT)) {
			getEnv().progress(getRelativeX() - (MainFrame.WIDTH - MIN_SIGHT));
		}

		if (getRelativeX() < 0) {
			setRelativeX(0);
		}
		if (getRelativeY() < 0) {
			setRelativeY(0);
		}
	}
	
	private void calculateJump() {
		if (jump != 0) {
			AbstractEntity.log.info("jumping!");
			int calcX = (int) (System.nanoTime() - jump) / Time.NANOS_PER_MILLI;
			if (calcX >= JUMP_TIME) {
				AbstractEntity.log.info("jumping time is over!" + calcX);
				endJump();
			}

			if (deltaY == JUMP_SPEED && getRelativeY() <= 0) {
				deltaY = 0;
				jump = 0;
				setRelativeY(0);
			}
		}
	}

	@Override
	public boolean collidedWith(AbstractEntity e) {
		return false;
	}

	private void startJump() {
		if (jump == 0 && onGround()) {
			AbstractEntity.log.info("jumping");
			jump = System.nanoTime();
			deltaY = -1 * JUMP_SPEED;
		}
	}

	private void endJump() {
		deltaY = JUMP_SPEED;
	}

	@Override
	public void draw(Graphics g, DisplayWriter d) {
		d.println("pos: " + (int) x + " / " + (int) getRelativeY());
		d.println("speed: " + deltaX + " / " + deltaY);

		d.printToPos("energy: " + energy, ENERGY_POS_X,
				DisplayWriter.FIRST_LINE);

		super.draw(g, d);
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
				//AbstractEntity.log.info("moving left");
				deltaX = -1 * MOVE_SPEED;
				break;
			case KEY_RIGHT:
				//AbstractEntity.log.info("moving right");
				deltaX = MOVE_SPEED;
				break;
			case KEY_JUMP:
				startJump();
				break;
			case RunnerPanel.KEY_PAUSE:
				getEnv().pause();
				break;
			default:
				AbstractEntity.log.info("Key event! (" + (int) key + ")");
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
