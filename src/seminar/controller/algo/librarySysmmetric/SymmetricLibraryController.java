package seminar.controller.algo.librarySysmmetric;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import seminar.controller.Controller;
import seminar.model.Constant;
import seminar.model.ConstantAlgo;
import seminar.model.FileTransfer;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class SymmetricLibraryController extends Controller implements ActionListener {
	private String algoName;
	private ASymmetricLibrary sysmetricLibraryAlgo;

	public SymmetricLibraryController(Screen view, Model model, FileTransfer fileTransfer) {
		super(view, model, fileTransfer);
		algoName = (String) view.getCbAlgo().getSelectedItem();

		view.getTitle().setText(Constant.titlePanelLib + ": " + algoName);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		algoName = (String) view.getCbAlgo().getSelectedItem();

		view.getTitle().setText(Constant.titlePanelLib + ": " + algoName);

		if (src.equals(view.getBtnCreateKey())) {
			sysmetricLibraryAlgo = FactorySymetricLibraryAlgo.createSymmetricLibraryAlgo(algoName);

			if (openFolderAndSaveKey() && openFolderAndSaveIV()) {
				view.getBtnEncrypt().setEnabled(true);
				view.getBtnDecrypt().setEnabled(true);
				view.getBtnEncryptFile().setEnabled(true);
				view.getBtnDecryptFile().setEnabled(true);
			}

		} else if (src.equals(view.getBtnLoadKey())) {
			sysmetricLibraryAlgo = FactorySymetricLibraryAlgo.createSymmetricLibraryAlgo(algoName);

			if (openFolderAndLoadKey() && openFolderAndLoadIV()) {
				view.getBtnEncrypt().setEnabled(true);
				view.getBtnDecrypt().setEnabled(true);
				view.getBtnEncryptFile().setEnabled(true);
				view.getBtnDecryptFile().setEnabled(true);
			}
		} else if (src.equals(view.getBtnEncryptFile())) {
			String pathSrc = fileTransfer.getSrc();
			if (pathSrc == null) {
				Constant.jOptionPaneNotChooseFile();
				return;
			}

			String pathDes = fileTransfer.getDes() + "/encrypt"
					+ pathSrc.substring(pathSrc.lastIndexOf("."), pathSrc.length());
			fileTransfer.setDes(pathDes);
			pathDes = fileTransfer.getDes();

			encryptFile(pathSrc, pathDes);
			view.getTaDecrypt().setText("Đã mã hóa file: " + fileTransfer.getSrc());
		} else if (src.equals(view.getBtnDecryptFile())) {
			String pathSrc = fileTransfer.getSrc();
			if (pathSrc == null) {
				Constant.jOptionPaneNotChooseFile();
				return;
			}

			String pathDes = fileTransfer.getDes() + "/decrypt"
					+ pathSrc.substring(pathSrc.lastIndexOf("."), pathSrc.length());
			fileTransfer.setDes(pathDes);
			pathDes = fileTransfer.getDes();

			decryptFile(pathSrc, pathDes);
			view.getTaDecrypt().setText("Đã giải mã file: " + fileTransfer.getSrc());
		} else if (src.equals(view.getBtnEncrypt())) {
			String input = view.getTaEncrypt().getText().trim();
			if (input.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			}

			model.setText(input);
			String encrypt = encryptText(model.getText());
			view.getTaDecrypt().setText(encrypt);

		} else if (src.equals(view.getBtnDecrypt())) {
			String input = view.getTaEncrypt().getText().trim();
			if (input.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			}
			model.setText(input);

			String decrypt = decryptText(model.getText());
			view.getTaDecrypt().setText(decrypt);
		}
	}

	@Override
	protected String encryptText(String text) {
		return sysmetricLibraryAlgo.encryptText(text);
	}

	@Override
	protected String decryptText(String text) {
		return sysmetricLibraryAlgo.decryptText(text);
	}

	@Override
	protected boolean encryptFile(String fileSrc, String fileDes) {
		if (sysmetricLibraryAlgo.encryptFile(fileSrc, fileDes)) {
			Constant.jOptionPaneEncryptFileSuccess();
			return true;
		}
		Constant.jOptionPaneEncryptFileFailed();
		return false;
	}

	@Override
	protected boolean decryptFile(String fileSrc, String fileDes) {
		if (sysmetricLibraryAlgo.decryptFile(fileSrc, fileDes)) {
			Constant.jOptionPaneDecryptFileSuccess();
			return true;
		}
		Constant.jOptionPaneDecryptFileFailed();
		return false;
	}

	@Override
	protected boolean genKey(int size) {
		if (sysmetricLibraryAlgo.genKey(size)) {
			Constant.jOptionPaneCreateKeySuccess();
			return true;
		}
		Constant.jOptionPaneCreateKeyFailed();
		return false;
	}

	private boolean genIV() {
		if (sysmetricLibraryAlgo.genIV()) {
			Constant.jOptionPaneCreateKeySuccess();
			return true;
		}
		Constant.jOptionPaneCreateKeyFailed();
		return false;
	}

	@Override
	protected boolean loadKey(String fileSrc) {
		if (sysmetricLibraryAlgo.loadKey(fileSrc)) {
			Constant.jOptionPaneLoadKeySuccess();
			return true;
		}
		Constant.jOptionPaneLoadKeyFailed();
		return false;
	}

	protected boolean loadIV(String fileSrc) {
		if (sysmetricLibraryAlgo.loadIV(fileSrc)) {
			Constant.jOptionPaneLoadKeySuccess();
			return true;
		}
		Constant.jOptionPaneLoadKeyFailed();
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

			if (loadKey(fileSrc)) {
				return true;
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	protected boolean openFolderAndLoadIV() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn đường dẫn của IV");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "dat");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(view);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String fileSrc = fileToOpen.getAbsolutePath();

			if (loadIV(fileSrc)) {
				return true;
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
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
				try {
					int size = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập kích thước khóa:", "Kích thước",
							JOptionPane.PLAIN_MESSAGE));
					if (!genKey(size)) {
						return false;
					}
					if (!saveKey(folder, fileName)) {
						return false;
					}
					return true;
				} catch (Exception e) {
					Constant.jOptionPaneWrongFormatKey();
					e.printStackTrace();
				}
				return false;
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
			String fileName = JOptionPane.showInputDialog(null, "Nhập tên file:", "Tên file",
					JOptionPane.PLAIN_MESSAGE);

			if (fileName != null && !fileName.trim().isEmpty()) {
				if (!genIV()) {
					return false;
				}
				if (!saveIV(folder, fileName)) {
					return false;
				}

				return true;
			}
		} else {
			Constant.jOptionPaneNotChooseFolder();
		}
		return false;
	}

	@Override
	protected boolean saveKey(String folder, String fileName) {
		if (sysmetricLibraryAlgo.saveKey(folder, fileName, sysmetricLibraryAlgo.getKey())) {
			return true;
		}
		return false;
	}

	private boolean saveIV(String folder, String fileName) {
		if (sysmetricLibraryAlgo.saveKey(folder, fileName, sysmetricLibraryAlgo.getIv())) {
			return true;
		}
		return false;
	}
}
