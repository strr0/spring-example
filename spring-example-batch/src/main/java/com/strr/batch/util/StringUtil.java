package com.strr.batch.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final Pattern UNDERSCORE = Pattern.compile("_(\\S)");

    /**
     * 下划线转驼峰
     */
    public static String toCamelCase(String underscore) {
        Matcher matcher = UNDERSCORE.matcher(underscore);
        return matcher.replaceAll(r -> r.group(1).toUpperCase(Locale.ROOT));
    }
}
