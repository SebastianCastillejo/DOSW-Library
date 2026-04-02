package edu.eci.dosw.DOSW_Library.core.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    void testValidateNotEmpty_Exitoso() {
        assertDoesNotThrow(() -> ValidationUtil.validateNotEmpty("valor", "campo"));
    }

    @Test
    void testValidateNotEmpty_Vacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidationUtil.validateNotEmpty("", "campo"));
    }

    @Test
    void testValidateNotEmpty_Nulo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidationUtil.validateNotEmpty(null, "campo"));
    }

    @Test
    void testValidateNotEmpty_SoloEspacios_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidationUtil.validateNotEmpty("   ", "campo"));
    }

    @Test
    void testValidateNotNull_Exitoso() {
        assertDoesNotThrow(() -> ValidationUtil.validateNotNull("valor", "campo"));
    }

    @Test
    void testValidateNotNull_Nulo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidationUtil.validateNotNull(null, "campo"));
    }

    @Test
    void testValidatePositive_Exitoso() {
        assertDoesNotThrow(() -> ValidationUtil.validatePositive(10.0, "monto"));
    }

    @Test
    void testValidatePositive_Cero_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidationUtil.validatePositive(0, "monto"));
    }

    @Test
    void testValidatePositive_Negativo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidationUtil.validatePositive(-5.0, "monto"));
    }
}
