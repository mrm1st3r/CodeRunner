package net.selfip.mrmister.codeRunner.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * helper for drawing texts.
 * @author mrm1st3r
 *
 */
public class DisplayWriter {

	public static final int LINE_HEIGHT = 15;
	public static final int FIRST_LINE = 20;
	public static final int SIDE_MARGIN = 10;

	public static final int BIG_FONT_SIZE = 20;

	private static final Color DEFAULT_COLOR = Color.BLACK;

	private static Font bigFont = new Font("Lucida Console", 0, BIG_FONT_SIZE);
	private Font defFont;
	private int currentLineHeight = FIRST_LINE;
	private int rightLineHeight = FIRST_LINE;
	private Graphics g;
	private Color c;

	/**
	 * @param gra graphics to draw with
	 */
	public DisplayWriter(Graphics gra) {
		g = gra;
		defFont = g.getFont();
	}

	/**
	 * print a text line to screen.
	 * @param msg text to print
	 */
	public void println(String msg) {
		g.setColor(DEFAULT_COLOR);
		g.setFont(defFont);
		g.drawString(msg, SIDE_MARGIN, currentLineHeight);

		currentLineHeight += LINE_HEIGHT;
	}

	/**
	 * print a text line to a specified position.
	 * @param msg text to print
	 * @param x x-axis position
	 * @param y y-axis position
	 */
	public void printToPos(String msg, int x, int y) {
		g.setColor(c);
		g.setFont(defFont);
		g.drawString(msg, x, y);
	}
	
	/**
	 * print a text line centered on both axis.
	 * @param msg text to print
	 */
	public void printCentered(String msg) {
		g.setColor(c);
		g.setFont(bigFont);

		// calculate position
		FontMetrics fm = g.getFontMetrics();
		int x = (g.getClipBounds().width - fm.stringWidth(msg)) / 2;
		int y = (g.getClipBounds().height - fm.getHeight()) / 2;

		g.drawString(msg, x, y);
	}

	/**
	 * print a text line right-aligned to the screen.
	 * @param msg text to print
	 */
	public void printlnRight(String msg) {
		g.setColor(c);
		g.setFont(defFont);
		g.drawString(msg, 
				g.getClipBounds().width - g.getFontMetrics().stringWidth(msg)
				- SIDE_MARGIN,
				rightLineHeight);
	}
	
	/**
	 * set a new text-color.
	 * @param newColor new color
	 */
	public void setColor(Color newColor) {
		c = newColor;
	}
}
