package com.example.timeupgrader;

import java.util.Date;

public abstract class Act {
    public static final int SINGLE = 0;
    public static final int GROUP = 1;

    private String id;
    private String name;
    private String description;
    private int type;
    private Date startTime;
    private boolean notify;
    private boolean isTiming;
    private int rewardPoint;

    public Act(String id, String name, String description, int type, Date startTime, boolean notify, boolean isTiming, int rewardPoint) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.startTime = startTime;
        this.notify = notify;
        this.isTiming = isTiming;
        this.rewardPoint = rewardPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public boolean isTiming() {
        return isTiming;
    }

    public void setTiming(boolean timing) {
        isTiming = timing;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }
}
