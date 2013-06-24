package net.selfip.mrmister.codeRunner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import net.selfip.mrmister.codeRunner.frame.MainFrame;

/**
 * Launcher for CodeRunner.
 * @author mrm1st3r
 *
 */
public final class CodeRunner {

	public static final String APP_NAME = "Code Runner";
	public static final String VERSION = "0.3.1";
	public static final String AUTHOR = "Lukas Taake, Steffen Schiffel";
	public static final String YEAR = "2013";

	private static Logger log;

	private CodeRunner() { }

	/**
	 * 
	 * @param args no parameters available yet
	 */
	public static void main(String[] args) {
		new MainFrame();
		
		log = Logger.getLogger(CodeRunner.class.getName());
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
	 * load an image.
	 * @param path image location
	 * @param num number of single images (side by side)
	 * @return array of all images
	 */
	public static BufferedImage[] loadImages(String path, int num) {
		BufferedImage[] anim = new BufferedImage[num];
		BufferedImage src = null;

		try {
			src = ImageIO.read(new File(path));
		} catch (Exception e) {
			log.severe("Failed loading image '" + path + "'");
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
