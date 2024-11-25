package seminar.controller.algo.asymmetric;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA extends AAsymmetric {
	private Cipher cipher;

	public RSA() {
		super();
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public boolean genKey(int size) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(size);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
			return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean loadPublicKey(String fileSrc) {
		try {
			byte[] keyBytes = Files.readAllBytes(Paths.get(fileSrc));
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(spec);
			return true;
		} catch (InvalidKeySpecException | IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loadPrivateKey(String fileSrc) {
		try {
			byte[] keyBytes = Files.readAllBytes(Paths.get(fileSrc));
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			privateKey = keyFactory.generatePrivate(spec);
			return true;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveKey(String folderSrc, String filename, byte[] key) {
		String fileSrc = folderSrc + "/key_rsa_" + filename + ".dat";
		try (java.io.FileOutputStream fos = new java.io.FileOutputStream(fileSrc)) {
			fos.write(key);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String encrypt(String text) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encryptedData = cipher.doFinal(text.getBytes());
			return Base64.getEncoder().encodeToString(encryptedData);
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String text) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decodedData = Base64.getDecoder().decode(text);
			byte[] decryptedData;
			decryptedData = cipher.doFinal(decodedData);
			return new String(decryptedData, StandardCharsets.UTF_8);
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String signText(String text) {
		return null;
	}

	@Override
	public String signFile(String fileSrc) {
		return null;
	}

	@Override
	public String verifySignature(String data, String signature) {
		return null;
	}

	@Override
	public String verifySignatureFile(String data, String signature) {
		return null;
	}
}
