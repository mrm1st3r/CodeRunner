package net.selfip.mrmister.coderunner.lang;

import com.google.common.collect.ImmutableMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Main class for Internationalization.
 */
public final class I18n {

	private static final String FILE_PATH = "/lang/";
	private static final String FILE_SUFFIX = ".lng";
	private static final String DELIMITER = "=";

	private final Map<String, String> translations;

	/**
	 * Create a new Internationalization.
	 * @param lang language to use
	 */
	public I18n(String lang) {
		InputStream input = I18n.class.getResourceAsStream(FILE_PATH + lang + FILE_SUFFIX);
		try(BufferedReader in = new BufferedReader(new InputStreamReader(input))) {
			translations = readTranslations(in);
		} catch (IOException e) {
			throw new LanguageException("Could not read translations", e);
		}
	}

	private Map<String, String> readTranslations(BufferedReader in) {
		ImmutableMap.Builder<String,String> builder = ImmutableMap.builder();
		in.lines().forEach(line -> {
            String[] t = line.split(DELIMITER);
            builder.put(t[0], t[1]);
        });
		return builder.build();
	}

	public String t(String source) {
		if (translations.containsKey(source)) {
			return translations.get(source);
		}
		return source;
	}
}
