package com.yanxin.store.utils;


import com.yanxin.store.annoation.XmlLayoutResId;

public class XmlLayoutResIdUtils {
    public static int checkRes(Object clazz) {
        boolean annotationPresent = clazz.getClass().isAnnotationPresent(XmlLayoutResId.class);
        if (!annotationPresent) {
            throw new IllegalStateException("Activity或Fragment必须有注解布局文件");
        }
        XmlLayoutResId annotation = clazz.getClass().getAnnotation(XmlLayoutResId.class);
        return annotation.contentId();
    }
}
