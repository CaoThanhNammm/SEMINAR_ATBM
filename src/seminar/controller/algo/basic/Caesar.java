package seminar.controller.algo.basic;

import seminar.model.Constant;

public class Caesar extends ABasicAlgo {
	private int shift;

	@Override
	protected boolean setKey(Object... keys) {
		try {
			shift = Integer.parseInt((String) keys[0]);
			return true;
		} catch (Exception e) {
			System.out.println("Sai định dạng");
		}
		return false;
	}

	@Override
	protected String encryptEnglish(String text) {
		return caesarEncryptEnglish(text, shift);
	}

	@Override
	protected String decryptEnglish(String text) {
		return caesarEncryptEnglish(text, -shift);
	}

	private String caesarEncryptEnglish(String text, int shift) {
		StringBuilder encryptedText = new StringBuilder();

		for (char c : text.toCharArray()) {

			if (Character.isUpperCase(c)) {
				char encryptedChar = (char) (((c - 'A' + shift) % 26 + 26) % 26 + 'A');
				encryptedText.append(encryptedChar);
			}

			else if (Character.isLowerCase(c)) {
				char encryptedChar = (char) (((c - 'a' + shift) % 26 + 26) % 26 + 'a');
				encryptedText.append(encryptedChar);
			} else {
				encryptedText.append(c);
			}
		}

		return encryptedText.toString();
	}

	@Override
	protected String encryptVietnamese(String text) {
		StringBuilder encryptedText = new StringBuilder();
		int m = Constant.VIET_ALPHABET.length();

		for (char c : text.toCharArray()) {
			int index = Constant.VIET_ALPHABET.indexOf(c);
			if (index != -1) {
				int encryptedIndex = (index + shift) % m;
				encryptedText.append(Constant.VIET_ALPHABET.charAt(encryptedIndex));
			} else {
				encryptedText.append(c);
			}
		}
		return encryptedText.toString();
	}

	@Override
	protected String decryptVietnamese(String text) {
		StringBuilder decryptedText = new StringBuilder();
		int m = Constant.VIET_ALPHABET.length();

		for (char c : text.toCharArray()) {
			int index = Constant.VIET_ALPHABET.indexOf(c);
			if (index != -1) {

				int decryptedIndex = (index - shift + m) % m;
				decryptedText.append(Constant.VIET_ALPHABET.charAt(decryptedIndex));
			} else {
				decryptedText.append(c);
			}
		}
		return decryptedText.toString();
	}

}
