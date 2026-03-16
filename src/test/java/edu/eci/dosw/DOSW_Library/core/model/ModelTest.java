package edu.eci.dosw.DOSW_Library.core.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    void testBook() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        assertEquals("B001", book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAutor());

        book.setTitle("Refactoring");
        book.setAutor("Fowler");
        book.setId("B002");
        assertEquals("Refactoring", book.getTitle());
        assertEquals("Fowler", book.getAutor());
        assertEquals("B002", book.getId());
    }

    @Test
    void testUser() {
        User user = new User("Sebastian", "U001");
        assertEquals("U001", user.getId());
        assertEquals("Sebastian", user.getName());

        user.setName("Maria");
        user.setId("U002");
        assertEquals("Maria", user.getName());
        assertEquals("U002", user.getId());
    }

    @Test
    void testLoan() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        User user = new User("Sebastian", "U001");
        Loan loan = new Loan(book, user);

        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(Status.ACTIVE, loan.getStatus());
        assertNotNull(loan.getLoanDate());

        loan.setStatus(Status.RETURNED);
        assertEquals(Status.RETURNED, loan.getStatus());
    }

    @Test
    void testStatus() {
        assertEquals(Status.ACTIVE, Status.valueOf("ACTIVE"));
        assertEquals(Status.RETURNED, Status.valueOf("RETURNED"));
    }
}