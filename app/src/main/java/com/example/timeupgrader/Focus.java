package com.example.timeupgrader;

import java.util.Date;

public class Focus {
    private String name;
    private String description;
    private int duration;
    private int rewardPoint;
    private boolean success;
    private Date timeCreated;

    public Focus(String name, String description, int duration, int rewardPoint, boolean success, Date timeCreated) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.rewardPoint = rewardPoint;
        this.success = success;
        this.timeCreated = timeCreated;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public int startFocus() {
        return 0;
    }

    public boolean endFocus() {
        return true;
    }
}
