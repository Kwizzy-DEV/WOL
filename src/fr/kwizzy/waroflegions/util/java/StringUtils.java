package fr.kwizzy.waroflegions.util.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class StringUtils {

    public static String getString(int length, char charToFill) {
        if (length > 0) {
            char[] array = new char[length];
            Arrays.fill(array, charToFill);
            return new String(array);
        }
        return "";
    }

    public static String toHexString(byte[] bytes){
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i< bytes.length;i++) {
            String hex=Integer.toHexString(0xff & bytes[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String safeSubstring(String s , int beginIndex , int endIndex){
        if(beginIndex < 0 || endIndex <0) return "";
        //We can do this with %
        else return s.substring(s.length() > beginIndex ? beginIndex : s.length() > endIndex ? endIndex : s.length());
    }

    public static final String LINE = "§f+§e§m------------------------------------------------§f+";

    public static String[] messageWithLine(String... s){
        LinkedList<String> lines = new LinkedList<>();
        lines.addFirst(LINE);
        Collections.addAll(lines, s);
        lines.addLast(LINE);
        String[] l = new String[lines.size()];
        l = lines.toArray(l);
        return l;
    }

    public static String parenthesisText(String text){
        return String.format("§7(§a%s§7)", text);
    }
}
