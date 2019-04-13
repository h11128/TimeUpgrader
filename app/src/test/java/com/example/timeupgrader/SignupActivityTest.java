package com.example.timeupgrader;

import org.junit.Test;
import static org.junit.Assert.*;

public class SignupActivityTest {

    @Test
    public void testIsPassword() {
        assertTrue(SignupActivity.isPassword("avdv443e"));
        assertTrue(SignupActivity.isPassword("dieuttride"));
        assertTrue(SignupActivity.isPassword("3478fje"));
        assertTrue(SignupActivity.isPassword("87348643"));
        assertFalse(SignupActivity.isPassword("nidei"));
        assertFalse(SignupActivity.isPassword("diueh+dwek"));
        assertFalse(SignupActivity.isPassword("fi_urjifefe"));
        assertFalse(SignupActivity.isPassword("dnenr12dwesjwnwievrgr"));
    }

    @Test
    public void testIsEmail() {
        assertTrue(SignupActivity.isEmail("avdv443e@dneii.coeoce"));
        assertTrue(SignupActivity.isEmail("dieu_ttri@abs.de"));
        assertTrue(SignupActivity.isEmail("4363443@a.gy"));
        assertTrue(SignupActivity.isEmail("22di13@ab.de"));
        assertTrue(SignupActivity.isEmail("ttt4@fithui-45.gtui"));
        assertFalse(SignupActivity.isEmail("kerfe@fei.i"));
        assertFalse(SignupActivity.isEmail("nideni@@def.efe"));
        assertFalse(SignupActivity.isEmail("n=ideni@def.efe"));
        assertFalse(SignupActivity.isEmail("dnenr12@nj,vrgr"));
        assertFalse(SignupActivity.isEmail("abc123@nj.de-fr"));
    }
}