package seminar.controller.algo.basic;

import java.util.Scanner;

import seminar.model.Constant;

public class Affine extends ABasicAlgo {
	private int a, b;

	public static int modInverse(int a, int m) {
		a = a % m;
		for (int x = 1; x < m; x++) {
			if ((a * x) % m == 1) {
				return x;
			}
		}
		return 1;
	}

	public int gcd(int a, int b) {
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	public boolean areCoprime(int a, int b) {
		return gcd(a, b) == 1;
	}

	@Override
	protected boolean setKey(Object... keys) {
		try {
			int nums1 = Integer.parseInt((String) keys[0]);
			int nums2 = Integer.parseInt((String) keys[1]);

			if (areCoprime(nums1, nums2)) {
				a = nums1;
				b = nums2;
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected String encryptEnglish(String text) {
		String cipher = "";
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);

			if (Character.isLetter(ch)) {
				char base = Character.isUpperCase(ch) ? 'A' : 'a';

				cipher += (char) ((a * (ch - base) + b) % 26 + base);
			} else {
				cipher += ch;
			}
		}
		return cipher;
	}

	@Override
	protected String decryptEnglish(String text) {
		String msg = "";
		int a_inv = modInverse(a, 26);

		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);

			if (Character.isLetter(ch)) {
				char base = Character.isUpperCase(ch) ? 'A' : 'a';

				msg += (char) (a_inv * ((ch - base) - b + 26) % 26 + base);
			} else {
				msg += ch;
			}
		}
		return msg;
	}

	@Override
	protected String encryptVietnamese(String text) {
		StringBuilder encryptedText = new StringBuilder();
		int m = Constant.VIET_ALPHABET.length();

		for (char c : text.toCharArray()) {
			int index = Constant.VIET_ALPHABET.indexOf(c);
			if (index != -1) {
				int encryptedIndex = (a * index + b) % m;
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
		int aInverse = modInverse(a, m);
		for (char c : text.toCharArray()) {
			int index = Constant.VIET_ALPHABET.indexOf(c);
			if (index != -1) {
				int decryptedIndex = (aInverse * (index - b + m)) % m;
				decryptedText.append(Constant.VIET_ALPHABET.charAt(decryptedIndex));
			} else {
				decryptedText.append(c);
			}
		}
		return decryptedText.toString();
	}
}
