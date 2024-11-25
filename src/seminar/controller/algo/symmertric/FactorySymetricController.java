package seminar.controller.algo.symmertric;

import seminar.model.ConstantAlgo;

public class FactorySymetricController {

	public static ASymmetric createSymmetricAlgo(String algoName, String mode, String padding) {
		String algo = "";
		if (mode.equals(ConstantAlgo.EMPTY) || mode.equals(ConstantAlgo.EMPTY)) {
			algo += algoName;
		} else {
			algo += algoName + "/" + mode + "/" + padding;
		}

		return new ASymmetric(algo);

	}

}
