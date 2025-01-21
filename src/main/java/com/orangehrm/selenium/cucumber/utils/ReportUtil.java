package com.orangehrm.selenium.cucumber.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReportUtil {

    private static final Logger logger = LogManager.getLogger(ReportUtil.class);

    /**
     * Logs a test step with a custom description in Allure.
     *
     * @param stepDescription Description of the test step.
     */
    @Step("{0}")
    public static void logStep(String stepDescription) {
        logger.info("Logging step to Allure: {}", stepDescription);
        Allure.step(stepDescription);
    }

    /**
     * Attaches a screenshot to the Allure report.
     *
     * @param screenshotPath Path of the screenshot file to attach.
     */
    public static void attachScreenshot(String screenshotPath) {
        try {
            File screenshotFile = new File(screenshotPath);
            if (screenshotFile.exists()) {
                try (FileInputStream fis = new FileInputStream(screenshotFile)) {
                    Allure.addAttachment("Screenshot", "image/png", fis, "png");
                    logger.info("Screenshot attached to Allure: {}", screenshotPath);
                }
            } else {
                logger.error("Screenshot file not found at: {}", screenshotPath);
            }
        } catch (IOException e) {
            logger.error("Error attaching screenshot to Allure: {}", e.getMessage(), e);
        }
    }

    /**
     * Attaches an arbitrary file to the Allure report.
     *
     * @param fileName Name of the file as it will appear in the Allure report.
     * @param filePath Path of the file to attach.
     */
    public static void attachFile(String fileName, String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                byte[] fileData = FileUtils.readFileToByteArray(file);
                Allure.addAttachment(fileName, new ByteArrayInputStream(fileData));
                logger.info("File attached to Allure: {}", fileName);
            } else {
                logger.error("File not found at: {}", filePath);
            }
        } catch (IOException e) {
            logger.error("Error attaching file to Allure: {}", e.getMessage(), e);
        }
    }

    /**
     * Logs an error message and attaches it to the Allure report.
     *
     * @param errorMessage Error message to log.
     */
    public static void logError(String errorMessage) {
        logger.error("Logging error to Allure: {}", errorMessage);
        Allure.addAttachment("Error", "text/plain", errorMessage);
    }

    /**
     * Attaches a log or text content dynamically to the Allure report.
     *
     * @param logName    Name of the log file as it will appear in Allure.
     * @param logContent Log or text content to attach.
     */
    public static void attachLog(String logName, String logContent) {
        Allure.addAttachment(logName, "text/plain", logContent);
        logger.info("Log content attached to Allure: {}", logName);
    }
}
