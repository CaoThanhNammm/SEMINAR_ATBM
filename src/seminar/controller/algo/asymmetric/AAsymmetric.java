package seminar.controller.algo.asymmetric;

import java.security.PrivateKey;
import java.security.PublicKey;

public abstract class AAsymmetric implements IAsymmetric {
	protected PublicKey publicKey;
	protected PrivateKey privateKey;

	public AAsymmetric() {
		super();
	}

	@Override
	public abstract boolean genKey(int size);

	@Override
	public abstract boolean loadPublicKey(String fileSrc);

	@Override
	public abstract boolean loadPrivateKey(String fileSrc);

	@Override
	public abstract boolean saveKey(String folderSrc, String filename, byte[] key);

	@Override
	public abstract String encrypt(String text);

	@Override
	public abstract String decrypt(String text);

	@Override
	public abstract String signText(String text);

	@Override
	public abstract String signFile(String fileSrc);

	@Override
	public abstract String verifySignature(String data, String signature);

	@Override
	public abstract String verifySignatureFile(String fileSrc, String signature);

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

}
