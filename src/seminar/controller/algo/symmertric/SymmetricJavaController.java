package seminar.controller.algo.symmertric;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import seminar.controller.Controller;
import seminar.model.Constant;
import seminar.model.ConstantAlgo;
import seminar.model.FileTransfer;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class SymmetricJavaController extends Controller implements ActionListener {
	private ASymmetric sysmetricAlgo;
	private String algoName;
	private String mode;
	private String padding;

	public SymmetricJavaController(Screen view, Model model, FileTransfer fileTransfer) {
		super(view, model, fileTransfer);
		algoName = (String) view.getCbAlgo().getSelectedItem();
		mode = (String) view.getCbMode().getSelectedItem();
		padding = (String) view.getCbPadding().getSelectedItem();

		view.getTitle().setText(Constant.titlePanelJava + ": " + algoName + "/" + mode + "/" + padding);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		algoName = (String) view.getCbAlgo().getSelectedItem();
		mode = (String) view.getCbMode().getSelectedItem();
		padding = (String) view.getCbPadding().getSelectedItem();
		view.getTitle().setText(Constant.titlePanelJava + ": " + algoName + "/" + mode + "/" + padding);

		if (src.equals(view.getBtnCreateKey())) {
			model.setAlgoName(algoName);
			model.setMode(mode);
			model.setPadding(padding);

			sysmetricAlgo = FactorySymetricController.createSymmetricAlgo(algoName, mode, padding);
			if (!sysmetricAlgo.createCipher()) {
				Constant.jOptionPaneNotProviderSupporting();
				return;
			}

			if (mode.equals(ConstantAlgo.ECB) || mode.equals(ConstantAlgo.EMPTY)) {
				if (openFolderAndSaveKey()) {
					view.getBtnEncrypt().setEnabled(true);
					view.getBtnDecrypt().setEnabled(true);
					view.getBtnEncryptFile().setEnabled(true);
					view.getBtnDecryptFile().setEnabled(true);
				}
			} else {
				if (openFolderAndSaveKey() && openFolderAndSaveIV()) {
					view.getBtnEncrypt().setEnabled(true);
					view.getBtnDecrypt().setEnabled(true);
					view.getBtnEncryptFile().setEnabled(true);
					view.getBtnDecryptFile().setEnabled(true);
				}
			}
		} else if (src.equals(view.getBtnLoadKey())) {
			model.setAlgoName(algoName);
			model.setMode(mode);
			model.setPadding(padding);

			sysmetricAlgo = FactorySymetricController.createSymmetricAlgo(algoName, mode, padding);

			if (mode.equals(ConstantAlgo.ECB) || mode.equals(ConstantAlgo.EMPTY)) {
				if (openFolderAndLoadKey()) {
					view.getBtnEncrypt().setEnabled(true);
					view.getBtnDecrypt().setEnabled(true);
					view.getBtnEncryptFile().setEnabled(true);
					view.getBtnDecryptFile().setEnabled(true);
				}
			} else {
				if (openFolderAndLoadKey() && openFolderAndLoadIV()) {
					view.getBtnEncrypt().setEnabled(true);
					view.getBtnDecrypt().setEnabled(true);
					view.getBtnEncryptFile().setEnabled(true);
					view.getBtnDecryptFile().setEnabled(true);
				}
			}
		} else if (src.equals(view.getBtnEncryptFile())) {
			String pathSrc = fileTransfer.getSrc();
			if (pathSrc == null) {
				Constant.jOptionPaneNotChooseFile();
				return;
			} else if (sysmetricAlgo == null) {
				Constant.jOptionPaneNotFoundKey();
				return;
			}

			String pathDes = fileTransfer.getDes() + "/encrypt"
					+ pathSrc.substring(pathSrc.lastIndexOf("."), pathSrc.length());
			fileTransfer.setDes(pathDes);

			if (mode.equals(ConstantAlgo.ECB) || mode.equals(ConstantAlgo.EMPTY)) {
				encryptFile(pathSrc, pathDes);
			} else {
				encryptFileWithIV(pathSrc, pathDes);
			}
			view.getTaDecrypt().setText("Đã mã hóa file: " + fileTransfer.getSrc());
		} else if (src.equals(view.getBtnDecryptFile())) {
			String pathSrc = fileTransfer.getSrc();
			if (pathSrc == null) {
				Constant.jOptionPaneNotChooseFile();
				return;
			} else if (sysmetricAlgo == null) {
				Constant.jOptionPaneNotFoundKey();
				return;
			}

			String pathDes = fileTransfer.getDes() + "/decrypt"
					+ pathSrc.substring(pathSrc.lastIndexOf("."), pathSrc.length());
			fileTransfer.setDes(pathDes);

			if (mode.equals(ConstantAlgo.ECB) || mode.equals(ConstantAlgo.EMPTY)) {
				decryptFile(pathSrc, pathDes);
			} else {

				decryptFileWithIV(pathSrc, pathDes);
			}

			view.getTaDecrypt().setText("Đã giải mã file: " + fileTransfer.getSrc());
		} else if (src.equals(view.getBtnEncrypt())) {
			String input = view.getTaEncrypt().getText().trim();
			if (input.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			} else if (sysmetricAlgo == null) {
				Constant.jOptionPaneNotFoundKey();
				return;
			}

			model.setText(input);
			String encrypt = "";
			if (mode.equals(ConstantAlgo.ECB) || mode.equals(ConstantAlgo.EMPTY)) {
				encrypt = encryptText(model.getText());
			} else {
				encrypt = encryptTextWithIV(model.getText());
			}

			model.setEncrypt(encrypt);
			view.getTaDecrypt().setText(model.getEncrypt());

		} else if (src.equals(view.getBtnDecrypt())) {
			String input = view.getTaEncrypt().getText().trim();
			if (input.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			} else if (sysmetricAlgo == null) {
				Constant.jOptionPaneNotFoundKey();
				return;
			}

			model.setText(input);
			String decrypt = "";
			if (mode.equals(ConstantAlgo.ECB) || mode.equals(ConstantAlgo.EMPTY)) {
				decrypt = decryptText(model.getText());
			} else {
				decrypt = decryptTextWithIV(model.getText());
			}

			if (decrypt != null) {
				model.setDecrypt(decrypt);
				view.getTaDecrypt().setText(model.getDecrypt());
			}
		}
	}

	@Override
	protected boolean encryptFile(String inputFile, String outputFile) {
		if (sysmetricAlgo.encryptFile(inputFile, outputFile)) {
			Constant.jOptionPaneEncryptFileSuccess();
			return true;
		}
		Constant.jOptionPaneEncryptFileFailed();
		return false;
	}

	@Override
	protected boolean decryptFile(String inputFile, String outputFile) {
		if (sysmetricAlgo.decryptFile(inputFile, outputFile)) {
			Constant.jOptionPaneDecryptFileSuccess();
			return true;
		}
		Constant.jOptionPaneDecryptFileFailed();
		return false;
	}

	private boolean encryptFileWithIV(String inputFile, String outputFile) {
		if (sysmetricAlgo.encryptFileWithIV(inputFile, outputFile)) {
			Constant.jOptionPaneEncryptFileSuccess();
			return true;
		}
		Constant.jOptionPaneEncryptFileFailed();
		return false;
	}

	private boolean decryptFileWithIV(String inputFile, String outputFile) {
		if (sysmetricAlgo.decryptFileWithIV(inputFile, outputFile)) {
			Constant.jOptionPaneDecryptFileSuccess();
			return true;
		}
		Constant.jOptionPaneDecryptFileFailed();
		return false;
	}

	@Override
	protected String encryptText(String text) {
		return sysmetricAlgo.encrypt(text);
	}

	@Override
	protected String decryptText(String text) {
		try {
			return sysmetricAlgo.decrypt(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Constant.jOptionPaneContentCannotDecrypt();
		return null;
	}

	private String encryptTextWithIV(String text) {
		try {
			return sysmetricAlgo.encryptWithIV(text);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Constant.jOptionPaneContentCannotDecrypt();
		return null;
	}

	private String decryptTextWithIV(String text) {
		return sysmetricAlgo.decryptWithIV(text);
	}

	@Override
	protected boolean genKey(int size) {
		if (sysmetricAlgo.genKey(size)) {
			Constant.jOptionPaneCreateKeySuccess();
			return true;
		}
		Constant.jOptionPaneCreateKeyFailed();
		return false;
	}

	private boolean genIV(int size) {
		if (sysmetricAlgo.genIV(size)) {
			Constant.jOptionPaneCreateKeySuccess();
			return true;
		}
		Constant.jOptionPaneCreateKeyFailed();
		return false;
	}

	@Override
	protected boolean loadKey(String fileSrc) {
		if (sysmetricAlgo.loadKey(fileSrc)) {
			Constant.jOptionPaneLoadKeySuccess();
			return true;
		}
		Constant.jOptionPaneLoadKeyFailed();
		return false;
	}

	protected boolean loadIV(String fileSrc) {
		if (sysmetricAlgo.loadIV(fileSrc)) {
			Constant.jOptionPaneLoadKeySuccess();
			return true;
		}
		Constant.jOptionPaneLoadKeyFailed();
		return false;
	}

	@Override
	protected boolean openFolderAndSaveKey() {
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		folderChooser.setDialogTitle("Chọn thư mục cần lưu khóa");
		int result = folderChooser.showOpenDialog(view);

		if (result == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = folderChooser.getSelectedFile();
			String folder = fileToOpen.getAbsolutePath();
			String fileName = JOptionPane.showInputDialog(null, "Nhập tên file:", "Tên file",
					JOptionPane.PLAIN_MESSAGE);

			if (fileName != null && !fileName.trim().isEmpty()) {
				int size = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập kích thước khóa:", "Kích thước",
						JOptionPane.PLAIN_MESSAGE));

				if (!genKey(size)) {
					return false;
				}
				if (!saveKey(folder, fileName)) {
					return false;
				}

				return true;
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	private boolean openFolderAndSaveIV() {
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		folderChooser.setDialogTitle("Chọn thư mục cần lưu IV");
		int result = folderChooser.showOpenDialog(view);

		if (result == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = folderChooser.getSelectedFile();
			String folder = fileToOpen.getAbsolutePath();

			String fileNameIV = JOptionPane.showInputDialog(null, "Nhập tên file IV:", "Tên file",
					JOptionPane.PLAIN_MESSAGE);

			if ((fileNameIV != null && !fileNameIV.trim().isEmpty())) {

				int sizeIV = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập kích thước IV:", "Kích thước",
						JOptionPane.PLAIN_MESSAGE));

				if (!genIV(sizeIV)) {
					return false;
				}

				if (!saveIV(folder, fileNameIV)) {
					return false;
				}

				return true;
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	private boolean saveIV(String folder, String fileNameIV) {
		if (sysmetricAlgo.saveKey(folder, fileNameIV)) {
			Constant.jOptionPaneSaveKeySuccess();
			return true;
		}
		Constant.jOptionPaneSaveKeyFailed();
		return false;
	}

	@Override
	protected boolean saveKey(String folder, String fileName) {
		if (sysmetricAlgo.saveKey(folder, fileName)) {
			Constant.jOptionPaneSaveKeySuccess();
			return true;
		}
		Constant.jOptionPaneSaveKeyFailed();
		return false;
	}

	@Override
	protected boolean openFolderAndLoadKey() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn đường dẫn của khóa");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "dat");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(view);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String fileSrc = fileToOpen.getAbsolutePath();

			if (!loadKey(fileSrc)) {
				return false;
			}

			return true;

		} else {
			Constant.jOptionPaneNotChooseFile();
		}
		return false;
	}

	private boolean openFolderAndLoadIV() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn đường dẫn của IV");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "dat");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(view);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String fileSrc = fileToOpen.getAbsolutePath();

			if (!loadIV(fileSrc)) {
				return false;
			}

			return true;

		} else {
			Constant.jOptionPaneNotChooseFile();
		}

		return false;
	}

}
