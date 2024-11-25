package seminar.controller.asymmetric;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import seminar.controller.Controller;
import seminar.controller.algo.asymmetric.AAsymmetric;
import seminar.controller.algo.asymmetric.RSA;
import seminar.model.Constant;
import seminar.model.FileTransfer;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class RSAController extends Controller implements ActionListener {
	private AAsymmetric rsa;
	private int size;

	public RSAController(Screen view, Model model, FileTransfer fileTransfer) {
		super(view, model, fileTransfer);
		rsa = new RSA();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(view.getBtnCreateKey())) {
			String sizeInput = JOptionPane.showInputDialog(null, "Kích thước(1024, 2048, 3072, 4096)",
					"Chọn kích thước của khóa", JOptionPane.PLAIN_MESSAGE);
			if (sizeInput.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			}
			try {
				size = Integer.parseInt(sizeInput);
				if (!genKey(size) || !openFolderAndSavePublicKey() || !openFolderAndSavePrivateKey()) {
					return;
				}

				view.getBtnEncrypt().setEnabled(true);
				view.getBtnDecrypt().setEnabled(true);
				view.getBtnEncryptFile().setEnabled(true);
				view.getBtnDecryptFile().setEnabled(true);
			} catch (Exception e1) {
				Constant.jOptionPaneWrongFormatKey();
				e1.printStackTrace();
			}
		} else if (src.equals(view.getBtnLoadKey())) {
			try {
				if (!openFolderAndLoadPulicKey() || !openFolderAndLoadPrivateKey()) {
					return;
				}

				view.getBtnEncrypt().setEnabled(true);
				view.getBtnDecrypt().setEnabled(true);
				view.getBtnEncryptFile().setEnabled(true);
				view.getBtnDecryptFile().setEnabled(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (src.equals(view.getBtnEncrypt())) {
			String text = view.getTaEncrypt().getText().trim();

			byte[] lenText = text.getBytes(StandardCharsets.UTF_8);
			int keySizeBytes = ((java.security.interfaces.RSAKey) rsa.getPublicKey()).getModulus().bitLength() / 8;
			keySizeBytes -= 11;

			if (text.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			} else if (lenText.length > keySizeBytes) {
				Constant.jOptionPaneContentOverByte(lenText.length, keySizeBytes);
				return;
			}

			model.setText(text);
			String encrypt = encryptText(model.getText());
			model.setEncrypt(encrypt);
			view.getTaDecrypt().setText(model.getEncrypt());

		} else if (src.equals(view.getBtnDecrypt())) {
			String text = view.getTaEncrypt().getText().trim();
			if (text.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			}

			model.setText(text);

			String decrypt = decryptText(model.getText());
			model.setDecrypt(decrypt);
			view.getTaDecrypt().setText(model.getDecrypt());
		}
	}

	@Override
	protected String encryptText(String text) {
		return rsa.encrypt(text);
	}

	@Override
	protected String decryptText(String text) {
		return rsa.decrypt(text);
	}

	@Override
	protected boolean encryptFile(String src, String des) {
		return true;
	}

	@Override
	protected boolean decryptFile(String src, String des) {
		return true;
	}

	private boolean openFolderAndSavePublicKey() {
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.setDialogTitle("Chọn thư mục cần lưu khoá công khai");
		int result = folderChooser.showOpenDialog(view);

		if (result == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = folderChooser.getSelectedFile();
			String folder = fileToOpen.getAbsolutePath();
			String fileName = JOptionPane.showInputDialog(null, "Nhập tên file:", "Input File Name",
					JOptionPane.PLAIN_MESSAGE);

			if (fileName == null || fileName.trim().isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return false;
			}

			if (savePublicKey(folder, fileName)) {
				return true;
			}

		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	private boolean openFolderAndSavePrivateKey() {
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.setDialogTitle("Chọn thư mục cần lưu khoá riêng tư");
		int result = folderChooser.showOpenDialog(view);

		if (result == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = folderChooser.getSelectedFile();
			String folder = fileToOpen.getAbsolutePath();
			String fileName = JOptionPane.showInputDialog(null, "Nhập tên file:", "Input File Name",
					JOptionPane.PLAIN_MESSAGE);

			if (fileName == null || fileName.trim().isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return false;
			}

			if (savePrivateKey(folder, fileName)) {
				return true;
			}

		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	private boolean openFolderAndLoadPulicKey() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn đường dẫn của khóa công khai");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "dat");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(view);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String fileSrc = fileToOpen.getAbsolutePath();

			if (loadPublicKey(fileSrc)) {
				return true;
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	private boolean openFolderAndLoadPrivateKey() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn đường dẫn của khóa công khai");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "dat");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(view);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String fileSrc = fileToOpen.getAbsolutePath();

			if (loadPrivateKey(fileSrc)) {
				return true;
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	@Override
	protected boolean genKey(int size) {
		if (rsa.genKey(size)) {
			Constant.jOptionPaneCreateKeySuccess();
			return true;
		}
		Constant.jOptionPaneCreateKeyFailed();
		return false;
	}

	private boolean loadPublicKey(String fileSrc) {
		if (rsa.loadPublicKey(fileSrc)) {
			Constant.jOptionPaneLoadKeySuccess();
			return true;
		}
		Constant.jOptionPaneLoadKeyFailed();
		return false;
	}

	private boolean loadPrivateKey(String fileSrc) {
		if (rsa.loadPrivateKey(fileSrc)) {
			Constant.jOptionPaneLoadKeySuccess();
			return true;
		}
		Constant.jOptionPaneLoadKeyFailed();
		return false;
	}

	private boolean savePublicKey(String folder, String fileName) {
		if (rsa.saveKey(folder, fileName, rsa.getPublicKey().getEncoded())) {
			Constant.jOptionPaneSaveKeySuccess();
			return true;
		}
		Constant.jOptionPaneSaveKeyFailed();
		return false;
	}

	private boolean savePrivateKey(String folder, String fileName) {
		if (rsa.saveKey(folder, fileName, rsa.getPrivateKey().getEncoded())) {
			Constant.jOptionPaneSaveKeySuccess();
			return true;
		}
		Constant.jOptionPaneSaveKeyFailed();
		return false;
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
	protected boolean saveKey(String folder, String fileName) {
		return true;
	}

	@Override
	protected boolean loadKey(String filePath) {
		return true;
	}

}
