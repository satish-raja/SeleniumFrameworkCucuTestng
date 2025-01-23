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
 * Step definitions for the login page scenario where the username and password fields are left empty.
 */
public class EmptyLoginStepDef {

    private WebDriver driver;
    private LoginPage loginPage;
    private static final TestLogger testLogger = new TestLogger(); // Initialize TestLogger

    /**
     * Constructor initializes the WebDriver and LoginPage instances.
     */
    public EmptyLoginStepDef() {
        this.driver = DriverManager.getDriver(); // Use WebDriver instance from DriverManager
        this.loginPage = new LoginPage(driver);
    }

    /**
     * Leaves the username and password fields empty.
     * Logs the action and attaches the empty values to the Allure report.
     */
    @Step("Leaving the username and password fields empty")
    @Given("I leave the username and password fields empty")
    @Description("Enter username and password with BLANK into the login form.")
    @Severity(SeverityLevel.NORMAL)
    public void i_leave_fields_empty() {
        testLogger.logInfo("Leaving the username and password fields empty.");
        loginPage.enterUserName("");
        loginPage.enterPassword("");
        Allure.addAttachment("Username", ""); // Attach empty username for the Allure report
        Allure.addAttachment("Password", ""); // Attach empty password for the Allure report
    }

    /**
     * Verifies error messages for blank username and password fields.
     * Logs the action, attaches error messages to the Allure report, and asserts the expected error messages.
     */
    @Step("Verifying error messages for blank username and password fields")
    @Then("I should see error messages for blank username and password fields")
    @Description("Verify error messages under username and password fields in the login form.")
    @Severity(SeverityLevel.NORMAL)
    public void i_should_see_error_messages() {
        testLogger.logInfo("Verifying error messages for blank username and password fields.");

        String usernameErrorMessage = loginPage.getUsernameFieldErrorMessage();
        String passwordErrorMessage = loginPage.getPasswordFieldErrorMessage();
        
        // Attach error messages to Allure report for better traceability
        Allure.addAttachment("Username Error Message", usernameErrorMessage);
        Allure.addAttachment("Password Error Message", passwordErrorMessage);

        try {
            Assert.assertEquals(usernameErrorMessage, "Required", "Error message for Username field does not match!");
            Assert.assertEquals(passwordErrorMessage, "Required", "Error message for Password field does not match!");
        } catch (AssertionError e) {
        	CommonStepDef.takeScreenshot(driver, "Assertion Failed: Error message not displayed as expected");
            throw e;
        }
    }
}
