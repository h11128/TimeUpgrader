package com.example.timeupgrader;

import android.provider.BaseColumns;

public class UGASchema {
    private UGASchema(){};

    public static class UGA implements BaseColumns {
        private static final String Table_UserGroupActivity = "UserGroupActivity";
        private static final String Column_MemberStatus = "MemberStatus";
        private static final String Column_gTotalTime = "gTotalTime";
        private static final String Column_gCurTime = "gCurTime";
        private static final String Column_UGAId = "UGAId";

    }
}
