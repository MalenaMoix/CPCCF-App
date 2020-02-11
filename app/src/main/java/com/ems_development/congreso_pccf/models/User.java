package com.ems_development.congreso_pccf.models;

import java.time.LocalDate;
import java.util.List;

public class User {

    private String email;
    private String password;
    private String name;
    private String lastname;
    private LocalDate birthDate;
    private String location;
    private List<Presentation> presentations;
    private Boolean isAssistant = false;

    public User() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Presentation> getPresentations() {
        return presentations;
    }

    public void setPresentations(List<Presentation> presentations) {
        this.presentations = presentations;
    }

    public Boolean getAssistant() {
        return isAssistant;
    }

    public void setAssistant(Boolean assistant) {
        isAssistant = assistant;
    }
}
