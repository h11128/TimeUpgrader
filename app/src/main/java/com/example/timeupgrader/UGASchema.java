package com.example.timeupgrader;

import android.provider.BaseColumns;

public final class UGASchema {
    private UGASchema(){};

    public static class UGA implements BaseColumns {
        public static final String Table_UserGroupActivity = "UserGroupActivity";
        public static final String Column_MemberStatus = "MemberStatus";
        public static final String Column_gTotalTime = "gTotalTime";
        public static final String Column_gCurTime = "gCurTime";
        public static final String Column_UGAId = "UGAId";

    }
}
