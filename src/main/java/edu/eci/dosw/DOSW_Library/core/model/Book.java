package edu.eci.dosw.DOSW_Library.core.model;

public class Book {
    private String title;
    private String autor;
    private String id;

    public Book(String title, String autor, String id) {
        this.title = title;
        this.autor = autor;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
