package com.example.timeupgrader;
import android.provider.BaseColumns;

public class ActSchema {
    private ActSchema(){}

    public static class ACT implements BaseColumns{
        private static final String Table_Activity = "UserGroupActivity";
        private static final String Column_ActId = "ActId";
        private static final String Column_ActName = "ActName";
        private static final String Column_ActDescription = "ActDescription";
        private static final String Column_ActType = "ActType";
        private static final String Column_StartTime = "StartTime";
        private static final String Column_Notify = "Notify";
        private static final String Column_IsTiming = "IsTiming";
        private static final String Column_RewardPoint = "RewardPoint";
        private static final String Column_Status = "Status";
    }
}
