package com.orangehrm.selenium.cucumber.stepdefs;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.orangehrm.selenium.cucumber.pages.LoginPage;
import com.orangehrm.selenium.cucumber.utils.DriverManager;
import com.orangehrm.selenium.cucumber.utils.TestLogger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

/**
 * Step definitions for the login and logout scenarios using valid credentials.
 */
public class ValidLoginStepDef {

    private WebDriver driver;
    private LoginPage loginPage;
    private static final TestLogger logger = new TestLogger(); // Initialize TestLogger

    /**
     * Constructor initializes the WebDriver and LoginPage instances.
     */
    public ValidLoginStepDef() {
        this.driver = DriverManager.getDriver(); // Assuming DriverManager is being used for WebDriver initialization
        this.loginPage = new LoginPage(driver);
    }

    /**
     * Enters valid credentials into the login form.
     * Logs the action and attaches the entered credentials to the Allure report.
     *
     * @param username The valid username to enter.
     * @param password The valid password to enter.
     */
    @Step("Entering valid credentials. Username: {0}, Password: {1}")
    @Given("I enter a valid user name {string} and a valid password {string}")
    @Description("Enter valid username and password into the login form.")
    @Severity(SeverityLevel.NORMAL)
    public void i_enter_valid_user_credentials(String username, String password) {
        logger.logInfo("Entering valid credentials. Username: " + username + ", Password: " + password);
        loginPage.enterUserName(username);
        loginPage.enterPassword(password);
    }

    /**
     * Verifies successful login by checking the visibility of the Dashboard page.
     * Logs the action and takes a screenshot if the assertion fails.
     */
    @Step("Verifying login success and Dashboard page visibility.")
    @Then("I should be logged in successfully and I should see the Dashboard page")
    @Description("Verify successful login by checking the Dashboard page.")
    @Severity(SeverityLevel.BLOCKER)
    public void i_should_be_logged_in_successfully() {
        logger.logInfo("Verifying login success and Dashboard page visibility.");
        try {
            Assert.assertTrue(loginPage.isDashboardPageDisplayed(), "Dashboard page not displayed after login!");
        } catch (AssertionError e) {
        	CommonStepDef.takeScreenshot(driver, "Assertion Failed: Dashboard page not displayed after login");
            throw e;
        }
    }

    /**
     * Clicks the logout button.
     * Logs the action.
     */
    @Step("Clicking on the logout button.")
    @When("I click on the logout button")
    @Description("Verify successful logout from the Dashboard page.")
    @Severity(SeverityLevel.CRITICAL)
    public void i_click_on_the_logout_button() {
        logger.logInfo("Clicking on the logout button.");
        loginPage.clickLogoutLink();
    }

    /**
     * Verifies successful navigation to the Login page after logout.
     * Logs the action and takes a screenshot if the assertion fails.
     */
    @Step("Verifying successful navigation to the Login page.")
    @Then("I should be successfully navigated to the Login page")
    @Description("Verify logout redirection to login page.")
    @Severity(SeverityLevel.NORMAL)
    public void i_should_be_navigated_to_the_login_page() {
        logger.logInfo("Verifying successful navigation to the Login page.");
        try {
            Assert.assertTrue(loginPage.checkLoginPageTitle(), "Login page not displayed after logout!");
        } catch (AssertionError e) {
        	CommonStepDef.takeScreenshot(driver, "Assertion Failed: Login page not displayed after logout");
            throw e;
        }
    }
}