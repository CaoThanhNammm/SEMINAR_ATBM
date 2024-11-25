package seminar.controller.algo.basic;

import seminar.model.ConstantAlgo;

public class FactoryBasicAlgo {

	public static ABasicAlgo createBasicAlgo(String algoName) {
		algoName = algoName.toLowerCase();
		if (algoName.equals(ConstantAlgo.AFFINE.toLowerCase())) {
			return new Affine();
		} else if (algoName.equals(ConstantAlgo.CAESAR.toLowerCase())) {
			return new Caesar();
		} else if (algoName.equals(ConstantAlgo.VIGENERE.toLowerCase())) {
			return new Vigenere();
		}

		return null;
	}
}
