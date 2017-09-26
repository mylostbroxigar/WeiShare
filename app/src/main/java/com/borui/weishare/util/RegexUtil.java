package com.borui.weishare.util;

import java.util.regex.Pattern;

/**
 * Created by borui on 2017/9/5.
 */

public class RegexUtil {

    public static boolean isCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[6-8])|(18[0-9]))\\d{8}$";
        return Pattern.matches(regex, cellphone);
    }

    public static boolean isPersonalid(String personalId){
        String regex="(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
        return Pattern.matches(regex,personalId);
    }

    public static boolean isEmail(String email){

        String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        return Pattern.matches(regex, email);
    }
}
