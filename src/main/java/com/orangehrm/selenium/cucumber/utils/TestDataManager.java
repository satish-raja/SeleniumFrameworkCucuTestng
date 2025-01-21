package com.orangehrm.selenium.cucumber.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestDataManager {

    private static final Logger logger = LogManager.getLogger(TestDataManager.class);

    /**
     * Determines the file type (e.g., JSON, Excel) and retrieves test data.
     *
     * @param filePath Path to the test data file.
     * @return List of maps representing the test data.
     * @throws IOException If the file cannot be read or parsed.
     */
    public static List<Map<String, String>> getTestData(String filePath) throws IOException {
        File file = new File(filePath);
        String fileName = file.getName();

        logger.info("Processing file: {}", fileName);

        // Check if file exists
        if (!file.exists()) {
            logger.error("The file does not exist: {}", filePath);
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }

        // Ensure correct file extension for data files
        if (fileName.toLowerCase().endsWith(".xlsx")) {
            logger.info("File type detected: Excel");
            return DataUtil.getDataFromExcel(filePath);
        } else if (fileName.toLowerCase().endsWith(".json")) {
            logger.info("File type detected: JSON");
            return DataUtil.getDataFromJSON(filePath);
        } else {
            logger.error("Unsupported file format: {}", fileName);
            throw new IllegalArgumentException("Unsupported file format: " + fileName);
        }
    }
}
