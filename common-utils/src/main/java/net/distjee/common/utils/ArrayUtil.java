package net.distjee.common.utils;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 2016/1/19 0019.
 */
public class ArrayUtil extends ArrayUtils {
    public static <T> List<T> toList(T[] pks) {
        List<T> result = new ArrayList<T>();
        if (isNotEmpty(pks)) {
            for (int i = 0; i < pks.length; i++) {
                result.add(pks[i]);
            }
        }
        return result;
    }
}
