package org.mass.framework.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Allen on 2015/4/18.
 */
public class RegexUtil {

    public static final String numericPattern = "([1-9]+[0-9]*|0)(\\.[\\d]+)?";

    public static String getMatchData(String pattern, String matcher, int index) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(matcher);
        if (m.find()) {
            return m.group(index);
        } else {
            return "";
        }
    }

    public static String getMatchData(Pattern pattern, String text, int index) {
        Matcher m = pattern.matcher(text);
        if (m.find()) {
            return m.group(index);
        } else {
            return "";
        }
    }

    public static String getSingleMatchData(String pattern, String text) {
        return getMatchData(pattern,text,0);
    }


    public static List<String> getMatchData(String pattern, String matcher) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(matcher);
        List<String> result = new ArrayList<String>();
        while (m.find()) {
            result.add(m.group(0));
        }
        return result;
    }

    public static String getNumericData(String matcher) {
        return getSingleMatchData(numericPattern,matcher);
    }

    /**
     * 获得括号里的字符串
     */
    public static String getBracketsData(String input) {
        String result = getSingleMatchData("\\(.*?\\)",input);
        return result.substring(1,result.length()-1);
    }

}
