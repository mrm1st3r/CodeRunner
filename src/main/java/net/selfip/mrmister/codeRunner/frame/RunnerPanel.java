package net.selfip.mrmister.codeRunner.frame;

import net.selfip.mrmister.codeRunner.ApplicationInfo;
import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.util.Images;
import net.selfip.mrmister.codeRunner.entities.*;
import net.selfip.mrmister.codeRunner.event.KeyConfig;
import net.selfip.mrmister.codeRunner.event.MouseHandler;
import net.selfip.mrmister.codeRunner.lang.I18n;
import net.selfip.mrmister.codeRunner.util.DisplayWriter;
import net.selfip.mrmister.codeRunner.util.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * where all the action goes.
 *
 */
public class RunnerPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 0x1;

	private double progress = 0;
	private boolean paused = false;
	private boolean started = false;
	private MainFrame mainFrame;
	private final I18n i18n;

	private long delta = 0;
	private long last = 0;
	private long fps = 0;

	private Logger log;
	private BufferedImage bg;
	private String msg;

	private Player player;
	private KeyConfig keyConf;
	private Vector<AbstractEntity> entities;
	private SpawnManager spawner;

	/**
	 * @param main parent frame
	 * @param applicationInfo
	 * @param i18n
	 */
	public RunnerPanel(MainFrame main, ApplicationInfo applicationInfo, I18n i18n) {
		super();
		mainFrame = main;
		this.i18n = i18n;
		log = Logger.getLogger(getClass().getName());

		keyConf = new KeyConfig(CodeRunner.KEYCONFIG_FILE, applicationInfo, i18n);
		keyConf.setDefaultValue("start", CodeRunner.KEY_START);
		keyConf.setDefaultValue("pause", CodeRunner.KEY_PAUSE);
		keyConf.setDefaultValue("move_left", CodeRunner.KEY_LEFT);
		keyConf.setDefaultValue("move_right", CodeRunner.KEY_RIGHT);
		keyConf.setDefaultValue("jump", CodeRunner.KEY_JUMP);

		try {
			bg = Images.loadImage("background.png");
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
	public void start() throws Exception {
		if (started) {
			return;
		}

		msg = null;
		progress = 0;
		addMouseListener(new MouseHandler(this));

		entities = new Vector<>();
		player = new Player(this, i18n);
		player.registerKeyHandler(mainFrame, keyConf);
		entities.add(player);

		log.info("starting a new game");
		last = System.nanoTime();

		spawner = new SpawnManager(this);

		started = true;
		Thread th = new Thread(this);
		th.start();
		spawner.start();
	}

	@Override
	public void run() {
		log.info("Main Loop starting");
		while (mainFrame.isVisible() && started) {

			calculateDelta();

			doLogic();

			repaint();

			try {
				Thread.sleep((Time.MILLIS_PER_SEC / CodeRunner.FPS_LIMIT));
			} catch (InterruptedException e) {
				log.info("interrupted during sleep!");
			}
		}

		log.info("Main-Loop is over!");
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

		log.info("paused the running game");
		paused = true;
	}

	/**
	 * resume a paused game.
	 */
	public void resume() {
		if (!paused || !started) {
			return;
		}

		log.info("resumed the game");
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

	/**
	 * save the key-configuration.
	 */
	public void saveKeyConfig() {
		keyConf.save();
	}

	/**
	 * @return the current key-configuration
	 */
	public KeyConfig getKeyConfig() {
		return keyConf;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		DisplayWriter out = new DisplayWriter(g, this);
		out.setColor(Color.BLACK);

		// draw background
		g.drawImage(bg, (int) -(progress % CodeRunner.WIDTH), 0, this);
		g.drawImage(bg,
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
