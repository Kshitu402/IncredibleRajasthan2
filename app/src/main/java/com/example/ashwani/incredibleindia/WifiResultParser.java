package com.example.ashwani.incredibleindia;

import com.google.zxing.Result;

/**
 * Created by ashwani on 4/10/17.
 */

public final class WifiResultParser extends ResultParser {
    public WifiParsedResult parse(Result result) {
        String rawText = ResultParser.getMassagedText(result);
        if (!rawText.startsWith("WIFI:")) {
            return null;
        }
        String ssid = ResultParser.matchSinglePrefixedField("S:", rawText, ';', false);
        if (ssid == null || ssid.isEmpty()) {
            return null;
        }
        String pass = ResultParser.matchSinglePrefixedField("P:", rawText, ';', false);
        String type = ResultParser.matchSinglePrefixedField("T:", rawText, ';', false);
        if (type == null) {
            type = "nopass";
        }
        return new WifiParsedResult(type, ssid, pass, Boolean.parseBoolean(ResultParser.matchSinglePrefixedField("H:", rawText, ';', false)));
    }
}