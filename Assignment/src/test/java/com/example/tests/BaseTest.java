package com.example.tests;

import com.example.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;
    private static final boolean HEADLESS_MODE = false;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
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