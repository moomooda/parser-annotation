package com.javatechie.spring.ajax.api.util;

public class StringUtil {

	/**
     * isBlank(null) = true;
     * isBlank("") = true;
     * isBlank("  ") = true;
     * isBlank("a") = false;
     * isBlank("a ") = false;
     * isBlank(" a") = false;
     * isBlank("a b") = false;
     *
     * @param str 字符串
     * @return 如果字符串为空或者长度为0，返回true，否则返回false
     */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	/**
     * isEmpty(null) = true;
     * isEmpty("") = true;
     * isEmpty("  ") = false;
     *
     * @param c 字符序列
     * @return 如果字符序列为空或者长度为0，返回true，否则返回false
     */
	public static boolean isEmpty(CharSequence c) {
		return (c == null || c.length() == 0);
	}

}
