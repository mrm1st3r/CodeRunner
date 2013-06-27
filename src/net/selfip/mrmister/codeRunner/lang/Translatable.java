package net.selfip.mrmister.codeRunner.lang;

/**
 * 
 * @author mrm1st3r
 *
 */
public interface Translatable {

	/**
	 * translate a text into the current I18n language.
	 * @param t text to translate
	 * @return translated text
	 */
	String t(String t);
}
