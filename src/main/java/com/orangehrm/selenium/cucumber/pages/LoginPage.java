package com.orangehrm.selenium.cucumber.pages;

import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.orangehrm.selenium.cucumber.utils.LogUtil;
import com.orangehrm.selenium.cucumber.utils.WaitUtil;

public class LoginPage {

    private WebDriver driver;
    private WaitUtil waitUtil; // Util for waiting actions

    // By Locators
    private final By usernameInputLocator = By.name("username");
    private final By passwordInputLocator = By.name("password");
    private final By loginButtonLocator = By.xpath("//button[@type='submit']");
    private final By forgotPasswordLinkLocator = By.xpath("//p[@class='oxd-text oxd-text--p orangehrm-login-forgot-header']");
    private final By logoutLinkLocator = By.xpath("//a[normalize-space()='Logout']");
    private final By resetPasswordTitleLocator = By.xpath("//h6[@class='oxd-text oxd-text--h6 orangehrm-forgot-password-title']");
    private final By resetPasswordSuccessfullPageTitleLocator = By.xpath("//h6[@class='oxd-text oxd-text--h6 orangehrm-forgot-password-title']");
    private final By loginErrorMessage = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");
    private final By dashboardTitleLocator = By.xpath("//h6[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module']");
    private final By loginPageTitleLocator = By.xpath("//h5[@class='oxd-text oxd-text--h5 orangehrm-login-title']");
    private final By userDropDownLocator = By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']");
    private final By usernameErrorLocator = By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]/following-sibling::span[contains(@class,'oxd-input-field-error-message')]");
    private final By passwordErrorLocator = By.xpath("//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]/following-sibling::span[contains(@class,'oxd-input-field-error-message')]");

    // Constructor that accepts WebDriver
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        if (this.driver == null) {
            throw new NullPointerException("WebDriver instance is not initialized. Please set it using DriverManager.");
        }
        this.waitUtil = new WaitUtil(driver); // Initialize the wait utility with the driver
    }

    // Page Methods // Actions
    public void enterUserName(String username) {
        performAction(usernameInputLocator, element -> element.sendKeys(username));
        LogUtil.log("Entered username: " + username, LogUtil.LogLevel.INFO);
    }

    public void enterPassword(String password) {
        performAction(passwordInputLocator, element -> element.sendKeys(password));
        LogUtil.log("Entered password: " + password, LogUtil.LogLevel.INFO);
    }

    public void clickLoginButton() {
        performAction(loginButtonLocator, WebElement::click);
        LogUtil.log("Clicked login button", LogUtil.LogLevel.INFO);
    }

    public boolean checkForgotPasswordLink() {
        return waitUtil.isElementDisplayed(forgotPasswordLinkLocator);
    }

    public void clickForgotPasswordLink() {
        performAction(forgotPasswordLinkLocator, WebElement::click);
        LogUtil.log("Clicked forgot password link", LogUtil.LogLevel.INFO);
    }

    public boolean checkLogoutLink() {
        return waitUtil.isElementDisplayed(logoutLinkLocator);
    }

    public void clickLogoutLink() {
        performAction(userDropDownLocator, WebElement::click);
        performAction(logoutLinkLocator, WebElement::click);
        LogUtil.log("Clicked logout link", LogUtil.LogLevel.INFO);
    }

    public void login(String username, String password) {
        enterUserName(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getForgotPasswordPageUrl() {
        return driver.getCurrentUrl();
    }

    public boolean checkResetPasswordPageTitle() {
        return waitUtil.isElementDisplayed(resetPasswordTitleLocator);
    }

    public void resetPassword(String username) {
        clickForgotPasswordLink();
        enterUserName(username);
        clickLoginButton();
    }

    public String checkResetPasswordPageUrl() {
        return driver.getCurrentUrl();
    }

    public boolean checkResetPasswordSuccessfullPageTitle() {
        return waitUtil.isElementDisplayed(resetPasswordSuccessfullPageTitleLocator);
    }

    public boolean checkLoginErrorMessage() {
        return waitUtil.isElementDisplayed(loginErrorMessage);
    }

    public String getLoginErrorMessage() {
        return waitUtil.getElementText(loginErrorMessage);
    }

    public boolean checkDashboardTitle() {
        return waitUtil.isElementDisplayed(dashboardTitleLocator);
    }

    public boolean checkLoginPageTitle() {
        return waitUtil.isElementDisplayed(loginPageTitleLocator);
    }

    public void clearPassword() {
        performAction(passwordInputLocator, WebElement::clear);
        LogUtil.log("Cleared password field", LogUtil.LogLevel.INFO);
    }

    public String getUsernameFieldErrorMessage() {
        return waitUtil.getElementText(usernameErrorLocator);
    }

    public String getPasswordFieldErrorMessage() {
        return waitUtil.getElementText(passwordErrorLocator);
    }

    public boolean isDashboardPageDisplayed() {
        return waitUtil.isElementDisplayed(dashboardTitleLocator);
    }

    private void performAction(By locator, Consumer<WebElement> action) {
        WebElement element = waitUtil.waitForElementToBeVisible(locator);
        action.accept(element);
    }

    // New methods to clear the username and password fields
    public void clearUserName() {
        performAction(usernameInputLocator, WebElement::clear);
        LogUtil.log("Cleared username field", LogUtil.LogLevel.INFO);
    }
}