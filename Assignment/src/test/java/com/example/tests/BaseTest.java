package com.example.tests;

import com.example.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;
    private static final boolean IS_JENKINS = System.getenv("JENKINS_HOME") != null;
    private static final boolean HEADLESS_MODE = IS_JENKINS;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        if (IS_JENKINS && !browser.equalsIgnoreCase("chrome")) {
            throw new SkipException("Skipping test for " + browser + " browser in Jenkins environment");
        }

        System.out.println("Running in " + (HEADLESS_MODE ? "headless" : "normal") + " mode");
        System.out.println("Browser: " + browser);

        driver = DriverManager.getDriver(browser, HEADLESS_MODE);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}