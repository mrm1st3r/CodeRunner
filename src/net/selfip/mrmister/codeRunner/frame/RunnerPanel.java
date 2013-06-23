package net.selfip.mrmister.codeRunner.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.entities.AbstractEntity;
import net.selfip.mrmister.codeRunner.entities.Player;
import net.selfip.mrmister.codeRunner.event.MouseHandler;
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
	private static final int FPS_POS_X = 10;
	private static final int FPS_POS_Y = 20;
	
	private static final int START_MSG_SIZE = 20;

	private double progress = 0;
	private boolean paused = false;
	private boolean started = false;
	private MainFrame mainFrame;
	
	private long delta = 0;
	private long last = 0;
	private long fps = 0;
	
	private BufferedImage bg;
	private Vector<AbstractEntity> entities;

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
		while (mainFrame.isVisible()) {

			calculateDelta();

			if (entities != null) {
				for (AbstractEntity s : entities) {
					s.doLogic(delta);
					s.move(delta);
				}	
			}

			repaint();

			try {
				Thread.sleep((Time.MILLIS_PER_SEC / FPS_LIMIT));
						//- (delta / Time.NANOS_PER_MILLI));
			} catch (InterruptedException e) {
				log.info("interrupted during sleep!");
			}
		}

		log.info("Main-Loop is over!");
	}

	/**
	 * start a new game.
	 */
	public void start() {
		if (started) {
			return;
		}

		Thread th = new Thread(this);
		th.start();
		
		addMouseListener(new MouseHandler(this));

		bg = CodeRunner.loadImages("background.png", 1)[0];
		
		entities = new Vector<AbstractEntity>();
		Player p = new Player(this);
		p.registerKeyHandler(mainFrame);
		entities.add(p);

		log.info("starting a new game");
		last = System.nanoTime();
		
		started = true;
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
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (!started) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Lucida Console", 0, START_MSG_SIZE));
			g.drawString("press ENTER to start", MainFrame.WIDTH / 3,
					(MainFrame.HEIGHT - START_MSG_SIZE) / 2);
			return;
		}
		
		g.drawImage(bg, (int) -(progress % MainFrame.WIDTH), 0, this);
		g.drawImage(bg, (int) -(progress % MainFrame.WIDTH) + MainFrame.WIDTH,
				0, this);
		
		// write FPS-count to the upperleft corner
		g.setColor(Color.BLACK);
		g.drawString("FPS: " + fps, FPS_POS_X, FPS_POS_Y);

		for (AbstractEntity e : entities) {
			e.draw(g);
		}
	}
}
