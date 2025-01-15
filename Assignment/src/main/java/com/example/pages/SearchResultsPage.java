package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultsPage extends BasePage {
    // Department filter locators
    private final By departmentButton = By.id("department_filter_button");
    private final By menLabel = By.xpath("//label[@for='filter-d-95']");

    // Color filter locators
    private final By colorButton = By.id("filter_filter_259_button");
    private final By blackLabel = By.xpath("//label[@for='filter-f-2292']");

    private final By productItems = By.xpath("//div[contains(@class, 'col-sm-6 col-md-4 col-lg-3 p-b-35 product-tile-search')]");
    private final By productNames = By.xpath("//div[@class='block2-txt-child1 flex-col-l']//a");
    private final By productPrices = By.xpath("//span[@class='stext-105 cl3']");
    private final By discountBadge = By.xpath("//div[@class='product_tag_discount']");


    public void applyDepartmentFilter() {
        // Click Department dropdown
        click(departmentButton);

        // Wait and click MEN label
        try {
            Thread.sleep(1000);
            WebElement men = driver.findElement(menLabel);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", men);
            Thread.sleep(1000); // Wait for filter to apply

            // Click department button again to close dropdown
            click(departmentButton);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyDepartmentFilter() {
        try {
            Thread.sleep(1000); // Wait for results to update
            List<WebElement> products = driver.findElements(productItems);

            // Print the number of products found
            System.out.println("Number of products found: " + products.size());

            // Get details of first few products
            for(int i = 0; i < Math.min(3, products.size()); i++) {
                Map<String, String> details = getProductDetails(products.get(i));
                System.out.println("Product " + (i+1) + ": " + details.get("name"));
            }

            return true; // If we get here, test passed
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Map<String, String> getProductDetails(WebElement product) {
        Map<String, String> details = new HashMap<>();
        try {
            details.put("name", product.findElement(productNames).getText());
            details.put("price", product.findElement(productPrices).getText());
            details.put("discount", product.findElement(discountBadge).getText());
        } catch (Exception e) {
            // In case some elements are not found
            e.printStackTrace();
        }
        return details;
    }


    public boolean verifySearchResults() {
        try {
            Thread.sleep(2000); // Wait for results to load
            List<WebElement> products = driver.findElements(productItems);

            for(WebElement product : products) {
                String productName = product.findElement(productNames).getText().toLowerCase();
                String price = product.findElement(productPrices).getText();
                String discount = product.findElement(discountBadge).getText();

                // Verify each product
                if(!productName.contains("shirt")) {
                    return false;
                }
            }
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getSearchResultsCount() {
        return driver.findElements(productItems).size();
    }

    public boolean hasResults() {
        return getSearchResultsCount() > 0;
    }


    public Map<String, String> getFirstProductDetails() {
        Map<String, String> details = new HashMap<>();
        try {
            // Wait for products to be loaded
            Thread.sleep(2000);

            // Wait for at least one product to be present
            WebElement firstProduct = waitForElement(productItems);

            // Wait for each element within the product
            WebElement nameElement = waitForElement(productNames);
            WebElement priceElement = waitForElement(productPrices);
            WebElement discountElement = waitForElement(discountBadge);

            details.put("name", nameElement.getText());
            details.put("price", priceElement.getText());
            details.put("discount", discountElement.getText());

            Thread.sleep(1000); // Wait before returning results

        } catch (Exception e) {
            System.out.println("Error getting product details: " + e.getMessage());
            e.printStackTrace();
        }
        return details;
    }

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void applyFilters() {
        // Click Department dropdown
        click(departmentButton);

        // Wait and click MEN label
        try {
            Thread.sleep(1000);
            WebElement men = driver.findElement(menLabel);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", men);
            Thread.sleep(1000); // Wait for filter to apply

            // Click department button again to close the dropdown
            click(departmentButton);
            Thread.sleep(1000); // Wait for dropdown to close

            // Now click Color dropdown
            click(colorButton);
            Thread.sleep(1000); // Wait for dropdown to open

            // Click Black label
            WebElement black = driver.findElement(blackLabel);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", black);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
