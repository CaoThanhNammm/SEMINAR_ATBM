package seminar.controller.algo.basic;

import java.util.HashSet;
import java.util.Set;

public abstract class ABasicAlgo {
	protected boolean isVietnamese;

	protected void isVietnamese(String text) {
		String vietnamese = "àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ";
		char[] vietnameseChars = vietnamese.toCharArray();
		for (char c : vietnameseChars) {
			if (text.contains(c + "")) {
				isVietnamese = true;
				return;
			}
		}
		isVietnamese = false;
	}


	protected String encrypt(String text) {
		if (isVietnamese) {
			return encryptVietnamese(text);
		}
		return encryptEnglish(text);
	}

	protected String decrypt(String text) {
		if (isVietnamese) {
			return decryptVietnamese(text);
		}
		return decryptEnglish(text);
	}

	protected abstract boolean setKey(Object... keys);

	protected abstract String encryptEnglish(String text);

	protected abstract String decryptEnglish(String text);

	protected abstract String encryptVietnamese(String text);

	protected abstract String decryptVietnamese(String text);

}
