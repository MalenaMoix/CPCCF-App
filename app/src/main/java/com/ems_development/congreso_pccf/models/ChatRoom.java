package com.ems_development.congreso_pccf.models;

import java.util.List;

public class ChatRoom {

    private Integer numberRoom;
    private Integer capacity;
    private List<Presentation> Presentations;

    public ChatRoom() {
    }

    public Integer getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(Integer numberRoom) {
        this.numberRoom = numberRoom;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Presentation> getPresentations() {
        return Presentations;
    }

    public void setPresentations(List<Presentation> presentations) {
        Presentations = presentations;
    }
}
