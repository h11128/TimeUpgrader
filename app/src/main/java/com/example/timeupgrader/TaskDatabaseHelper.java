package com.example.timeupgrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TaskDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "task.sqlite";
    private static final int Version = 1;
    private static final String Table_UserAccount = "UserAccount";
    private static final String Table_Achievements = "UserAccount";
    private static final String Column_username = "UserName";
    private static final String Column_password = "Password";
    private static final String Column_userid = "UserId";
    private static final String Column_level = "Level";
    private static final String Column_point = "Point";
    private static final String Column_numFocuses = "numFocuses";
    private static final String Table_UserAchievements = "UserAchievements";

    public TaskDatabaseHelper(Context context){
        super(context, DB_Name, null, Version);
    }

    @Override
    public void OnCreate(SQLiteDatabase db){
        db.execSQL("create table useraccount ("+
                "UserId integer primary key, UserName String, LoginName String" +
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

}
