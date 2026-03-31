package edu.eci.dosw.DOSW_Library.persistence.relational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.model.Status;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanEntityMapper {

    public Loan toDomain(LoanEntity entity) {
        if (entity == null) return null;
        return Loan.builder()
                .id(String.valueOf(entity.getId()))
                .bookId(String.valueOf(entity.getBook().getId()))
                .userId(String.valueOf(entity.getUser().getId()))
                .loanDate(entity.getLoanDate())
                .returnDate(entity.getReturnDate())
                .status(Status.valueOf(entity.getStatus().name()))
                .build();
    }

    public LoanEntity toEntity(Loan loan) {
        if (loan == null) return null;
        return LoanEntity.builder()
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getReturnDate())
                .build();
    }
}