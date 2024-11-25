package seminar.view.component.screen.asymmetric;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import seminar.controller.algo.hash.HashController;
import seminar.controller.asymmetric.RSAController;
import seminar.model.Constant;
import seminar.model.ConstantAlgo;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class HashScreen extends Screen {
	private static final long serialVersionUID = 1L;

	public HashScreen() {
		super(Constant.titlePanelHash);
		String[] items1 = { ConstantAlgo.MD5, ConstantAlgo.SHA1, ConstantAlgo.SHA256, ConstantAlgo.SHA512,
				ConstantAlgo.SH3_256, ConstantAlgo.SH3_512 };
		cbAlgo = new JComboBox<String>(items1);
		panelOperation.add(cbAlgo);

		btnEncrypt.setText("Băm chữ");
		btnEncryptFile.setText("Băm file");

		btnEncrypt.setEnabled(false);
		btnEncryptFile.setEnabled(false);
		
		btnDecrypt.setVisible(false);
		btnDecryptFile.setVisible(false);
		btnLoadKey.setVisible(false);

		addActionListener();
	}

	@Override
	protected void addActionListener() {
		ActionListener event = new HashController(this, new Model(), fileTransfer);
		btnEncrypt.addActionListener(event);
		btnDecrypt.addActionListener(event);
		btnEncryptFile.addActionListener(event);
		btnDecryptFile.addActionListener(event);
		btnCreateKey.addActionListener(event);
		btnLoadKey.addActionListener(event);
		cbAlgo.addActionListener(event);
	}

}
