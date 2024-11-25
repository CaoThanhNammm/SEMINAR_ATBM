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

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

public class Serpent extends ASymmetricLibrary {

	@Override
	protected boolean genKey(int size) {
		if (size == 0 && size != 32 && size != 16 && size != 24) {
			return false;
		}
		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] b = new byte[size];
			secureRandom.nextBytes(b);
			this.key = b;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected boolean saveKey(String folder, String fileName, byte[] key) {
		String fileSrc = folder + "/key_" + fileName + "_.dat";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileSrc))) {
			String keyBase64 = Base64.getEncoder().encodeToString(key);
			writer.write(keyBase64);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
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

	@Override
	protected String encryptText(String text) {
		try {
			// Khởi tạo Serpent engine và sử dụng PKCS7 padding
			SerpentEngine serpent = new SerpentEngine();
			PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(serpent, new PKCS7Padding());
			CipherParameters keyParam = new KeyParameter(key);
			cipher.init(true, keyParam); // true để mã hóa

			byte[] plaintextBytes = text.getBytes(StandardCharsets.UTF_8);
			byte[] ciphertext = new byte[cipher.getOutputSize(plaintextBytes.length)];

			int outputLen = cipher.processBytes(plaintextBytes, 0, plaintextBytes.length, ciphertext, 0);
			outputLen += cipher.doFinal(ciphertext, outputLen);

			// Trả về chuỗi Base64 để dễ dàng truyền tải
			return Base64.getEncoder().encodeToString(ciphertext);
		} catch (InvalidCipherTextException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected String decryptText(String text) {
		try {
			// Khởi tạo Serpent engine và sử dụng PKCS7 padding
			SerpentEngine serpent = new SerpentEngine();
			PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(serpent, new PKCS7Padding());
			CipherParameters keyParam = new KeyParameter(key);
			cipher.init(false, keyParam); // false để giải mã

			byte[] ciphertextBytes = Base64.getDecoder().decode(text);
			byte[] plaintext = new byte[cipher.getOutputSize(ciphertextBytes.length)];

			int outputLen = cipher.processBytes(ciphertextBytes, 0, ciphertextBytes.length, plaintext, 0);
			outputLen += cipher.doFinal(plaintext, outputLen);

			// Trả về chuỗi văn bản đã giải mã
			return new String(plaintext, 0, outputLen, StandardCharsets.UTF_8);
		} catch (InvalidCipherTextException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected boolean encryptFile(String fileSrc, String fileDes) {
		try {
			// Khởi tạo Serpent engine và padding
			SerpentEngine serpent = new SerpentEngine();
			PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(serpent, new PKCS7Padding());
			CipherParameters keyParam = new KeyParameter(key);
			cipher.init(true, keyParam); // true để mã hóa

			FileInputStream fis = new FileInputStream(fileSrc);
			FileOutputStream fos = new FileOutputStream(fileDes);
			byte[] buffer = new byte[4096]; // Đọc và xử lý dữ liệu từng phần (4 KB mỗi lần)
			byte[] ciphertext = new byte[cipher.getOutputSize(buffer.length)];
			int bytesRead;

			while ((bytesRead = fis.read(buffer)) != -1) {
				int outputLen = cipher.processBytes(buffer, 0, bytesRead, ciphertext, 0);
				fos.write(ciphertext, 0, outputLen);
			}
			int outputLen = cipher.doFinal(ciphertext, 0);
			fos.write(ciphertext, 0, outputLen);

			fis.close();
			fos.close();
			return true;
		} catch (IOException | InvalidCipherTextException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected boolean decryptFile(String fileSrc, String fileDes) {
		try {
			// Khởi tạo Serpent engine và padding
			SerpentEngine serpent = new SerpentEngine();
			PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(serpent, new PKCS7Padding());
			CipherParameters keyParam = new KeyParameter(key);
			cipher.init(false, keyParam); // false để giải mã

			FileInputStream fis = new FileInputStream(fileSrc);
			FileOutputStream fos = new FileOutputStream(fileDes);
			byte[] buffer = new byte[4096]; // Đọc và xử lý dữ liệu từng phần (4 KB mỗi lần)
			byte[] plaintext = new byte[cipher.getOutputSize(buffer.length)];
			int bytesRead;

			while ((bytesRead = fis.read(buffer)) != -1) {
				int outputLen = cipher.processBytes(buffer, 0, bytesRead, plaintext, 0);
				fos.write(plaintext, 0, outputLen);
			}
			int outputLen = cipher.doFinal(plaintext, 0);
			fos.write(plaintext, 0, outputLen);

			fis.close();
			fos.close();
			return true;
		} catch (IOException | InvalidCipherTextException e) {
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

	@Override
	protected boolean loadIV(String fileName) {
		return true;
	}

	@Override
	protected boolean genIV() {
		return false;
	}

}
