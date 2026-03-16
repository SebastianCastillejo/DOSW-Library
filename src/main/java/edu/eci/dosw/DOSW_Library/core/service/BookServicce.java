package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.controller.dto.UserDTO;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class BookServicce {
    private final Map<String, Book> books;

    public BookServicce() {
        this.books = new HashMap<>();
    }

    public void addBook(String title, String name, String id) {
        this.books.put(id, new Book(title, name, id));
    }

    public Book getBookById(String id) {
        return books.values().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
}
