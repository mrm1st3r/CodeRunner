package net.selfip.mrmister.coderunner;

import net.selfip.mrmister.coderunner.frame.MainFrame;
import net.selfip.mrmister.coderunner.lang.I18n;

import java.awt.event.KeyEvent;

/**
 * Launcher for CodeRunner.
 */
public final class CodeRunner {

	private static final String LANG = "de";

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int FPS_LIMIT = 60;

	public static final int KEY_START = KeyEvent.VK_ENTER;
	public static final int KEY_PAUSE = 'p';
	public static final int KEY_LEFT = KeyEvent.VK_LEFT;
	public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	public static final int KEY_JUMP = KeyEvent.VK_SPACE;
	public static final int KEY_TOGGLE_DEV = KeyEvent.VK_F11;

	public static final String KEYCONFIG_FILE = "keyboard.ini";

	public static final int SPAWN_TIMEOUT = 300;
	public static final int SPAWN_POS = 20;
	public static final int SPAWN_DIST = 110;

	private static boolean devMode = false;

	public static void main(String[] args) {
		new CodeRunner();
	}

	private CodeRunner() {
		new MainFrame(new ApplicationInfo(), new I18n(LANG));
	}

	/**
	 * @return whether developer mode is activated or not
	 */
	public static boolean devMode() {
		return devMode;
	}

	/**
	 * toggle developer mode.
	 */
	public static void toggleDevMode() {
		devMode = !devMode;
	}

}
