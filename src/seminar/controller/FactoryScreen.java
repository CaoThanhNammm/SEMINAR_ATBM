package seminar.controller;

import javax.swing.JPanel;

import seminar.view.component.screen.asymmetric.DigitalSignatureScreen;
import seminar.view.component.screen.asymmetric.HashScreen;
import seminar.view.component.screen.asymmetric.RSAScreen;
import seminar.view.component.screen.basic.BasicScreen;
import seminar.view.component.screen.symmetric.SymmetricJavaScreen;
import seminar.view.component.screen.symmetric.SymmetricLibraryScreen;

public class FactoryScreen {

	public static JPanel createScreen(String text) {
		if (text.equals(""))
			return null;

		if (text.equals("basic")) {
			return new BasicScreen();
		} else if (text.equals("digitalSignature")) {
			return new DigitalSignatureScreen();
		} else if (text.equals("hash")) {
			return new HashScreen();
		} else if (text.equals("rsa")) {
			return new RSAScreen();
		} else if (text.equals("symmetricJava")) {
			return new SymmetricJavaScreen();
		} else if (text.equals("symmetricLib")) {
			return new SymmetricLibraryScreen();
		}

		return null;
	}
}
