package seminar.view.component.screen.asymmetric;

import java.awt.event.ActionListener;

import seminar.controller.algo.hash.HashController;
import seminar.controller.asymmetric.RSAController;
import seminar.model.Constant;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class RSAScreen extends Screen {
	public RSAScreen() {
		super(Constant.titlePanelRsa);
		btnEncryptFile.setVisible(false);
		btnDecryptFile.setVisible(false);

		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);
		
		btnEncryptFile.setVisible(false);
		btnDecryptFile.setVisible(false);

		addActionListener();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void addActionListener() {
		ActionListener event = new RSAController(this, new Model(), fileTransfer);
		btnEncrypt.addActionListener(event);
		btnDecrypt.addActionListener(event);
		btnEncryptFile.addActionListener(event);
		btnDecryptFile.addActionListener(event);
		btnCreateKey.addActionListener(event);
		btnLoadKey.addActionListener(event);
	}

}
