package seminar.view.component.screen.asymmetric;

import java.awt.event.ActionListener;

import seminar.controller.algo.hash.HashController;
import seminar.controller.asymmetric.DigitalSignatureController;
import seminar.model.Constant;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class DigitalSignatureScreen extends Screen {
	private static final long serialVersionUID = 1L;

	public DigitalSignatureScreen() {
		super(Constant.titlePanelDigitalSignature);
		btnEncrypt.setText("Ký chữ");
		btnEncryptFile.setText("Ký file");

		btnDecrypt.setText("Xác minh chữ ký chữ");
		btnDecryptFile.setText("Xác minh chữ ký file");

		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);
		btnEncryptFile.setEnabled(false);
		btnDecryptFile.setEnabled(false);

		addActionListener();
	}

	@Override
	protected void addActionListener() {
		ActionListener event = new DigitalSignatureController(this, new Model(), fileTransfer);
		btnEncrypt.addActionListener(event);
		btnDecrypt.addActionListener(event);
		btnEncryptFile.addActionListener(event);
		btnDecryptFile.addActionListener(event);
		btnCreateKey.addActionListener(event);
		btnLoadKey.addActionListener(event);
	}

}
