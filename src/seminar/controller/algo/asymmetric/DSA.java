package seminar.controller.algo.asymmetric;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DSA extends AAsymmetric {
	private Signature dsa;

	public DSA() {
		try {
			dsa = Signature.getInstance("SHA256withDSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean genKey(int size) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			SecureRandom random = new SecureRandom();

			// Khởi tạo key với độ dài 1024 bits
			keyGen.initialize(size, random);

			// Tạo cặp khóa (Key Pair)
			KeyPair pair = keyGen.generateKeyPair();
			publicKey = pair.getPublic();
			privateKey = pair.getPrivate();
			return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean loadPublicKey(String fileSrc) {
		try {
			byte[] keyBytes = Files.readAllBytes(Paths.get(fileSrc));
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			publicKey = keyFactory.generatePublic(spec);
			return true;
		} catch (InvalidKeySpecException | IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean loadPrivateKey(String fileSrc) {
		try {
			byte[] keyBytes = Files.readAllBytes(Paths.get(fileSrc));
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			privateKey = keyFactory.generatePrivate(spec);
			return true;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean saveKey(String folderSrc, String filename, byte[] key) {
		try {
			String fileSrc = folderSrc + "/key_dsa_" + filename + ".dat";
			FileOutputStream fos = new FileOutputStream(fileSrc);
			fos.write(key);
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String encrypt(String text) {
		return signText(text);
	}

	@Override
	public String decrypt(String text) {
		return "";
	}

	@Override
	public String signText(String text) {
		try {
			dsa.initSign(privateKey);

			dsa.update(text.getBytes());

			return Base64.getEncoder().encodeToString(dsa.sign());
		} catch (SignatureException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String verifySignature(String data, String signature) {
		try {
			byte[] digitalSignature = Base64.getDecoder().decode(signature);
			dsa.initVerify(publicKey);
			dsa.update(data.getBytes());
			return dsa.verify(digitalSignature) + "";
		} catch (SignatureException | InvalidKeyException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return false + "";
	}

	@Override
	public String signFile(String fileSrc) {
		try {
			dsa.initSign(privateKey);

			try (FileInputStream fis = new FileInputStream(fileSrc)) {
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) != -1) {
					dsa.update(buffer, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return Base64.getEncoder().encodeToString(dsa.sign());
		} catch (SignatureException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String verifySignatureFile(String fileSrc, String signature) {
		try {
			dsa.initVerify(publicKey);

			byte[] digitalSignature = Base64.getDecoder().decode(signature);
			try (FileInputStream fis = new FileInputStream(fileSrc)) {
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) != -1) {
					dsa.update(buffer, 0, len);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return dsa.verify(digitalSignature) + "";
		} catch (SignatureException | InvalidKeyException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		return false + "";
	}

}
