package net.selfip.mrmister.coderunner;

import net.selfip.mrmister.coderunner.event.KeyConfig;
import net.selfip.mrmister.coderunner.frame.MainFrame;
import net.selfip.mrmister.coderunner.game.GameLoop;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.ApplicationInfo;

/**
 * Launcher for CodeRunner.
 */
public final class CodeRunner {

	private static final String LANG = "de";
	private static final String KEY_CONFIGURATION_FILE = "keyboard.ini";

	public static void main(String[] args) {
		new CodeRunner();
	}

	private CodeRunner() {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		I18n i18n = new I18n(LANG);
		KeyConfig keyConfig = new KeyConfig(KEY_CONFIGURATION_FILE, applicationInfo.getSignature());
		new MainFrame(
				applicationInfo,
				i18n,
				keyConfig,
                new GameLoop(i18n, keyConfig));
	}
}
