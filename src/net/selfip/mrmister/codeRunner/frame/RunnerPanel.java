package net.selfip.mrmister.codeRunner.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ListIterator;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.entities.AbstractEntity;
import net.selfip.mrmister.codeRunner.entities.Bed;
import net.selfip.mrmister.codeRunner.entities.Bug;
import net.selfip.mrmister.codeRunner.entities.Coffee;
import net.selfip.mrmister.codeRunner.entities.Player;
import net.selfip.mrmister.codeRunner.event.MouseHandler;
import net.selfip.mrmister.codeRunner.util.DisplayWriter;
import net.selfip.mrmister.codeRunner.util.Time;

/**
 * where all the action goes.
 * @author mrm1st3r
 *
 */
public class RunnerPanel extends JPanel implements ActionListener, Runnable {

	public static final int KEY_PAUSE = 'p';

	static final long serialVersionUID = 0x1;
	private static Logger log = Logger.getLogger(RunnerPanel.class.getName());

	private static final int FPS_LIMIT = 60;

	private static final int START_MSG_SIZE = 20;

	private static final double COFFEE_CHANCE = 0.1;
	private static final double BED_CHANCE = 0.2;
	private static final double BUG_CHANCE = 0.02;

	private static final int SPAWN_TIMEOUT = 300;
	private static final int SPAWN_POS = 20;
	private static final int SPAWN_DIST = 100;

	private double progress = 0;
	private boolean paused = false;
	private boolean started = false;
	private MainFrame mainFrame;

	private long delta = 0;
	private long last = 0;
	private long fps = 0;
	private int lastSpawn = 0;

	private Timer timer;
	private AbstractEntity player;
	private BufferedImage bg;
	private Vector<AbstractEntity> entities;
	private String msg = "press ENTER to start";

	/**
	 * @param main parent frame
	 */
	public RunnerPanel(MainFrame main) {
		super();
		mainFrame = main;
	}
	
	@Override
	public void run() {
		log.info("Main Loop starting");
		while (mainFrame.isVisible() && started) {

			calculateDelta();

			if (entities != null) {
				for (ListIterator<AbstractEntity> it = entities.listIterator();
						it.hasNext();) {

					AbstractEntity s = it.next();
					s.doLogic(delta);
					s.move(delta);

					if (s.outOfSight()) {
						it.remove();
					}
				}	
			}

			calculateCollisions();

			repaint();

			try {
				Thread.sleep((Time.MILLIS_PER_SEC / FPS_LIMIT));
			} catch (InterruptedException e) {
				log.info("interrupted during sleep!");
			}
		}

		log.info("Main-Loop is over!");
	}
	
	private void calculateCollisions() {
		for (int i = 0; i < entities.size(); i++) {
			if (!(entities.elementAt(i) instanceof Player)
					&& entities.elementAt(i).collidedWith(player)) {
				entities.remove(i);
			}
		}
	}
	
	private void spawn() {
		if (progress - lastSpawn < SPAWN_DIST) {
			return;
		}
		AbstractEntity e = null;
		// spawn a new Coffee
		if (Math.random() <= BUG_CHANCE) {
			log.info("spawning new bug");
			e = new Bug(
					new Point2D.Double(getWidth() + progress + SPAWN_POS, 0),
					this);
		} else if (Math.random() <= COFFEE_CHANCE) {
			log.info("spawing new coffee");
			e = new Coffee(
					new Point2D.Double(getWidth() + progress + SPAWN_POS, 0),
					this);

		} else if (Math.random() < BED_CHANCE) {
			log.info("spawning new bed");
			e = new Bed(
					new Point2D.Double(getWidth() + progress + SPAWN_POS, 0),
					this);
		}

		if (e != null) {
			ListIterator<AbstractEntity> it = entities.listIterator();
			it.add(e);
			lastSpawn = (int) progress;
		}
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
		lastSpawn = 0;
		addMouseListener(new MouseHandler(this));

		bg = CodeRunner.loadImages("background.png", 1)[0];
		Coffee.setPics(CodeRunner.loadImages("coffee.png", 1));
		Bug.setPics(CodeRunner.loadImages("bug.png", 4));

		entities = new Vector<AbstractEntity>();
		player = new Player(this);
		((Player) player).registerKeyHandler(mainFrame);
		entities.add(player);

		log.info("starting a new game");
		last = System.nanoTime();

		timer = new Timer(SPAWN_TIMEOUT, this);
		timer.start();

		started = true;
		Thread th = new Thread(this);
		th.start();
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
		timer.stop();
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
	 * 
	 * @return whether or not the game is paused
	 */
	public boolean isPaused() {
		return paused;
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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(timer)) {
			spawn();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		DisplayWriter out = new DisplayWriter(g);
		out.setColor(Color.BLACK);

		if (started) {
			// draw background
			g.drawImage(bg, (int) -(progress % MainFrame.WIDTH), 0, this);
			g.drawImage(bg,
					(int) -(progress % MainFrame.WIDTH) + MainFrame.WIDTH,
					0, this);
	
			// write FPS-count to the upperleft corner
	
			out.println("FPS: " + fps);
			out.println(entities.size() + " entities");
			
			for (AbstractEntity e : entities) {
				e.draw(g, out);
			}
		}

		if (msg != null) {
			g.drawString(msg, MainFrame.WIDTH / 3,
					(MainFrame.HEIGHT - START_MSG_SIZE) / 2);
		}
	}
}
