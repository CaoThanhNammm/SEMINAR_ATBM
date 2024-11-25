package seminar.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import seminar.model.Constant;
import seminar.view.component.screen.Screen;

public class OptimizeInputController extends DocumentFilter implements DocumentListener, FocusListener {
	private Screen view;
	private final int documentLengthMax = Constant.DOCUMENT_LENGTH_MAX;

	public OptimizeInputController(Screen view) {
		super();
		this.view = view;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		setWordQuantity(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		setWordQuantity(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		if (fb.getDocument().getLength() + string.length() <= documentLengthMax) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
			throws BadLocationException {
		if (fb.getDocument().getLength() - length + string.length() <= documentLengthMax) {
			super.replace(fb, offset, length, string, attrs);
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		super.remove(fb, offset, length);
	}

	private void setWordQuantity(DocumentEvent e) {
		int documentLength = e.getDocument().getLength();
		view.getLbLengthDocument().setText(documentLength + "/");
	}

	@Override
	public void focusGained(FocusEvent e) {
		view.getTaEncrypt().setBorder(Constant.BORDER_WHEN_FOCUS);
	}

	@Override
	public void focusLost(FocusEvent e) {
		view.getTaEncrypt().setBorder(Constant.BOTDER_ENCRYPT);
	}

}
