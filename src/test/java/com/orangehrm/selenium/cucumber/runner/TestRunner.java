package com.orangehrm.selenium.cucumber.runner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.orangehrm.selenium.cucumber.utils.TestLogger;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

/**
 * TestRunner class for executing Cucumber tests with TestNG.
 * It also includes a method to clear previous Allure results before the test suite runs.
 */
@Test
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.orangehrm.selenium.cucumber.stepdefs", "com.orangehrm.selenium.cucumber.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        publish = true,
        dryRun = false
)
@Listeners(com.orangehrm.selenium.cucumber.retry.RetryListener.class)
public class TestRunner extends AbstractTestNGCucumberTests {
    private static final TestLogger testLogger = new TestLogger();

    /**
     * Clears previous Allure results before starting the test suite.
     */
    @BeforeSuite
    @Description("Clear previous Allure results before starting the suite.")
    @Severity(SeverityLevel.MINOR)
    public void cleareAllureResults() {
        try {
			FileUtils.deleteDirectory(new File("allure-results"));
		} catch (IOException e) {
//			e.printStackTrace();
		}
        testLogger.logInfo("Deleted directory allure-results");
    }

    /**
     * Provides scenarios for Cucumber tests to run in parallel.
     *
     * @return An array of scenarios.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}