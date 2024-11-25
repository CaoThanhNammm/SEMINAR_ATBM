package seminar.view.component.screen;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.undo.UndoManager;

import seminar.controller.FileDropTarget;
import seminar.controller.OperationInputTextArea;
import seminar.controller.OptimizeInputController;
import seminar.model.Constant;
import seminar.model.FileTransfer;

public abstract class Screen extends JPanel {
	private static final long serialVersionUID = 1L;

	// JLabel
	protected JLabel title;

	// JPanel
	protected JPanel panelHeader;
	protected JPanel panelCrypt;
	protected JPanel panelOperation;

	// JButton
	protected JButton btnCreateKey;
	protected JButton btnLoadKey;
	protected JButton btnEncrypt;
	protected JButton btnDecrypt;
	protected JButton btnEncryptFile;
	protected JButton btnDecryptFile;
	// JTextArea
	private JPanel panelConfigTextArea;
	protected JTextArea taEncrypt;
	protected JTextArea taDecrypt;

	// JTextLabel
	protected JLabel lbLengthDocument;
	protected JLabel lbLengthDocumentMax;

	// JComboBox
	protected JComboBox<String> cbAlgo;
	protected JComboBox<String> cbMode;
	protected JComboBox<String> cbPadding;

	protected FileTransfer fileTransfer = new FileTransfer();

	protected String titleName;
	protected BorderLayout borderLayout;
	protected UndoManager undoManager;
	protected Document doc;

	public Screen(String titleName) {
		this.titleName = titleName;
		initComponent();
		showComponent();
		addOptimizeTextArea();
	}

	private void initComponent() {
		panelHeader = new JPanel();
		panelCrypt = new JPanel();
		panelOperation = new JPanel();

		title = new JLabel(titleName, JLabel.CENTER);

		btnLoadKey = new JButton(Constant.titleBtnLoadKey);
		btnCreateKey = new JButton(Constant.titleBtnCreateKey);
		btnEncrypt = new JButton(Constant.titleBtnEncrypt);
		btnDecrypt = new JButton(Constant.titleBtnDecrypt);
		btnEncryptFile = new JButton(Constant.titleBtnEncryptFile);
		btnDecryptFile = new JButton(Constant.titleBtnDecryptFile);

		taEncrypt = new JTextArea();
		taDecrypt = new JTextArea();

		panelConfigTextArea = new JPanel();
		lbLengthDocument = new JLabel("0/", JLabel.RIGHT);
		lbLengthDocumentMax = new JLabel(Constant.DOCUMENT_LENGTH_MAX + "", JLabel.RIGHT);

		undoManager = new UndoManager();
	}

	private void showComponent() {
		addPanelHeader();
		addPanelCrypt();
		addPanelOperation();

		setLayout(new BorderLayout());
		add(panelHeader, BorderLayout.NORTH);
		add(panelCrypt, BorderLayout.CENTER);
		add(panelOperation, BorderLayout.SOUTH);
	}

	private void addPanelHeader() {
		title.setFont(Constant.h1);
		panelHeader.add(title);
	}

	private void addPanelCrypt() {
		JPanel panelInput = new JPanel();

		taEncrypt.setLineWrap(true);
		taEncrypt.setBorder(BorderFactory.createTitledBorder(Constant.BOTDER_ENCRYPT));

		doc = taEncrypt.getDocument();
		taEncrypt.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "Undo");

		JScrollPane spEncrypt = new JScrollPane(taEncrypt);

		new DropTarget(taEncrypt, new FileDropTarget(this, fileTransfer));

		taDecrypt.setLineWrap(true);
		taDecrypt.setEditable(false);
		taDecrypt.setBorder(BorderFactory.createTitledBorder(Constant.BOTDER_DECRYPT));
		JScrollPane spDecrypt = new JScrollPane(taDecrypt);

		borderLayout = new BorderLayout();
		panelInput.setLayout(new GridLayout(2, 1));
		panelConfigTextArea.setLayout(borderLayout);

		panelConfigTextArea.add(lbLengthDocument, BorderLayout.CENTER);
		panelConfigTextArea.add(lbLengthDocumentMax, BorderLayout.EAST);

		panelInput.add(spEncrypt);
		panelInput.add(spDecrypt);

		panelCrypt.setLayout(new BorderLayout());
		panelCrypt.add(panelConfigTextArea, BorderLayout.NORTH);
		panelCrypt.add(panelInput, BorderLayout.CENTER);
	}

	private void addPanelOperation() {
		panelOperation.add(btnEncrypt);
		panelOperation.add(btnDecrypt);
		panelOperation.add(btnEncryptFile);
		panelOperation.add(btnDecryptFile);
		panelOperation.add(btnCreateKey);
		panelOperation.add(btnLoadKey);
	}

	protected abstract void addActionListener();

	private void addOptimizeTextArea() {
		DocumentListener documentListener = new OptimizeInputController(this);
		OptimizeInputController optimizeInputController = new OptimizeInputController(this);

		OperationInputTextArea operationInputTextArea = new OperationInputTextArea(this);

		((PlainDocument) taEncrypt.getDocument()).setDocumentFilter(optimizeInputController);
		taEncrypt.addFocusListener(optimizeInputController);
		taEncrypt.getDocument().addDocumentListener(documentListener);

		doc.addUndoableEditListener(operationInputTextArea);
		taEncrypt.getActionMap().put("Undo", operationInputTextArea);
	}

	public JButton getBtnLoadKey() {
		return btnLoadKey;
	}

	public JButton getBtnEncrypt() {
		return btnEncrypt;
	}

	public JButton getBtnDecrypt() {
		return btnDecrypt;
	}

	public JButton getBtnEncryptFile() {
		return btnEncryptFile;
	}

	public JButton getBtnDecryptFile() {
		return btnDecryptFile;
	}

	public JButton getBtnCreateKey() {
		return btnCreateKey;
	}

	public void setBtnCreateKey(JButton btnCreateKey) {
		this.btnCreateKey = btnCreateKey;
	}

	public JTextArea getTaEncrypt() {
		return taEncrypt;
	}

	public JTextArea getTaDecrypt() {
		return taDecrypt;
	}

	public JLabel getLbLengthDocument() {
		return lbLengthDocument;
	}

	public JPanel getpanelConfigTextArea() {
		return panelConfigTextArea;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public BorderLayout getBorderLayout() {
		return borderLayout;
	}

	public JComboBox<String> getCbAlgo() {
		return cbAlgo;
	}

	public void setCbAlgo(JComboBox<String> cbAlgo) {
		this.cbAlgo = cbAlgo;
	}

	public JComboBox<String> getCbMode() {
		return cbMode;
	}

	public void setCbMode(JComboBox<String> cbMode) {
		this.cbMode = cbMode;
	}

	public JComboBox<String> getCbPadding() {
		return cbPadding;
	}

	public JLabel getTitle() {
		return title;
	}

	public void setTitle(JLabel title) {
		this.title = title;
	}

	public void setCbPadding(JComboBox<String> cbPadding) {
		this.cbPadding = cbPadding;
	}
}
