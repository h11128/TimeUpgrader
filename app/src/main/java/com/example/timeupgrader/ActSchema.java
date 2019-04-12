package com.example.timeupgrader;
import android.provider.BaseColumns;

public final class ActSchema {
    private ActSchema(){}

    public static class ACT implements BaseColumns{
        public static final String Table_Activity = "Activity";
        public static final String Column_ActId = "ActId";
        public static final String Column_Owner = "Owner";
        public static final String Column_ActName = "ActName";
        public static final String Column_ActDescription = "ActDescription";
        public static final String Column_ActType = "ActType";
        public static final String Column_StartTime = "StartTime";
        public static final String Column_Notify = "Notify";
        public static final String Column_IsTiming = "IsTiming";
        public static final String Column_Duration = "Duration";
        public static final String Column_RewardPoint = "RewardPoint";
        public static final String Column_Status = "Status";
        public static final String Column_Synced = "Synced";
        public static final String Column_Location = "Location";
    }
}
