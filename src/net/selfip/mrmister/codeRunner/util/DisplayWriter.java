package net.selfip.mrmister.codeRunner.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * helper for drawing texts.
 * @author mrm1st3r
 *
 */
public class DisplayWriter {

	public static final int LINE_HEIGHT = 15;
	public static final int FIRST_LINE = 20;
	public static final int LEFT_MARGIN = 10;
	
	public static final int BIG_FONT_SIZE = 20;
	
	private static final Color DEFAULT_COLOR = Color.BLACK;
	
	private static Font bigFont = null;
	private int currentLineHeight = FIRST_LINE;
	private Graphics g;
	
	public DisplayWriter(Graphics gra) {
		if (bigFont == null) {
			bigFont = new Font("Lucida Console", 0, BIG_FONT_SIZE);
		}
		
		g = gra;
	}
	
	public void println(String msg) {
		g.setColor(DEFAULT_COLOR);
		g.drawString(msg, LEFT_MARGIN, currentLineHeight);
		
		currentLineHeight += LINE_HEIGHT;
	}
	
	public void printToPos(String msg, int x, int y) {
		g.drawString(msg, x, y);
	}
	
	public void setColor(Color c) {
		g.setColor(c);
	}
}
