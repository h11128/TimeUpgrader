package com.example.timeupgrader;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GroupAct extends Act {
    private ArrayList memberIds;
    private HashMap members;
    private HashMap memberStatus;
    private HashMap memberTotalTime;
    private HashMap memberCurrentTime;
    private int leader;

    public GroupAct(String id, String name, String description, long type, long startTime, boolean notify, boolean isTiming, int rewardPoint, ArrayList memberIds, HashMap members, HashMap memberStatus, HashMap memberTotalTime, HashMap memberCurrentTime, int leader) {
        super(id, name, description, type, startTime, notify, isTiming, rewardPoint);
        this.memberIds = memberIds;
        this.members = members;
        this.memberStatus = memberStatus;
        this.memberTotalTime = memberTotalTime;
        this.memberCurrentTime = memberCurrentTime;
        this.leader = leader;
    }

    public ArrayList getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(ArrayList memberIds) {
        this.memberIds = memberIds;
    }

    public boolean addMember(User user) {
        return true;
    }

    public boolean removeMember(User user) {
        return true;
    }

    public int getStatusByUser (User user) {
        return 0;
    }

    public boolean setStatusByUser (User user, int status) {
        return true;
    }

    public int getTotalTimeByUser (User user) {
        return 0;
    }

    public boolean setTotalTimeByUser (User user, int time) {
        return true;
    }

    public int getCurrentTimeByUser (User user) {
        return 0;
    }

    public boolean setCurrentTimeByUser (User user, int time) {
        return true;
    }

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public boolean startActByUser(User user) {
        return true;
    }

    public boolean pauseActByUser(User user) {
        return true;
    }

    public boolean endActByUser(User user) {
        return true;
    }
}
