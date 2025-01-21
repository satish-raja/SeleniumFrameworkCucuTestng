package com.orangehrm.selenium.cucumber.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for common WebDriver operations.
 */
public class ElementUtil {

    // Logger for debugging
    private static final Logger logger = LogManager.getLogger(ElementUtil.class);

    /**
     * Finds an element on the page using the specified locator.
     *
     * @param driver  The WebDriver instance.
     * @param locator The locator to find the element.
     * @return The found WebElement.
     * @throws IllegalArgumentException if the element is not found.
     */
    public static WebElement findElement(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            logger.info("Element found: {}", locator);
            return element;
        } catch (Exception e) {
            logger.error("Element not found: {}", locator);
            throw new IllegalArgumentException("Element not found: " + locator, e);
        }
    }

    /**
     * Clicks on the specified element.
     *
     * @param driver  The WebDriver instance.
     * @param locator The locator of the element to click.
     * @throws IllegalArgumentException if the element is not clickable.
     */
    public static void clickElement(WebDriver driver, By locator) {
        WebElement element = findElement(driver, locator);
        if (element.isDisplayed() && element.isEnabled()) {
            element.click();
            logger.info("Clicked on element: {}", locator);
        } else {
            logger.error("Element is not clickable: {}", locator);
            throw new IllegalArgumentException("Element is not clickable: " + locator);
        }
    }

    /**
     * Sends keys to the specified element.
     *
     * @param driver  The WebDriver instance.
     * @param locator The locator of the element to send keys to.
     * @param text    The text to send to the element.
     * @throws IllegalArgumentException if the element is not interactable.
     */
    public static void sendKeysToElement(WebDriver driver, By locator, String text) {
        WebElement element = findElement(driver, locator);
        if (element.isDisplayed() && element.isEnabled()) {
            element.clear();
            element.sendKeys(text);
            logger.info("Sent keys to element: {}", locator);
        } else {
            logger.error("Element is not interactable: {}", locator);
            throw new IllegalArgumentException("Element is not interactable: " + locator);
        }
    }

    /**
     * Waits for an element to be visible.
     *
     * @param driver  The WebDriver instance.
     * @param locator The locator of the element to wait for.
     * @param timeout The timeout in seconds.
     * @throws IllegalArgumentException if the element is not visible within the timeout.
     */
    public static void waitForElementToBeVisible(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Element is visible: {}", locator);
        } catch (Exception e) {
            logger.error("Element not visible: {}", locator);
            throw new IllegalArgumentException("Element not visible: " + locator, e);
        }
    }

    /**
     * Checks if an element is present in the DOM.
     *
     * @param driver  The WebDriver instance.
     * @param locator The locator of the element to check.
     * @return true if the element is present, false otherwise.
     */
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            boolean isPresent = !driver.findElements(locator).isEmpty();
            logger.info("Element presence check for {}: {}", locator, isPresent);
            return isPresent;
        } catch (Exception e) {
            logger.error("Error checking element presence: {}", locator);
            return false;
        }
    }

    /**
     * Waits for an element to be clickable.
     *
     * @param driver  The WebDriver instance.
     * @param locator The locator of the element to wait for.
     * @param timeout The timeout in seconds.
     * @throws IllegalArgumentException if the element is not clickable within the timeout.
     */
    public static void waitForElementToBeClickable(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.info("Element is clickable: {}", locator);
        } catch (Exception e) {
            logger.error("Element not clickable: {}", locator);
            throw new IllegalArgumentException("Element not clickable: " + locator, e);
        }
    }

    /**
     * Retrieves the text of the specified element.
     *
     * @param driver  The WebDriver instance.
     * @param locator The locator of the element.
     * @return The text of the element.
     * @throws IllegalArgumentException if the element is not visible.
     */
    public static String getElementText(WebDriver driver, By locator) {
        WebElement element = findElement(driver, locator);
        if (element != null && element.isDisplayed()) {
            String text = element.getText();
            logger.info("Text retrieved from element: {} - {}", locator, text);
            return text;
        } else {
            logger.error("Element is not visible: {}", locator);
            throw new IllegalArgumentException("Element is not visible: " + locator);
        }
    }

    /**
     * Retrieves the attribute value of the specified element.
     * Use getDomAttribute() for the exact value of an attribute defined in the HTML markup.
     * Use getDomProperty() for JavaScript properties.
     *
     * @param driver    The WebDriver instance.
     * @param locator   The locator of the element.
     * @param attribute The attribute to retrieve.
     * @return The value of the attribute.
     * @throws IllegalArgumentException if the element is not visible.
     */
    public static String getElementAttribute(WebDriver driver, By locator, String attribute) {
        WebElement element = findElement(driver, locator);
        if (element != null && element.isDisplayed()) {
            String attributeValue = element.getDomAttribute(attribute);  // Use getDomAttribute for HTML attributes
            logger.info("Attribute '{}' retrieved from element: {} - {}", attribute, locator, attributeValue);
            return attributeValue;
        } else {
            logger.error("Element is not visible: {}", locator);
            throw new IllegalArgumentException("Element is not visible: " + locator);
        }
    }

    /**
     * Retrieves the property value of the specified element.
     *
     * @param driver   The WebDriver instance.
     * @param locator  The locator of the element.
     * @param property The property to retrieve.
     * @return The value of the property.
     * @throws IllegalArgumentException if the element is not visible.
     */
    public static String getElementProperty(WebDriver driver, By locator, String property) {
        WebElement element = findElement(driver, locator);
        if (element != null && element.isDisplayed()) {
            String propertyValue = element.getDomProperty(property);  // Use getDomProperty for JavaScript properties
            logger.info("Property '{}' retrieved from element: {} - {}", property, locator, propertyValue);
            return propertyValue;
        } else {
            logger.error("Element is not visible: {}", locator);
            throw new IllegalArgumentException("Element is not visible: " + locator);
        }
    }
}