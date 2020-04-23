package com.example.junitextentreports.report.base;

import com.example.junitextentreports.report.manager.ExtentReportsManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * 1. 实现 @BeforeClass 和 @AfterClass 静态方法，控制写入的节点
 * 2. 自定义 Rule，监听 Junit 的 succeeded 和 failed，写入HTML测试报告
 */
public abstract class AbstractExtentReportsTest {

    @BeforeClass
    public static void beforeClass() {
        ExtentReportsManager.getInstance().open();
    }

    @AfterClass
    public static void afterClass() {
        ExtentReportsManager.getInstance().close();
    }

    @Rule
    public TestWatcher mExtentReportsRule = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            ExtentTest test =
                    ExtentReportsManager.getInstance().getExtentReports().startTest(description.getDisplayName(), "-");

            // step log
            test.log(LogStatus.PASS, "-");
            flushReports(ExtentReportsManager.getInstance().getExtentReports(), test);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            ExtentTest test =
                    ExtentReportsManager.getInstance().getExtentReports().startTest(description.getDisplayName(), "Test failed");

            // step log
            test.log(LogStatus.FAIL, e);
            flushReports(ExtentReportsManager.getInstance().getExtentReports(), test);
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            ExtentTest test =
                    ExtentReportsManager.getInstance().getExtentReports().startTest(description.getDisplayName(), "Test skipped");

            // step log
            test.log(LogStatus.SKIP, e);
            flushReports(ExtentReportsManager.getInstance().getExtentReports(), test);
        }

        private void flushReports(ExtentReports extent, ExtentTest test) {
            // ending test
            extent.endTest(test);
            // writing everything to document
            extent.flush();
        }
    };
}
