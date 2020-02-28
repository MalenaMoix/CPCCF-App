package com.ems_development.congreso_pccf.models;

import java.util.List;


public class Lecturer {
    private String name;
    private String lastName;
    private String universityDegrees;
    private String birthDate;
    //private News news;
    private String location;
    //private List<Chat> chats;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*public List<Chat> getChat() {
        return chats;
    }

    public void setChat(List<Chat> chats) {
        this.chats = chats;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }*/
}
