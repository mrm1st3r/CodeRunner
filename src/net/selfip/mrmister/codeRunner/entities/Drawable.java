package net.selfip.mrmister.codeRunner.entities;

import java.awt.Graphics;

import net.selfip.mrmister.codeRunner.util.DisplayWriter;

/**
 * 
 * @author mrm1st3r
 *
 */
public interface Drawable {

	/**
	 * draw the object to the screen.
	 * @param g used graphics
	 * @param d helper object for writing text
	 */
	void draw(Graphics g, DisplayWriter d);
}
