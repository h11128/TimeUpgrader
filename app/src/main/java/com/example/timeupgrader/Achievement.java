package com.example.timeupgrader;

public class Achievement {
    public static final int POINT = 0;
    public static final int ACTIVITY = 1;
    public static final int FOCUS = 2;

    private int id;
    private String name;
    private String description;
    private int criterion;
    private int threshold;

    public Achievement(int id, String name, String description, int criterion, int threshold) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.criterion = criterion;
        this.threshold = threshold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCriterion() {
        return criterion;
    }

    public void setCriterion(int criterion) {
        this.criterion = criterion;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }


}
