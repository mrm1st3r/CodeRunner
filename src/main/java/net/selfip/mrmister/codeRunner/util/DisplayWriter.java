package net.selfip.mrmister.codeRunner.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * helper for drawing texts.
 *
 */
public class DisplayWriter {

	public static final int LINE_HEIGHT = 15;
	public static final int FIRST_LINE = 20;
	public static final int SIDE_MARGIN = 10;

	public static final int BIG_FONT_SIZE = 20;
	public static final int BIG_FONT_LINE_HEIGHT = 25;

	private static final Color DEFAULT_COLOR = Color.BLACK;

	private static Font bigFont = new Font("Lucida Console", Font.PLAIN, BIG_FONT_SIZE);
	private Font defFont;
	private int currentLineHeight = FIRST_LINE;
	private int rightLineHeight = FIRST_LINE;
	private Graphics g;
	private JPanel env;
	private Color c;

	/**
	 * @param gra graphics to draw with
	 * @param p panel to draw in
	 */
	public DisplayWriter(Graphics gra, JPanel p) {
		g = gra;
		env = p;
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
	 * print a text line centered on both axis.
	 * @param msg text to print
	 */
	public void printCentered(String msg) {
		g.setColor(c);
		g.setFont(bigFont);

		String[] text = msg.split("\n");

		// calculate position
		FontMetrics fm = g.getFontMetrics();
		int y = (env.getHeight() - BIG_FONT_LINE_HEIGHT * text.length) / 2;

		for (int i = 0; i < text.length; i++) {
			int x = (env.getWidth() - fm.stringWidth(text[i])) / 2;
			y += BIG_FONT_LINE_HEIGHT;

			g.drawString(text[i], x, y);
		}
	}

	/**
	 * print a text line right-aligned to the screen.
	 * @param msg text to print
	 */
	public void printlnRight(String msg) {
		g.setColor(c);
		g.setFont(defFont);
		g.drawString(msg,
				env.getWidth() - g.getFontMetrics().stringWidth(msg)
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
