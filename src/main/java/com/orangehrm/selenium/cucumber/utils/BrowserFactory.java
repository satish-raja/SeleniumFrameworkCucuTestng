package com.orangehrm.selenium.cucumber.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Factory class to create and configure WebDriver instances based on browser type and headless mode.
 */
public class BrowserFactory {

    /**
     * Creates and configures a WebDriver instance based on the specified browser type and headless mode.
     *
     * @param browser The type of browser ("chrome", "firefox", "edge").
     * @param isHeadless Whether to run the browser in headless mode.
     * @return A configured WebDriver instance.
     * @throws IllegalArgumentException if the specified browser type is not supported.
     */
    public static WebDriver createDriver(String browser, boolean isHeadless) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) chromeOptions.addArguments("--headless");
                return new ChromeDriver(chromeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) firefoxOptions.addArguments("--headless", "--width=1920", "--height=1080");
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) edgeOptions.addArguments("--headless", "--window-size=1920x1080");
                return new EdgeDriver(edgeOptions);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}