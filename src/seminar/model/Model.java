package seminar.model;

public class Model {
	private String text;
	private String encrypt;
	private String decrypt;

	private String algoName;
	private String mode;
	private String padding;

	public Model() {

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public String getDecrypt() {
		return decrypt;
	}

	public String getAlgoName() {
		return algoName;
	}

	public void setAlgoName(String algoName) {
		this.algoName = algoName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPadding() {
		return padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

	public void setDecrypt(String decrypt) {
		this.decrypt = decrypt;
	}

}
