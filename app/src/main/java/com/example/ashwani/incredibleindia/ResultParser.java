package com.example.ashwani.incredibleindia;

import android.annotation.SuppressLint;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashwani on 3/10/17.
 */

public class ResultParser {

    @SuppressLint("ByteOrderMark")
    private static final String BYTE_ORDER_MARK = "﻿";

    protected static String getMassagedText(Result result) {
        String text = result.getText();
        if (text.startsWith(BYTE_ORDER_MARK)) {
            return text.substring(1);
        }
        return text;
    }

    protected static String unescapeBackslash(String escaped) {
        int backslash = escaped.indexOf(92);
        if (backslash < 0) {
            return escaped;
        }
        int max = escaped.length();
        StringBuilder unescaped = new StringBuilder(max - 1);
        unescaped.append(escaped.toCharArray(), 0, backslash);
        boolean nextIsEscaped = false;
        for (int i = backslash; i < max; i++) {
            char c = escaped.charAt(i);
            if (nextIsEscaped || c != '\\') {
                unescaped.append(c);
                nextIsEscaped = false;
            } else {
                nextIsEscaped = true;
            }
        }
        return unescaped.toString();
    }

    static String[] matchPrefixedField(String prefix, String rawText, char endChar, boolean trim) {
        List<String> matches = null;
        int i = 0;
        int max = rawText.length();
        while (i < max) {
            i = rawText.indexOf(prefix, i);
            if (i < 0) {
                break;
            }
            i += prefix.length();
            int start = i;
            boolean more = true;
            while (more) {
                i = rawText.indexOf(endChar, i);
                if (i < 0) {
                    i = rawText.length();
                    more = false;
                } else if (rawText.charAt(i - 1) == '\\') {
                    i++;
                } else {
                    if (matches == null) {
                        matches = new ArrayList(3);
                    }
                    String element = unescapeBackslash(rawText.substring(start, i));
                    if (trim) {
                        element = element.trim();
                    }
                    if (!element.isEmpty()) {
                        matches.add(element);
                    }
                    i++;
                    more = false;
                }
            }
        }
        if (matches == null || matches.isEmpty()) {
            return null;
        }
        return (String[]) matches.toArray(new String[matches.size()]);
    }

    static String matchSinglePrefixedField(String prefix, String rawText, char endChar, boolean trim) {
        String[] matches = matchPrefixedField(prefix, rawText, endChar, trim);
        return matches == null ? null : matches[0];
    }
}
