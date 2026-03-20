package edu.eci.dosw.DOSW_Library.core.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Loan {
    private Book book;
    private User user;
    private LocalDate loanDate;
    private Status status;

    public Loan(Book book, User user) {
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.status = Status.ACTIVE;
    }
}