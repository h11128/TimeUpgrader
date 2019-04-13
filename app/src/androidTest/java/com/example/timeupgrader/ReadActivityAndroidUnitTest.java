package com.example.timeupgrader;
import android.os.Parcel;
import android.util.Pair;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;


// @RunWith is required only if you use a mix of JUnit3 and JUnit4.
@RunWith(AndroidJUnit4.class)
public class ReadActivityAndroidUnitTest {



    @Test
    public void testData() {
        ViewActivity vv = new ViewActivity();
        SingleAct act = new SingleAct("1", "2",
                "3", 0, 4, true,
                false, 5, "6", SingleAct.SET, 0,
                7, false, "8");
        TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(vv.getApplicationContext());
        dbHelper.insert_Activity(act);
        String id = "1";
        String name = "2";
        String location = "8";
        List<SingleAct> mData = new ArrayList<>();
        mData = dbHelper.loadActivityByStatus("6",
                new int[]{SingleAct.SET, SingleAct.START}, false);
        assertEquals(mData.get(0), "1");
        assertEquals(mData.get(1), "2");
        assertEquals(mData.get(2), "3");
        assertEquals(mData.get(3), 0);
        assertEquals(mData.get(4), 4);
        assertEquals(mData.get(5), true);
        assertEquals(mData.get(6), false);
        assertEquals(mData.get(7), 5);
        assertEquals(mData.get(8), "6");
        assertEquals(mData.get(9), SingleAct.SET);
        assertEquals(mData.get(10), 0);
        assertEquals(mData.get(11), 7);
        assertEquals(mData.get(12), false);
        assertEquals(mData.get(13), "8");






        }
}
