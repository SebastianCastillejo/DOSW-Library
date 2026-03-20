package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.validator.BookValidator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final Map<String, Book> books;

    public BookService() {
        this.books = new HashMap<>();
    }

    public void addBook(String title, String autor, String id) {
        BookValidator.validate(title, autor, id);
        this.books.put(id, new Book(title, autor, id));
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

    public void deleteBookId(String id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException(id);
        }
        books.remove(id);
    }

    public void updateBook(String id, String title, String autor) {
        Book book = books.values().stream()
                .filter(k -> k.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setTitle(title);
        book.setAutor(autor);
    }

    public List<Book> getBooksByAutor(String autor) {
        List<Book> result = books.values().stream()
                .filter(b -> b.getAutor().equals(autor))
                .toList();
        if (result.isEmpty()) {
            throw new BookNotFoundException(autor);
        }
        return result;
    }

    public boolean existsBook(String id) {
        return books.containsKey(id);
    }
}
