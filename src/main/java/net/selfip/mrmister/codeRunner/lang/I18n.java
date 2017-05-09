package net.selfip.mrmister.codeRunner.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 * 
 *
 */
public final class I18n {

	private static final String DEFAULT_PATH = "/lang";
	private static final String FILE_EXT = ".lng";
	private static final String DELIMITER = "=";

	private static String lang;
	private static String langFilePath = DEFAULT_PATH;
	private static String langFile;
	private static Hashtable<String, String> translations;

	private I18n() { }

	/**
	 * initialize with a specified language.
	 * @param l new language
	 */
	public static void init(String l) {
		if (lang != null) {
			throw new LanguageException("I18n has already been initialized!");
		}

		lang = l;
		langFile = langFilePath + "/" + l + FILE_EXT;


		File f = new File(I18n.class.getResource(langFile).getFile());
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				throw new LanguageException("couldn't create language-file '"
						+ langFile + "': " + e.getMessage());
			}
		}

		translations = new Hashtable<>();
		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(f));

			String line = in.readLine();
			while (line != null) {
				String[] t = line.split(DELIMITER);
				translations.put(t[0], t[1]);

				line = in.readLine();
			}

		} catch (Exception e) {
			lang = null;
		} finally {
			try { in.close(); } catch (IOException e) { }
		}
	}

	/**
	 * get the translation for a text.
	 * @param s source text
	 * @return translated text
	 */
	public static String getTranslationFor(String s) {
		if (lang == null) {
			throw new LanguageException("I18n must be initialized before use!");
		}
		if (translations.containsKey(s)) {
			return translations.get(s);
		}
		return s;
	}
}
