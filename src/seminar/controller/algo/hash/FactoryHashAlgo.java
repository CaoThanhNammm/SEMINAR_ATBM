package seminar.controller.algo.hash;

import seminar.model.ConstantAlgo;

public class FactoryHashAlgo {

	public static AHash createAlgo(String algoName) {
		algoName = algoName.toLowerCase();

		if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		} else if (algoName.equals(ConstantAlgo.MD5.toLowerCase())) {
			return new AHash(algoName);
		}

		return null;
	}
}
