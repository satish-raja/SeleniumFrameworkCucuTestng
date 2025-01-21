package com.orangehrm.selenium.cucumber.utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final int MAX_RETRIES = 3;

    /**
     * Capture a screenshot, save it, and attach it to Allure report.
     *
     * @param driver   WebDriver instance.
     * @param testName Name of the test case.
     */
    public static void captureAndAttachScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            logger.error("WebDriver cannot be null");
            throw new IllegalArgumentException("WebDriver cannot be null");
        }

        try {
            // Take the screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);

            // Generate a unique filename with thread ID and timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + "_T" + Thread.currentThread().getId() + ".png";

            // Dynamically get the path for the screenshots directory
            String allureResultsDir = Paths.get(System.getProperty("user.dir"), "allure-results", "screenshots").toString();
            File screenshotDir = new File(allureResultsDir);

            // Create directory in a thread-safe manner
            createDirectorySafely(screenshotDir);

            String filePath = allureResultsDir + File.separator + fileName;

            // Save the screenshot to the allure-results/screenshots directory with retries
            boolean isSaved = saveFileWithRetries(screenshot, new File(filePath));
            if (!isSaved) {
                logger.error("Failed to save screenshot after retries.");
                return;
            }

            logger.info("Screenshot saved at: {}", filePath);

            // Attach the screenshot to Allure using the lifecycle API
            try (FileInputStream fis = new FileInputStream(filePath)) {
                Allure.addAttachment("Screenshot: " + testName, "image/png", fis, "png");
                logger.info("Screenshot attached to Allure report.");
            }
        } catch (IOException e) {
            logger.error("Error capturing or attaching screenshot: {}", e.getMessage(), e);
        }
    }

    /**
     * Thread-safe directory creation.
     *
     * @param directory Directory to create.
     */
    private static synchronized void createDirectorySafely(File directory) {
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (isCreated) {
                logger.info("Created directory: {}", directory.getAbsolutePath());
            } else {
                logger.warn("Failed to create directory: {}", directory.getAbsolutePath());
            }
        }
    }

    /**
     * Save file with retries to handle transient I/O failures.
     *
     * @param sourceFile      Source file.
     * @param destinationFile Destination file.
     * @return True if the file was saved successfully, false otherwise.
     */
    private static boolean saveFileWithRetries(File sourceFile, File destinationFile) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                FileUtils.copyFile(sourceFile, destinationFile);
                return true; // Successfully saved
            } catch (IOException e) {
                attempts++;
                logger.warn("Attempt {} to save file failed: {}", attempts, e.getMessage());
                try {
                    Thread.sleep(500); // Wait before retrying
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
        }
        return false; // Failed after retries
    }
}
