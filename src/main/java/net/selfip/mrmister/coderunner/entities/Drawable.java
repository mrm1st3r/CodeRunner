package net.selfip.mrmister.coderunner.entities;

import java.awt.Graphics;

import net.selfip.mrmister.coderunner.util.DisplayWriter;

/**
 * object which should be drawn to the screen.
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