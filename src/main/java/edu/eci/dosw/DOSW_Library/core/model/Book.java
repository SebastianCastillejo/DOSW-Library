package edu.eci.dosw.DOSW_Library.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String id;
    private String title;
    private String autor;
    private String isbn;
    private Integer totalCopies;
    private Integer availableCopies;
}