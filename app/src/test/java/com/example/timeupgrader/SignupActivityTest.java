package com.example.timeupgrader;

import org.junit.Test;

import static org.junit.Assert.*;

public class SignupActivityTest {

    @Test
    public void isPassword() {
        assertTrue(SignupActivity.isPassword("avdv443e@dneii.coeoce"));
        assertTrue(SignupActivity.isPassword("dieu_ttri@abs.de"));
        assertFalse(SignupActivity.isPassword("nideni@@def.efe"));
        assertFalse(SignupActivity.isPassword("dnenr12@nj,vr+gr"));
    }

    @Test
    public void isEmail() {
    }
}