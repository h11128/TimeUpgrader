package com.example.timeupgrader;

import android.provider.BaseColumns;

public class AchievementsSchema {
    private AchievementsSchema(){}

    public static class AchSchema implements BaseColumns{
        private static final String Column_AchieveName = "AchieveName";
        private static final String Column_AchieveDescription = "AchieveDescription";
        private static final String Column_Criterion = "Criterion";
        private static final String Column_Threshold = "Threshold";
    }
}
