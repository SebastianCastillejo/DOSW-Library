package edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.BookDocument;
import org.springframework.stereotype.Component;

@Component
public class BookDocumentMapper {

    public Book toDomain(BookDocument document) {
        if (document == null) return null;
        return Book.builder()
                .id(document.getId())
                .title(document.getTitle())
                .autor(document.getAutor())
                .isbn(document.getIsbn())
                .totalCopies(document.getTotalCopies())
                .availableCopies(document.getAvailableCopies())
                .build();
    }

    public BookDocument toDocument(Book book) {
        if (book == null) return null;
        return BookDocument.builder()
                .id(book.getId())
                .title(book.getTitle())
                .autor(book.getAutor())
                .isbn(book.getIsbn())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .build();
    }
}