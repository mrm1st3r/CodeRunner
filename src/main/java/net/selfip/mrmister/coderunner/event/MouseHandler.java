package net.selfip.mrmister.coderunner.event;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import net.selfip.mrmister.coderunner.frame.RunnerPanel;

/**
 * auto-pause on mouse leave and resume on entering.
 *
 */
public class MouseHandler implements MouseListener {

	private RunnerPanel env;
	
	/**
	 * 
	 * @param rp environment to pause/resume
	 */
	public MouseHandler(RunnerPanel rp) {
		env = rp;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		env.resume();
		hide();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		env.pause();
		show();
	}

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }

	private void hide() {
		env.setCursor(java.awt.Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR),
				new java.awt.Point(0, 0), "NOCURSOR"));
	}
	
	private void show() {
		env.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
}
