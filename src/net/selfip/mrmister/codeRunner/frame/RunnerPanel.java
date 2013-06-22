package net.selfip.mrmister.codeRunner.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.selfip.mrmister.codeRunner.entities.Player;
import net.selfip.mrmister.codeRunner.entities.Sprite;
import net.selfip.mrmister.codeRunner.event.MouseHandler;

/**
 * where all the action goes.
 * @author mrm1st3r
 *
 */
public class RunnerPanel extends JPanel implements ActionListener, Runnable {

	public static final int KEY_PAUSE = 'p';
	
	static final long serialVersionUID = 0x1;
	private static Logger log = Logger.getLogger(RunnerPanel.class.getName());

	private static final int FPS_X = 10;
	private static final int FPS_Y = 20;

	private double progress = 0;
	private boolean paused = false;
	private boolean started = false;
	private MainFrame mainFrame;
	
	private long delta = 0;
	private long last = 0;
	private long fps = 0;
	
	private Vector<Sprite> entities;
	/**
	 * create a new RunnerPanel.
	 * @param main parent frame
	 */
	public RunnerPanel(MainFrame main) {
		setPreferredSize(new Dimension(MainFrame.HEIGHT, MainFrame.WIDTH));
		mainFrame = main;
		addMouseListener(new MouseHandler(this));
	}
	
	/**
	 * 
	 */
	public void run() {
		while (mainFrame.isVisible()) {

			calculateDelta();

			repaint();

			try {
				Thread.sleep(10);
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
		started = true;
		
		entities = new Vector<Sprite>();
		Sprite p = new Player(this);
		entities.add(p);
		
		log.info("starting a new game");
		last = System.nanoTime();
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
	
	private void calculateDelta() {
		delta = System.nanoTime() - last;
		last = System.nanoTime();
		fps = ((long) 1e9) / delta;
	}
	
	/**
	 * 
	 * @return time needed for the last frame
	 */
	public long getDelta() {
		return delta;
	}

	/**
	 * blub.
	 * @param e bla
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// write FPS-count to the upperleft corner
		g.setColor(Color.BLACK);
		g.drawString(fps + " FPS", FPS_X, FPS_Y);
		
		if (entities != null) {
			for (Sprite e : entities) {
				e.draw(g);
			}
		}
	}
}
