package com.ems_development.congreso_pccf.models;

import java.time.LocalDateTime;
import java.util.List;

public class Chat {

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String chatRoom;
    private List<Lecturer> lecturerList;
    private List<User> users;

    public Chat() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getRoom() {
        return chatRoom;
    }

    public void setRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public List<Lecturer> getLecturerList() {
        return lecturerList;
    }

    public void setLecturer(List<Lecturer> lecturerList) {
        this.lecturerList = lecturerList;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
