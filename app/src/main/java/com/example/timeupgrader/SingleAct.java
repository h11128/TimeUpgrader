package com.example.timeupgrader;

import java.util.Date;
import java.util.UUID;

public class SingleAct extends Act {
    public static final int SET = 0;
    public static final int START = 1;
    public static final int PAUSE = 2;
    public static final int END = 3;

    private User owner;
    private int status;
    private int totalTime;
    private int currentTime;

    public SingleAct(UUID id, String name, String description, int type, Date startTime, boolean notify, boolean isTiming, int rewardPoint, User owner, int status, int totalTime, int currentTime) {
        super(id, name, description, type, startTime, notify, isTiming, rewardPoint);
        this.owner = owner;
        this.status = status;
        this.totalTime = totalTime;
        this.currentTime = currentTime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public boolean startAct() {
        return true;
    }

    public boolean pauseAct() {
        return true;
    }

    public boolean endAct() {
        return true;
    }
}
