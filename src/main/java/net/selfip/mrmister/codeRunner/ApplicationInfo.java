package net.selfip.mrmister.codeRunner;

/**
 * General information about the application.
 */
public class ApplicationInfo {

    private static final String APP_NAME = "Code Runner";
    private static final String VERSION = "0.3.4";
    public static final String CODE_AUTHOR = "Lukas Taake, Steffen Schiffel";
    public static final String GRAPHICS_AUTHOR = "Lukas Taake";
    public static final String YEAR = "2013";

    public String getName() {
        return APP_NAME;
    }

    private String getVersion() {
        return VERSION;
    }

    public String getSignature() {
        return String.format("%s %s", getName(), getVersion());
    }
}
