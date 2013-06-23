package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.selfip.mrmister.codeRunner.frame.RunnerPanel;
import net.selfip.mrmister.codeRunner.util.Time;

/**
 * base class for non player entities.
 * @author mrm1st3r
 *
 */
public abstract class AbstractEntity
	extends Rectangle2D.Double
	implements Movable, Drawable {

	public static final int SIGHT_OFFSET = 5;
	
	protected static Logger log = Logger.getLogger("AbstractEntity");
	private static final long serialVersionUID = 1L;

	protected int deltaX = 0;
	protected int deltaY = 0;
	
	BufferedImage[] pics;
	private int currPic = 0;
	private long delay;
	private long anim = 0;

	private RunnerPanel env;

	/**
	 * 
	 * @param i images representing the sprite
	 * @param pos absolute position inside the Panel
	 * @param d delay between animation images in millisecs
	 * @param p environment
	 */
	public AbstractEntity(BufferedImage[] i,
			Point2D pos, long d, RunnerPanel p) {

		pics = i;
		x = pos.getX();
		y = pos.getY();
		this.delay = d;
		height = pics[0].getHeight();
		width = pics[0].getWidth();
		setEnv(p);
	}


	public int getRelativeY() {
		return (int) (getEnv().getHeight() - y - pics[0].getHeight());
	}
	
	public void setRelativeY(int pos) {
		y = (getEnv().getHeight() - pos - pics[0].getHeight());
	}
	
	public int getRelativeX() {
		return (int) (x - getEnv().getProgress());
	}
	
	public void setRelativeX(int pos) {
		x = pos + getEnv().getProgress();
	}

	public boolean onGround() {
		return getRelativeY() == 0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(pics[currPic], getRelativeX(), (int) y, null);
		//log.info("drawing at " + x + "/" + y);
	}

	@Override
	public void doLogic(long delta) {
		anim += delta / Time.NANOS_PER_MILLI;

		// show next animation image
		if (anim > delay) {
			anim = 0;
			currPic++;
			if (currPic >= pics.length) {
				currPic = 0;
			}
		}
	}

	@Override
	public void move(long delta) {
		if (deltaX != 0) {
			x += deltaX * (1.0 * delta / Time.NANOS_PER_SEC);
		}
	
		if (deltaY != 0) {
			y += deltaY * (1.0 * delta / Time.NANOS_PER_SEC);
		}
	}

	/**
	 * whether or not the entity is out of the currently displayed area.
	 * @return true if not visible
	 */
	public boolean outOfSight() {
		
		return x < (env.getProgress() + AbstractEntity.SIGHT_OFFSET);
	}

	/**
	 * get the environment panel.
	 * @return current environment
	 */
	protected RunnerPanel getEnv() {
		return env;
	}

	/**
	 * set a new environment panel.
	 * @param rp new panel
	 */
	protected void setEnv(RunnerPanel rp) {
		env = rp;
	}
}
