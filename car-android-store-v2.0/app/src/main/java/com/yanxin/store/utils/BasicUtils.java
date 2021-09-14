package com.yanxin.store.utils;

import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * 基本工具类，一些简单地工具类集合
 */
public class BasicUtils {
    /**
     * 防止一个页面过多isEmpty冗余代码
     *
     * @param tv
     * @return
     */
    public static boolean strIsEmpty(String tv) {
        return tv == null || tv.isEmpty();
    }

    /**
     * 返回Edit 文本
     */
    public static String getEditValue(EditText editText) {
        return editText.getText().toString().trim();
    }


    /**
     * 返回文件格式
     */
    public static String getFileFormat(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    /**
     * 从指定位置替换字符串
     */
    public static String setStringValue(int index, String str, char reply) {
        StringBuffer stringBuffer = new StringBuffer(str);
        for (int i = index; i < stringBuffer.length(); i++) {
            stringBuffer.setCharAt(i, reply);
        }
        return stringBuffer.toString();
    }

    /**
     * 返回两位小数的, 转成Float
     */
    public static String floatDecimalFormat(float amount) {
        return new DecimalFormat("0.00").format(amount);
    }

}
