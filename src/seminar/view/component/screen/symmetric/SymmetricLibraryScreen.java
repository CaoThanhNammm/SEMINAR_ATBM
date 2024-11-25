package seminar.view.component.screen.symmetric;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import seminar.controller.algo.librarySysmmetric.SymmetricLibraryController;
import seminar.model.Constant;
import seminar.model.ConstantAlgo;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class SymmetricLibraryScreen extends Screen {
	private static final long serialVersionUID = 1L;

	public SymmetricLibraryScreen() {
		super(Constant.titlePanelLib);

		String[] items1 = { ConstantAlgo.CHACHA20, ConstantAlgo.SALSA20, ConstantAlgo.HC256, ConstantAlgo.HC128 };
		cbAlgo = new JComboBox<String>(items1);
		panelOperation.add(cbAlgo);

		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);
		btnEncryptFile.setEnabled(false);
		btnDecryptFile.setEnabled(false);

		addActionListener();
	}

	@Override
	protected void addActionListener() {
		ActionListener event = new SymmetricLibraryController(this, new Model(), fileTransfer);

		btnEncrypt.addActionListener(event);
		btnDecrypt.addActionListener(event);
		btnEncryptFile.addActionListener(event);
		btnDecryptFile.addActionListener(event);
		btnCreateKey.addActionListener(event);
		btnLoadKey.addActionListener(event);
		cbAlgo.addActionListener(event);
	}

}
