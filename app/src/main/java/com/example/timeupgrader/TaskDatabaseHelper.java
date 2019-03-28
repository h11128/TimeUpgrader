package com.example.timeupgrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.timeupgrader.UserAccountSchema.*;
import com.example.timeupgrader.UserAchievementsSchema.*;
import com.example.timeupgrader.AchievementsSchema.*;

import com.example.timeupgrader.UGASchema.*;
import com.example.timeupgrader.ActSchema.*;

import java.text.SimpleDateFormat;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "task.db";
    private static final int Version = 1;

    public TaskDatabaseHelper(Context context){
        super(context, DB_Name, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + UASchema.Table_UserAccount + " (" +
                /*UASchema.Column_UserId + " TEXT PRIMARY KEY," +*/UASchema.Column_UserName +" TEXT," +
                UASchema.Column_Email + " TEXT PRIMARY KEY," + UASchema.Column_Password + " TEXT," +
                UASchema.Column_Level + " INTEGER," + UASchema.Column_Point + " INTEGER," +
                UASchema.Column_NumFocuses + " INTEGER," + UASchema.Column_TimeCreated + " TEXT)");
        db.execSQL("create table if not exists "+ UserAchSchema.Table_UserAchievements + "(" +
                UserAchSchema.Column_UAId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UASchema.Column_UserId + " TEXT," + AchSchema.Column_AchieveId + " INTEGER," +
                UserAchSchema.Column_AchieveTime + " INTEGER)");
        db.execSQL("create table if not exists " + AchSchema.Table_Achievements + " (" +
                AchSchema.Column_AchieveId + " INTEGER primary key autoincrement," +
                AchSchema.Column_AchieveName + " TEXT," + AchSchema.Column_AchieveDescription + " TEXT," +
                AchSchema.Column_Criterion + " INTEGER," + AchSchema.Column_Threshold +" INTEGER)");
        db.execSQL("create table if not exists " + UGA.Table_UserGroupActivity + " (" +
                UGA.Column_UGAId + " INTEGER primary key autoincrement," +
                UASchema.Column_UserId + " TEXT," + ACT.Column_ActId + " TEXT," +
                UGA.Column_MemberStatus + " INTEGER," + UGA.Column_gTotalTime + " INTEGER," +
                UGA.Column_gCurTime + " INTERGER)");
        db.execSQL("create table if not exists " + ACT.Table_Activity + " (" +
                ACT.Column_ActId + " TEXT PRIMARY KEY," + ACT.Column_Owner + "TEXT," +
                ACT.Column_ActName + " TEXT," + ACT.Column_ActDescription + " TEXT," +
                ACT.Column_ActType + " INTEGER," + ACT.Column_StartTime + " INTEGER," +
                ACT.Column_Notify + " INTEGER," + ACT.Column_IsTiming + " INTEGER," +
                ACT.Column_Duration + " INTEGER," + ACT.Column_RewardPoint + " INTEGER," +
                ACT.Column_Status + " INTEGER," + ACT.Column_Synced + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement schema changes and data massage here when upgrading
    }

    public long insert_UserAccount(Account account, User user) {
        ContentValues cv = new ContentValues();
        cv.put(UASchema.Column_UserName, account.getUsername());
        cv.put(UASchema.Column_Password, account.getPassword());
        // cv.put(UASchema.Column_UserId, account.getId());
        cv.put(UASchema.Column_Email, account.getEmail());
        cv.put(UASchema.Column_Level, user.getLevel());
        cv.put(UASchema.Column_Point, user.getPoint());
        cv.put(UASchema.Column_NumFocuses, user.getNumFocusesDone());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cv.put(UASchema.Column_TimeCreated, sdf.format(account.getTimeCreated()));
        return getWritableDatabase().insert(UASchema.Table_UserAccount, null, cv);
    }

    public long insert_UserAchievements(User user, Achievement achievement) {
            ContentValues cv = new ContentValues();
            cv.put(UASchema.Column_UserId, user.getId());
            cv.put(AchSchema.Column_AchieveId, achievement.getId());
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
            cv.put(ACT.Column_ActId, act.getId());
            return getWritableDatabase().insert(UGA.Table_UserGroupActivity, null, cv);
    }

    public long insert_Activity(SingleAct act){
        ContentValues cv = new ContentValues();
        cv.put(ACT.Column_ActId, act.getId());
        cv.put(ACT.Column_Owner, act.getOwner().getEmail());
        cv.put(ACT.Column_ActName, act.getName());
        cv.put(ACT.Column_ActDescription, act.getDescription());
        cv.put(ACT.Column_ActType, act.getType());
        cv.put(ACT.Column_StartTime, act.getStartTime().getTime());
        cv.put(ACT.Column_Notify, act.isNotify() ? 1 : 0);
        cv.put(ACT.Column_IsTiming, act.isTiming() ? 1 : 0);
        cv.put(ACT.Column_Duration, act.getDuration());
        cv.put(ACT.Column_RewardPoint, act.getRewardPoint());
        cv.put(ACT.Column_Status, act.getStatus());
        cv.put(ACT.Column_Synced, act.isSynced() ? 1 : 0);
        return getWritableDatabase().insert(ACT.Table_Activity, null, cv);
    }

    public long delete_Activity_ById(String actId){
        String selection = ACT.Column_ActId + " = ?";
        String[] selectionArgs = { actId };
        return getWritableDatabase().delete(ACT.Table_Activity, selection, selectionArgs);
    }

    public int delete_UserAccount(Account account){
            String selection = UASchema.Column_UserName + " LIKE ?";
            String[] selectionArgs = { account.getUsername() };
            int deletedRows = getWritableDatabase().delete(UASchema.Table_UserAccount, selection, selectionArgs);
            return deletedRows;
    }

    public int updatePoint(User user, long point) {
        String selection = UASchema.Column_Email + " = ?";
        String[] selectionArgs = { user.getEmail() };
        ContentValues values = new ContentValues();
        values.put(UASchema.Column_Point, point);
        return getWritableDatabase().update(
                UASchema.Table_UserAccount,
                values,
                selection,
                selectionArgs);
    }

    public int updateNumFocus(User user, long num) {
        String selection = UASchema.Column_Email + " = ?";
        String[] selectionArgs = { user.getEmail() };
        ContentValues values = new ContentValues();
        values.put(UASchema.Column_NumFocuses, num);
        return getWritableDatabase().update(
                UASchema.Table_UserAccount,
                values,
                selection,
                selectionArgs);
    }
}

