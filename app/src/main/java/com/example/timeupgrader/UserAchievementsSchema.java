package com.example.timeupgrader;

import android.provider.BaseColumns;

public class UserAchievementsSchema {
    private UserAchievementsSchema(){}

    public static class UserAchSchema implements BaseColumns {

        public static final String Table_UserAchievements = "UserAchievements"
        public static final String Column_AchieveId = "AchieveId";
        public static final String Column_AchieveTime = "AchieveTime";
    }
}
