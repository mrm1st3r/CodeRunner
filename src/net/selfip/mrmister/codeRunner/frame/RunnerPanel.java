package net.selfip.mrmister.codeRunner.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.selfip.mrmister.codeRunner.event.AutoPauseHandler;

/**
 * where all the action goes.
 * @author mrm1st3r
 *
 */
public class RunnerPanel extends JPanel implements ActionListener {

	static final long serialVersionUID = 0x3;
	private static final int REFRESH_RATE = 300;
	private static Logger log = Logger.getLogger(RunnerPanel.class.getName());

	private Timer t;
	private double progress = 0;

	/**
	 * create a new RunnerPanel.
	 */
	public RunnerPanel() {

		addMouseListener(new AutoPauseHandler(this));

		t = new Timer(REFRESH_RATE, this);
		t.start();
	}

	/**
	 * start a new game.
	 */
	public void start() {
		log.info("starting a new game");
	}

	/**
	 * pause the current game.
	 */
	public void pause() {
		log.info("paused the running game");
	}

	/**
	 * resume a paused game.
	 */
	public void resume() {
		log.info("resumed the game");
	}

	/**
	 * get the current game progress.
	 * @return progress in length-units
	 */
	public double getProgress() {
		return progress;
	}

	/**
	 * blub.
	 * @param e bla
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
