package seminar.controller.algo.librarySysmmetric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import org.bouncycastle.crypto.engines.HC128Engine;
import org.bouncycastle.crypto.engines.HC256Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class HC256 extends ASymmetricLibrary {

	@Override
	protected boolean genKey(int size) {
		if (size != 32 && size != 16) {
			return false;
		}
		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] b = new byte[size]; // 32 or 16
			secureRandom.nextBytes(b); // Tạo khóa ngẫu nhiên
			key = b;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected boolean genIV() {
		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] nonce = new byte[16];
			secureRandom.nextBytes(nonce);
			iv = nonce;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected boolean saveKey(String folder, String fileName, byte[] key) {
		if (key == null)
			return false;

		String fileSrc = folder + "/key_" + fileName + "_.dat";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileSrc))) {
			String keyBase64 = Base64.getEncoder().encodeToString(key);
			writer.write(keyBase64);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	protected boolean loadKey(String fileSrc) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileSrc));
			String keyBase64;
			keyBase64 = reader.readLine();
			key = Base64.getDecoder().decode(keyBase64);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected boolean loadIV(String fileSrc) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileSrc));
			String keyBase64;
			keyBase64 = reader.readLine();
			iv = Base64.getDecoder().decode(keyBase64);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected String encryptText(String text) {
		try {
			HC256Engine chacha = new HC256Engine();
			ParametersWithIV keyWithIV = new ParametersWithIV(new KeyParameter(key), iv);
			chacha.init(true, keyWithIV); // true để mã hóa

			byte[] plaintextBytes = text.getBytes(StandardCharsets.UTF_8);
			byte[] ciphertext = new byte[plaintextBytes.length];

			// Mã hóa
			chacha.processBytes(plaintextBytes, 0, plaintextBytes.length, ciphertext, 0);

			// Trả về chuỗi Base64 để dễ dàng truyền tải
			return Base64.getEncoder().encodeToString(ciphertext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected String decryptText(String text) {
		try {
			HC256Engine chacha = new HC256Engine();
			ParametersWithIV keyWithIV = new ParametersWithIV(new KeyParameter(key), iv);
			chacha.init(false, keyWithIV); // false để giải mã

			byte[] ciphertextBytes = Base64.getDecoder().decode(text);
			byte[] plaintextBytes = new byte[ciphertextBytes.length];

			// Giải mã
			chacha.processBytes(ciphertextBytes, 0, ciphertextBytes.length, plaintextBytes, 0);

			// Trả về chuỗi văn bản đã giải mã
			return new String(plaintextBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected boolean encryptFile(String fileSrc, String fileDes) {
		try {
			HC256Engine chacha = new HC256Engine();
			ParametersWithIV keyWithIV = new ParametersWithIV(new KeyParameter(key), iv);
			chacha.init(true, keyWithIV); // true để mã hóa

			FileInputStream fis = new FileInputStream(fileSrc);
			FileOutputStream fos = new FileOutputStream(fileDes);
			byte[] buffer = new byte[4096]; // Đọc và xử lý dữ liệu từng phần (4 KB mỗi lần)
			byte[] ciphertext = new byte[buffer.length];
			int bytesRead;

			while ((bytesRead = fis.read(buffer)) != -1) {
				chacha.processBytes(buffer, 0, bytesRead, ciphertext, 0); // Mã hóa từng phần
				fos.write(ciphertext, 0, bytesRead); // Ghi dữ liệu đã mã hóa vào file đích
			}

			fis.close();
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected boolean decryptFile(String fileSrc, String fileDes) {
		try {
			HC256Engine chacha = new HC256Engine();
			ParametersWithIV keyWithIV = new ParametersWithIV(new KeyParameter(key), iv);
			chacha.init(false, keyWithIV); // false để giải mã

			FileInputStream fis = new FileInputStream(fileSrc);
			FileOutputStream fos = new FileOutputStream(fileDes);
			byte[] buffer = new byte[4096]; // Đọc và xử lý dữ liệu từng phần (4 KB mỗi lần)
			byte[] plaintext = new byte[buffer.length];
			int bytesRead;

			while ((bytesRead = fis.read(buffer)) != -1) {
				chacha.processBytes(buffer, 0, bytesRead, plaintext, 0); // Giải mã từng phần
				fos.write(plaintext, 0, bytesRead); // Ghi dữ liệu đã giải mã vào file đích
			}

			fis.close();
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected byte[] addPadding(byte[] data, int bytesData) {
		return null;
	}

	@Override
	protected byte[] removePadding(byte[] data) {
		return null;
	}

}
