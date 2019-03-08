package com.example.timeupgrader;

import java.util.ArrayList;

public class User {
    private String id;
    private String username;
    private int point;
    private int level;
    private int numFocusesDone;
    private ArrayList achievements;

    public User(String id, String username, int point, int level, int numFocusesDone, ArrayList achievements) {
        this.id = id;
        this.username = username;
        this.point = point;
        this.level = level;
        this.numFocusesDone = numFocusesDone;
        this.achievements = achievements;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean addPoint(int point) {
        return true;
    }

    public boolean subtractPoint(int point) {
        return true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean setLevelByPoint(int point) {
        return true;
    }

    public int getNumFocusesDone() {
        return numFocusesDone;
    }

    public void setNumFocusesDone(int numFocusesDone) {
        this.numFocusesDone = numFocusesDone;
    }

    public ArrayList getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList achievements) {
        this.achievements = achievements;
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
}
