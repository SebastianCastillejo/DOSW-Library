package edu.eci.dosw.DOSW_Library.core.model;

import lombok.Data;

@Data
public class Book {
    private String title;
    private String autor;
    private String id;

    public Book(String title, String autor, String id) {
        this.title = title;
        this.autor = autor;
        this.id = id;
    }
}