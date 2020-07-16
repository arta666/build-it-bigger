package com.udacity.gradle.builditbigger;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.udacity.gradle.builditbigger.Network.ServiceAsync;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

/**
 * created by arman 2020
 */


@RunWith(AndroidJUnit4.class)
public class FetchJokeTaskTest extends TestCase {
    @Test
    public void testNonEmptyJokeReceived() {
        try {
            ServiceAsync task = new ServiceAsync();
            task.execute();
            String joke = task.get(30, TimeUnit.SECONDS);

            assertNotNull(joke);
            assertTrue(joke.length() > 0);
        } catch (Exception e) {
            fail("Operation timed out");
        }

    }
}
