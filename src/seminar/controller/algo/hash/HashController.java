package seminar.controller.algo.hash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import seminar.controller.Controller;
import seminar.model.Constant;
import seminar.model.FileTransfer;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public class HashController extends Controller implements ActionListener {
	private AHash hashAlgo;
	private String algoName;

	public HashController(Screen view, Model model, FileTransfer fileTransfer) {
		super(view, model, fileTransfer);
		algoName = (String) view.getCbAlgo().getSelectedItem();

		view.getTitle().setText(Constant.titlePanelHash + ": " + algoName);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		algoName = (String) view.getCbAlgo().getSelectedItem();
		view.getTitle().setText(Constant.titlePanelHash + ": " + algoName);
		
		if (src.equals(view.getBtnCreateKey())) {
			hashAlgo = new AHash(algoName);
			if (genKey(0)) {
				view.getBtnEncrypt().setEnabled(true);
				view.getBtnDecrypt().setEnabled(true);
				view.getBtnEncryptFile().setEnabled(true);
				view.getBtnDecryptFile().setEnabled(true);
			}

		} else if (src.equals(view.getBtnEncrypt())) {
			String text = view.getTaEncrypt().getText();
			if (text.isEmpty()) {
				Constant.jOptionPaneContentEmpty();
				return;
			}
			model.setText(text);

			String hash = hashText(model.getText());
			view.getTaDecrypt().setText(hash);
		} else if (src.equals(view.getBtnEncryptFile())) {
			String pathSrc = fileTransfer.getSrc();
			if (pathSrc == null) {
				Constant.jOptionPaneNotChooseFile();
				return;
			}

			String hash = hashFile(pathSrc);
			view.getTaDecrypt().setText(hash);
		}
	}

	@Override
	protected boolean genKey(int size) {
		if (hashAlgo.genKey()) {
			Constant.jOptionPaneCreateKeySuccess();
			return true;
		}
		Constant.jOptionPaneCreateKeyFailed();
		return false;
	}

	private String hashText(String text) {
		return hashAlgo.hashText(text);
	}

	private String hashFile(String fileSrc) {
		return hashAlgo.hashFile(fileSrc);
	}

	@Override
	protected boolean encryptFile(String fileSrc, String fileDes) {
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
	protected String decryptText(String text) {
		return null;
	}

	@Override
	protected String encryptText(String text) {
		return null;
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
