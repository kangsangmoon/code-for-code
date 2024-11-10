package com.codeforcode.example.domain;

import java.util.ArrayList;
import java.util.List;

public class ExampleParser {

    public static List<Object> parseInExample(String inExample) {
        String[] tokens = inExample.split(",");
        List<Object> parsedInputs = new ArrayList<>();

        for (String token : tokens) {
            Object parsedValue = parseToken(token.trim());
            parsedInputs.add(parsedValue);
        }

        return parsedInputs;
    }

    private static Object parseToken(String token) {
        try {
            if (token.contains(".")) {
                return Double.parseDouble(token);
            }

            return Integer.parseInt(token);

        } catch (NumberFormatException e) {
            return token;
        }
    }
}