package edu.eci.dosw.DOSW_Library.persistence.nonrelational.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "loans")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoanDocument {

    @Id
    private String id;

    private String bookId;
    private String userId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private String status;
}