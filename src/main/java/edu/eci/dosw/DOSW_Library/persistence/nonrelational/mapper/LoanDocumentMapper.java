package edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.model.Status;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.LoanDocument;
import org.springframework.stereotype.Component;

@Component
public class LoanDocumentMapper {

    public Loan toDomain(LoanDocument document) {
        if (document == null) return null;
        return Loan.builder()
                .id(document.getId())
                .bookId(document.getBookId())
                .userId(document.getUserId())
                .loanDate(document.getLoanDate())
                .returnDate(document.getReturnDate())
                .status(Status.valueOf(document.getStatus()))
                .build();
    }

    public LoanDocument toDocument(Loan loan) {
        if (loan == null) return null;
        return LoanDocument.builder()
                .id(loan.getId())
                .bookId(loan.getBookId())
                .userId(loan.getUserId())
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getReturnDate())
                .status(loan.getStatus().name())
                .build();
    }
}