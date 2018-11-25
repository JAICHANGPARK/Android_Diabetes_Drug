package com.dreamwalkers.elab_yang.mmk.model;

public class Dashboard {
    private int type;
    private String title;
    private String message;

    public Dashboard(int type) {
        this.type = type;
    }

    public Dashboard(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
