package net.selfip.mrmister.codeRunner.entities;

/**
 * thrown if a entity can't be initialized.
 *
 */
public class InitialisationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param msg error message
	 */
	public InitialisationException(String msg) {
		super(msg);
	}
	
}
