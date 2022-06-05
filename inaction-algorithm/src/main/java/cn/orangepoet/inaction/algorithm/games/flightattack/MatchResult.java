package cn.orangepoet.inaction.algorithm.games.flightattack;

import org.apache.commons.lang.StringUtils;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public enum MatchResult {
    UNKNOWN,
    NONE,
    BODY,
    HEAD;

    public static MatchResult parse(String s) {
        if (StringUtils.isBlank(s)) {
            return UNKNOWN;
        }
        switch (s.toUpperCase()) {
            case "N":
                return NONE;
            case "B":
                return BODY;
            case "H":
                return HEAD;
            default:
                return UNKNOWN;
        }
    }
}
