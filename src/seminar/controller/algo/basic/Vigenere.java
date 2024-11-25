package seminar.controller.algo.basic;

import seminar.model.Constant;

public class Vigenere extends ABasicAlgo {
	private String key;

	@Override
	protected boolean setKey(Object... keys) {
		try {
			key = ((String) keys[0]).toUpperCase().replaceAll(" ", "");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected String encryptEnglish(String text) {
		text = text.toUpperCase();
		StringBuilder cipher = new StringBuilder();
		for (int i = 0, j = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			if (Character.isAlphabetic(currentChar)) {
				int shift = key.charAt(j % key.length()) - 'A';
				char encryptedChar = (char) ((currentChar + shift - 'A') % 26 + 'A');
				cipher.append(encryptedChar);
				j++;
			} else {
				cipher.append(currentChar);
			}
		}
		return cipher.toString();
	}

	@Override
	protected String decryptEnglish(String text) {
		text = text.toUpperCase();
		StringBuilder decryptedText = new StringBuilder();
		for (int i = 0, j = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			if (Character.isAlphabetic(currentChar)) {
				int shift = key.charAt(j % key.length()) - 'A';
				char decryptedChar = (char) ((currentChar - shift - 'A' + 26) % 26 + 'A');
				decryptedText.append(decryptedChar);
				j++;
			} else {
				decryptedText.append(currentChar);
			}
		}
		return decryptedText.toString();
	}

	@Override
	protected String encryptVietnamese(String text) {
		StringBuilder cipher = new StringBuilder();
		int keyIndex = 0;

		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);

			if (Constant.VIET_ALPHABET.indexOf(currentChar) != -1) {
				int shift = Constant.VIET_ALPHABET.indexOf(key.charAt(keyIndex % key.length()));

				int encryptedIndex = (Constant.VIET_ALPHABET.indexOf(currentChar) + shift)
						% Constant.VIET_ALPHABET.length();
				cipher.append(Constant.VIET_ALPHABET.charAt(encryptedIndex));

				keyIndex++;
			} else {
				cipher.append(currentChar);
			}
		}

		return cipher.toString();
	}

	@Override
	protected String decryptVietnamese(String text) {
		StringBuilder decipheredText = new StringBuilder();
		int keyIndex = 0;

		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);

			if (Constant.VIET_ALPHABET.indexOf(currentChar) != -1) {
				int shift = Constant.VIET_ALPHABET.indexOf(key.charAt(keyIndex % key.length()));

				int decryptedIndex = (Constant.VIET_ALPHABET.indexOf(currentChar) - shift
						+ Constant.VIET_ALPHABET.length()) % Constant.VIET_ALPHABET.length();
				decipheredText.append(Constant.VIET_ALPHABET.charAt(decryptedIndex));

				keyIndex++;
			} else {
				decipheredText.append(currentChar);
			}
		}

		return decipheredText.toString();
	}
}
