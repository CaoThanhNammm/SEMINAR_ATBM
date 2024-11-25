package seminar.view.component.screen.basic;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;

import seminar.controller.OptimizeInputController;
import seminar.controller.algo.basic.BasicController;
import seminar.model.Constant;
import seminar.model.ConstantAlgo;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class BasicScreen extends Screen {
	private static final long serialVersionUID = 1L;

	public BasicScreen() {
		super(Constant.titlePanelBasic);
		btnLoadKey.setVisible(false);
		btnEncryptFile.setVisible(false);
		btnDecryptFile.setVisible(false);

		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);

		String[] items1 = { ConstantAlgo.AFFINE, ConstantAlgo.CAESAR, ConstantAlgo.VIGENERE };
		cbAlgo = new JComboBox<String>(items1);
		panelOperation.add(cbAlgo);

		addActionListener();
	}

	@Override
	protected void addActionListener() {
		ActionListener event = new BasicController(this, new Model(), fileTransfer);
		btnEncrypt.addActionListener(event);
		btnDecrypt.addActionListener(event);
		btnCreateKey.addActionListener(event);
		cbAlgo.addActionListener(event);
	}
}
