package edu.eci.dosw.DOSW_Library.core.validator;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    // ── BookValidator ─────────────────────────

    @Test
    void testBookValidator_Exitoso() {
        assertDoesNotThrow(() -> BookValidator.validate("Clean Code", "Martin", "B001"));
    }

    @Test
    void testBookValidator_TituloVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> BookValidator.validate("", "Martin", "B001"));
    }

    @Test
    void testBookValidator_AutorVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> BookValidator.validate("Clean Code", "", "B001"));
    }

    @Test
    void testBookValidator_IdVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> BookValidator.validate("Clean Code", "Martin", ""));
    }

    @Test
    void testBookValidator_Book_Exitoso() {
        Book book = Book.builder()
                .id("B001")
                .title("Clean Code")
                .autor("Martin")
                .isbn("123")
                .totalCopies(5)
                .availableCopies(3)
                .build();
        assertDoesNotThrow(() -> BookValidator.validate(book));
    }

    @Test
    void testBookValidator_BookNulo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> BookValidator.validate((Book) null));
    }

    // ── UserValidator ─────────────────────────

    @Test
    void testUserValidator_Exitoso() {
        assertDoesNotThrow(() -> UserValidator.validate("Sebastian", "U001"));
    }

    @Test
    void testUserValidator_NombreVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validate("", "U001"));
    }

    @Test
    void testUserValidator_IdVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validate("Sebastian", ""));
    }

    @Test
    void testUserValidator_User_Exitoso() {
        User user = User.builder()
                .id("U001")
                .name("Sebastian")
                .username("seba123")
                .email("seba@mail.com")
                .password("pass")
                .role("USER")
                .build();
        assertDoesNotThrow(() -> UserValidator.validate(user));
    }

    @Test
    void testUserValidator_UserNulo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validate((User) null));
    }

    // ── LoanValidator ─────────────────────────

    @Test
    void testLoanValidator_Exitoso() {
        Book book = Book.builder()
                .id("B001").title("Clean Code")
                .autor("Martin").isbn("123")
                .totalCopies(5).availableCopies(3)
                .build();
        User user = User.builder()
                .id("U001").name("Sebastian")
                .username("seba123").email("seba@mail.com")
                .password("pass").role("USER")
                .build();
        assertDoesNotThrow(() -> LoanValidator.validate(book, user));
    }

    @Test
    void testLoanValidator_BookNulo_LanzaExcepcion() {
        User user = User.builder()
                .id("U001").name("Sebastian")
                .username("seba123").email("seba@mail.com")
                .password("pass").role("USER")
                .build();
        assertThrows(IllegalArgumentException.class,
                () -> LoanValidator.validate(null, user));
    }

    @Test
    void testLoanValidator_UserNulo_LanzaExcepcion() {
        Book book = Book.builder()
                .id("B001").title("Clean Code")
                .autor("Martin").isbn("123")
                .totalCopies(5).availableCopies(3)
                .build();
        assertThrows(IllegalArgumentException.class,
                () -> LoanValidator.validate(book, null));
    }
}