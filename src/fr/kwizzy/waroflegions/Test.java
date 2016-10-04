package fr.kwizzy.waroflegions;

import fr.kwizzy.waroflegions.util.java.MathsUtils;

/**
 * Par Alexis le 04/10/2016.
 */

public class Test {

    private static final double COEFF = 1.044663;
    public static void main(String[] args) {
        double exp = 50;
        System.out.println("1 = 50" );
        for (int i = 2; i < 150; i++) {
            exp *= COEFF;
            System.out.println(i + " = " + MathsUtils.roundDouble(exp,2));
        }
    }


}
