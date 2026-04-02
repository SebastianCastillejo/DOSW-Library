package edu.eci.dosw.DOSW_Library.core.validator;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.util.ValidationUtil;

public class LoanValidator {

    private LoanValidator() {}

    public static void validate(Book book, User user) {
        ValidationUtil.validateNotNull(book, "book");
        ValidationUtil.validateNotNull(user, "user");
    }
}