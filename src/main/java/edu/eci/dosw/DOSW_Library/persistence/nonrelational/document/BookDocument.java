package edu.eci.dosw.DOSW_Library.persistence.nonrelational.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookDocument {

    @Id
    private String id;

    private String title;
    private String autor;
    private String isbn;
    private Integer totalCopies;
    private Integer availableCopies;
}