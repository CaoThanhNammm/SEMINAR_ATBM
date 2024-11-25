package seminar.view.component.option;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import seminar.controller.ChangeScreenController;
import seminar.view.View;

public class Menu extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private JMenu optionMenu;
	private JMenuItem basic;
	private JMenuItem symmetricJava;
	private JMenuItem symmetricLibrary;
	private JMenuItem rsa;
	private JMenuItem hash;
	private JMenuItem digitalSignature;

	private View view;

	public Menu(View view) {
		this.view = view;

		initComponent();
		showComponent();
	}

	private void initComponent() {
		optionMenu = new JMenu("Thuật toán");

		basic = new JMenuItem("Thuật toán mã hóa cơ bản");
		symmetricJava = new JMenuItem("Thuật toán mã hóa đối xứng của Java");
		symmetricLibrary = new JMenuItem("Thuật toán mã hóa đối xứng sử dụng thư viện");
		rsa = new JMenuItem("Thuật toán mã hóa RSA");
		hash = new JMenuItem("Thuật toán mã hóa Hash");
		digitalSignature = new JMenuItem("Chữ ký điện tử");

	}

	private void showComponent() {
		optionMenu.add(basic);
		optionMenu.add(symmetricJava);
		optionMenu.add(symmetricLibrary);
		optionMenu.add(rsa);
		optionMenu.add(hash);
		optionMenu.add(digitalSignature);

		ActionListener event = new ChangeScreenController(this, view);

		basic.addActionListener(event);
		symmetricJava.addActionListener(event);
		symmetricLibrary.addActionListener(event);
		rsa.addActionListener(event);
		hash.addActionListener(event);
		digitalSignature.addActionListener(event);

		add(optionMenu);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JMenu getOptionMenu() {
		return optionMenu;
	}

	public JMenuItem getBasic() {
		return basic;
	}

	public JMenuItem getSymmetricJava() {
		return symmetricJava;
	}

	public JMenuItem getSymmetricLibrary() {
		return symmetricLibrary;
	}

	public JMenuItem getRsa() {
		return rsa;
	}

	public JMenuItem getHash() {
		return hash;
	}

	public JMenuItem getDigitalSignature() {
		return digitalSignature;
	}
}
