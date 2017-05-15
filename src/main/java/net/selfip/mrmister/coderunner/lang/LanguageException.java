package net.selfip.mrmister.coderunner.lang;

/**
 * Exception thrown by I18n classes.
 */
class LanguageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LanguageException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
