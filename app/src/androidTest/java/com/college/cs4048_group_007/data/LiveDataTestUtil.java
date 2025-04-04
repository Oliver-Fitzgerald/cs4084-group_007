package com.college.cs4048_group_007.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.junit.rules.TestRule;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertNotNull;

public class LiveDataTestUtil {

    public static <T> T getValue(final LiveData<T> liveData) {
        // Create a CountDownLatch to block the thread until the LiveData is observed
        final Object[] observedValue = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);

        // Observe the LiveData
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                observedValue[0] = t;
                latch.countDown(); // When the LiveData is changed, release the latch
            }
        };

        // Start observing the LiveData (this should be done in the test thread)
        liveData.observeForever(observer);

        // Wait for the value to be set (timeout after 2 seconds)
        try {
            boolean await = latch.await(2, TimeUnit.SECONDS);
            if (!await) {
                throw new InterruptedException("LiveData value was not set within timeout.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return the observed value from LiveData
        assertNotNull(observedValue[0]);
        return (T) observedValue[0];
    }
}
