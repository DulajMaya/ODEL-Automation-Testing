package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.reports.ExtentReportManager;
import com.example.reports.PDFReportGenerator;
import com.example.tests.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

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

        // Get driver from test class to take screenshot
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        // Take screenshot
        if(driver != null) {
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.get().addScreenCaptureFromBase64String(base64Screenshot);
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();
        // Generate PDF Report
        PDFReportGenerator pdfGenerator = new PDFReportGenerator();
        pdfGenerator.generateReport(testResults, null);
    }

    // Add empty implementations for other ITestListener methods
    @Override
    public void onTestSkipped(ITestResult result) {}

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    @Override
    public void onStart(ITestContext context) {}
}