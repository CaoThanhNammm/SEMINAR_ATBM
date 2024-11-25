package seminar.controller;

import seminar.model.FileTransfer;
import seminar.model.Model;
import seminar.view.component.screen.Screen;

public abstract class Controller {
	protected Screen view;
	protected Model model;
	protected FileTransfer fileTransfer;

	public Controller(Screen view, Model model, FileTransfer fileTransfer) {
		super();
		this.view = view;
		this.model = model;
		this.fileTransfer = fileTransfer;
	}

	protected abstract String encryptText(String text);

	protected abstract String decryptText(String text);

	protected abstract boolean encryptFile(String fileSrc, String fileDes);

	protected abstract boolean decryptFile(String fileSrc, String fileDes);

	protected abstract boolean openFolderAndSaveKey();

	protected abstract boolean openFolderAndLoadKey();

	protected abstract boolean genKey(int size);

	protected abstract boolean saveKey(String folder, String fileSrc);

	protected abstract boolean loadKey(String fileSrc);

}
