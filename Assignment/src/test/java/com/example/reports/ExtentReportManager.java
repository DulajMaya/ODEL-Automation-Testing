package com.example.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            sparkReporter = new ExtentSparkReporter("test-output/OdelTestReport.html");
            extent.attachReporter(sparkReporter);

            // Report metadata
            extent.setSystemInfo("Application", "ODEL E-Commerce");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        }
        return extent;
    }

}
