package edu.eci.dosw.DOSW_Library.controller.dto;

import edu.eci.dosw.DOSW_Library.persistence.entity.LoanEntity.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private Long id;
    private BookDTO book;
    private UserDTO user;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanStatus status;
}