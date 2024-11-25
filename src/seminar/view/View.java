package seminar.view;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import seminar.controller.FactoryScreen;
import seminar.model.Constant;
import seminar.view.component.option.Menu;

public class View extends JFrame {
	private static final int WIDTH_WINDOW = 1000;
	private static final int HEIGHT_WINDOW = 600;

	private JPanel digitalSignatureScreen;
	private JPanel hashScreen;
	private JPanel rsaScreen;
	private JPanel basicScreen;
	private JPanel sysmetricJavaScreen;
	private JPanel sysmetricLibraryScreen;
	private JMenuBar menu;

	private Container c;
	private CardLayout cardLayout;

	public View() {
		initComponent();
		showComponent();
	}

	// khởi tạo instance cho các panel
	private void initComponent() {
		basicScreen = FactoryScreen.createScreen("basic");
		sysmetricJavaScreen = FactoryScreen.createScreen("symmetricJava");
		sysmetricLibraryScreen = FactoryScreen.createScreen("symmetricLib");
		rsaScreen = FactoryScreen.createScreen("rsa");
		hashScreen = FactoryScreen.createScreen("hash");
		digitalSignatureScreen = FactoryScreen.createScreen("digitalSignature");

		menu = new Menu(this);
		cardLayout = new CardLayout();
		c = getContentPane();
	}

	// hiển thị tất cả các component
	private void showComponent() {
		initWindow();
		addComponent();
	}

	// khởi tạo frame hiện lên màn hình
	private void initWindow() {
		setTitle("Seminar");
		setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	// thêm các panel vào frame
	private void addComponent() {

		setJMenuBar(menu);

		c.setLayout(cardLayout);

		c.add(basicScreen, "basicScreen");
		c.add(sysmetricJavaScreen, "sysmetricJavaScreen");
		c.add(sysmetricLibraryScreen, "sysmetricLibraryScreen");
		c.add(rsaScreen, "rsaScreen");
		c.add(hashScreen, "hashScreen");
		c.add(digitalSignatureScreen, "digitalSignatureScreen");
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public Container getC() {
		return c;
	}
}
