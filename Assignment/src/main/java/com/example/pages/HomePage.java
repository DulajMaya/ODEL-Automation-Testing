package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    // Search icon locator
    private final By searchIcon = By.xpath("//div[contains(@class, 'icon-header-item') and contains(@class, 'js-show-modal-search') and contains(@class, 'col-4')]");

    // Search input field locator
    private final By searchInput = By.xpath("//input[@class='plh3 header_search_input ui-autocomplete-input']");

    private final By searchButton = By.xpath("//button[@class='flex-c-m trans-04']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHome() {
        driver.get("https://odel.lk/home");
    }

    public void searchProduct(String productName) {
        click(searchIcon);
        try {
            Thread.sleep(2000); // Add more delay
            WebElement input = waitForElement(searchInput);
            input.clear(); // Clear any existing text
            input.sendKeys(productName);
            input.sendKeys(Keys.ENTER);
            Thread.sleep(1000); // Wait after search
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}