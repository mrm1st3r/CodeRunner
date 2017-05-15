package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.CodeRunner;
import net.selfip.mrmister.coderunner.game.GameLoop;
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
 */
public class Player extends AbstractEntity {

	private static final int MOVE_SPEED = 180;
	private static final int MIN_SIGHT = 250;
	private static final int COFFEE_SHOCK = 10;

	private static final int JUMP_TIME = 500;
	private static final int JUMP_SPEED = 200;

	private static final long serialVersionUID = 1L;
	private static final String PLAYER_SPRITE = "player.png";
	private static final int ANIMATION_TIMEOUT = 300;
	private static final int ANIMATION_STEPS = 2;
	private static final int START_POS = 100;
	private final I18n i18n;
	private final GameLoop game;

	private long jump = 0;
	private int energy = 0;

	public Player(RunnerPanel panel, I18n i18n, GameLoop game) throws IOException {
		super(
				Images.loadAnimation(PLAYER_SPRITE, ANIMATION_STEPS),
				new Point2D.Double(START_POS, 0),
				ANIMATION_TIMEOUT,
				game,
				panel
		);
		this.i18n = i18n;
		this.game = game;
	}

	@Override
	public void doLogic(long delta) {
		if (deltaX != 0) {
			super.doLogic(delta);
		} else {
			currPic = 0;
		}
	}

	void addEnergy() {
		energy++;

		if (energy >= COFFEE_SHOCK) {
			game.stop(i18n.t("coffee_shock"));
		}
	}

	void reduceEnergy() {
		energy--;

		if (energy < 0) {
			game.stop(i18n.t("sleeping"));
		}
	}

	void depress() {
		game.stop(i18n.t("depression"));
	}

	@Override
	public void move(long delta) {
		calculateJump();

		super.move(delta);

		if (getRelativeX() >= (CodeRunner.WIDTH - MIN_SIGHT)) {
			game.progress(getRelativeX() - (CodeRunner.WIDTH - MIN_SIGHT));
		}

		if (getRelativeX() < 0) {
			resetRelativeX();
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

		Keyboard(KeyConfig c) {
			conf = c;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = Character.toLowerCase((char) e.getKeyCode());

			if (game.isPaused()) {
				if (key == conf.get("pause")) {
					game.resume();
				}
				return;
			}

			if (key == conf.get("move_left")) {
				deltaX = -1 * MOVE_SPEED;
			} else if (key == conf.get("move_right")) {
				deltaX = MOVE_SPEED;
			} else if (key == conf.get("jump")) {
				startJump();
			} else if (key == conf.get("pause")) {
				game.pause();
			} else if (key == conf.get("dev_mode")) {
				CodeRunner.toggleDevMode();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = Character.toLowerCase((char) e.getKeyCode());

			if (key == conf.get("move_left")) {
				deltaX = 0;
			} else if (key == conf.get("move_right")) {
				deltaX = 0;
			} else if (key == conf.get("jump")) {
				endJump();
			}
		}
	}
}
