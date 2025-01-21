package com.orangehrm.selenium.cucumber.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RetryAnalyzer class implements IRetryAnalyzer to define the retry logic for failed tests.
 * It retries a test a specified number of times before marking it as failed.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 1; // Number of retries

    /**
     * This method decides whether the test needs to be retried.
     *
     * @param result The result of the test method that was executed.
     * @return true if the test should be retried, false otherwise.
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}