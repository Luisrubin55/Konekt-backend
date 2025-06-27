package com.konekt.backend.Entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konekt.backend.Validation.ExistsByEmail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String username;
    @NotBlank()
    private String paternalSurname;
    private String maternalSurname;
    @NotNull
    private Date birthdate;
    @NotBlank
    private String genre;
    private String bio;
    private String profilePictureUrl;
    
    @ExistsByEmail
    @NotBlank
    private String email;
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Boolean active;
    private Long token;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPaternalSurname() {
        return paternalSurname;
    }
    public void setPaternalSurname(String paternalSurname) {
        this.paternalSurname = paternalSurname;
    }
    public String getMaternalSurname() {
        return maternalSurname;
    }
    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public Long getToken() {
        return token;
    }
    public void setToken(Long token) {
        this.token = token;
    }
    
}   
