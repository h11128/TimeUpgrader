package com.example.timeupgrader;

import org.junit.Test;
import static org.junit.Assert.*;

public class FocusFragmentTest {

    @Test
    public void testFormat() {
        FocusFragment focusFragment = new FocusFragment();
        assertEquals(focusFragment.format(((10 * 60 + 22) * 60 + 30) * 1000L), "10:22:30");
        assertEquals(focusFragment.format(((9 * 60 + 45) * 60 + 11) * 1000L), "09:45:11");
        assertNotEquals(focusFragment.format(((9 * 60 + 45) * 60 + 11) * 1000L), "9:45:11");
        assertEquals(focusFragment.format((7 * 60 + 5) * 1000L), "00:07:05");
        assertNotEquals(focusFragment.format((7 * 60 + 5) * 1000L), "0:7:5");
        assertEquals(focusFragment.format((2 * 60 + 30) * 60 * 1000L), "02:30:00");
        assertNotEquals(focusFragment.format((2 * 60 + 30) * 60 * 1000L), "2:30:0");
        assertEquals(focusFragment.format(1000), "00:00:01");
        assertEquals(focusFragment.format(((23 * 60 + 59) * 60 + 59) * 1000L), "23:59:59");
        assertEquals(focusFragment.format(0), "00:00:00");
        assertEquals(focusFragment.format(-1000), "Invalid");
        assertEquals(focusFragment.format(((120 * 60 + 33) * 60 + 57) * 1000L), "120:33:57");
    }
}