package com.example.timeupgrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TaskDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "task.sqlite";
    private static final int Version = 1;
    private static final String Table_UserAccount = "UserAccount";
    private static final String Column_username = "UserName";
    private static final String Column_password = "Password";
    private static final String Column_userid = "UserId";
    private static final String Column_level = "Level";
    private static final String Column_point = "Point";
    private static final String Column_numFocuses = "numFocuses";
    private static final String Table_UserAchievements = "UserAchievements";
    private static final String Column_AchieveId = "AchieveId";
    private static final String Column_AchieveTime = "AchieveTime";
    private static final String Table_Achievements = "UserAccount";
    private static final String Column_AchieveName = "AchieveName";
    private static final String Column_AchieveDescription = "AchieveDescription";
    private static final String Column_Criterion = "Criterion";
    private static final String Column_Threshold = "Threshold";
    private static final String Table_UserGroupActivity = "UserGroupActivity";
    private static final String Column_ActId = "ActId";
    private static final String Column_MemberStatus = "MemberStatus";
    private static final String Column_gTotalTime = "gTotalTime";
    private static final String Column_gCurTime = "gCurTime";
    private static final String Column_UGAId = "UGAId";

    public TaskDatabaseHelper(Context context){
        super(context, DB_Name, null, Version);
    }


    public void OnCreate(SQLiteDatabase db){
        db.execSQL("create table UserAccount ("+
                "UserId String primary key, UserName String, LoginName String," +
                "Password String, level integer, point integer, numFocuses integer)");
        db.execSQL("create table UserAchievements ("+
                "UAId String primary key autoincrement, UserId UUID, AchieveId integer,AchieveTime integer)");
        db.execSQL("create table Achievements ("+
                "AchieveId String primary key, AchieveName String, AchieveDescription String," +
                "Criterion integer, Threshold integer)");
        db.execSQL("create table UserGroupActivity ("+
                "UGAId String primary key, UserId String, ActId String," +
                "MemberStatus integer, gTotalTime integer, gCurTime integer)");

        db.execSQL("create table useraccount ("+
                "UserId UUID primary key, UserName String, LoginName String," +
                "Password String, level integer, point integer, numFocuses integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Implement schema changes and data massage here when upgrading
    }
    public long insert_useraccount(User user,Account account) {
        ContentValues cv = new ContentValues();
        cv.put(Column_username, account.getUsername());
        cv.put(Column_password, account.getPassword());
        cv.put(Column_userid, account.getId());
        cv.put(Column_level, user.getLevel());
        cv.put(Column_point, user.getPoint());
        cv.put(Column_numFocuses, user.getNumFocusesDone());
        return getWritableDatabase().insert(Table_UserAccount, null, cv);
    }
    public long insert_UserAchievements(User user,Achievement achievement) {
        ContentValues cv = new ContentValues();
        cv.put(Column_userid, user.getId());
        cv.put(Column_AchieveId, achievement.getId());
        cv.put(Column_AchieveTime, achievement.getThreshold());
        return getWritableDatabase().insert(Table_UserAchievements, null, cv);
    }
    public long insert_Achievements(Achievement achievement) {
        ContentValues cv = new ContentValues();
        cv.put(Column_AchieveDescription, achievement.getDescription());
        cv.put(Column_AchieveName, achievement.getName());
        cv.put(Column_Criterion, achievement.getCriterion());
        cv.put(Column_Threshold, achievement.getThreshold());
        return getWritableDatabase().insert(Table_Achievements, null, cv);
    }
    public long insert_UserGroupActivity(GroupAct GA, Act act,User user){
        ContentValues cv = new ContentValues();
        cv.put(Column_gCurTime, GA.getCurrentTimeByUser(user));
        cv.put(Column_gTotalTime, GA.getTotalTimeByUser(user));
        cv.put(Column_MemberStatus, GA.getStatusByUser(user));
        cv.put(Column_UGAId, GA.getId());
        cv.put(Column_userid, user.getId());
        cv.put(Column_ActId, act.getId());
        return getWritableDatabase().insert(Table_UserGroupActivity, null, cv);
    }


}
