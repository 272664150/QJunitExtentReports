package com.example.junitextentreports;

import android.os.Parcel;
import android.util.Pair;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.junitextentreports.report.base.AbstractExtentReportsTest;
import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

// @RunWith is required only if you use a mix of JUnit3 and JUnit4.
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LogHistoryAndroidUnitTest extends AbstractExtentReportsTest {

    public static final String TEST_STRING = "This is a string";
    public static final long TEST_LONG = 12345678L;
    private LogHistory mLogHistory;

    @Before
    public void createLogHistory() {
        mLogHistory = new LogHistory();
    }

    @Test
    public void logHistory_ParcelableWriteRead() {
        // Set up the Parcelable object to send and receive.
        mLogHistory.addEntry(TEST_STRING, TEST_LONG);

        // Write the data.
        Parcel parcel = Parcel.obtain();
        mLogHistory.writeToParcel(parcel, mLogHistory.describeContents());

        // After you're done with writing, you need to reset the parcel for reading.
        parcel.setDataPosition(0);

        // Read the data.
        LogHistory createdFromParcel = LogHistory.CREATOR.createFromParcel(parcel);
        List<Pair<String, Long>> createdFromParcelData
                = createdFromParcel.getData();

        // Verify that the received data is correct.
        Truth.assertThat(createdFromParcelData.size()).isEqualTo(1);
        Truth.assertThat(createdFromParcelData.get(0).first).isEqualTo(TEST_STRING);
        Truth.assertThat(createdFromParcelData.get(0).second).isEqualTo(TEST_LONG);
    }
}