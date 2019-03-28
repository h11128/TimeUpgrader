package com.example.timeupgrader;

import android.provider.BaseColumns;

public class UserAccountSchema {
    private UserAccountSchema(){}

    public static class UASchema implements BaseColumns{
        public static final String Table_UserAccount = "UserAccount";
        public static final String Column_UserName = "UserName";
        public static final String Column_Password = "Password";
        public static final String Column_Email = "Email";
        public static final String Column_UserId = "UserId";
        public static final String Column_Level = "Level";
        public static final String Column_Point = "Point";
        public static final String Column_NumFocuses = "numFocuses";
        public static final String Column_TimeCreated = "timeCreated";
    }
}
