package com.example.junitextentreports.report.manager;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.collection.ArrayMap;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

import java.io.File;
import java.util.Date;
import java.util.Map;

public class ExtentReportsManager {
    private static final String TAG = ExtentReportsManager.class.getSimpleName();

    private ExtentReports mExtentReports;

    private static volatile ExtentReportsManager sInstance;

    public static ExtentReportsManager getInstance() {
        if (null == sInstance) {
            synchronized (ExtentReportsManager.class) {
                if (null == sInstance) {
                    sInstance = new ExtentReportsManager();
                }
            }
        }
        return sInstance;
    }

    private ExtentReportsManager() {
        mExtentReports = new ExtentReports(generateFilePath(), false, NetworkMode.OFFLINE);
        mExtentReports.addSystemInfo(generateSystemInfo());
    }

    private String generateFilePath() {
        Date date = new Date();
        String form = String.format("%tF", date);
        String hour = String.format("%tH", date);
        String minute = String.format("%tM", date);
        String second = String.format("%tS", date);
        String outputDir = Environment.getExternalStorageDirectory() + File.separator + "Reports" + File.separator + form + "_" + hour + minute + second;
        String fileName = "index.html";
        return outputDir + File.separator + fileName;
    }

    private Map<String, String> generateSystemInfo() {
        Map<String, String> info = new ArrayMap<>();
        info.put("OS", Build.VERSION.RELEASE);
        info.put("Java Version", System.getProperty("java.version"));
        return info;
    }

    public ExtentReports getExtentReports() {
        return mExtentReports;
    }

    public void open() {
        Log.d(TAG, "open");
    }

    public void close() {
        Log.d(TAG, "close");
        // TODO 由于调用close后，无法进行reopen，flush到已生成的HTML测试报告，所以不进行close操作
        // mExtentReports.close();
    }
}
