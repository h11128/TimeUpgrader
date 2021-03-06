package com.example.timeupgrader;

import android.provider.BaseColumns;

public class AchievementsSchema {
    private AchievementsSchema(){}

    public static class AchSchema implements BaseColumns{
        public static final String Table_Achievements = "Achievements";
        public static final String Column_AchieveId = "AchieveId";
        public static final String Column_AchieveName = "AchieveName";
        public static final String Column_AchieveDescription = "AchieveDescription";
        public static final String Column_Criterion = "Criterion";
        public static final String Column_Threshold = "Threshold";
    }
}
