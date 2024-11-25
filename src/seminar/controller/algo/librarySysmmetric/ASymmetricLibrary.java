package seminar.controller.algo.librarySysmmetric;

public abstract class ASymmetricLibrary {
	protected byte[] key;
	protected byte[] iv;

	protected abstract boolean genKey(int size);

	protected abstract boolean genIV();

	protected abstract boolean saveKey(String folder, String fileName, byte[] key);

	protected abstract boolean loadKey(String fileSrc);

	protected abstract boolean loadIV(String fileSrc);

	protected abstract String encryptText(String plaintext);

	protected abstract String decryptText(String ciphertext);

	protected abstract boolean encryptFile(String fileSrc, String fileDes);

	protected abstract boolean decryptFile(String fileSrc, String fileDes);

	protected abstract byte[] addPadding(byte[] data, int bytesRead);

	protected abstract byte[] removePadding(byte[] data);

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	/*
	 * case "Blowfish": key = new byte[56]; // Blowfish requires up to 448-bit key
	 * (56 bytes) break; case "Serpent": key = new byte[32]; // Serpent requires
	 * 256-bit key (32 bytes) break; case "ChaCha20": key = new byte[32]; //
	 * ChaCha20 requires 256-bit key (32 bytes) break;
	 */

}
