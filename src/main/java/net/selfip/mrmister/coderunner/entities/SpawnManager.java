package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.CodeRunner;
import net.selfip.mrmister.coderunner.frame.RunnerPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

/**
 * spawn new entities periodically.
 *
 */
public class SpawnManager implements ActionListener {

	private static final int SPAWN_TIMEOUT_MILLIS = 300;
	private static final Logger LOG = LoggerFactory.getLogger(RunnerPanel.class);

	private static final double COFFEE_CHANCE = 0.1;
	private static final double BED_CHANCE = 0.2;
	private static final double BUG_CHANCE = 0.02;

	private final RunnerPanel env;
	private final Timer timer;
	private long lastSpawn;

	/**
	 * @param rp panel to spawn entities at
	 */
	public SpawnManager(RunnerPanel rp) {
		env = rp;
		timer = new Timer(SPAWN_TIMEOUT_MILLIS, this);
	}

	/**
	 * start to spawn new entities.
	 */
	public void start() {
		timer.start();
	}

	/**
	 * stop to spawn new entities.
	 */
	public void stop() {
		timer.stop();
	}

	private void spawn() {
		if (env.getProgress() - lastSpawn < CodeRunner.SPAWN_DIST) {
			return;
		}
		AbstractEntity e = null;

		if (Math.random() <= BUG_CHANCE) {
			LOG.info("spawning new bug");
			e = new Bug(
					new Point2D.Double(env.getWidth() + env.getProgress()
							+ CodeRunner.SPAWN_POS, 0), env);
		} else if (Math.random() <= COFFEE_CHANCE) {
			LOG.info("spawning new coffee");
			e = new Coffee(
					new Point2D.Double(env.getWidth() + env.getProgress()
							+ CodeRunner.SPAWN_POS, 0), env);

		} else if (Math.random() < BED_CHANCE) {
			LOG.info("spawning new bed");
			e = new Bed(
					new Point2D.Double(env.getWidth() + env.getProgress()
							+ CodeRunner.SPAWN_POS, 0),	env);
		}

		if (e != null) {
			env.addEntity(e);
			lastSpawn = (int) env.getProgress();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(timer)) {
			spawn();
		}
	}
}
