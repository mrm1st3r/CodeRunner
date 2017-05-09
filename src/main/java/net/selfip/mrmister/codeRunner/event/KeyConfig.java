package net.selfip.mrmister.codeRunner.event;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

import net.selfip.mrmister.codeRunner.ApplicationInfo;
import net.selfip.mrmister.codeRunner.lang.I18n;
import net.selfip.mrmister.codeRunner.lang.Translatable;

/**
 * manage the keyboard configuration.
 *
 */
public class KeyConfig implements Translatable {

	private static final int LIST_COLS = 3;

	private String configFile;
	private ApplicationInfo applicationInfo;
	private Properties prop;
	private Logger log;
	private Hashtable<String, String> defaults;

	/**
	 * create a key-configuration and load settings from a given file.
	 * @param file stored key-configuration
	 * @param applicationInfo
	 */
	public KeyConfig(String file, ApplicationInfo applicationInfo) {
		log = Logger.getLogger(getClass().getName());
		defaults = new Hashtable<>();
		configFile = file;
		this.applicationInfo = applicationInfo;
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
		if (defaults.containsKey(key)) {
			return Integer.parseInt(prop.getProperty(key, defaults.get(key)));
		} else {
			return Integer.parseInt(prop.getProperty(key));
		}
	}

	/**
	 * build a list of all keys, values and default-values.
	 * @return String-array with all keys and values
	 */
	public String[][] list() {
		String[] s = prop.keySet().toArray(new String[0]);
		String[][] ret = new String[s.length][LIST_COLS];

		for (int i = 0; i < s.length; i++) {
			ret[i][0] = t(s[i]);
			ret[i][1] = KeyEvent.getKeyText(get(s[i]));
			ret[i][2] = KeyEvent.getKeyText(getDefaultValue(s[i]));
		}

		return ret;
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
			prop.store(new FileOutputStream(file), applicationInfo.getSignature());
			log.info("saved KeyConfig to '" + file + "'");
		} catch (IOException e) {
			log.info("couldn't save KeyConfig to '" + file + "'");
		}
	}

	@Override
	public String t(String t) {
		return I18n.getTranslationFor(t);
	}
}
