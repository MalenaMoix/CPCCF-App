package com.ems_development.congreso_pccf.models;

import java.util.ArrayList;
import java.util.List;


public class Chat {
    private String chatRoom;
    private String endDate;
    private String name;
    private String startDate;
    //private List<Lecturer> lecturerList;
    //private List<User> users;

    public Chat() {
        //lecturerList = new ArrayList<>();
        //users = new ArrayList<>();
    }

    public Chat (String chatName, String endDate, String chatRoom , String startDate){ //List<Lecturer> lecturers)
        //this.lecturerList = lecturers;
        //users = new ArrayList<>();
        this.name = chatName;
        this.endDate = endDate;
        this.chatRoom = chatRoom;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    /*public List<Lecturer> getLecturerList() {
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
    }*/
}
