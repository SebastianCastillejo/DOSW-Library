package edu.eci.dosw.DOSW_Library.core.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void testBookCreation() {
        Book book = Book.builder()
                .id("1")
                .title("Clean Code")
                .autor("Robert Martin")
                .isbn("978-0132350884")
                .totalCopies(5)
                .availableCopies(3)
                .build();

        assertEquals("1", book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAutor());
    }

    @Test
    void testUserCreation() {
        User user = User.builder()
                .id("1")
                .name("Juan")
                .username("juan123")
                .email("juan@mail.com")
                .password("pass")
                .role("USER")
                .build();

        assertEquals("1", user.getId());
        assertEquals("Juan", user.getName());
        assertEquals("juan123", user.getUsername());
    }

    @Test
    void testLoanCreation() {
        Loan loan = Loan.builder()
                .id("1")
                .bookId("1")
                .userId("1")
                .status(Status.ACTIVE)
                .build();

        assertEquals("1", loan.getId());
        assertEquals(Status.ACTIVE, loan.getStatus());
    }
}