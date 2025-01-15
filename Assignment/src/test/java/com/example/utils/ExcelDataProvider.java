package com.example.utils;



import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataProvider {

    private static final String TEST_DATA_PATH = "src/test/resources/testData.xlsx";

    public static Object[][] getTestData() {
        List<Object[]> testData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(TEST_DATA_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();

            // Skip header row, start from row 1
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                String testCase = row.getCell(0).getStringCellValue();
                String searchTerm = row.getCell(1).getStringCellValue();
                boolean expectedResult = row.getCell(2).getBooleanCellValue();

                testData.add(new Object[]{testCase, searchTerm, expectedResult});
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData.toArray(new Object[0][]);
    }

}
