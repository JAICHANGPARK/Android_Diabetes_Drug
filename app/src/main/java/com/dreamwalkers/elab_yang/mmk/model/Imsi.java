package com.dreamwalkers.elab_yang.mmk.model;

import android.app.Application;

public class Imsi extends Application {
    private int position;
    private String item_timepoint;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getItem_timepoint() {
        return item_timepoint;
    }

    public void setItem_timepoint(String item_timepoint) {
        this.item_timepoint = item_timepoint;
    }
}
