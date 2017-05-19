package net.selfip.mrmister.coderunner.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Helper class for drawing texts.
 */
public class DisplayWriter {

	private static final int LINE_HEIGHT = 15;
	private static final int FIRST_LINE = 20;
	private static final int SIDE_MARGIN = 10;
	private static final Color DEFAULT_COLOR = Color.BLACK;

	private static final int BIG_FONT_SIZE = 20;
	private static final int BIG_FONT_LINE_HEIGHT = 25;
	private static final Font BIG_FONT = new Font("Lucida Console", Font.PLAIN, BIG_FONT_SIZE);

	private final Font defaultFont;
	private final Graphics graphics;
	private final JPanel panel;

	private int currentLineHeight = FIRST_LINE;
	private int rightLineHeight = FIRST_LINE;
	private Color c = DEFAULT_COLOR;

	/**
	 * @param graphics graphics to draw with
	 * @param panel panel to draw in
	 */
	public DisplayWriter(Graphics graphics, JPanel panel) {
		this.graphics = graphics;
		this.panel = panel;
		defaultFont = graphics.getFont();
	}

	/**
	 * print a text line to screen.
	 * @param msg text to print
	 */
	public void println(String msg) {
		graphics.setColor(c);
		graphics.setFont(defaultFont);
		graphics.drawString(msg, SIDE_MARGIN, currentLineHeight);
		currentLineHeight += LINE_HEIGHT;
	}

	/**
	 * print a text line centered on both axis.
	 * @param msg text to print
	 */
	public void printCentered(String msg) {
		graphics.setColor(c);
		graphics.setFont(BIG_FONT);

		String[] text = msg.split("\n");

		// calculate position
		FontMetrics fm = graphics.getFontMetrics();
		int y = (panel.getHeight() - BIG_FONT_LINE_HEIGHT * text.length) / 2;

		for (String line : text) {
			int x = (panel.getWidth() - fm.stringWidth(line)) / 2;
			y += BIG_FONT_LINE_HEIGHT;
			graphics.drawString(line, x, y);
		}
	}

	/**
	 * print a text line right-aligned to the screen.
	 * @param msg text to print
	 */
	public void printlnRight(String msg) {
		graphics.setColor(c);
		graphics.setFont(defaultFont);
		graphics.drawString(msg,
				panel.getWidth() - graphics.getFontMetrics().stringWidth(msg)
				- SIDE_MARGIN,
				rightLineHeight);
		rightLineHeight += LINE_HEIGHT;
	}
}
