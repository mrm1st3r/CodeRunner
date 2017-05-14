package net.selfip.mrmister.codeRunner.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * Main class for Internationalization.
 */
public final class I18n {

	private static final String FILE_PATH = "/lang/";
	private static final String FILE_SUFFIX = ".lng";
	private static final String DELIMITER = "=";

	private final Hashtable<String, String> translations = new Hashtable<>();

	/**
	 * Create a new Internationalization.
	 * @param lang language to use
	 */
	public I18n(String lang) {
		InputStream input = I18n.class.getResourceAsStream(FILE_PATH + lang + FILE_SUFFIX);
		try(BufferedReader in = new BufferedReader(new InputStreamReader(input))) {
			readTranslations(in);
		} catch (IOException e) {
			throw new LanguageException("Could not read translations", e);
		}
	}

	private void readTranslations(BufferedReader in) {
		in.lines().forEach(line -> {
            String[] t = line.split(DELIMITER);
            translations.put(t[0], t[1]);
        });
	}

	public String t(String source) {
		if (translations.containsKey(source)) {
			return translations.get(source);
		}
		return source;
	}
}
