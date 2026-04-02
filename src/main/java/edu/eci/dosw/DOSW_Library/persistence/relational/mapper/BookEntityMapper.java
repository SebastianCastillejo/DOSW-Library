package edu.eci.dosw.DOSW_Library.persistence.relational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookEntityMapper {

    public Book toDomain(BookEntity entity) {
        if (entity == null) return null;
        return Book.builder()
                .id(String.valueOf(entity.getId()))
                .title(entity.getTitle())
                .autor(entity.getAutor())
                .isbn(entity.getIsbn())
                .totalCopies(entity.getTotalCopies())
                .availableCopies(entity.getAvailableCopies())
                .build();
    }

    public BookEntity toEntity(Book book) {
        if (book == null) return null;
        return BookEntity.builder()
                .title(book.getTitle())
                .autor(book.getAutor())
                .isbn(book.getIsbn())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .build();
    }
}