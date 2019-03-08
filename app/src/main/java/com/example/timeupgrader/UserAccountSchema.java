package com.example.timeupgrader;

import android.provider.BaseColumns;

public class UserAccountSchema {
    private UserAccountSchema(){}

    public static class UASchema implements BaseColumns{
        public static final String Column_username = "UserName";
        public static final String Column_password = "Password";
        public static final String Column_userid = "UserId";
        public static final String Column_level = "Level";
        public static final String Column_point = "Point";
        public static final String Column_numFocuses = "numFocuses";
    }
}
