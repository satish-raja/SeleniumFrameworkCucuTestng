package com.orangehrm.selenium.cucumber.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Utility class to handle reading data from Excel and JSON files.
 */
public class DataUtil {

    /**
     * Read login credentials from an Excel file.
     *
     * @param filePath Path to the Excel file.
     * @return List of Maps representing test data.
     * @throws IOException If the file cannot be read.
     */
    public static List<Map<String, String>> getDataFromExcel(String filePath) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);

        // Get the first sheet (assuming credentials are here)
        Sheet sheet = workbook.getSheetAt(0);

        // Read headers from the first row
        Row headerRow = sheet.getRow(0);
        int colCount = headerRow.getPhysicalNumberOfCells();

        // Iterate over rows
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { // Start from row 1 to skip headers
            Row currentRow = sheet.getRow(i);
            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
                String key = headerRow.getCell(j).getStringCellValue();
                String value = currentRow.getCell(j) != null ? currentRow.getCell(j).toString() : "";
                rowData.put(key, value);
            }
            data.add(rowData);
        }

        workbook.close();
        file.close();
        return data;
    }

    /**
     * Read login credentials from a JSON file.
     *
     * @param filePath Path to the JSON file.
     * @return List of Maps representing test data.
     * @throws IOException If the file cannot be read.
     */
    public static List<Map<String, String>> getDataFromJSON(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), new TypeReference<List<Map<String, String>>>() {});
    }

    /**
     * General method to fetch data from file (supports both JSON and Excel files).
     * 
     * @param filePath Path to the file (either .xlsx or .json).
     * @return List of Maps representing test data.
     * @throws IOException If the file cannot be read.
     */
    public static List<Map<String, String>> getTestData(String filePath) throws IOException {
        if (filePath.endsWith(".json")) {
            System.out.println("Loading test data from JSON file: " + filePath);
            return getDataFromJSON(filePath);
        } else if (filePath.endsWith(".xlsx")) {
            System.out.println("Loading test data from Excel file: " + filePath);
            return getDataFromExcel(filePath);
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + filePath);
        }
    }
}
