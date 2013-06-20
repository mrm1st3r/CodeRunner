package net.selfip.mrmister.codeRunner.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;

/**
 * auto-pause on mouse leave and resume on entering.
 * @author mrm1st3r
 *
 */
public class AutoPauseHandler implements MouseListener {

	private RunnerPanel env;
	
	/**
	 * 
	 * @param rp environment to pause/resume
	 */
	public AutoPauseHandler(RunnerPanel rp) {
		env = rp;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		env.resume();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		env.pause();
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }

}
