package seminar.controller.algo.hash;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AHash {
	private String algo;
	private MessageDigest md;

	public AHash(String algo) {
		this.algo = algo;
	}

	public boolean genKey() {
		try {
			md = MessageDigest.getInstance(algo);
			return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String hashText(String text) {
		byte[] hashBytes = md.digest(text.getBytes());
		StringBuilder hexString = new StringBuilder();
		for (byte b : hashBytes) {
			hexString.append(String.format("%02x", b));
		}
		return hexString.toString();
	}

	public String hashFile(String fileSrc) {
		try {
			FileInputStream fis = new FileInputStream(fileSrc);
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				md.update(buffer, 0, bytesRead);
			}

			fis.close();
			byte[] hashBytes = md.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String hashHmacSHA256(String key, String message) {
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(secretKey);
			byte[] bytes = mac.doFinal(message.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : bytes) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
}
