package seminar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;

import seminar.model.Model;
import seminar.view.View;
import seminar.view.component.option.Menu;

public class ChangeScreenController implements ActionListener {
	private Menu menuBar;
	private View view;

	public ChangeScreenController(Menu menuBar, View view) {
		super();
		this.menuBar = menuBar;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src.equals(menuBar.getBasic())) {
			view.getCardLayout().show(view.getC(), "basicScreen");
		} else if (src.equals(menuBar.getSymmetricJava())) {
			view.getCardLayout().show(view.getC(), "sysmetricJavaScreen");
		} else if (src.equals(menuBar.getSymmetricLibrary())) {
			view.getCardLayout().show(view.getC(), "sysmetricLibraryScreen");
		} else if (src.equals(menuBar.getRsa())) {
			view.getCardLayout().show(view.getC(), "rsaScreen");
		} else if (src.equals(menuBar.getHash())) {
			view.getCardLayout().show(view.getC(), "hashScreen");
		} else if (src.equals(menuBar.getDigitalSignature())) {
			view.getCardLayout().show(view.getC(), "digitalSignatureScreen");
		}
	}
}
