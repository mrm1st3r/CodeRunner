package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;

/**
 * base class for non player entities.
 * @author mrm1st3r
 *
 */
public class Sprite extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage[] pics;
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
	public void draw(Graphics g) {
		g.drawImage(pics[currPic], (int) x, (int) y, null);
	}
	
	@Override
	public void doLogic() {
		anim += getEnv().getDelta()/1e6;
		
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
