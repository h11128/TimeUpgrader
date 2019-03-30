package com.example.timeupgrader;

public class SingleAct extends Act {
    public static final int SET = 0;
    public static final int START = 1;
    public static final int PAUSE = 2;
    public static final int END = 3;
    public static final int DELETE = 4;

    private String owner;
    private int status;
    private long duration;
    private long currentTime;
    private boolean synced;




    public SingleAct(String id, String name, String description, int type, long startTime, boolean notify, boolean isTiming, long rewardPoint, String owner, int status, long duration, long currentTime, boolean synced) {

        super(id, name, description, type, startTime, notify, isTiming, rewardPoint);
        this.owner = owner;
        this.status = status;
        this.duration = duration;
        this.currentTime = currentTime;
        this.synced = synced;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusText() {
        switch (status) {
            case 0:
                return "Not started";
            case 1:
                return "Started";
            case 2:
                return "Paused";
            case 3:
                return "Ended";
            case 4:
                return "Deleted";
            default:
                break;
        }
        return "";
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDuration() {
        return duration;
    }

    public void setTotalTime(int duration) {
        this.duration = duration;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
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
