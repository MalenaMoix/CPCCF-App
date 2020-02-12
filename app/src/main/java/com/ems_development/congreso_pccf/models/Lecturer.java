package com.ems_development.congreso_pccf.models;

import java.time.LocalDate;

public class Lecturer {

    private String name;
    private String lastName;
    private String universityDegrees;
    private LocalDate birthDate;
    private News news;
    private String location;
    private Chat chat;

    public Lecturer() {
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

    public String getUniversityDegrees() {
        return universityDegrees;
    }

    public void setUniversityDegrees(String degrees) {
        this.universityDegrees = universityDegrees;
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

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
