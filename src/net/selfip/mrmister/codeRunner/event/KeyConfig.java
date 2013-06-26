package net.selfip.mrmister.codeRunner.event;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * manage the keyboard configuration.
 * @author mrm1st3r
 *
 */
public class KeyConfig {

	private Properties prop;
	private Logger log;
	
	/**
	 * create an empty key-configuration.
	 */
	public KeyConfig() {
		log = Logger.getLogger(getClass().getName());
	}

	/**
	 * create a key-configuration and load settings from a given file.
	 * @param file stored key-configuration
	 */
	public KeyConfig(String file) {
		loadFrom(file);
	}

	/**
	 * load a new configuration from a file.
	 * @param f file to load from
	 */
	public void loadFrom(String f) {
		File file = new File(f);
		if (!file.exists()) {
			throw new RuntimeException("the file '" + file.getAbsolutePath()
					+ "wasn't found!");
		}

		prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (Exception e) {
			log.info("failed loading KeyConfig: " + e.getMessage());
		}
	}
}
