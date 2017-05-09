package net.selfip.mrmister.codeRunner.lang;

/**
 * Exception thrown by I18n classes.
 *
 */
public class LanguageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * create new Exception with message.
	 * @param msg message-text
	 */
	public LanguageException(String msg) {
		super(msg);
	}
}
