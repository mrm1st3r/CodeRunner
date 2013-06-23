package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;
import net.selfip.mrmister.codeRunner.util.Time;

/**
 * base class for non player entities.
 * @author mrm1st3r
 *
 */
public class Sprite extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	BufferedImage[] pics;
	private int currPic = 0;
	private long delay;
	private long anim = 0;

	/**
	 * 
	 * @param i images representing the sprite
	 * @param pos absolute position inside the Panel
	 * @param d delay between animation images in millisecs
	 * @param p environment
	 */
	public Sprite(BufferedImage[] i, Point2D pos, long d, RunnerPanel p) {
		pics = i;
		x = pos.getX();
		y = pos.getY();
		this.delay = d;
		height = pics[0].getHeight();
		width = pics[0].getWidth();
		setEnv(p);
	}
	
	@Override
	public int getRelHeight() {
		return (int) (getEnv().getHeight() - y - pics[0].getHeight());
	}
	
	public void setRelHeight(int pos) {
		y = (getEnv().getHeight() - pos - pics[0].getHeight());
	}
	
	public boolean onGround() {
		return getRelHeight() == 0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(pics[currPic], (int) x, (int) y, null);
		//log.info("drawing at " + x + "/" + y);
	}

	@Override
	public void doLogic() {
		anim += getEnv().getDelta() / Time.NANOS_PER_MILLI;

		// show next animation image
		if (anim > delay) {
			anim = 0;
			currPic++;
			if (currPic >= pics.length) {
				currPic = 0;
			}
		}
	}
}
