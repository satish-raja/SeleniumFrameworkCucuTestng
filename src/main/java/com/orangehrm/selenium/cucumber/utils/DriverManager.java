package com.orangehrm.selenium.cucumber.utils;

import org.openqa.selenium.WebDriver;

/**
 * DriverManager is a utility class that manages the WebDriver instances using ThreadLocal.
 * It provides methods to initialize, retrieve, and quit WebDriver instances on a per-thread basis.
 */
public class DriverManager {

    // ThreadLocal to store WebDriver instances per thread
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initializes a WebDriver instance based on the specified browser type and headless mode,
     * and stores it in ThreadLocal.
     *
     * @param browser The type of browser ("chrome", "firefox", "edge").
     * @param isHeadless Whether to run the browser in headless mode.
     * @return The initialized WebDriver instance.
     */
    public static WebDriver initializeDriver(String browser, boolean isHeadless) {
        WebDriver driver = BrowserFactory.createDriver(browser, isHeadless);
        driverThreadLocal.set(driver);
        return driver;
    }

    /**
     * Retrieves the current thread's WebDriver instance.
     *
     * @return The WebDriver instance associated with the current thread, or null if not initialized.
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Quits the current thread's WebDriver instance and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}