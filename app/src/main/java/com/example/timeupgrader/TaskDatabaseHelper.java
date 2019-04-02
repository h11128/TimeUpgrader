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
import java.util.ArrayList;
import java.util.List;

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
                UASchema.Column_Email + " TEXT PRIMARY KEY," + /*UASchema.Column_Password + " TEXT," +*/
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
                ACT.Column_ActId + " TEXT PRIMARY KEY," + ACT.Column_Owner + " TEXT," +
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

    public void getLoginUser(String email) {

        String selection = UASchema.Column_Email + " = ?";
        String[] projection = {
                UASchema.Column_UserName,
                UASchema.Column_Email,
                UASchema.Column_Level,
                UASchema.Column_Point,
                UASchema.Column_NumFocuses,
                UASchema.Column_TimeCreated
        };

        Cursor cursor = getReadableDatabase().query(
                UASchema.Table_UserAccount,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                new String[] {email},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while (cursor.moveToNext()) {
            String uEmail = cursor.getString(cursor.getColumnIndexOrThrow(UASchema.Column_Email));
            String uUsername = cursor.getString(cursor.getColumnIndexOrThrow(UASchema.Column_UserName));
            long uPoint = cursor.getLong(cursor.getColumnIndexOrThrow(UASchema.Column_Point));
            long uLevel = cursor.getLong(cursor.getColumnIndexOrThrow(UASchema.Column_Level));
            long uNumFocusesDone = cursor.getLong(cursor.getColumnIndexOrThrow(UASchema.Column_NumFocuses));
            long uTimeCreated = cursor.getLong(cursor.getColumnIndexOrThrow(UASchema.Column_TimeCreated));
            User u = new User("", uEmail, uUsername, uPoint, uLevel, uNumFocusesDone, new ArrayList(), uTimeCreated);
        }

        cursor.close();
    }

    public long insert_User(User user) {
        ContentValues cv = new ContentValues();
        cv.put(UASchema.Column_UserName, user.getUsername());
        // cv.put(UASchema.Column_Password, account.getPassword());
        // cv.put(UASchema.Column_UserId, account.getId());
        cv.put(UASchema.Column_Email, user.getEmail());
        cv.put(UASchema.Column_Level, user.getLevel());
        cv.put(UASchema.Column_Point, user.getPoint());
        cv.put(UASchema.Column_NumFocuses, user.getNumFocusesDone());
        cv.put(UASchema.Column_TimeCreated, user.getTimeCreated());
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
        cv.put(ACT.Column_Owner, act.getOwner());
        cv.put(ACT.Column_ActName, act.getName());
        cv.put(ACT.Column_ActDescription, act.getDescription());
        cv.put(ACT.Column_ActType, act.getType());
        cv.put(ACT.Column_StartTime, act.getStartTime());
        cv.put(ACT.Column_Notify, act.isNotify() ? 1 : 0);
        cv.put(ACT.Column_IsTiming, act.isTiming() ? 1 : 0);
        cv.put(ACT.Column_Duration, act.getDuration());
        cv.put(ACT.Column_RewardPoint, act.getRewardPoint());
        cv.put(ACT.Column_Status, act.getStatus());
        cv.put(ACT.Column_Synced, act.isSynced() ? 1 : 0);
        return getWritableDatabase().insert(ACT.Table_Activity, null, cv);
    }

    public int delete_User(User user){
            String selection = UASchema.Column_UserName + " = ?";
            String[] selectionArgs = { user.getUsername() };
            return getWritableDatabase().delete(UASchema.Table_UserAccount, selection, selectionArgs);
    }

    public long updateActivityStatus(String actId, int status){
        String selection = ACT.Column_ActId + " = ?";
        String[] selectionArgs = { actId };
        ContentValues values = new ContentValues();
        values.put(ACT.Column_Status, status);
        values.put(ACT.Column_Synced, 0);
        return getWritableDatabase().update(ACT.Table_Activity, values, selection, selectionArgs);
    }

    public long getPointByEmail(String email) {
        String selection = UASchema.Column_Email + " = ?";
        String[] projection = {
                UASchema.Column_Point,
        };

        Cursor cursor = getReadableDatabase().query(
                UASchema.Table_UserAccount,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                new String[] {email},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        long point = -1;
        if (cursor.getCount() == 1) {
            cursor.moveToNext();
            point = cursor.getLong(cursor.getColumnIndexOrThrow(UASchema.Column_Point));
        }

        cursor.close();

        return point;
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

    public int updatePointByEmail(String email, long point) {
        String selection = UASchema.Column_Email + " = ?";
        String[] selectionArgs = { email };
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

    public int updateUsername(User user, String name) {
        String selection = UASchema.Column_Email + " = ?";
        String[] selectionArgs = { user.getEmail() };
        ContentValues values = new ContentValues();
        values.put(UASchema.Column_UserName, name);
        return getWritableDatabase().update(
                UASchema.Table_UserAccount,
                values,
                selection,
                selectionArgs);
    }

    public List<SingleAct> loadActivityByStatus(String email, int[] status, boolean syncRequested) {

        List<SingleAct> listAct = new ArrayList<>();

        String selection = ACT.Column_Owner + " = ?";
        String[] projection = {
                ACT.Column_ActId,
                ACT.Column_Owner,
                ACT.Column_ActName,
                ACT.Column_ActDescription,
                ACT.Column_ActType,
                ACT.Column_StartTime,
                ACT.Column_Notify,
                ACT.Column_IsTiming,
                ACT.Column_Duration,
                ACT.Column_RewardPoint,
                ACT.Column_Status,
                ACT.Column_Synced,
        };

        Cursor cursor = getReadableDatabase().query(
                ACT.Table_Activity,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                new String[] {email},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while (cursor.moveToNext()) {
            int curStatus = cursor.getInt(cursor.getColumnIndexOrThrow(ACT.Column_Status));
            boolean synced = cursor.getInt(cursor.getColumnIndexOrThrow(ACT.Column_Synced)) == 1;
            if (!syncRequested || !synced) {
                boolean flag = false;
                for (int s : status) {
                    if (curStatus == s) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(ACT.Column_ActId));
                    String owner = cursor.getString(cursor.getColumnIndexOrThrow(ACT.Column_Owner));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ACT.Column_ActName));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(ACT.Column_ActDescription));
                    int type = cursor.getInt(cursor.getColumnIndexOrThrow(ACT.Column_ActType));
                    long startTime = cursor.getLong(cursor.getColumnIndexOrThrow(ACT.Column_StartTime));
                    boolean notify = cursor.getInt(cursor.getColumnIndexOrThrow(ACT.Column_Notify)) == 1;
                    boolean isTiming = cursor.getInt(cursor.getColumnIndexOrThrow(ACT.Column_IsTiming)) == 1;
                    long duration = cursor.getLong(cursor.getColumnIndexOrThrow(ACT.Column_Duration));
                    long rewardPoint = cursor.getLong(cursor.getColumnIndexOrThrow(ACT.Column_RewardPoint));
                    listAct.add(new SingleAct(id, name, description, type, startTime, notify, isTiming,
                            rewardPoint, owner, curStatus, duration, 0, synced));
                }
            }
        }

        cursor.close();

        return listAct;
    }

    public Cursor getActCursorById(String id) {
        String selection = ActSchema.ACT.Column_ActId + " = ?";
        String[] projection = {
                ActSchema.ACT.Column_ActId,
                ActSchema.ACT.Column_Owner,
                ActSchema.ACT.Column_Synced,
        };
        return getReadableDatabase().query(
                ActSchema.ACT.Table_Activity,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                new String[] {id},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
    }
}

