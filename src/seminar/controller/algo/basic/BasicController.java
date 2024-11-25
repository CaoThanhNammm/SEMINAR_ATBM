package seminar.controller.algo.basic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import seminar.controller.Controller;
import seminar.model.Constant;
import seminar.model.ConstantAlgo;
import seminar.model.FileTransfer;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class BasicController extends Controller implements ActionListener {
	private ABasicAlgo basicAlgo;
	private String selectedItem;

	public BasicController(Screen view, Model model, FileTransfer fileTransfer) {
		super(view, model, fileTransfer);
		selectedItem = (String) view.getCbAlgo().getSelectedItem();
		view.getTitle().setText(Constant.titlePanelBasic + ": " + selectedItem);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		selectedItem = (String) view.getCbAlgo().getSelectedItem();
		view.getTitle().setText(Constant.titlePanelBasic + ": " + selectedItem);

		if (src.equals(view.getBtnCreateKey())) {
			basicAlgo = FactoryBasicAlgo.createBasicAlgo(selectedItem);

			Object[] keys = new Object[2];
			if (basicAlgo instanceof Affine) {
				JOptionPane.showMessageDialog(view,
						"Hãy chọn a và b sao cho a và b là 2 số nguyên tố cùng nhau(mặc định a = 5, b = 8)", "Lưu ý",
						JOptionPane.PLAIN_MESSAGE);

				Object a = JOptionPane.showInputDialog(view, "Chọn key", "5");
				Object b = JOptionPane.showInputDialog(view, "Chọn key", "8");
				keys[0] = a;
				keys[1] = b;

			} else {
				Object a = JOptionPane.showInputDialog(view, "Chọn key");
				keys[0] = a;
			}

			if (basicAlgo.setKey(keys)) {
				Constant.jOptionPaneCreateKeySuccess();
				view.getBtnEncrypt().setEnabled(true);
				view.getBtnDecrypt().setEnabled(true);
			} else {
				Constant.jOptionPaneCreateKeyFailed();
			}
		} else if (src.equals(view.getBtnEncrypt())) {
			String text = view.getTaEncrypt().getText();
			if (text.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			}

			model.setText(text);
			basicAlgo.isVietnamese(model.getText());
			String encrypt = basicAlgo.encrypt(model.getText());
			model.setEncrypt(encrypt);

			view.getTaDecrypt().setText(model.getEncrypt());
		} else if (src.equals(view.getBtnDecrypt())) {
			String text = view.getTaEncrypt().getText().trim();
			if (text.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			}

			model.setText(text);
			String decrypt = basicAlgo.decrypt(text);
			model.setDecrypt(decrypt);
			view.getTaDecrypt().setText(model.getDecrypt());
		}
	}

	@Override
	protected String encryptText(String text) {
		return null;
	}

	@Override
	protected String decryptText(String text) {
		return null;
	}

	@Override
	protected boolean encryptFile(String src, String des) {
		return true;
	}

	@Override
	protected boolean decryptFile(String src, String des) {
		return true;
	}

	@Override
	protected boolean openFolderAndSaveKey() {
		return true;
	}

	@Override
	protected boolean openFolderAndLoadKey() {
		return true;
	}

	@Override
	protected boolean genKey(int size) {
		return true;
	}

	@Override
	protected boolean saveKey(String folder, String fileSrc) {
		return true;
	}

	@Override
	protected boolean loadKey(String filePath) {
		return true;
	}
}
