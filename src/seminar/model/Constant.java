package seminar.model;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Constant {
	public static final String VIET_ALPHABET = "aăâbcdđeêghiklmnoôơpqrstuưvxyáàảãạấầẩẫậắằẳẵặéèẻẽẹếềểễệíìỉĩịóòỏõọốồổỗộớờởỡợúùủũụứừửữựýỳỷỹỵAĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXYÁÀẢÃẠẮẰẲẴẶẤẦẨẪẬÉÈẺẼẸẾỀỂỄỆÓÒỎÕỌỐỒỔỖỘỚỜỞỠỢÚÙỦŨỤỨỪỬỮỰÝỲỶỸỴ";

	// Tiêu đề của từng screen thuật toán
	public static final String titlePanelBasic = "MÃ HÓA CƠ BẢN";
	public static final String titlePanelJava = "MÃ HÓA ĐỐI XỨNG CỦA JAVA";
	public static final String titlePanelLib = "MÃ HÓA ĐỐI XỨNG SỬ DỤNG THƯ VIỆN";
	public static final String titlePanelRsa = "MÃ HÓA BẤT ĐỐI XỨNG RSA";
	public static final String titlePanelHash = "HÀM BĂM";
	public static final String titlePanelDigitalSignature = "CHỮ KÝ ĐIỆN TỬ";

	// Tiêu đề của text area
	public static final String titleTextAreaEncrypt = "Mã hóa";
	public static final String titleTextAreaDecrypt = "Giải mã";

	// Tiêu đề của các nút
	public static final String titleBtnEncrypt = "Mã hóa";
	public static final String titleBtnDecrypt = "Giải mã";
	public static final String titleBtnCreateKey = "Tạo khóa";
	public static final String titleBtnLoadKey = "Tải khóa";
	public static final String titleBtnEncryptFile = "Mã hóa file";
	public static final String titleBtnDecryptFile = "Giải mã file";

	// Font chữ
	public static final Font h1 = new Font("Arial", Font.BOLD, 20);
	public static final Font h2 = new Font("Arial", Font.BOLD, 18);
	public static final Font h3 = new Font("Arial", Font.PLAIN, 15);

	// Param mã hóa AES
	public static final int KEY_SIZE = 256;

	public static void jOptionPaneNotFoundKey() {
		JOptionPane.showMessageDialog(null, "Chọn tải khóa", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneCreateKeySuccess() {
		JOptionPane.showMessageDialog(null, "Tạo khóa thành công", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneCreateKeyFailed() {
		JOptionPane.showMessageDialog(null, "Tạo khóa thất bại", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneLoadKeySuccess() {
		JOptionPane.showMessageDialog(null, "Tải khóa thành công", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneLoadKeyFailed() {
		JOptionPane.showMessageDialog(null, "Tải khóa thất bại", "Thất bại", JOptionPane.INFORMATION_MESSAGE);

	}

	public static void jOptionPaneNotChooseFolder() {
		JOptionPane.showMessageDialog(null, "Chưa chọn thư mục", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneNotChooseFile() {
		JOptionPane.showMessageDialog(null, "Chưa chọn file", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneContentEmpty() {
		JOptionPane.showMessageDialog(null, "Chưa nhập nội dung", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneContentOverByte(int lenText, int maxByte) {
		JOptionPane.showMessageDialog(null,
				"Chiều dài ký tự là " + lenText + " đã vượt quá " + maxByte + " mà thuật toán hỗ trợ", "Thất bại",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneInvalidKey() {
		JOptionPane.showMessageDialog(null, "Khóa không hợp lệ", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneFileNotFound() {
		JOptionPane.showMessageDialog(null, "File không tồn tại", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneNotProviderSupporting() {
		JOptionPane.showMessageDialog(null, "Không hỗ trợ thuật toán", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneWrongFormatKey() {
		JOptionPane.showMessageDialog(null, "Khóa sai định dạng", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneSaveKeySuccess() {
		JOptionPane.showMessageDialog(null, "Lưu khóa thành công", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneSaveKeyFailed() {
		JOptionPane.showMessageDialog(null, "Lưu khóa thất bại", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneDecryptFileFailed() {
		JOptionPane.showMessageDialog(null, "Giải mã file thất bại", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneDecryptFileSuccess() {
		JOptionPane.showMessageDialog(null, "Giải mã file thành công", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneEncryptFileFailed() {
		JOptionPane.showMessageDialog(null, "Mã hóa file thất bại", "Thất bại", JOptionPane.INFORMATION_MESSAGE);

	}

	public static void jOptionPaneEncryptFileSuccess() {
		JOptionPane.showMessageDialog(null, "Mã hóa file thành công", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void jOptionPaneContentCannotDecrypt() {
		JOptionPane.showMessageDialog(null, "Nội dung không thể giải mã", "Thất bại", JOptionPane.INFORMATION_MESSAGE);
	}

	// config JTextArea
	public static final int DOCUMENT_LENGTH_MAX = 2_000_000;
	public static final Border BORDER_WHEN_FOCUS = BorderFactory.createTitledBorder(new LineBorder(Color.red, 2),
			Constant.titleTextAreaEncrypt, TitledBorder.LEFT, TitledBorder.TOP, Constant.h2);

	public static final Border BOTDER_ENCRYPT = BorderFactory.createTitledBorder(new LineBorder(Color.black, 2),
			Constant.titleTextAreaEncrypt, TitledBorder.LEFT, TitledBorder.TOP, Constant.h2);
	
	public static final Border BOTDER_DECRYPT = BorderFactory.createTitledBorder(new LineBorder(Color.black, 2),
			Constant.titleTextAreaDecrypt, TitledBorder.LEFT, TitledBorder.TOP, Constant.h2);
}
