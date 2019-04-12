package com.example.timeupgrader;

import org.junit.Test;
import static org.junit.Assert.*;

public class SignupActivityTest {

    @Test
    public void isPassword() {
        assertTrue(SignupActivity.isPassword("avdv443e"));
        assertTrue(SignupActivity.isPassword("dieuttride"));
        assertFalse(SignupActivity.isPassword("nide"));
        assertFalse(SignupActivity.isPassword("diueh+dwek"));
        assertFalse(SignupActivity.isPassword("fi_urjifefe"));
        assertFalse(SignupActivity.isPassword("dnenr12dwesjwnwievrgr"));
    }

    @Test
    public void isEmail() {
        assertTrue(SignupActivity.isEmail("avdv443e@dneii.coeoce"));
        assertTrue(SignupActivity.isEmail("dieu_ttri@abs.de"));
        assertFalse(SignupActivity.isEmail("nideni@@def.efe"));
        assertFalse(SignupActivity.isEmail("n=ideni@@def.efe"));
        assertFalse(SignupActivity.isEmail("dnenr12@nj,vr+gr"));
    }
}