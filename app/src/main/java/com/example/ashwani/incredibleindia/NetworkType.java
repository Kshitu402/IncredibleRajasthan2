package com.example.ashwani.incredibleindia;

/**
 * Created by ashwani on 4/10/17.
 */

enum NetworkType {
    WEP,
    WPA,
    NO_PASSWORD;

    static NetworkType forIntentValue(String networkTypeString) {
        if (networkTypeString == null) {
            return NO_PASSWORD;
        }
        if ("WPA".equals(networkTypeString)) {
            return WPA;
        }
        if ("WEP".equals(networkTypeString)) {
            return WEP;
        }
        if ("nopass".equals(networkTypeString)) {
            return NO_PASSWORD;
        }
        throw new IllegalArgumentException(networkTypeString);
    }
}
