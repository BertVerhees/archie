package com.nedap.archie.util;

import com.google.common.base.CaseFormat;

/**
 * Converts java names to openEHR names and vice versa
 *
 * Created by pieter.bos on 10/11/15.
 */
public class NamingUtil {

    public static String typeIdToClassName(String typeId) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, typeId);
    }

    public static String classNameToTypeId(String className) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, className);
    }

    public static String attributeToField(String attributeName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, attributeName);
    }

    public static String fieldToAttributeName(String fieldName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
    }

    public static String attributeNameToTypeId(String attributeName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, attributeName);
    }

    public static String attributeNameToGetMethod(String snakeCased) {
        return "get" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, snakeCased);
    }
}
