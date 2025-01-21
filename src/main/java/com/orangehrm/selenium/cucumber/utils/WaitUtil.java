package com.orangehrm.selenium.cucumber.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Utility class for waiting conditions in Selenium WebDriver.
 */
public class WaitUtil {

    private WebDriverWait wait;

    /**
     * Constructor that initializes the WebDriverWait with the given driver and a default timeout.
     *
     * @param driver The WebDriver instance.
     */
    public WaitUtil(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Default wait time of 10 seconds
    }

    /**
     * Waits for an element to be visible on the page.
     *
     * @param locator The locator of the element to wait for.
     * @return The visible WebElement.
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be clickable on the page.
     *
     * @param locator The locator of the element to wait for.
     * @return The clickable WebElement.
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for an element to disappear from the page.
     *
     * @param locator The locator of the element to wait for.
     */
    public void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits for a checkbox or radio button to be selected.
     *
     * @param locator The locator of the element to wait for.
     * @return The selected WebElement.
     * @throws UnsupportedOperationException if the element is not selectable.
     */
    public WebElement waitForElementToBeSelected(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            if (element.isSelected()) {
                return element;
            } else {
                throw new UnsupportedOperationException("Element is not selected and cannot be interacted with.");
            }
        } catch (Exception e) {
            System.err.println("Error: Element is not selectable (checkbox or radio button required).");
            throw new UnsupportedOperationException("Element is not selectable", e);
        }
    }

    /**
     * Waits for an alert to be present on the page.
     */
    public void waitForAlertToBePresent() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Waits for the specified text to be present in an element.
     *
     * @param locator The locator of the element.
     * @param text    The text to wait for.
     * @return The WebElement containing the specified text.
     * @throws UnsupportedOperationException if the text is not found in the element.
     */
    public WebElement waitForTextToBePresentInElement(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            if (element.getText().equals(text)) {
                return element;
            } else {
                throw new UnsupportedOperationException("Text not found in element: " + text);
            }
        } catch (Exception e) {
            System.err.println("Error: Unable to find text in element: " + text);
            throw new UnsupportedOperationException("Element text not found", e);
        }
    }

    /**
     * Waits for the specified attribute value to be present in an element.
     *
     * @param locator   The locator of the element.
     * @param attribute The attribute to wait for.
     * @param value     The expected value of the attribute.
     * @return The WebElement with the specified attribute value.
     * @throws UnsupportedOperationException if the attribute value does not match the expected value.
     */
    public WebElement waitForAttributeToBe(By locator, String attribute, String value) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String actualAttribute = element.getDomAttribute(attribute);  // Use getDomAttribute for HTML attributes
            if (actualAttribute != null && actualAttribute.equals(value)) {
                return element;
            } else {
                throw new UnsupportedOperationException("Attribute value mismatch. Expected: " + value + " but found: " + actualAttribute);
            }
        } catch (Exception e) {
            System.err.println("Error: Attribute not found or element not present.");
            throw new UnsupportedOperationException("Element attribute not found", e);
        }
    }

    /**
     * Checks if an element is displayed on the page.
     *
     * @param locator The locator of the element.
     * @return true if the element is displayed, false otherwise.
     */
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;  // If element is not found, return false
        }
    }

    /**
     * Retrieves the text of an element.
     *
     * @param locator The locator of the element.
     * @return The text of the element, or an empty string if the element is not found.
     */
    public String getElementText(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.getText();
        } catch (Exception e) {
            return "";  // If element is not found, return empty string
        }
    }
}