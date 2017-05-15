package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.CodeRunner;
import net.selfip.mrmister.coderunner.util.Images;
import net.selfip.mrmister.coderunner.event.KeyConfig;
import net.selfip.mrmister.coderunner.frame.RunnerPanel;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.DisplayWriter;
import net.selfip.mrmister.coderunner.util.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * the player object.
 *
 */
public class Player extends AbstractEntity {

	public static final int MOVE_SPEED = 180;
	public static final int MIN_SIGHT = 250;
	public static final int COFFEE_SHOCK = 10;

	private static final int JUMP_TIME = 500;
	private static final int JUMP_SPEED = 200;

	private static final long serialVersionUID = 1L;
	private static final String PLAYER_SPRITE = "player.png";
	private static final int ANIMATION_TIMEOUT = 300;
	private static final int ANIMATION_STEPS = 2;
	private static final int START_POS = 100;
	private final I18n i18n;

	private long jump = 0;
	private int energy = 0;

	/**
	 * create a new player.
	 * @param p containing panel
	 * @param i18n
	 */
	public Player(RunnerPanel p, I18n i18n) throws IOException {
		super(Images.loadAnimation(PLAYER_SPRITE, ANIMATION_STEPS),
				new Point2D.Double(START_POS, 0),
				ANIMATION_TIMEOUT, p);
		this.i18n = i18n;
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
			getEnv().stop(i18n.t("coffee_shock"));
		}
	}

	/**
	 * reduce energy (e.g. after laying into a bed). 
	 */
	public void reduceEnergy() {
		energy--;

		if (energy < 0) {
			getEnv().stop(i18n.t("sleeping"));
		}
	}

	/**
	 * depress the player.
	 */
	public void depress() {
		getEnv().stop(i18n.t("depression"));
	}

	@Override
	public void move(long delta) {
		calculateJump();

		super.move(delta);

		// Level weiterscrollen
		if (getRelativeX() >= (CodeRunner.WIDTH - MIN_SIGHT)) {
			getEnv().progress(getRelativeX() - (CodeRunner.WIDTH - MIN_SIGHT));
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
			int calcX = (int) (System.nanoTime() - jump) / Time.NANOS_PER_MILLI;
			if (calcX >= JUMP_TIME) {
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
			jump = System.nanoTime();
			deltaY = -1 * JUMP_SPEED;
		}
	}

	private void endJump() {
		deltaY = JUMP_SPEED;
	}

	@Override
	public void draw(Graphics g, DisplayWriter d) {
		if (CodeRunner.devMode()) {
			d.println("pos: " + (int) x + " / " + getRelativeY());
			d.println("speed: " + deltaX + " / " + deltaY);
		}
		d.printlnRight(i18n.t("energy") + ": " + energy);

		super.draw(g, d);
	}

	/**
	 * register the player input.
	 * @param p related frame
	 * @param c key-configuration to use
	 */
	public void registerKeyHandler(JFrame p, KeyConfig c) {
		KeyListener[] l = p.getKeyListeners();

		for (KeyListener k : l) {
			if (k instanceof Keyboard) {
				p.removeKeyListener(k);
			}
		}

		p.addKeyListener(new Keyboard(c));
	}

	class Keyboard extends KeyAdapter {

		private KeyConfig conf;

		public Keyboard(KeyConfig c) {
			conf = c;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = Character.toLowerCase((char) e.getKeyCode());

			if (getEnv().isPaused()) {
				if (key == conf.get("pause")) {
					getEnv().resume();
				}

				return;
			}

			if (key == conf.get("move_left")) {
				//AbstractEntity.log.info("moving left");
				deltaX = -1 * MOVE_SPEED;
			} else if (key == conf.get("move_right")) {
				//AbstractEntity.log.info("moving right");
				deltaX = MOVE_SPEED;
			} else if (key == conf.get("jump")) {
				startJump();
			} else if (key == conf.get("pause")) {
				getEnv().pause();
			} else if (key == CodeRunner.KEY_TOGGLE_DEV) {
				CodeRunner.toggleDevMode();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = Character.toLowerCase((char) e.getKeyCode());

			if (key == conf.get("move_left")) {
				//AbstractEntity.log.info("moving left");
				deltaX = 0;
			} else if (key == conf.get("move_right")) {
				//AbstractEntity.log.info("moving right");
				deltaX = 0;
			} else if (key == conf.get("jump")) {
				endJump();
			}
		}
	}

}
