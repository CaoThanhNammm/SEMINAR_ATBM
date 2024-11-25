package seminar.controller.algo.symmertric;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class ASymmetric {
	private SecretKey key;
	private IvParameterSpec iv;
	private Cipher cipher;
	private String algo;

	public ASymmetric(String algo) {
		this.algo = algo;
	}

	public boolean createCipher() {
		try {
			cipher = Cipher.getInstance(algo);
			return true;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean genKey(int size) {
		try {
			String nameAlgo = "";
			try {
				nameAlgo = this.algo.substring(0, this.algo.indexOf("/"));
			} catch (Exception e) {
				nameAlgo = this.algo;
			}

			KeyGenerator keyGenerator = KeyGenerator.getInstance(nameAlgo);
			keyGenerator.init(size);
			key = keyGenerator.generateKey();
			return true;
		} catch (InvalidParameterException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean genIV(int size) {
		try {
			byte[] iv = new byte[size];
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.nextBytes(iv);
			this.iv = new IvParameterSpec(iv);
			return true;
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loadKey(String fileSrc) {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileSrc))) {
			try {
				key = (SecretKey) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loadIV(String fileSrc) {
		try (FileInputStream fis = new FileInputStream(fileSrc)) {
			byte[] iv = new byte[8];
			fis.read(iv);

			this.iv = new IvParameterSpec(iv);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean saveKey(String folderSrc, String filename) {
		String fileSrc = folderSrc + "/key_" + filename + ".dat";
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileSrc))) {
			out.writeObject(key);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveIV(String folder, String fileName) {
		String fileSrc = folder + "/key_" + fileName + ".dat";
		try (FileOutputStream fos = new FileOutputStream(fileSrc)) {
			fos.write(iv.getIV());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String encrypt(String text) {
		if (key == null) {
			return null;
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] b = text.getBytes(StandardCharsets.UTF_8);

			byte[] encrypted = cipher.doFinal(b);
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (InvalidKeyException | IllegalBlockSizeException | IllegalArgumentException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String text) {
		if (key == null) {
			return null;
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(text));
			return new String(decrypted, StandardCharsets.UTF_8);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean encryptFile(String fileSrc, String fileDes) {
		if (key == null) {
			return false;
		}
		try (FileInputStream fis = new FileInputStream(fileSrc);
				FileOutputStream fos = new FileOutputStream(fileDes);
				CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = fis.read(buffer)) != -1) {
				cos.write(buffer, 0, length);
			}

			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean decryptFile(String fileSrc, String fileDes) {
		if (key == null) {
			return false;
		}
		try (FileInputStream fis = new FileInputStream(fileSrc);
				FileOutputStream fos = new FileOutputStream(fileDes);
				CipherInputStream cis = new CipherInputStream(fis, cipher)) {

			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] buffer = new byte[1024];
			int length;

			while ((length = cis.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}

			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String encryptWithIV(String text) {
		if (key == null) {
			return null;
		}
		try {
			byte[] b = text.getBytes(StandardCharsets.UTF_8);

			Cipher cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byte[] encryptedData = cipher.doFinal(b);
			return Base64.getEncoder().encodeToString(encryptedData);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decryptWithIV(String text) {
		if (key == null) {
			return null;
		}
		try {
			Cipher cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] decodedData = Base64.getDecoder().decode(text);
			byte[] decryptedData = cipher.doFinal(decodedData);
			return new String(decryptedData, StandardCharsets.UTF_8);
		} catch (IllegalBlockSizeException | IllegalArgumentException | BadPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean encryptFileWithIV(String fileSrc, String fileDes) {
		if (key == null || iv == null) {
			return false;
		}

		try (FileInputStream fis = new FileInputStream(fileSrc);
				FileOutputStream fos = new FileOutputStream(fileDes);
				CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);

			byte[] buffer = new byte[1024];
			int length;

			while ((length = fis.read(buffer)) != -1) {
				cos.write(buffer, 0, length);
			}
			return true;

		} catch (IOException | InvalidKeyException | InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public boolean decryptFileWithIV(String fileSrc, String fileDes) {
		if (key == null || iv == null) {
			return false;
		}

		try (FileInputStream fis = new FileInputStream(fileSrc);
				FileOutputStream fos = new FileOutputStream(fileDes);
				CipherInputStream cis = new CipherInputStream(fis, cipher)) {
			cipher.init(Cipher.DECRYPT_MODE, key, iv);

			byte[] buffer = new byte[1024];
			int length;

			while ((length = cis.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}
			return true;

		} catch (IOException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return false;

	}

	public SecretKey getKey() {
		return key;
	}

	public void setKey(SecretKey key) {
		this.key = key;
	}

	public IvParameterSpec getIv() {
		return iv;
	}

	public void setIv(IvParameterSpec iv) {
		this.iv = iv;
	}

	public Cipher getCipher() {
		return cipher;
	}

	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}
}
