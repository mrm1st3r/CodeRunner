package net.selfip.mrmister.coderunner.game;

import net.selfip.mrmister.coderunner.entities.AbstractEntity;
import net.selfip.mrmister.coderunner.entities.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

/**
 * spawn new entities periodically.
 */
class SpawnManager implements ActionListener {

	private static final int SPAWN_POS = 20;
	private static final int SPAWN_DIST = 110;
	private static final int SPAWN_TIMEOUT_MILLIS = 300;
	private static final Logger LOG = LoggerFactory.getLogger(SpawnManager.class);

	private static final double COFFEE_CHANCE = 0.1;
	private static final double BED_CHANCE = 0.2;
	private static final double BUG_CHANCE = 0.02;

	private final SpawnTarget game;
	private final Bounds gameBounds;
	private final EntityFactory factory;
	private final Timer timer;
	private long lastSpawnPosition;

	SpawnManager(SpawnTarget game, Bounds gameBounds, EntityFactory factory) {
		this.game = game;
		this.gameBounds = gameBounds;
		this.factory = factory;
		timer = new Timer(SPAWN_TIMEOUT_MILLIS, this);
	}

	void start() {
		timer.start();
	}

	void stop() {
		timer.stop();
	}

	private void spawn() {
		if (gameBounds.getOffset() - lastSpawnPosition < SPAWN_DIST) {
			return;
		}
		AbstractEntity e = null;

		if (Math.random() <= BUG_CHANCE) {
			LOG.info("spawning new bug");
			e = factory.createBug(new Point2D.Double(
					gameBounds.getWidth() + gameBounds.getOffset() + SPAWN_POS,
					0));
		} else if (Math.random() <= COFFEE_CHANCE) {
			LOG.info("spawning new coffee");
			e = factory.createCoffee(new Point2D.Double(
					gameBounds.getWidth() + gameBounds.getOffset() + SPAWN_POS,
					0));
		} else if (Math.random() < BED_CHANCE) {
			LOG.info("spawning new bed");
			e = factory.createBed(new Point2D.Double(
					gameBounds.getWidth() + gameBounds.getOffset() + SPAWN_POS,
					0));
		}

		if (e != null) {
			game.spawnEntity(e);
			lastSpawnPosition = gameBounds.getOffset();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(timer)) {
			spawn();
		}
	}

	interface SpawnTarget {

		void spawnEntity(AbstractEntity entity);
	}
}
