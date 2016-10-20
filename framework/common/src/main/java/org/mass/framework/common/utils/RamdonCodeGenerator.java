package org.mass.framework.common.utils;


/**
 * Created by Allen on 2015/7/6.
 */
public class RamdonCodeGenerator {

	public static String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,

        "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,

        "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,

        "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,

        "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,

        "U" , "V" , "W" , "X" , "Y" , "Z"

    };


    public static String nextRandomCode(int length) {
        String code = "";
        for (int i = 0; i < length; i++) {
            code += chars[(int) RandomUtils.getRandomLong(0, 61)];
        }
        return code;
    }

    public static String nextRandomNumbericCode(int length) {
        String code = "";
        for (int i = 0; i < length; i++) {
            code += (int) RandomUtils.getRandomLong(0, 10);
        }
        return code;
    }

}
