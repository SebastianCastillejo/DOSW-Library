package edu.eci.dosw.DOSW_Library.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    private String id;
    private String bookId;
    private String userId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private Status status;
}