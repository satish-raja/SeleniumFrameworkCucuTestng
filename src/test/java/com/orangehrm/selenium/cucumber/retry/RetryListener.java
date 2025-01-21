package com.orangehrm.selenium.cucumber.retry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * RetryListener class implements IAnnotationTransformer to modify test annotations at runtime.
 * It assigns the RetryAnalyzer to each test method.
 */
public class RetryListener implements IAnnotationTransformer {

    /**
     * This method is invoked by TestNG to modify a test annotation at runtime.
     *
     * @param annotation The test annotation.
     * @param testClass The test class (can be null).
     * @param testConstructor The test constructor (can be null).
     * @param testMethod The test method (can be null).
     */
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}