package edu.eci.dosw.DOSW_Library.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String autor;
    private String isbn;
    private Integer totalCopies;
    private Integer availableCopies;
}