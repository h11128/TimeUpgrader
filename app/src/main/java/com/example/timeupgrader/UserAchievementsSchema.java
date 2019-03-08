package com.example.timeupgrader;

import android.provider.BaseColumns;

public class UserAchievementsSchema {
    private UserAchievementsSchema(){}

    public static class UserAchSchema implements BaseColumns {
        private static final String Column_AchieveId = "AchieveId";
        private static final String Column_AchieveTime = "AchieveTime";
    }
}
