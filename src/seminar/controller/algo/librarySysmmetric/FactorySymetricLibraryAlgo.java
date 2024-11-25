package seminar.controller.algo.librarySysmmetric;

import seminar.model.ConstantAlgo;

public class FactorySymetricLibraryAlgo {

	public static ASymmetricLibrary createSymmetricLibraryAlgo(String algo) {
		algo = algo.toLowerCase();

		ASymmetricLibrary symmetricLibrary = null;

		if (algo.equals(ConstantAlgo.CHACHA20.toLowerCase())) {
			symmetricLibrary = new ChaCha20();
		} else if (algo.equals(ConstantAlgo.IDEA.toLowerCase())) {
			symmetricLibrary = new IDEA();
		} else if (algo.equals(ConstantAlgo.SERPENT.toLowerCase())) {
			symmetricLibrary = new Serpent();
		}

		return symmetricLibrary;
	}
}
