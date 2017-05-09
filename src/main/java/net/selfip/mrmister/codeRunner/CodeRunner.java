package net.selfip.mrmister.codeRunner;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import net.selfip.mrmister.codeRunner.frame.MainFrame;
import net.selfip.mrmister.codeRunner.lang.I18n;

/**
 * Launcher for CodeRunner.
 * @author mrm1st3r
 *
 */
public final class CodeRunner {

	// ---------------- general application informations ----------------------
	public static final String APP_NAME = "Code Runner";
	public static final String VERSION = "0.3.4";
	public static final String CODE_AUTHOR = "Lukas Taake, Steffen Schiffel";
	public static final String GRAPHICS_AUTHOR = "Lukas Taake";
	public static final String YEAR = "2013";

	// ---------------- global application settings ---------------------------
	public static final String LANG = "de";

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

	// ---------------- other -------------------------------------------------
	private static Logger log;
	private static boolean devMode = false;

	private CodeRunner() { }

	/**
	 * 
	 * @param args no parameters available yet
	 */
	public static void main(String[] args) {
		I18n.init(LANG);
		Locale.setDefault(new Locale(LANG));
		log = Logger.getLogger(CodeRunner.class.getName());

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

	/**
	 * load an image.
	 * @param path image location
	 * @param num number of single images (side by side)
	 * @return array of all images
	 */
	public static BufferedImage[] loadImages(String path, int num) {
		BufferedImage[] anim = new BufferedImage[num];
		BufferedImage src = null;

		try {
			File file = new File(CodeRunner.class.getResource("/" + path).getFile());
			src = ImageIO.read(file);
		} catch (Exception e) {
			log.severe(e.getMessage());
			//log.severe("Failed loading image '" + path + "'");
			return null;
		}

		for (int i = 0; i < num; i++) {
			anim[i] = src.getSubimage(
					i * src.getWidth() / num,
					0,
					src.getWidth() / num,
					src.getHeight());
		}

		return anim;
	}
}
