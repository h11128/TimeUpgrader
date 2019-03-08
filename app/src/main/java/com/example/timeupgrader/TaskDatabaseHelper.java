package com.example.timeupgrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.timeupgrader.UserAccountSchema.*;
import com.example.timeupgrader.UserAchievementsSchema.*;
import com.example.timeupgrader.AchievementsSchema.*;

import com.example.timeupgrader.UGASchema.*

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "task.sqlite";
    private static final int Version = 1;


        public TaskDatabaseHelper(Context context){
            super(context, DB_Name, null, Version);
        }


        public void OnCreate(SQLiteDatabase db) {

            db.execSQL("create table " +  UASchema.Table_UserAccount + "(" +
                    UASchema.Column_UserId + "TEXT PRIMARY KEY,"+ UASchema.Column_UserName +"TEXT," +
                    UASchema.Column_Email + "TEXT," + UASchema.Column_Password + "TEXT," + UASchema.Column_Level+ "INTEGER," +
                    UASchema.Column_Point + "INTEGER," + UASchema.Column_NumFocuses + "INTEGER)");

            db.execSQL("create table UserAchievements (" +
                    "UAId INTEGET primary key autoincrement, UserId TEXT, AchieveId INTEGER,AchieveTime INTEGER)");

            db.execSQL("create table Achievements (" +
                    "AchieveId INTEGER primary key, AchieveName TEXT, AchieveDescription TEXT," +
                    "Criterion INTEGER, Threshold INTEGER)");

            db.execSQL("create table UserGroupActivity (" +
                    "UGAId TEXT primary key, UserId TEXT, ActId TEXT," +
                    "MemberStatus INTEGER, gTotalTime INTEGER, gCurTime INTERGER)");

            db.execSQL("create table Activity (" +
                    "ActId TEXT primary key, ActName TEXT, ActDescription TEXT," +
                    "ActType INTEGER, StartTime INTEGER, Notify INTEGER," +
                    "IsTiming INTEGER, RewardPoint INTEGER, Status INTEGER)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Implement schema changes and data massage here when upgrading
        }
        public long insert_useraccount(User user, Account account) {
            ContentValues cv = new ContentValues();
            cv.put(UASchema.Column_UserName, account.getUsername());
            cv.put(UASchema.Column_Password, account.getPassword());
            cv.put(UASchema.Column_UserId, account.getId());
            cv.put(UASchema.Column_Email,account.getEmail());
            cv.put(UASchema.Column_Level, user.getLevel());
            cv.put(UASchema.Column_Point, user.getPoint());
            cv.put(UASchema.Column_NumFocuses, user.getNumFocusesDone());
            return getWritableDatabase().insert(UASchema.Table_UserAccount, null, cv);
        }
        public long insert_UserAchievements(User user, Achievement achievement) {
            ContentValues cv = new ContentValues();
            cv.put(UASchema.Column_UserId, user.getId());
            cv.put(UserAchSchema.Column_AchieveId, achievement.getId());
            cv.put(UserAchSchema.Column_AchieveTime, achievement.getThreshold());
            return getWritableDatabase().insert(UserAchSchema.Table_UserAchievements, null, cv);
        }
        public long insert_Achievements(Achievement achievement) {
            ContentValues cv = new ContentValues();
            cv.put(AchSchema.Column_AchieveDescription, achievement.getDescription());
            cv.put(AchSchema.Column_AchieveName, achievement.getName());
            cv.put(AchSchema.Column_Criterion, achievement.getCriterion());
            cv.put(AchSchema.Column_Threshold, achievement.getThreshold());
            return getWritableDatabase().insert(AchSchema.Table_Achievements, null, cv);
        }
        public long insert_UserGroupActivity(GroupAct GA, Act act, User user){
            ContentValues cv = new ContentValues();
            cv.put(UGA.Column_gCurTime, GA.getCurrentTimeByUser(user));
            cv.put(UGA.Column_gTotalTime, GA.getTotalTimeByUser(user));
            cv.put(UGA.Column_MemberStatus, GA.getStatusByUser(user));
            cv.put(UGA.Column_UGAId, GA.getId());
            cv.put(UASchema.Column_UserId, user.getId());
            cv.put(.Column_ActId, act.getId());
            return getWritableDatabase().insert(UGA.Table_UserGroupActivity, null, cv);
        }
        public long insert_Activity(Act act){
            ContentValues cv = new ContentValues();
            cv.put(Column_ActId, act.getId());
            cv.put(Column_ActName, act.getName());
            cv.put(Column_ActDescription, act.getDescription());
            cv.put(Column_ActType, act.getType());
            cv.put(Column_StartTime, act.getStartTime());
            cv.put(Column_Notify, act.isNotify());
            cv.put(Column_IsTiming, act.isTiming());
            cv.put(Column_RewardPoint, act.getRewardPoint());
            cv.put(Column_Status, act.getStatus());
            return getWritableDatabase().insert(.Table_Activity, null, cv);
        }
}
