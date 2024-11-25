package seminar.controller.asymmetric;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import seminar.controller.Controller;
import seminar.controller.algo.asymmetric.AAsymmetric;
import seminar.controller.algo.asymmetric.DSA;
import seminar.model.Constant;
import seminar.model.FileTransfer;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class DigitalSignatureController extends Controller implements ActionListener {
	private AAsymmetric dsa;

	public DigitalSignatureController(Screen view, Model model, FileTransfer fileTransfer) {
		super(view, model, fileTransfer);
		dsa = new DSA();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(view.getBtnCreateKey())) {
			if (dsa.getPublicKey() == null || dsa.getPrivateKey() == null) {
				String sizeInput = JOptionPane.showInputDialog(null, "Kích thước(512, 1024, 2048, 3072)",
						"Chọn kích thước của khóa", JOptionPane.PLAIN_MESSAGE);
				if (sizeInput.isEmpty()) {
					Constant.jOptionPaneContentEmpty();
					return;
				}

				try {
					int size = Integer.parseInt(sizeInput);
					if (genKey(size) && savePublicKey() && savePrivateKey()) {
						view.getBtnEncrypt().setEnabled(true);
						view.getBtnDecrypt().setEnabled(true);
						view.getBtnEncryptFile().setEnabled(true);
						view.getBtnDecryptFile().setEnabled(true);
					}
				} catch (Exception e2) {
					Constant.jOptionPaneWrongFormatKey();
					return;
				}
			} else {
				int isReGenkey = JOptionPane.showConfirmDialog(null, "Đã có khóa, có muốn tạo lại", "Thông báo",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION);
				if (isReGenkey == JOptionPane.YES_OPTION) {
					String sizeInput = JOptionPane.showInputDialog(null, "Kích thước(512, 1024, 2048, 3072)",
							"Chọn kích thước của khóa", JOptionPane.PLAIN_MESSAGE);
					try {
						int size = Integer.parseInt(sizeInput);
						genKey(size);
						savePublicKey();
						savePrivateKey();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(view, "Sai định dạng");
						return;
					}
				}
			}
		} else if (src.equals(view.getBtnLoadKey())) {
			try {
				if (openFolderAndLoadPulicKey() && openFolderAndLoadPrivateKey()) {
					view.getBtnEncrypt().setEnabled(true);
					view.getBtnDecrypt().setEnabled(true);
					view.getBtnEncryptFile().setEnabled(true);
					view.getBtnDecryptFile().setEnabled(true);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (src.equals(view.getBtnEncrypt())) {
			String text = view.getTaEncrypt().getText().trim();
			if (text.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			}
			
			String sign = encryptText(text);
			model.setEncrypt(sign);
			model.setText(text);
			view.getTaDecrypt().setText(model.getEncrypt());
		} else if (src.equals(view.getBtnDecrypt())) {
			String sign = view.getTaEncrypt().getText().trim();
			if (sign.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			} 

			String isSign = verifySignatureText(model.getText(), sign) + "";
			view.getTaDecrypt().setText(isSign);

		} else if (src.equals(view.getBtnEncryptFile())) {
			String pathSrc = fileTransfer.getSrc();

			if (pathSrc == null) {
				Constant.jOptionPaneNotChooseFile();
				return;
			}

			String sign = signFile(pathSrc);
			model.setEncrypt(sign);

			view.getTaDecrypt().setText(model.getEncrypt());

		} else if (src.equals(view.getBtnDecryptFile())) {
			String fileSrc = fileTransfer.getSrc();

			if (fileSrc == null) {
				Constant.jOptionPaneNotChooseFile();
				return;
			}

			String signBase64 = view.getTaEncrypt().getText();
			model.setText(signBase64);

			view.getTaDecrypt().setText(verifySignatureFile(fileSrc, model.getText()));
		}
	}

	@Override
	protected String encryptText(String text) {
		return dsa.encrypt(text);
	}

	public String verifySignatureText(String data, String signature) {
		return dsa.verifySignature(data, signature);
	}

	public String signFile(String fileSrc) {
		return dsa.signFile(fileSrc);
	}

	public String verifySignatureFile(String fileSrc, String signature) {
		return dsa.verifySignatureFile(fileSrc, signature);
	}

	@Override
	protected boolean genKey(int keySize) {
		if (dsa.genKey(keySize)) {
			Constant.jOptionPaneCreateKeySuccess();
			return true;
		}
		Constant.jOptionPaneCreateKeyFailed();
		return false;
	}

	private boolean openFolderAndLoadPulicKey() throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn đường dẫn của khóa công khai");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "dat");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(view);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String fileSrc = fileToOpen.getAbsolutePath();

			if (dsa.loadPublicKey(fileSrc)) {
				Constant.jOptionPaneLoadKeySuccess();
				return true;
			} else {
				Constant.jOptionPaneLoadKeyFailed();
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	private boolean openFolderAndLoadPrivateKey() throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn đường dẫn của khóa riêng tư");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "dat");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(view);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String fileSrc = fileToOpen.getAbsolutePath();

			if (dsa.loadPrivateKey(fileSrc)) {
				Constant.jOptionPaneLoadKeySuccess();
				return true;
			} else {
				Constant.jOptionPaneLoadKeyFailed();
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	private boolean savePublicKey() {
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

			if (dsa.saveKey(folder, fileName, dsa.getPublicKey().getEncoded())) {
				Constant.jOptionPaneCreateKeySuccess();
				return true;
			} else {
				Constant.jOptionPaneCreateKeyFailed();
			}

		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	private boolean savePrivateKey() {
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		folderChooser.setDialogTitle("Chọn thư mục cần lưu khóa riêng tư");
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

			if (dsa.saveKey(folder, fileName, dsa.getPrivateKey().getEncoded())) {
				Constant.jOptionPaneCreateKeySuccess();
				return true;
			} else {
				Constant.jOptionPaneCreateKeyFailed();
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	@Override
	protected boolean saveKey(String folder, String fileName) {
		return true;
	}

	@Override
	protected boolean loadKey(String filePath) {
		return true;
	}

	@Override
	protected String decryptText(String text) {
		return "";
	}

	@Override
	protected boolean encryptFile(String src, String des) {
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
	protected boolean decryptFile(String src, String signatureBase64) {
		return true;
	}

}
