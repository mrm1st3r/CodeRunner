package net.selfip.mrmister.codeRunner;

import net.selfip.mrmister.codeRunner.frame.MainFrame;

/**
 * Launcher for CodeRunner.
 * @author mrm1st3r
 *
 */
public final class CodeRunner {

	public static final String APP_NAME = "Code Runner";
	public static final double VERSION = 0.1;
	public static final String AUTHOR = "Lukas Taake, Steffen Schiffel";
	public static final String YEAR = "2013";
	
	private CodeRunner() { }
	
	/**
	 * 
	 * @param args no parameters available yet
	 */
	public static void main(String[] args) {
		new MainFrame();
	}
	
	/**
	 * build the window title.
	 * @param showVersion should the version be viewed
	 * @return window title
	 */
	public static String getWindowTitle(boolean showVersion) {
		String ret = APP_NAME;
		
		if (showVersion) {
			ret += " " + VERSION;
		}
		
		return ret;
	}
}
