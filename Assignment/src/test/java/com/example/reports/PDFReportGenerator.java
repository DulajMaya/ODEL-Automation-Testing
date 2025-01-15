package com.example.reports;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import org.testng.ITestResult;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PDFReportGenerator {

    private static final String OUTPUT_PATH = "test-output/Combined_Test_Report.pdf";
    private Document document;

    public void generateReport(List<ITestResult> results) {
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(OUTPUT_PATH));
            document.open();

            // Add Title and Jenkins Build Info
            addJenkinsInfo();

            // Add Test Summary
            addTestSummary(results);

            // Add Detailed Test Results
            addTestDetails(results);

            // Add Screenshots
            addScreenshots(results);

            // Add ExtentReport Summary
            addExtentReportSummary();

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTitle() throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("ODEL E-commerce Test Automation Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));
    }

    private void addTestSummary(List<ITestResult> results) throws DocumentException {
        Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        document.add(new Paragraph("Test Execution Summary", headingFont));

        int passed = 0, failed = 0, skipped = 0;
        for (ITestResult result : results) {
            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    passed++;
                    break;
                case ITestResult.FAILURE:
                    failed++;
                    break;
                case ITestResult.SKIP:
                    skipped++;
                    break;
            }
        }

        document.add(new Paragraph("Total Tests: " + results.size()));
        document.add(new Paragraph("Passed: " + passed));
        document.add(new Paragraph("Failed: " + failed));
        document.add(new Paragraph("Skipped: " + skipped));
        document.add(new Paragraph("\n"));
    }

    private void addTestDetails(List<ITestResult> results) throws DocumentException {
        Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        document.add(new Paragraph("Test Case Details", headingFont));

        for (ITestResult result : results) {
            document.add(new Paragraph("Test: " + result.getName()));
            document.add(new Paragraph("Status: " + getStatus(result.getStatus())));
            if (result.getThrowable() != null) {
                document.add(new Paragraph("Error: " + result.getThrowable().getMessage()));
            }
            document.add(new Paragraph("\n"));
        }
    }

    private String getStatus(int status) {
        switch (status) {
            case ITestResult.SUCCESS:
                return "PASSED";
            case ITestResult.FAILURE:
                return "FAILED";
            case ITestResult.SKIP:
                return "SKIPPED";
            default:
                return "UNKNOWN";
        }
    }

    private void addScreenshots(List<ITestResult> results) throws DocumentException {
        Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        document.add(new Paragraph("Test Screenshots", headingFont));

        for (ITestResult result : results) {
            if (result.getStatus() == ITestResult.FAILURE) {
                try {
                    // Change the path to match where TestListener saves screenshots
                    String screenshotPath = System.getProperty("user.dir")
                            + "/test-output/screenshots/"
                            + result.getName() + ".png";

                    Image img = Image.getInstance(screenshotPath);
                    img.scaleToFit(400, 300);  // Adjust size if needed
                    img.setAlignment(Element.ALIGN_CENTER);

                    // Add test name before screenshot
                    Paragraph testName = new Paragraph("Screenshot for: " + result.getName());
                    testName.setAlignment(Element.ALIGN_CENTER);
                    document.add(testName);
                    document.add(new Paragraph("\n"));
                    document.add(img);
                    document.add(new Paragraph("\n"));
                } catch (Exception e) {
                    document.add(new Paragraph("Error adding screenshot for: " + result.getName()));
                    e.printStackTrace();
                }
            }
        }
    }

    private void addJenkinsInfo() throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Title
        Paragraph title = new Paragraph("Test Automation Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Jenkins Build Info
        document.add(new Paragraph("Jenkins Build Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        document.add(new Paragraph("Build Number: " + System.getenv("BUILD_NUMBER"), normalFont));
        document.add(new Paragraph("Job Name: " + System.getenv("JOB_NAME"), normalFont));
        document.add(new Paragraph("Build URL: " + System.getenv("BUILD_URL"), normalFont));
        document.add(new Paragraph("Build Time: " + new Date(), normalFont));
        document.add(new Paragraph("\n"));
    }

    private void addExtentReportSummary() throws DocumentException {
        document.add(new Paragraph("ExtentReport Summary:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        document.add(new Paragraph("Full HTML Report available at: test-output/OdelTestReport.html"));
        document.add(new Paragraph("\n"));
        // Add any specific metrics from ExtentReports if needed
    }

}
