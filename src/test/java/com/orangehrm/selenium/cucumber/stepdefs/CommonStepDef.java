package com.orangehrm.selenium.cucumber.stepdefs;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.orangehrm.selenium.cucumber.pages.LoginPage;
import com.orangehrm.selenium.cucumber.utils.DriverManager;
import com.orangehrm.selenium.cucumber.utils.TestLogger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

/**
 * CommonStepDef class contains common step definitions for Cucumber tests.
 * It includes steps for navigating to the login page and clicking the login button.
 */
public class CommonStepDef {

    private WebDriver driver;
    private LoginPage loginPage;
    private static final TestLogger testLogger = new TestLogger(); // Initialize TestLogger
    
    /**
     * Constructor initializes the WebDriver and LoginPage instances.
     */
    public CommonStepDef() {
        this.driver = DriverManager.getDriver(); // Use WebDriver instance from DriverManager
        this.loginPage = new LoginPage(driver);
    }

    /**
     * Navigates to the OrangeHRM login page and verifies that it is loaded correctly.
     */
    @Step("Navigating to the OrangeHRM login page")
    @Given("I am on the login page")
    @Description("Navigate to the OrangeHRM login page and verify it's loaded correctly.")
    @Severity(SeverityLevel.BLOCKER)
    public void i_am_on_the_login_page() {
        loginPage = new LoginPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        testLogger.logInfo("Navigating to the OrangeHRM login page."); // Log the navigation action
        Allure.addAttachment("Login Page URL", "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"); // Attach URL to the Allure report
        boolean isLoginPageLoaded = loginPage.checkLoginPageTitle();

        if (isLoginPageLoaded) {
            testLogger.logInfo("Login page loaded successfully."); // Log successful page load
            Allure.addAttachment("Login Page Status", "Successfully loaded"); // Attach page load status to the report
        } else {
            testLogger.logError("Login page failed to load."); // Log error if page fails to load
            Allure.addAttachment("Login Page Status", "Failed to load"); // Attach error status to the report
        }

        try {
            Assert.assertTrue(isLoginPageLoaded, "Login page not loaded properly!");
        } catch (AssertionError e) {
            takeScreenshot(driver, "Assertion Failed: Login page not loaded properly");
            throw e;
        }
    }

    /**
     * Clicks the login button to submit credentials.
     */
    @Step("Clicking the login button")
    @When("I click on the login button")
    @Description("Click the login button to submit credentials.")
    @Severity(SeverityLevel.CRITICAL)
    public void i_click_on_the_login_button() {
        loginPage.clickLoginButton();
        testLogger.logInfo("Login button clicked."); // Log the action of clicking the login button
        Allure.addAttachment("Login Button Action", "Clicked on the login button"); // Attach the action to Allure report
    }

    /**
     * Takes a screenshot and attaches it to the Allure report.
     *
     * @param driver      The WebDriver instance.
     * @param description The description of the screenshot.
     */
    public static void takeScreenshot(WebDriver driver, String description) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(description, new ByteArrayInputStream(screenshot));
    }    
}