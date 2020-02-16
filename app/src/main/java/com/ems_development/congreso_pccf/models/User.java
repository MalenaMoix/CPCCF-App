package com.ems_development.congreso_pccf.models;

import java.time.LocalDate;
import java.util.List;

public class User {

    private Long dni;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String location;
    private List<Chat> chats;
    private List<News> newsList;
    private Boolean isAssistant = false;

    public User() {
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastName;
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

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public Boolean getAssistant() {
        return isAssistant;
    }

    public void setAssistant(Boolean assistant) {
        isAssistant = assistant;
    }
}
