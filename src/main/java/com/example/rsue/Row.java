package com.example.rsue;

public class Row {
    private String fio;
    private String type;
    private String title;
    private String time;

    public Row(){
        this.fio = "";
        this.type = "";
        this.title = "";
        this.time = "";
    }

    public Row(String fio, String type, String title, String time) {
        this.fio = fio;
        this.type = type;
        this.title = title;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
