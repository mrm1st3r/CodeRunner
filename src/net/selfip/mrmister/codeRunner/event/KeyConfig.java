package net.selfip.mrmister.codeRunner.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

import net.selfip.mrmister.codeRunner.CodeRunner;

/**
 * manage the keyboard configuration.
 * @author mrm1st3r
 *
 */
public class KeyConfig {

	private String configFile;
	private Properties prop;
	private Logger log;
	private Hashtable<String, String> defaults;
	
	/**
	 * create an empty key-configuration.
	 */
	public KeyConfig() {
		log = Logger.getLogger(getClass().getName());
		defaults = new Hashtable<String, String>();
	}

	/**
	 * create a key-configuration and load settings from a given file.
	 * @param file stored key-configuration
	 */
	public KeyConfig(String file) {
		this();
		configFile = file;
		loadFrom(file);
	}
	
	/**
	 * set a default value for a specific key.
	 * @param key key
	 * @param value default value
	 */
	public void setDefaultValue(String key, int value) {
		if (prop != null && !prop.contains(key)) {
			prop.setProperty(key, Integer.toString(value));
		} else {
			defaults.put(key, Integer.toString(value));
		}
	}
	
	/**
	 * get the specified key-value or if not found default value.
	 * @param key name of the key
	 * @return key-value
	 */
	public int get(String key) {
		if (defaults.containsKey(key)) {
			return Integer.parseInt(prop.getProperty(key, defaults.get(key)));
		} else {
			return Integer.parseInt(prop.getProperty(key));
		}
	}

	/**
	 * load a new configuration from a file.
	 * @param f file to load from
	 */
	public void loadFrom(String f) {
		File file = new File(f);

		if (!file.exists()) {
			try {
				file.createNewFile();
				log.info("created new KeyConfig at '" + f + "'");
			} catch (IOException e) {
				log.info("couldn't read from or create '" + f + "'");
				return;
			}
		}

		prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
			log.info("read KeyConfig from '" + f + "'");
		} catch (Exception e) {
			log.info("failed loading KeyConfig: " + e.getMessage());
		}
	}

	/**
	 * save the current key-configuration into
	 * the predefined configuration file.
	 */
	public void save() {
		save(configFile);
	}
	
	/**
	 * save the current key-configuration to
	 * a specific file.
	 * @param file configuration-file
	 */
	public void save(String file) {
		try {
			prop.store(new FileOutputStream(file),
					CodeRunner.getWindowTitle(true));
			log.info("saved KeyConfig to '" + file + "'");
		} catch (IOException e) {
			log.info("couldn't save KeyConfig to '" + file + "'");
		}
	}
}
