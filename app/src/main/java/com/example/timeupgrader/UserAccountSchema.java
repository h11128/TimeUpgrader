package com.example.timeupgrader;

import android.provider.BaseColumns;

public class UserAccountSchema {
    private UserAccountSchema(){}

    public static class UASchema implements BaseColumns{
        public static final String Column_username = "UserName";
        private static final String Column_password = "Password";
        private static final String Column_userid = "UserId";
        private static final String Column_level = "Level";
        private static final String Column_point = "Point";
        private static final String Column_numFocuses = "numFocuses";
    }
}
