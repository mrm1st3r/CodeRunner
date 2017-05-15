package net.selfip.mrmister.coderunner.frame;

import net.selfip.mrmister.coderunner.CodeRunner;
import net.selfip.mrmister.coderunner.entities.*;
import net.selfip.mrmister.coderunner.event.KeyConfig;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.DisplayWriter;
import net.selfip.mrmister.coderunner.util.Images;
import net.selfip.mrmister.coderunner.util.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

/**
 * where all the action goes.
 */
public class RunnerPanel extends JPanel implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(RunnerPanel.class);

	private final MainFrame mainFrame;
	private final I18n i18n;
	private final KeyConfig keyConfig;

	private double progress = 0;
	private boolean paused = false;
	private boolean started = false;

	private long delta = 0;
	private long last = 0;
	private long fps = 0;

	private BufferedImage backgroundImages;
	private String msg;

	private Player player;
	private Vector<AbstractEntity> entities;
	private SpawnManager spawner;

	RunnerPanel(MainFrame main, I18n i18n, KeyConfig keyConfig) {
		super();
		mainFrame = main;
		this.i18n = i18n;
		this.keyConfig = keyConfig;

		try {
			backgroundImages = Images.loadImage("background.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Coffee.init();
		Bug.init();

		msg = i18n.t("start_msg");
	}

	/**
	 * start a new game.
	 */
	void start() throws IOException {
		if (started) {
			return;
		}

		msg = null;
		progress = 0;

		entities = new Vector<>();
		player = new Player(this, i18n);
		player.registerKeyHandler(mainFrame, keyConfig);
		entities.add(player);

		LOG.info("starting a new game");
		last = System.nanoTime();

		spawner = new SpawnManager(this);

		started = true;
		Thread th = new Thread(this);
		th.start();
		spawner.start();
	}

	@Override
	public void run() {
		LOG.info("Main Loop starting");
		while (mainFrame.isVisible() && started) {

			calculateDelta();

			doLogic();

			repaint();

			try {
				Thread.sleep((Time.MILLIS_PER_SEC / CodeRunner.FPS_LIMIT));
			} catch (InterruptedException e) {
				LOG.info("interrupted during sleep!");
			}
		}

		LOG.info("Main-Loop is over!");
	}

	private void doLogic() {
		if (entities != null && !paused) {
			for (int i = 0; i < entities.size(); i++) {

				AbstractEntity s = entities.elementAt(i);
				s.doLogic(delta);
				s.move(delta);

				if (s.outOfSight()) {
					entities.remove(i);
				}
			}

			calculateCollisions();
		}
	}

	private void calculateCollisions() {
		for (int i = 0; i < entities.size(); i++) {
			if (!(entities.elementAt(i) instanceof Player)
					&& entities.elementAt(i).collidedWith(player)) {
				entities.remove(i);
			}
		}
	}

	/**
	 * add a new entity to the game.
	 * @param e new entity
	 */
	public void addEntity(AbstractEntity e) {
		entities.add(e);
	}

	/**
	 * end the current game.
	 * @param newMsg message to be displayed
	 */
	public void stop(String newMsg) {
		if (!started) {
			return;
		}

		msg = newMsg + "\n" + i18n.t("score") + ": " + (int) getProgress();
		started = false;
		spawner.stop();
	}

	/**
	 * pause the current game.
	 */
	public void pause() {
		if (paused || !started) {
			return;
		}

		LOG.info("paused the running game");
		paused = true;
	}

	/**
	 * resume a paused game.
	 */
	public void resume() {
		if (!paused || !started) {
			return;
		}

		LOG.info("resumed the game");
		paused = false;
	}

	/**
	 * @return whether or not the game is paused
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * @return whether or not the game is paused
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * get the current game progress.
	 * @return progress in length-units
	 */
	public double getProgress() {
		return progress;
	}

	/**
	 * increment game progress.
	 * @param p increment value
	 */
	public void progress(int p) {
		progress += p;
	}

	private void calculateDelta() {
		delta = System.nanoTime() - last;
		last = System.nanoTime();
		fps = Time.NANOS_PER_SEC / delta;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		DisplayWriter out = new DisplayWriter(g, this);
		out.setColor(Color.BLACK);

		// draw background
		g.drawImage(backgroundImages, (int) -(progress % CodeRunner.WIDTH), 0, this);
		g.drawImage(backgroundImages,
				(int) -(progress % CodeRunner.WIDTH) + CodeRunner.WIDTH,
				0, this);

		if (started) {
			// write FPS-count to the upperleft corner

			if (CodeRunner.devMode()) {
				out.println("FPS: " + fps);
				out.println(entities.size() + " entities");
			}

			for (AbstractEntity e : entities) {
				e.draw(g, out);
			}
		}

		if (msg != null) {
			out.printCentered(msg);
		}
	}

}
