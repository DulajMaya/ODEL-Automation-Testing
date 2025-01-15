package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.reports.ExtentReportManager;
import com.example.reports.PDFReportGenerator;
import com.example.tests.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestListener implements ITestListener {

    private ExtentReports extent = ExtentReportManager.getInstance();
    private ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private List<ITestResult> testResults = new ArrayList<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().log(Status.PASS, "Test passed");
        testResults.add(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        testResults.add(result);

        if (result.getInstance() instanceof BaseTest) {
            WebDriver driver = ((BaseTest) result.getInstance()).getDriver();
            try {
                // Create screenshots directory if it doesn't exist
                File screenshotsDir = new File("test-output/screenshots");
                if (!screenshotsDir.exists()) {
                    screenshotsDir.mkdirs();
                }

                // Take and save screenshot
                File screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);
                String screenshotPath = "test-output/screenshots/"
                        + result.getName() + ".png";
                FileUtils.copyFile(screenshot, new File(screenshotPath));

                // Also add to extent report
                String base64Screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BASE64);
                test.get().addScreenCaptureFromBase64String(base64Screenshot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        // Generate ExtentReport
        extent.flush();

        // Generate Combined PDF Report
        PDFReportGenerator pdfGenerator = new PDFReportGenerator();
        pdfGenerator.generateReport(testResults);

        // If running in Jenkins, archive the reports
        if (System.getenv("BUILD_NUMBER") != null) {
            try {
                // Copy reports to Jenkins workspace
                String workspace = System.getenv("WORKSPACE");
                if (workspace != null) {
                    FileUtils.copyDirectory(
                            new File("test-output"),
                            new File(workspace + "/test-reports")
                    );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Add empty implementations for other ITestListener methods
    @Override
    public void onTestSkipped(ITestResult result) {}

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    @Override
    public void onStart(ITestContext context) {}
}