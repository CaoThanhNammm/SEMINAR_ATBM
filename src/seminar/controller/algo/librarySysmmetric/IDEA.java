package seminar.controller.algo.librarySysmmetric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import org.bouncycastle.crypto.engines.IDEAEngine;
import org.bouncycastle.crypto.params.KeyParameter;

public class IDEA extends ASymmetricLibrary {

	@Override
	public boolean genKey(int size) {
		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] b = new byte[size];
			secureRandom.nextBytes(b);
			key = b;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean saveKey(String folder, String fileName, byte[] key) {
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
	public boolean loadKey(String fileSrc) {
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
	public String encryptText(String text) {
		try {
			IDEAEngine ideaEngine = new IDEAEngine();
			KeyParameter keyParam = new KeyParameter(key);
			ideaEngine.init(true, keyParam); // true để mã hóa

			byte[] plaintextBytes = text.getBytes(StandardCharsets.UTF_8);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[8]; // IDEA sử dụng khối 8 byte
			byte[] encryptedBlock = new byte[8];

			for (int i = 0; i < plaintextBytes.length; i += 8) {
				int blockSize = Math.min(8, plaintextBytes.length - i);
				System.arraycopy(plaintextBytes, i, buffer, 0, blockSize);

				if (blockSize < 8) {
					buffer = addPadding(buffer, blockSize); // Thêm padding nếu khối cuối nhỏ hơn 8 byte
				}

				ideaEngine.processBlock(buffer, 0, encryptedBlock, 0);
				outputStream.write(encryptedBlock);
			}

			return Base64.getEncoder().encodeToString(outputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String decryptText(String text) {
		try {
			IDEAEngine ideaEngine = new IDEAEngine();
			KeyParameter keyParam = new KeyParameter(key);
			ideaEngine.init(false, keyParam); // false để giải mã

			byte[] ciphertextBytes = Base64.getDecoder().decode(text);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[8]; // IDEA sử dụng khối 8 byte
			byte[] decryptedBlock = new byte[8];

			for (int i = 0; i < ciphertextBytes.length; i += 8) {
				System.arraycopy(ciphertextBytes, i, buffer, 0, 8);
				ideaEngine.processBlock(buffer, 0, decryptedBlock, 0);

				if (i + 8 >= ciphertextBytes.length) {
					decryptedBlock = removePadding(decryptedBlock); // Loại bỏ padding khối cuối
				}

				outputStream.write(decryptedBlock);
			}

			return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean encryptFile(String fileSrc, String fileDes) {
		try {
			IDEAEngine ideaEngine = new IDEAEngine();
			KeyParameter keyParam = new KeyParameter(key);
			ideaEngine.init(true, keyParam); // true để mã hóa

			FileInputStream fis = new FileInputStream(fileSrc);
			FileOutputStream fos = new FileOutputStream(fileDes);
			byte[] buffer = new byte[8]; // IDEA sử dụng khối 8 byte
			int bytesRead;
			byte[] encryptedBlock = new byte[8];

			while ((bytesRead = fis.read(buffer)) != -1) {
				if (bytesRead < 8) {
					buffer = addPadding(buffer, bytesRead); // Thêm padding nếu khối cuối nhỏ hơn 8 byte
				}
				ideaEngine.processBlock(buffer, 0, encryptedBlock, 0);
				fos.write(encryptedBlock);
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
	public boolean decryptFile(String fileSrc, String fileDes) {
		try {
			IDEAEngine ideaEngine = new IDEAEngine();
			KeyParameter keyParam = new KeyParameter(key);
			ideaEngine.init(false, keyParam); // false để giải mã

			FileInputStream fis = new FileInputStream(fileSrc);
			FileOutputStream fos = new FileOutputStream(fileDes);
			byte[] buffer = new byte[8]; // IDEA sử dụng khối 8 byte
			int bytesRead;
			byte[] decryptedBlock = new byte[8];

			while ((bytesRead = fis.read(buffer)) != -1) {
				ideaEngine.processBlock(buffer, 0, decryptedBlock, 0);
				if (fis.available() == 0) {
					decryptedBlock = removePadding(decryptedBlock); // Loại bỏ padding trên khối cuối
				}
				fos.write(decryptedBlock);
			}

			fis.close();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected byte[] addPadding(byte[] data, int bytesRead) {
		int paddingLength = 8 - bytesRead; // Cần thêm số byte còn thiếu để đủ 8 byte
		byte[] paddedData = new byte[8];
		System.arraycopy(data, 0, paddedData, 0, bytesRead);
		for (int i = bytesRead; i < 8; i++) {
			paddedData[i] = (byte) paddingLength; // Thêm giá trị padding
		}
		return paddedData;
	}

	@Override
	protected byte[] removePadding(byte[] data) {
		int paddingLength = data[data.length - 1]; // Giá trị padding là byte cuối cùng
		byte[] unpaddedData = new byte[data.length - paddingLength];
		System.arraycopy(data, 0, unpaddedData, 0, unpaddedData.length);
		return unpaddedData;
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
