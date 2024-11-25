package seminar.controller.algo.asymmetric;

public interface IAsymmetric {
	boolean genKey(int size);

	boolean loadPublicKey(String fileSrc);

	boolean loadPrivateKey(String fileSrc);

	boolean saveKey(String folderSrc, String filename, byte[] key);

	String encrypt(String text);

	String decrypt(String text);

	public String signText(String text);

	public String signFile(String fileSrc);

	String verifySignature(String data, String signature);

	String verifySignatureFile(String fileSrc, String signature);
}
