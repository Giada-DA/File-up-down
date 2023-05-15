package com.example.fileupdown.entity;

import jakarta.persistence.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String nome;
    private String cognome;
    private String email;
    private String profilePicture;

    public User() {
    }

    public User(long id, String nome, String cognome, String email, String profilePicture) {
        Id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
