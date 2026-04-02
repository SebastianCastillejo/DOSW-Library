package edu.eci.dosw.DOSW_Library.core.util;

public class ValidationUtil {

    private ValidationUtil() {}

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + fieldName + " no puede estar vacio");
        }
    }

    public static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException("El campo " + fieldName + " no puede ser nulo");
        }
    }

    public static void validatePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException("El campo " + fieldName + " debe ser mayor a 0");
        }
    }
}
