package com.dreamwalkers.elab_yang.mmk.model;

public class TimePoint {
    private String timepoint;
    private String name;
    private String unit;

    public TimePoint(String timepoint) {
        this.timepoint = timepoint;
    }

    public TimePoint(String timepoint, String name) {
        this.timepoint = timepoint;
        this.name = name;
    }

    public String getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(String timepoint) {
        this.timepoint = timepoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
