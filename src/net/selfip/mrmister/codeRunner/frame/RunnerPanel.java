package net.selfip.mrmister.codeRunner.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.entities.AbstractEntity;
import net.selfip.mrmister.codeRunner.entities.Bug;
import net.selfip.mrmister.codeRunner.entities.Coffee;
import net.selfip.mrmister.codeRunner.entities.Player;
import net.selfip.mrmister.codeRunner.entities.SpawnManager;
import net.selfip.mrmister.codeRunner.event.MouseHandler;
import net.selfip.mrmister.codeRunner.util.DisplayWriter;
import net.selfip.mrmister.codeRunner.util.Time;

/**
 * where all the action goes.
 * @author mrm1st3r
 *
 */
public class RunnerPanel extends JPanel implements Runnable {

	public static final int KEY_PAUSE = 'p';

	private static final long serialVersionUID = 0x1;

	private static final int FPS_LIMIT = 60;


	private double progress = 0;
	private boolean paused = false;
	private boolean started = false;
	private MainFrame mainFrame;

	private long delta = 0;
	private long last = 0;
	private long fps = 0;

	private Logger log;
	private BufferedImage bg;
	private String msg = "press ENTER to start";

	private Player player;
	private Vector<AbstractEntity> entities;
	private SpawnManager spawner;

	/**
	 * @param main parent frame
	 */
	public RunnerPanel(MainFrame main) {
		super();
		mainFrame = main;
		log = Logger.getLogger(getClass().getName());

		bg = CodeRunner.loadImages("background.png", 1)[0];
		Coffee.init();
		Bug.init();
	}

	/**
	 * start a new game.
	 */
	public void start() {
		if (started) {
			return;
		}

		msg = null;
		progress = 0;
		addMouseListener(new MouseHandler(this));

		entities = new Vector<AbstractEntity>();
		player = new Player(this);
		player.registerKeyHandler(mainFrame);
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
				Thread.sleep((Time.MILLIS_PER_SEC / FPS_LIMIT));
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

		msg = newMsg;
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
	 * @return time needed for the last frame
	 */
	public long getDelta() {
		return delta;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		DisplayWriter out = new DisplayWriter(g);
		out.setColor(Color.BLACK);

		// draw background
		g.drawImage(bg, (int) -(progress % MainFrame.WIDTH), 0, this);
		g.drawImage(bg,
				(int) -(progress % MainFrame.WIDTH) + MainFrame.WIDTH,
				0, this);

		if (started) {
			// write FPS-count to the upperleft corner

			out.println("FPS: " + fps);
			out.println(entities.size() + " entities");

			for (AbstractEntity e : entities) {
				e.draw(g, out);
			}
		}

		if (msg != null) {
			out.printCentered(msg);
		}
	}
}
