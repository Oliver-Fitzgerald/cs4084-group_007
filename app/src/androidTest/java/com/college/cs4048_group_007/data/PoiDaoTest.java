package com.college.cs4048_group_007.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.college.cs4048_group_007.entities.Poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.nio.channels.FileChannel;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class PoiDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase database;
    private PoiDao poiDao;

    @Before
    public void setup() {
        database = Room.databaseBuilder(
                        InstrumentationRegistry.getInstrumentation().getTargetContext(),
                        AppDatabase.class,
                        "mobileDatabase.db"  // Persistent database file
        ).build();
        poiDao = database.poiDao();

    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void insertPoiToDatabase() throws Exception {
        Poi poi = new Poi();
        poi.name = "merry_go_round";
        poi.description = "Take a whimsical spin on our classic Merry-Go-Round! With beautifully crafted horses and cheerful music, this timeless ride is perfect for guests of all ages looking for a gentle, magical experience. Hold on tight and enjoy the ride!";
        poi.openTime = "9:00";
        poi.closeTime = "5:00";
        poi.type = "entertainment";

        poiDao.insert(poi); // Insert data

        LiveData<List<Poi>> liveDataPois = poiDao.getAllPoi(); // Retrieve data

        // Create a List to hold the result
        List<Poi> poisList = LiveDataTestUtil.getValue(liveDataPois); // Utility function to get LiveData value
        // Verify that 1 poi has been inserted
        assertEquals(1, poisList.size());
        assertEquals("merry_go_round", poisList.get(0).name);
    }

    @Test
    public void exportDatabase() throws IOException {
        File dbFile = InstrumentationRegistry.getInstrumentation().getTargetContext().getDatabasePath("mobileDatabase.db");
        File exportFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "exported_mobileDatabase.db");
       // File exportFile = new File(
         //       InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null),
           //     "exported_mobileDatabase.db"
        //);

        FileChannel src = new FileInputStream(dbFile).getChannel();
        FileChannel dst = new FileOutputStream(exportFile).getChannel();
        dst.transferFrom(src, 0, src.size());
        src.close();
        dst.close();

        Log.d("DatabaseExport", "Database copied to: " + exportFile.getAbsolutePath());

        assertTrue(exportFile.exists()); // Ensure the file was copied
    }

}
