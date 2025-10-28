package com.example.demo.util;

public class ValidUtil {

    public static void isRequired(String value, String field) {
        if (value == null || value.isEmpty()) {
            throw new ValidException(new StringBuilder("¡")
                    .append(field).append(" es un campo requerido!").toString());
        }
    }

    public static void isRequired(Object value, String field) {
        if (value == null) {
            throw new ValidException(new StringBuilder("¡")
                    .append(field).append(" es un campo requerido!").toString());
        }
    }

    public static void comply(boolean value, String message) {
        if (!value) {
            throw new ValidException(message);
        }
    }

    public static void complyRegex(String value, String regex, String message) {
        if (!value.matches(regex)) {
            throw new ValidException(message);
        }
    }

}
