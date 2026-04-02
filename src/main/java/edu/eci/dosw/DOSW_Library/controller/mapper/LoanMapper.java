package edu.eci.dosw.DOSW_Library.controller.mapper;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanMapper {

    public LoanDTO toDTO(Loan loan) {
        if (loan == null) return null;
        return LoanDTO.builder()
                .id(loan.getId())
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getReturnDate())
                .status(loan.getStatus() != null ? LoanEntity.LoanStatus.valueOf(loan.getStatus().name()) : null)
                .build();
    }

    public List<LoanDTO> toDTOList(List<Loan> loans) {
        return loans.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}