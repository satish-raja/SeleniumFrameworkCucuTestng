package com.orangehrm.selenium.cucumber.stepdefs;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.orangehrm.selenium.cucumber.pages.LoginPage;
import com.orangehrm.selenium.cucumber.utils.DriverManager;
import com.orangehrm.selenium.cucumber.utils.TestLogger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

/**
 * Step definitions for the login page scenario where invalid credentials are entered.
 */
public class InvalidLoginStepDef {

    private WebDriver driver;
    private LoginPage loginPage;
    private static final TestLogger testLogger = new TestLogger(); // Initialize TestLogger

    /**
     * Constructor initializes the WebDriver and LoginPage instances.
     */
    public InvalidLoginStepDef() {
        this.driver = DriverManager.getDriver(); // Use WebDriver instance from DriverManager
        this.loginPage = new LoginPage(driver);
    }

    /**
     * Enter invalid credentials into the login form.
     * Logs the action and attaches the entered credentials to the Allure report.
     *
     * @param username The invalid username to enter.
     * @param password The invalid password to enter.
     */
    @Step("Entering invalid credentials. Username: {0}, Password: {1}")
    @Given("I enter an invalid user name {string} and an invalid password {string}")
    @Description("Enter invalid credentials into the login form.")
    @Severity(SeverityLevel.NORMAL)
    public void i_enter_invalid_user_credentials(String username, String password) {
        testLogger.logInfo("Entering invalid credentials. Username: " + username + ", Password: " + password);
        loginPage.enterUserName(username);
        loginPage.enterPassword(password);
    }

    /**
     * Verify error message on the Login page.
     * Logs the action, attaches the actual error message to the Allure report,
     * and asserts the expected error message.
     *
     * @param errorMessage The expected error message to verify.
     */
    @Step("Verifying the error message: {0}")
    @Then("I should see an error message {string} on the Login page")
    @Description("Verify error message on the Login page.")
    @Severity(SeverityLevel.NORMAL)
    public void i_should_see_error_message(String errorMessage) {
        testLogger.logInfo("Verifying the error message: " + errorMessage);
        String actualErrorMessage = loginPage.getLoginErrorMessage();
        try {
            Assert.assertEquals(actualErrorMessage, errorMessage, "Error message not displayed as expected!");
        } catch (AssertionError e) {
        	CommonStepDef.takeScreenshot(driver, "Assertion Failed: Error message not displayed as expected");
            throw e;
        }
        Allure.addAttachment("Actual Error Message", actualErrorMessage); // Attach the actual error message to Allure
    }
}