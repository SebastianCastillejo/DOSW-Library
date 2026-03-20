package edu.eci.dosw.DOSW_Library.core.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    void testBook_Getters() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        assertEquals("B001", book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAutor());
    }

    @Test
    void testBook_Setters() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        book.setTitle("Refactoring");
        book.setAutor("Fowler");
        book.setId("B002");
        assertEquals("Refactoring", book.getTitle());
        assertEquals("Fowler", book.getAutor());
        assertEquals("B002", book.getId());
    }

    @Test
    void testBook_EqualsYHashCode() {
        Book book1 = new Book("Clean Code", "Robert Martin", "B001");
        Book book2 = new Book("Clean Code", "Robert Martin", "B001");
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testBook_ToString() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        assertNotNull(book.toString());
    }

    @Test
    void testUser_Getters() {
        User user = new User("Sebastian", "U001");
        assertEquals("U001", user.getId());
        assertEquals("Sebastian", user.getName());
    }

    @Test
    void testUser_Setters() {
        User user = new User("Sebastian", "U001");
        user.setName("Maria");
        user.setId("U002");
        assertEquals("Maria", user.getName());
        assertEquals("U002", user.getId());
    }

    @Test
    void testUser_EqualsYHashCode() {
        User user1 = new User("Sebastian", "U001");
        User user2 = new User("Sebastian", "U001");
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testUser_ToString() {
        User user = new User("Sebastian", "U001");
        assertNotNull(user.toString());
    }

    @Test
    void testLoan_Getters() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        User user = new User("Sebastian", "U001");
        Loan loan = new Loan(book, user);
        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(Status.ACTIVE, loan.getStatus());
        assertNotNull(loan.getLoanDate());
    }

    @Test
    void testLoan_Setters() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        User user = new User("Sebastian", "U001");
        Loan loan = new Loan(book, user);
        loan.setStatus(Status.RETURNED);
        assertEquals(Status.RETURNED, loan.getStatus());
    }

    @Test
    void testLoan_ToString() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        User user = new User("Sebastian", "U001");
        Loan loan = new Loan(book, user);
        assertNotNull(loan.toString());
    }

    @Test
    void testStatus_Valores() {
        assertEquals(Status.ACTIVE, Status.valueOf("ACTIVE"));
        assertEquals(Status.RETURNED, Status.valueOf("RETURNED"));
        assertEquals(2, Status.values().length);
    }

    @Test
    void testLoan_EqualsYHashCode() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        User user = new User("Sebastian", "U001");
        Loan loan1 = new Loan(book, user);
        Loan loan2 = new Loan(book, user);
        assertEquals(loan1, loan2);
        assertEquals(loan1.hashCode(), loan2.hashCode());
    }

    @Test
    void testLoan_SetBook() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        User user = new User("Sebastian", "U001");
        Loan loan = new Loan(book, user);
        Book newBook = new Book("Refactoring", "Fowler", "B002");
        loan.setBook(newBook);
        assertEquals("B002", loan.getBook().getId());
    }

    @Test
    void testLoan_SetUser() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        User user = new User("Sebastian", "U001");
        Loan loan = new Loan(book, user);
        User newUser = new User("Maria", "U002");
        loan.setUser(newUser);
        assertEquals("U002", loan.getUser().getId());
    }

    @Test
    void testLoan_SetLoanDate() {
        Book book = new Book("Clean Code", "Robert Martin", "B001");
        User user = new User("Sebastian", "U001");
        Loan loan = new Loan(book, user);
        java.time.LocalDate newDate = java.time.LocalDate.of(2026, 1, 1);
        loan.setLoanDate(newDate);
        assertEquals(newDate, loan.getLoanDate());
    }
}