package net.selfip.mrmister.coderunner.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Keyboard configuration file.
 */
public class KeyConfig {

	private static final Logger LOG = LoggerFactory.getLogger(KeyConfig.class);

	private static final int DEFAULT_KEY_START = KeyEvent.VK_ENTER;
	private static final int DEFAULT_KEY_PAUSE = 'p';
	private static final int DEFAULT_KEY_LEFT = KeyEvent.VK_LEFT;
	private static final int DEFAULT_KEY_RIGHT = KeyEvent.VK_RIGHT;
	private static final int DEFAULT_KEY_JUMP = KeyEvent.VK_SPACE;
	private static final int DEFAULT_KEY_TOGGLE_DEV = KeyEvent.VK_F11;

	private final String configFile;
	private final Hashtable<String, String> defaults = new Hashtable<>();
	private final String applicationSignature;
	private final Properties prop = new Properties();

	/**
	 * create a key-configuration and load settings from a given file.
	 * @param configFile configuration file name
	 * @param applicationSignature The application signature to save as comment
	 */
	public KeyConfig(String configFile, String applicationSignature) {
		this.applicationSignature = applicationSignature;
		this.configFile = configFile;
		loadFile(configFile);
		loadDefaults();
	}

	private void loadFile(String f) {
		File file = new File(f);
		if (file.exists()) {
			try {
				prop.load(new FileInputStream(file));
				LOG.info("Read KeyConfig from: " + f);
			} catch (Exception e) {
				LOG.warn("Failed to load KeyConfig", e);
			}
		}
	}

	private void loadDefaults() {
		setDefaultValue("start", DEFAULT_KEY_START);
		setDefaultValue("pause", DEFAULT_KEY_PAUSE);
		setDefaultValue("move_left", DEFAULT_KEY_LEFT);
		setDefaultValue("move_right", DEFAULT_KEY_RIGHT);
		setDefaultValue("jump", DEFAULT_KEY_JUMP);
		setDefaultValue("dev_mode", DEFAULT_KEY_TOGGLE_DEV);
	}

	private void setDefaultValue(String key, int value) {
		if (!prop.contains(key)) {
			prop.setProperty(key, Integer.toString(value));
		}
		defaults.put(key, Integer.toString(value));
	}

	/**
	 * get the default value for a specific key.
	 * @param key key
	 * @return default value
	 */
	public int getDefaultValue(String key) {
		return Integer.parseInt(defaults.get(key));
	}

	/**
	 * get the specified key-value or if not found default value.
	 * @param key name of the key
	 * @return key-value
	 */
	public int get(String key) {
		return Integer.parseInt(prop.getProperty(key, defaults.get(key)));
	}

	public Enumeration<Object> keys() {
		return prop.keys();
	}

	public int keyCount() {
		return defaults.size();
	}

	/**
	 * save the current key-configuration into
	 * the predefined configuration file.
	 */
	public void save() {
		try {
			prop.store(new FileOutputStream(configFile), getFileComment());
			LOG.info("Saved configuration to: " + configFile);
		} catch (IOException e) {
			LOG.warn("Failed to save configuration", e);
		}
	}

	private String getFileComment() {
		return "Generated by: " + applicationSignature;
	}
}
