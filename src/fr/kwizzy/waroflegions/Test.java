package fr.kwizzy.waroflegions;

import fr.kwizzy.waroflegions.util.java.MathsUtils;

/**
 * Par Alexis le 04/10/2016.
 */

public class Test {

	private static final double COEFF = 1.044663;
	public static void main(String[] args) {

		int i = MathsUtils.nextDecade(23);
		System.out.println(i);

	}
}
