package seminar.controller.algo.librarySysmmetric;

import seminar.model.ConstantAlgo;

public class FactorySymetricLibraryAlgo {

	public static ASymmetricLibrary createSymmetricLibraryAlgo(String algo) {
		algo = algo.toLowerCase();

		ASymmetricLibrary symmetricLibrary = null;

		if (algo.equals(ConstantAlgo.CHACHA20.toLowerCase())) {
			symmetricLibrary = new ChaCha20();
		} else if (algo.equals(ConstantAlgo.SALSA20.toLowerCase())) {
			symmetricLibrary = new Salsa20();
		} else if (algo.equals(ConstantAlgo.HC256.toLowerCase())) {
			symmetricLibrary = new HC256();
		} else if (algo.equals(ConstantAlgo.HC128.toLowerCase())) {
			symmetricLibrary = new HC256();
		}

		return symmetricLibrary;
	}
}
