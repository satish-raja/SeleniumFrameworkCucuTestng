package com.orangehrm.selenium.cucumber.hooks;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.orangehrm.selenium.cucumber.utils.DriverManager;
import com.orangehrm.selenium.cucumber.utils.TestLogger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * WebDriverHooks class provides setup and teardown methods for WebDriver instances
 * using Cucumber's @Before and @After hooks.
 */
public class WebDriverHooks {

    private static final TestLogger testLogger = new TestLogger(); // Using TestLogger for logging
    private static Map<String, Integer> scenarioMap = new ConcurrentHashMap<>(); // Track scenario retries

    /**
     * Sets up the WebDriver instance before each scenario.
     * Initializes the WebDriver based on system properties for browser and headless mode.
     * Maximizes the browser window if not in headless mode.
     *
     * @param scenario The current Cucumber scenario.
     */
    @Before
    public void setUp(Scenario scenario) {
        String browser = System.getProperty("browser", "chrome"); // Default to chrome if no system property is set
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        testLogger.logInfo("Initializing WebDriver: Browser = " + browser + ", Headless = " + isHeadless);
        WebDriver driver = DriverManager.initializeDriver(browser, isHeadless);
        if (!isHeadless) {
            driver.manage().window().maximize(); // Maximizes the browser window
        }
        testLogger.logInfo("Driver Instance : " + driver + " at WebDriverHooks : setUp");
    }

    /**
     * Tears down the WebDriver instance after each scenario.
     * Captures a screenshot if the scenario fails.
     * Quits the WebDriver instance.
     *
     * @param scenario The current Cucumber scenario.
     */
    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
	        if (scenario.isFailed() && driver != null) {
	            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	            scenario.attach(screenshot, "image/png", scenario.getName());   
	            testLogger.logError("Test Failed: " + scenario.getName());
	            screenshotManager(driver, scenario);
	        }        
        // Quit the driver
        DriverManager.quitDriver();
    }
    
    /**
     * Takes a screenshot and saves it to the specified location.
     *
     * @param driver      The WebDriver instance.
     * @param description The description of the screenshot.
     */
    public static void saveScreenshot(WebDriver driver, String description) {
        try {
            // Take the screenshot as bytes
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Get the project directory dynamically
            String projectDir = System.getProperty("user.dir"); // This gives you the current project directory

            // Get the current date and time to append to the file name
            String dateTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Sanitize the description to remove invalid characters
            String sanitizedDescription = description.replaceAll("[\\\\/:*?\"<>| ]", "_") + "_" + dateTimeStamp;

            // Define the path where screenshots will be saved
            String filePath = projectDir + File.separator + "allure-results" + File.separator + "screenshots" + File.separator + sanitizedDescription + ".png";

            // Create a new file object for the destination path
            File destination = new File(filePath);

            // Create the necessary directories if they don't exist
            destination.getParentFile().mkdirs();

            // Copy the screenshot to the destination path
            FileUtils.copyFile(screenshot, destination);
            testLogger.logInfo("Screenshot of Failed Test Scenario " + description + " is saved.");
        } catch (IOException e) {
            testLogger.logError("Failed to save the screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void screenshotManager(WebDriver driver, Scenario scenario) {
        String scenarioName = scenario.getUri().toString()
                                      .replace("classpath:features/", "")
                                      .replace(".feature", "");
        int scenarioLine = scenario.getLine();
        testLogger.logInfo("Processing Scenario: Name = " + scenarioName + ", Line = " + scenarioLine);
        if (scenarioMap.containsKey(scenarioName) && scenarioMap.get(scenarioName) == scenarioLine) {
            testLogger.logInfo("Duplicate Screenshot: " + scenarioName + " at line " + scenarioLine);
            return;
        } 
        scenarioMap.put(scenarioName, scenarioLine);
        saveScreenshot(driver, scenarioName);
    }
    
}