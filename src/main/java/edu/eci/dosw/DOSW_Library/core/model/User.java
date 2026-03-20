package edu.eci.dosw.DOSW_Library.core.model;

import lombok.Data;

@Data
public class User {
    private String name;
    private String id;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
