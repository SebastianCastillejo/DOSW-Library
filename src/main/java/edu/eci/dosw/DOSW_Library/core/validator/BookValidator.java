package edu.eci.dosw.DOSW_Library.core.validator;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.util.ValidationUtil;

public class BookValidator {
    private BookValidator() {}

    public static void validate(String title, String autor, String id) {
        ValidationUtil.validateNotEmpty(title, "title");
        ValidationUtil.validateNotEmpty(autor, "autor");
        ValidationUtil.validateNotEmpty(id, "id");
    }

    public static void validate(Book book) {
        ValidationUtil.validateNotNull(book, "book");
        ValidationUtil.validateNotEmpty(book.getTitle(), "title");
        ValidationUtil.validateNotEmpty(book.getAutor(), "autor");
        ValidationUtil.validateNotEmpty(book.getId(), "id");
    }
}