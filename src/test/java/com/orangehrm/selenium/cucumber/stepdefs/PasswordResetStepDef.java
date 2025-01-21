package com.orangehrm.selenium.cucumber.stepdefs;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.orangehrm.selenium.cucumber.pages.LoginPage;
import com.orangehrm.selenium.cucumber.utils.DriverManager;
import com.orangehrm.selenium.cucumber.utils.TestLogger;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

/**
 * Step definitions for the password reset scenario.
 */
public class PasswordResetStepDef {

    private WebDriver driver;
    private LoginPage loginPage;
    private static final TestLogger testLogger = new TestLogger(); // Initialize TestLogger

    /**
     * Constructor initializes the WebDriver and LoginPage instances.
     */
    public PasswordResetStepDef() {
        this.driver = DriverManager.getDriver(); // Use WebDriver instance from DriverManager
        this.loginPage = new LoginPage(driver);
    }

    /**
     * Clicks on the specified link (e.g., Forgot Password link) on the login page.
     * Logs the action and attaches the link text to the Allure report.
     *
     * @param linkText The text of the link to click.
     */
    @Step("Clicking on the Forgot Password link: {0}")
    @When("I click on the {string} link")
    @Description("Click on a Forgot Password link on the login page.")
    @Severity(SeverityLevel.NORMAL)
    public void i_click_on_the_link(String linkText) {
        testLogger.logInfo("Clicking on the link: " + linkText);
        loginPage.clickForgotPasswordLink();
    }

    /**
     * Verifies that the user is redirected to the Reset Password page.
     * Logs the action and takes a screenshot if the assertion fails.
     */
    @Step("Verifying redirection to the Reset Password page.")
    @Then("I should be redirected to the Reset Password page")
    @Description("Verify navigation to the reset password page.")
    @Severity(SeverityLevel.CRITICAL)
    public void i_should_be_redirected_to_the_reset_password_page() {
        testLogger.logInfo("Verifying that I am redirected to the Reset Password page.");
        try {
            Assert.assertTrue(loginPage.checkResetPasswordPageTitle(), "Reset Password page not found!");
        } catch (AssertionError e) {
        	CommonStepDef.takeScreenshot(driver, "Assertion Failed: Reset Password page not found");
            throw e;
        }
    }

    /**
     * Enters a valid username for password reset.
     * Logs the action and attaches the entered username to the Allure report.
     *
     * @param username The valid username to enter.
     */
    @Step("Entering a valid username: {0}")
    @And("I enter a valid username {string}")
    @Description("Enter a valid username for password reset.")
    @Severity(SeverityLevel.NORMAL)
    public void i_enter_a_valid_username(String username) {
        testLogger.logInfo("Entering a valid username: " + username);
        loginPage.enterUserName(username);
    }

    /**
     * Clicks the Reset Password button.
     * Logs the action.
     */
    @Step("Clicking on the Reset Password button.")
    @When("I click on the Reset Password button")
    @Description("Click the reset password button.")
    @Severity(SeverityLevel.CRITICAL)
    public void i_click_on_the_reset_password_button() {
        testLogger.logInfo("Clicking on the Reset Password button.");
        loginPage.clickLoginButton();
    }

    /**
     * Verifies the success message and URL after clicking the Reset Password button.
     * Logs the action and takes a screenshot if the assertion fails.
     *
     * @param expectedMessage The expected success message to verify.
     */
    @Step("Verifying success message and URL after clicking the Reset Password button.")
    @Then("I should see a message {string}")
    @Description("Verify success message after password reset.")
    @Severity(SeverityLevel.MINOR)
    public void i_should_see_a_message(String expectedMessage) {
        testLogger.logInfo("Verifying the success message and URL after clicking the Reset Password button.");
        String currentUrl = driver.getCurrentUrl();
        try {
            Assert.assertEquals(currentUrl, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/sendPasswordReset", "Navigated to wrong page");
            Assert.assertTrue(loginPage.checkResetPasswordSuccessfullPageTitle(), "Reset Password page not found!");
        } catch (AssertionError e) {
        	CommonStepDef.takeScreenshot(driver, "Assertion Failed: Reset Password page not found or Navigated to wrong page");
            throw e;
        }
        Allure.addAttachment("Actual Error Message", expectedMessage); // Attach the actual error message to Allure
    }
}