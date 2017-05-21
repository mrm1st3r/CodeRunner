package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.util.Images;
import net.selfip.mrmister.coderunner.util.Time;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * the player object.
 */
class Player extends AbstractEntity implements PlayableEntity {

	private static final int MOVE_SPEED = 180;
	private static final int COFFEE_SHOCK = 10;

	private static final int JUMP_TIME = 500;
	private static final int JUMP_SPEED = 200;

	private static final long serialVersionUID = 1L;
	private static final String PLAYER_SPRITE = "player.png";
	private static final int ANIMATION_TIMEOUT = 300;
	private static final int ANIMATION_STEPS = 2;
	private static final int START_POS = 100;

	private long jump = 0;
	private int energy = 0;
	private State state = State.ALIVE;

	Player(Bounds gameBounds) throws IOException {
		super(
				Images.loadAnimation(PLAYER_SPRITE, ANIMATION_STEPS),
				new Point2D.Double(START_POS, 0),
				ANIMATION_TIMEOUT,
				gameBounds
		);
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
			state = State.CAFFEINE_SHOCKED;
		}
	}

	void reduceEnergy() {
		energy--;
		if (energy < 0) {
			state = State.ASLEEP;
		}
	}

	void depress() {
		state = State.DEPRESSED;
	}

	@Override
	public void move(long delta) {
		calculateJump();

		super.move(delta);

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
				stopJump();
			}

			if (deltaY == JUMP_SPEED && getRelativeY() <= 0) {
				deltaY = 0;
				jump = 0;
				setRelativeY(0);
			}
		}
	}

	@Override
	public boolean collidedWith(Entity e) {
		return false;
	}

	@Override
	public void startJump() {
		if (jump == 0 && onGround()) {
			jump = System.nanoTime();
			deltaY = -1 * JUMP_SPEED;
		}
	}

	@Override
	public void stopJump() {
		deltaY = JUMP_SPEED;
	}

	@Override
	public void moveLeft() {
		deltaX = -1 * MOVE_SPEED;
	}

	@Override
	public void moveRight() {
		deltaX = MOVE_SPEED;
	}

	@Override
	public void stop() {
		deltaX = 0;
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void reset() {
		state = State.ALIVE;
		resetRelativeX();
	}

	@Override
	public int getEnergy() {
		return energy;
	}
}
