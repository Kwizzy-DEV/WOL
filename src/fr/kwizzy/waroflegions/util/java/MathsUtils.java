package fr.kwizzy.waroflegions.util.java;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Par Alexis le 30/09/2016.
 */

public class MathsUtils {

    public static String toRomanNumber(int number) {
        LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
        roman_numerals.put("M", 1000);
        roman_numerals.put("CM", 900);
        roman_numerals.put("D", 500);
        roman_numerals.put("CD", 400);
        roman_numerals.put("C", 100);
        roman_numerals.put("XC", 90);
        roman_numerals.put("L", 50);
        roman_numerals.put("XL", 40);
        roman_numerals.put("X", 10);
        roman_numerals.put("IX", 9);
        roman_numerals.put("V", 5);
        roman_numerals.put("IV", 4);
        roman_numerals.put("I", 1);
        String res = "";
        for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
            int matches = number / entry.getValue();
            res += repeat(entry.getKey(), matches);
            number = number % entry.getValue();
        }
        return res;
    }

    private static String repeat(String s, int n) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }


    public static boolean chanceOf(int d) {
        int fD = randomInteger(0, 100);
        return fD <= d;
    }

    public static double roundDouble(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static int randomInteger(int low, int high) {
        Random r = new Random();
        return r.nextInt((high + 1) - low) + low;
    }

    public static int amountTimeFor(double base, double number) {
        double n = base;
        int t = 0;
        if (number < 11)
            return 1;
        while (n < number) {
            n += base;
            t++;
        }
        return t;
    }

    public static int nextDecade(int i){
        if(i % 10 == 0)
            return i;
        while (i % 10 != 0){
            i++;
        }
        return i;
    }

    public static Set<Integer> getBorderSlot(int size){
        final int t = 9;
        final int d = (int) (t/2.0 + (1.0));
        final int midleDown = size*t - d;
        Set<Integer> slots = new HashSet<>();
        for (int i = 0; i < size; i++) {
            slots.add(i*t);
            slots.add(t-1 + (i*t));
        }
        for (int i = 0; i < t-1; i++) {
            slots.add(i);
            slots.add(i+(t*size - t));
        }
        slots.remove(midleDown);

        List<Integer> autorized = new ArrayList<>();
        for (int i = 0; i < size * t; i++) {
            autorized.add(i);
        }
        List<Integer> nAutorized = slots.stream().filter(slot -> !autorized.contains(slot)).collect(Collectors.toList());
        slots.removeAll(nAutorized);
        return slots;
    }
}