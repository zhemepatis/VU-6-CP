package utils;

public class ArrayUtils {
    public static boolean isWithinRange(int rangeIdxFrom, int rangeIdxTo, int idx) {
        return rangeIdxFrom <= idx && rangeIdxTo >= idx;
    }
}