package seminar.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import seminar.view.component.screen.Screen;

public class OperationInputTextArea extends AbstractAction implements UndoableEditListener {
	private static final long serialVersionUID = 1L;
	private Screen view;

	public OperationInputTextArea(Screen view) {
		super();
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (view.getUndoManager().canUndo()) {
			view.getUndoManager().undo();
		}
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		view.getUndoManager().addEdit(e.getEdit());
	}

}
