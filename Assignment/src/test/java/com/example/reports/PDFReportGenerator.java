package com.example.reports;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import org.testng.ITestResult;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class PDFReportGenerator {

    private static final String OUTPUT_PATH = "test-output/TestReport.pdf";
    private Document document;

    public void generateReport(List<ITestResult> results, Map<String, String> testData) {
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(OUTPUT_PATH));
            document.open();

            // Add Title
            addTitle();

            // Add Test Summary
            addTestSummary(results);

            // Add Test Details
            addTestDetails(results);

            // Add Screenshots if tests failed
            addScreenshots(results);

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
                    Image img = Image.getInstance("test-output/screenshots/" + result.getName() + ".png");
                    img.scaleToFit(500, 500);
                    document.add(img);
                } catch (Exception e) {
                    document.add(new Paragraph("Screenshot not available for: " + result.getName()));
                }
            }
        }
    }

}
