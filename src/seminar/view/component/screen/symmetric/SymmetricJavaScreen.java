package seminar.view.component.screen.symmetric;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import seminar.controller.algo.symmertric.SymmetricJavaController;
import seminar.model.Constant;
import seminar.model.ConstantAlgo;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class SymmetricJavaScreen extends Screen {
	private static final long serialVersionUID = 1L;

	public SymmetricJavaScreen() {
		super(Constant.titlePanelJava);
		String[] items1 = { ConstantAlgo.AES, ConstantAlgo.DES, ConstantAlgo.TRIPLE_DES, ConstantAlgo.BLOW_FISH,
				ConstantAlgo.RC2, ConstantAlgo.RC4 };
		cbAlgo = new JComboBox<String>(items1);
		panelOperation.add(cbAlgo);
		
		String[] items2 = { ConstantAlgo.EMPTY, ConstantAlgo.ECB, ConstantAlgo.CBC, ConstantAlgo.PCBC, ConstantAlgo.OFB,
				ConstantAlgo.CFB, ConstantAlgo.CTR };
		cbMode = new JComboBox<String>(items2);
		panelOperation.add(cbMode);

		String[] items3 = { ConstantAlgo.EMPTY, ConstantAlgo.NOPADDING, ConstantAlgo.PKCS1PADDING,
				ConstantAlgo.PKCS5PADDING, ConstantAlgo.PKCS7PADDING, ConstantAlgo.SSL3PADDING };
		cbPadding = new JComboBox<String>(items3);
		panelOperation.add(cbPadding);

		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);
		btnEncryptFile.setEnabled(false);
		btnDecryptFile.setEnabled(false);

		addActionListener();
	}

	@Override
	protected void addActionListener() {
		ActionListener event = new SymmetricJavaController(this, new Model(), fileTransfer);
		
		btnEncrypt.addActionListener(event);
		btnDecrypt.addActionListener(event);
		btnEncryptFile.addActionListener(event);
		btnDecryptFile.addActionListener(event);
		btnCreateKey.addActionListener(event);
		btnLoadKey.addActionListener(event);
		cbAlgo.addActionListener(event);
		cbMode.addActionListener(event);
		cbPadding.addActionListener(event);
	}
}
