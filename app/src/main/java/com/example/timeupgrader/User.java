package com.example.timeupgrader;

import java.util.ArrayList;
// import java.util.Date;

public class User {
    private String id;
    private String email;
    private String username;
    private long point;
    private long level;
    private long numFocusesDone;
    private ArrayList achievements;
    private long timeCreated;

    private static User currentUser;

    public User(String id, String email, String username, long point, long level, long numFocusesDone, ArrayList achievements, long timeCreated) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.point = point;
        this.level = level;
        this.numFocusesDone = numFocusesDone;
        this.achievements = achievements;
        this.timeCreated = timeCreated;
        currentUser = this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public boolean addPoint(long point) {
        if (point >= 0 && this.point <= Long.MAX_VALUE - point) this.point += point;
        return true;
    }

    public boolean subtractPoint(long point) {
        if (point >= 0 && this.point >= point) this.point -= point;
        return true;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public boolean setLevelByPoint(long point) {
        return true;
    }

    public long getNumFocusesDone() {
        return numFocusesDone;
    }

    public void setNumFocusesDone(long numFocusesDone) {
        this.numFocusesDone = numFocusesDone;
    }

    public ArrayList getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList achievements) {
        this.achievements = achievements;
    }

    public String getEmail() {
        return email;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public Act createAct() {
        return null;
    }

    public boolean start(Act act) {
        return true;
    }

    public boolean pause(Act act) {
        return true;
    }

    public boolean end(Act act) {
        return true;
    }

    public boolean share(Act act) {
        return true;
    }

    public boolean join(Act act) {
        return true;
    }

    public Focus createFocus() {
        return null;
    }

    public boolean focusOn(Focus focus) {
        return true;
    }

    public boolean viewAchievement() {
        return true;
    }

    public boolean viewHistory() {
        return true;
    }

    public boolean generateReport() {
        return true;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
