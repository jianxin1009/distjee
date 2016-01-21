package net.distjee.common.utils;

import org.apache.commons.lang.ClassUtils;

/**
 * Created by king on 2016/1/19 0019.
 */
public class ClassUtil extends ClassUtils {
    /**
     * 判断一个类是JAVA类型还是用户定义类型
     * @param clz
     * @return
     */
    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    public static void main(String[] args) {
        System.out.println(isJavaClass(ClassUtil.class));
    }
}
